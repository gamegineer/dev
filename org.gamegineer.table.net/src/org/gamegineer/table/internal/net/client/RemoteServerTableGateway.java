/*
 * RemoteServerTableGateway.java
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
 * Created on Apr 10, 2011 at 5:34:39 PM.
 */

package org.gamegineer.table.internal.net.client;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway;
import org.gamegineer.table.internal.net.common.Authenticator;
import org.gamegineer.table.internal.net.common.ProtocolVersions;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.gamegineer.table.internal.net.transport.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.transport.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.transport.messages.EndAuthenticationMessage;
import org.gamegineer.table.internal.net.transport.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.transport.messages.HelloResponseMessage;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A gateway to a remote server table.
 * 
 * <p>
 * This gateway provides a network service that represents the client half of
 * the network table protocol.
 * </p>
 */
@ThreadSafe
final class RemoteServerTableGateway
    extends AbstractRemoteTableGateway
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the remote server or {@code null} if the client has not yet
     * been authenticated.
     */
    @GuardedBy( "getLock()" )
    private String playerName_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RemoteServerTableGateway} class.
     * 
     * @param context
     *        The table gateway context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    RemoteServerTableGateway(
        /* @NonNull */
        final ITableGatewayContext context )
    {
        super( context );

        playerName_ = null;
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
            assertStateLegal( playerName_ != null, Messages.RemoteServerTableGateway_playerNotAuthenticated );
            return playerName_;
        }
    }

    /**
     * Handles a Begin Authentication Request message.
     * 
     * @param context
     *        The service context; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    private void handleBeginAuthenticationRequestMessage(
        /* @NonNull */
        final IServiceContext context,
        /* @NonNull */
        final BeginAuthenticationRequestMessage message )
    {
        assert context != null;
        assert message != null;
        assert Thread.holdsLock( getLock() );

        final BeginAuthenticationResponseMessage response = new BeginAuthenticationResponseMessage();
        response.setId( getNextMessageId() ); // TODO: move to sendMessage
        response.setCorrelationId( message.getId() );
        response.setPlayerName( getTableGatewayContext().getLocalPlayerName() );

        final SecureString password = getTableGatewayContext().getPassword();
        try
        {
            final Authenticator authenticator = new Authenticator();
            final byte[] authResponse = authenticator.createResponse( message.getChallenge(), password, message.getSalt() );
            response.setResponse( authResponse );

            if( !context.sendMessage( response ) )
            {
                context.stopService();
            }
        }
        catch( final NetworkTableException e )
        {
            // TODO: in this case, and probably elsewhere, need to communicate the error
            // to the network table so it can eventually be reported to the local user via the UI
            // --> may not serialize exceptions in messages, but rather use an enum to force
            // specification of error.  actual exception should be logged locally.
            System.out.println( "ClientService : failed to generate authentication response with exception: " + e ); //$NON-NLS-1$
            context.stopService();
        }
        finally
        {
            password.dispose();
        }
    }

    /**
     * Handles an End Authentication message.
     * 
     * @param context
     *        The service context; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    @SuppressWarnings( "boxing" )
    private void handleEndAuthenticationMessage(
        /* @NonNull */
        final IServiceContext context,
        /* @NonNull */
        final EndAuthenticationMessage message )
    {
        assert context != null;
        assert message != null;
        assert Thread.holdsLock( getLock() );

        if( message.getException() != null )
        {
            System.out.println( String.format( "ClientService : failed authentication (id=%d, correlation-id=%d) with exception: ", message.getId(), message.getCorrelationId() ) + message.getException() ); //$NON-NLS-1$
            context.stopService();
        }
        else
        {
            System.out.println( String.format( "ClientService : completed authentication successfully (id=%d, correlation-id=%d): ", message.getId(), message.getCorrelationId() ) ); //$NON-NLS-1$

            try
            {
                playerName_ = "<<server>>"; //$NON-NLS-1$
                getTableGatewayContext().addTableGateway( this );
            }
            catch( final NetworkTableException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.RemoteServerTableGateway_serverTableGatewayNotRegistered, e );
                playerName_ = null;
                context.stopService();
            }
        }
    }

    /**
     * Handles a Hello Response message.
     * 
     * @param context
     *        The service context; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    @SuppressWarnings( "boxing" )
    private void handleHelloResponseMessage(
        /* @NonNull */
        final IServiceContext context,
        /* @NonNull */
        final HelloResponseMessage message )
    {
        assert context != null;
        assert message != null;
        assert Thread.holdsLock( getLock() );

        if( message.getException() != null )
        {
            System.out.println( String.format( "ClientService : received hello response (id=%d, correlation-id=%d) with exception: ", message.getId(), message.getCorrelationId() ) + message.getException() ); //$NON-NLS-1$
            context.stopService();
        }
        else
        {
            System.out.println( String.format( "ClientService : received hello response (id=%d, correlation-id=%d) with chosen version '%d'", message.getId(), message.getCorrelationId(), message.getChosenProtocolVersion() ) ); //$NON-NLS-1$
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway#messageReceivedInternal(org.gamegineer.table.internal.net.transport.IServiceContext, org.gamegineer.table.internal.net.transport.IMessage)
     */
    @Override
    protected boolean messageReceivedInternal(
        final IServiceContext context,
        final IMessage message )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        if( message instanceof HelloResponseMessage )
        {
            handleHelloResponseMessage( context, (HelloResponseMessage)message );
            return true;
        }
        else if( message instanceof BeginAuthenticationRequestMessage )
        {
            handleBeginAuthenticationRequestMessage( context, (BeginAuthenticationRequestMessage)message );
            return true;
        }
        else if( message instanceof EndAuthenticationMessage )
        {
            handleEndAuthenticationMessage( context, (EndAuthenticationMessage)message );
            return true;
        }

        return false;
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway#peerStoppedInternal(org.gamegineer.table.internal.net.transport.IServiceContext)
     */
    @Override
    protected void peerStoppedInternal(
        final IServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        if( playerName_ != null )
        {
            getTableGatewayContext().removeTableGateway( this );
            playerName_ = null;
        }

        getTableGatewayContext().disconnectNetworkTable();
    }

    /**
     * Sets the name of the remote player.
     * 
     * <p>
     * This method is only intended to support testing.
     * </p>
     */
    void setPlayerName()
    {
        synchronized( getLock() )
        {
            playerName_ = "<<server>>"; //$NON-NLS-1$
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway#startedInternal(org.gamegineer.table.internal.net.transport.IServiceContext)
     */
    @Override
    protected void startedInternal(
        final IServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setId( getNextMessageId() ); // TODO: move to sendMessage
        message.setSupportedProtocolVersion( ProtocolVersions.VERSION_1 );
        if( !context.sendMessage( message ) )
        {
            context.stopService();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway#stoppedInternal(org.gamegineer.table.internal.net.transport.IServiceContext)
     */
    @Override
    protected void stoppedInternal(
        final IServiceContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        if( playerName_ != null )
        {
            getTableGatewayContext().removeTableGateway( this );
            playerName_ = null;
        }

        getTableGatewayContext().disconnectNetworkTable();
    }
}
