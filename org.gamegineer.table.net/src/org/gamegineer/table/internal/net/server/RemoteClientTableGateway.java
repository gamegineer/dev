/*
 * RemoteClientTableGateway.java
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
 * Created on Apr 10, 2011 at 5:34:50 PM.
 */

package org.gamegineer.table.internal.net.server;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.Arrays;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway;
import org.gamegineer.table.internal.net.common.Authenticator;
import org.gamegineer.table.internal.net.common.ProtocolVersions;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.transport.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.transport.messages.EndAuthenticationMessage;
import org.gamegineer.table.internal.net.transport.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.transport.messages.HelloResponseMessage;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A gateway to a remote client table.
 * 
 * <p>
 * This gateway provides a network service that represents the server half of
 * the network table protocol.
 * </p>
 */
@ThreadSafe
final class RemoteClientTableGateway
    extends AbstractRemoteTableGateway
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
     * The name of the remote player or {@code null} if the client has not yet
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
     * Initializes a new instance of the {@code RemoteClientTableGateway} class.
     * 
     * @param context
     *        The table gateway context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    RemoteClientTableGateway(
        /* @NonNull */
        final ITableGatewayContext context )
    {
        super( context );

        challenge_ = null;
        playerName_ = null;
        salt_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * @throws java.lang.IllegalStateException
     *         If the client has not yet been authenticated.
     * 
     * @see org.gamegineer.table.internal.net.ITableGateway#getPlayerName()
     */
    @Override
    public String getPlayerName()
    {
        synchronized( getLock() )
        {
            assertStateLegal( playerName_ != null, Messages.RemoteClientTableGateway_playerNotAuthenticated );
            return playerName_;
        }
    }

    /**
     * Handles a Begin Authentication Response message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    @SuppressWarnings( "boxing" )
    private void handleBeginAuthenticationResponseMessage(
        /* @NonNull */
        final BeginAuthenticationResponseMessage message )
    {
        assert message != null;
        assert Thread.holdsLock( getLock() );

        final EndAuthenticationMessage endAuthenticationMessage = new EndAuthenticationMessage();
        endAuthenticationMessage.setCorrelationId( message.getId() );

        final SecureString password = getTableGatewayContext().getPassword();
        try
        {
            final Authenticator authenticator = new Authenticator();
            final byte[] expectedResponse = authenticator.createResponse( challenge_, password, salt_ );
            if( Arrays.equals( expectedResponse, message.getResponse() ) )
            {
                System.out.println( String.format( "ServerService : client authenticated (id=%d, correlation-id=%d)", message.getId(), message.getCorrelationId() ) ); //$NON-NLS-1$
                playerName_ = message.getPlayerName();
                try
                {
                    getTableGatewayContext().addTableGateway( this );
                }
                catch( final NetworkTableException e )
                {
                    System.out.println( "ServerService: a player with the same name is already registered" ); //$NON-NLS-1$
                    playerName_ = null;
                    throw e;
                }
            }
            else
            {
                System.out.println( String.format( "ServerService : client failed authentication (id=%d, correlation-id=%d)", message.getId(), message.getCorrelationId() ) ); //$NON-NLS-1$
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

        if( sendMessage( endAuthenticationMessage ) )
        {
            if( endAuthenticationMessage.getException() != null )
            {
                stop();
            }
        }
        else
        {
            stop();
        }
    }

    /**
     * Handles a Hello Request message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    @SuppressWarnings( "boxing" )
    private void handleHelloRequestMessage(
        /* @NonNull */
        final HelloRequestMessage message )
    {
        assert message != null;
        assert Thread.holdsLock( getLock() );

        System.out.println( String.format( "ServerService : received hello request (id=%d, correlation-id=%d) with supported version '%d'", message.getId(), message.getCorrelationId(), message.getSupportedProtocolVersion() ) ); //$NON-NLS-1$

        final HelloResponseMessage response = new HelloResponseMessage();
        response.setCorrelationId( message.getId() );
        if( message.getSupportedProtocolVersion() >= ProtocolVersions.VERSION_1 )
        {
            response.setChosenProtocolVersion( ProtocolVersions.VERSION_1 );
        }
        else
        {
            response.setException( new NetworkTableException( "unsupported protocol version" ) ); //$NON-NLS-1$
        }

        if( !sendMessage( response ) )
        {
            stop();
            return;
        }

        if( response.getException() == null )
        {
            try
            {
                final Authenticator authenticator = new Authenticator();
                final BeginAuthenticationRequestMessage beginAuthenticationRequest = new BeginAuthenticationRequestMessage();
                challenge_ = authenticator.createChallenge();
                beginAuthenticationRequest.setChallenge( challenge_ );
                salt_ = authenticator.createSalt();
                beginAuthenticationRequest.setSalt( salt_ );

                if( !sendMessage( beginAuthenticationRequest ) )
                {
                    stop();
                }
            }
            catch( final NetworkTableException e )
            {
                System.out.println( "ServerService : failed to generate begin authentication request with exception: " + e ); //$NON-NLS-1$
                stop();
            }
        }
        else
        {
            System.out.println( String.format( "ServerService : received hello request (id=%d, correlation-id=%d) but the requested version is unsupported", message.getId(), message.getCorrelationId() ) ); //$NON-NLS-1$
            stop();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway#messageReceivedInternal(org.gamegineer.table.internal.net.transport.IMessage)
     */
    @Override
    protected boolean messageReceivedInternal(
        final IMessage message )
    {
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        if( message instanceof HelloRequestMessage )
        {
            handleHelloRequestMessage( (HelloRequestMessage)message );
            return true;
        }
        else if( message instanceof BeginAuthenticationResponseMessage )
        {
            handleBeginAuthenticationResponseMessage( (BeginAuthenticationResponseMessage)message );
            return true;
        }

        return false;
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway#peerStoppedInternal()
     */
    @Override
    protected void peerStoppedInternal()
    {
        assert Thread.holdsLock( getLock() );

        if( playerName_ != null )
        {
            getTableGatewayContext().removeTableGateway( this );
            playerName_ = null;
        }
    }

    /**
     * Sets the name of the remote player.
     * 
     * <p>
     * This method is only intended to support testing.
     * </p>
     * 
     * @param playerName
     *        The name of the remote player or {@code null} if the client has
     *        not yet been authenticated.
     */
    void setPlayerName(
        /* @Nullable */
        final String playerName )
    {
        synchronized( getLock() )
        {
            playerName_ = playerName;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway#stoppedInternal()
     */
    @Override
    protected void stoppedInternal()
    {
        assert Thread.holdsLock( getLock() );

        if( playerName_ != null )
        {
            getTableGatewayContext().removeTableGateway( this );
            playerName_ = null;
        }
    }
}
