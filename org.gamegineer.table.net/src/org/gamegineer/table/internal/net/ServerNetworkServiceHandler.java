/*
 * ServerNetworkServiceHandler.java
 * Copyright 2008-2011 Gamegineer.org
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Created on Mar 25, 2011 at 11:28:55 PM.
 */

package org.gamegineer.table.internal.net;

import java.util.Arrays;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.INetworkServiceContext;
import org.gamegineer.table.internal.net.transport.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.transport.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.transport.messages.EndAuthenticationMessage;
import org.gamegineer.table.internal.net.transport.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.transport.messages.HelloResponseMessage;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A service handler that represents the server half of the network table
 * protocol.
 */
@ThreadSafe
final class ServerNetworkServiceHandler
    extends AbstractNetworkServiceHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The most-recent challenge used to authenticate the client or {@code null}
     * if an authentication request has not yet been sent.
     */
    @GuardedBy( "getLock()" )
    private byte[] challenge_;

    /**
     * The name of the remote player or {@code null} if the player has not yet
     * been authenticated.
     */
    @GuardedBy( "getLock()" )
    private String playerName_;

    /**
     * The most-recent salt used to authenticate the client or {@code null} if
     * an authentication request has not yet been sent.
     */
    @GuardedBy( "getLock()" )
    private byte[] salt_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServerNetworkServiceHandler}
     * class.
     * 
     * @param networkTable
     *        The network table associated with the service handler; must not be
     *        {@code null}.
     */
    ServerNetworkServiceHandler(
        /* @NonNull */
        final NetworkTable networkTable )
    {
        super( networkTable );

        challenge_ = null;
        playerName_ = null;
        salt_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a Begin Authentication Response message.
     * 
     * @param context
     *        The network service context; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    @SuppressWarnings( "boxing" )
    private void handleBeginAuthenticationResponseMessage(
        /* @NonNull */
        final INetworkServiceContext context,
        /* @NonNull */
        final BeginAuthenticationResponseMessage message )
    {
        assert context != null;
        assert message != null;
        assert Thread.holdsLock( getLock() );

        final EndAuthenticationMessage endAuthenticationMessage = new EndAuthenticationMessage();
        endAuthenticationMessage.setTag( IMessage.NO_TAG );

        final SecureString password = getNetworkTable().getPassword();
        try
        {
            final Authenticator authenticator = new Authenticator();
            final byte[] expectedResponse = authenticator.createResponse( challenge_, password, salt_ );
            if( Arrays.equals( expectedResponse, message.getResponse() ) )
            {
                System.out.println( String.format( "ServerNetworkServiceHandler : client authenticated (tag=%d)", message.getTag() ) ); //$NON-NLS-1$
                getNetworkTable().playerConnected( message.getPlayerName(), this );
                playerName_ = message.getPlayerName();
            }
            else
            {
                System.out.println( String.format( "ServerNetworkServiceHandler : client failed authentication (tag=%d)", message.getTag() ) ); //$NON-NLS-1$
                throw new NetworkTableException( "failed authentication" ); //$NON-NLS-1$
            }
        }
        catch( final NetworkTableException e )
        {
            endAuthenticationMessage.setException( e );
        }
        finally
        {
            password.dispose();
        }

        if( context.sendMessage( endAuthenticationMessage ) )
        {
            if( endAuthenticationMessage.getException() != null )
            {
                context.stopService();
            }
        }
        else
        {
            context.stopService();
        }
    }

    /**
     * Handles a Hello Request message.
     * 
     * @param context
     *        The network service context; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    @SuppressWarnings( "boxing" )
    private void handleHelloRequestMessage(
        /* @NonNull */
        final INetworkServiceContext context,
        /* @NonNull */
        final HelloRequestMessage message )
    {
        assert context != null;
        assert message != null;
        assert Thread.holdsLock( getLock() );

        System.out.println( String.format( "ServerNetworkServiceHandler : received hello request (tag=%d) with supported version '%d'", message.getTag(), message.getSupportedProtocolVersion() ) ); //$NON-NLS-1$

        final HelloResponseMessage response = new HelloResponseMessage();
        response.setTag( message.getTag() );
        if( message.getSupportedProtocolVersion() >= ProtocolVersions.VERSION_1 )
        {
            response.setChosenProtocolVersion( ProtocolVersions.VERSION_1 );
        }
        else
        {
            response.setException( new NetworkTableException( "unsupported protocol version" ) ); //$NON-NLS-1$
        }

        if( !context.sendMessage( response ) )
        {
            context.stopService();
            return;
        }

        if( response.getException() == null )
        {
            try
            {
                final Authenticator authenticator = new Authenticator();
                final BeginAuthenticationRequestMessage beginAuthenticationRequest = new BeginAuthenticationRequestMessage();
                beginAuthenticationRequest.setTag( getNextMessageTag() );
                challenge_ = authenticator.createChallenge();
                beginAuthenticationRequest.setChallenge( challenge_ );
                salt_ = authenticator.createSalt();
                beginAuthenticationRequest.setSalt( salt_ );

                if( !context.sendMessage( beginAuthenticationRequest ) )
                {
                    context.stopService();
                }
            }
            catch( final NetworkTableException e )
            {
                System.out.println( "ServerNetworkServiceHandler : failed to generate begin authentication request with exception: " + e ); //$NON-NLS-1$
                context.stopService();
            }
        }
        else
        {
            System.out.println( String.format( "ServerNetworkServiceHandler : received hello request (tag=%d) but the requested version is unsupported", message.getTag() ) ); //$NON-NLS-1$
            context.stopService();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkServiceHandler#messageReceivedInternal(org.gamegineer.table.internal.net.transport.INetworkServiceContext, org.gamegineer.table.internal.net.transport.IMessage)
     */
    @Override
    boolean messageReceivedInternal(
        final INetworkServiceContext context,
        final IMessage message )
    {
        assert context != null;
        assert message != null;
        assert Thread.holdsLock( getLock() );

        if( message instanceof HelloRequestMessage )
        {
            handleHelloRequestMessage( context, (HelloRequestMessage)message );
            return true;
        }
        else if( message instanceof BeginAuthenticationResponseMessage )
        {
            handleBeginAuthenticationResponseMessage( context, (BeginAuthenticationResponseMessage)message );
            return true;
        }

        return false;
    }

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkServiceHandler#peerStoppedInternal(org.gamegineer.table.internal.net.transport.INetworkServiceContext)
     */
    @Override
    void peerStoppedInternal(
        final INetworkServiceContext context )
    {
        assert context != null;
        assert Thread.holdsLock( getLock() );

        if( playerName_ != null )
        {
            getNetworkTable().playerDisconnected( playerName_ );
            playerName_ = null;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkServiceHandler#stoppedInternal(org.gamegineer.table.internal.net.transport.INetworkServiceContext)
     */
    @Override
    void stoppedInternal(
        final INetworkServiceContext context )
    {
        assert context != null;
        assert Thread.holdsLock( getLock() );

        if( playerName_ != null )
        {
            getNetworkTable().playerDisconnected( playerName_ );
            playerName_ = null;
        }
    }
}
