/*
 * ClientServiceHandler.java
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
 * Created on Jan 7, 2011 at 10:31:56 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import java.io.IOException;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.AbstractMessage;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.MessageEnvelope;
import org.gamegineer.table.internal.net.ProtocolVersions;
import org.gamegineer.table.internal.net.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.messages.EndAuthenticationMessage;
import org.gamegineer.table.internal.net.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.messages.HelloResponseMessage;

/**
 * A service handler that represents the client half of a network table
 * connection.
 */
@ThreadSafe
final class ClientServiceHandler
    extends AbstractServiceHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The network table password. */
    private final SecureString password_;

    /** The player name. */
    private final String playerName_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientServiceHandler} class.
     * 
     * @param networkInterface
     *        The network interface associated with the service handler; must
     *        not be {@code null}.
     * @param playerName
     *        The player name; must not be {@code null}.
     * @param password
     *        The network table password; must not be {@code null}.
     */
    ClientServiceHandler(
        /* @NonNull */
        final AbstractNetworkInterface networkInterface,
        /* @NonNull */
        final String playerName,
        /* @NonNull */
        final SecureString password )
    {
        super( networkInterface );

        assert playerName != null;
        assert password != null;

        password_ = new SecureString( password );
        playerName_ = playerName;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a Begin Authentication Request message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    private void handleBeginAuthenticationRequestMessage(
        /* @NonNull */
        final BeginAuthenticationRequestMessage message )
    {
        assert message != null;
        assert Thread.holdsLock( getLock() );

        final BeginAuthenticationResponseMessage response = new BeginAuthenticationResponseMessage();
        response.setTag( message.getTag() );
        response.setPlayerName( playerName_ );
        // TODO: calculate HMAC using password, challenge, salt, and iterationCount
        response.setResponse( new byte[] {
            7, 6, 5, 4, 3, 2, 1, 0
        } );

        if( !sendMessage( response ) )
        {
            close();
        }
    }

    /**
     * Handles an End Authentication message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    @SuppressWarnings( "boxing" )
    private void handleEndAuthenticationMessage(
        /* @NonNull */
        final EndAuthenticationMessage message )
    {
        assert message != null;
        assert Thread.holdsLock( getLock() );

        if( message.getException() != null )
        {
            System.out.println( String.format( "ClientServiceHandler : failed authentication (tag=%d) with exception: ", message.getTag() ) + message.getException() ); //$NON-NLS-1$
            close();
        }
        else
        {
            System.out.println( String.format( "ClientServiceHandler : completed authentication successfully (tag=%d): ", message.getTag() ) ); //$NON-NLS-1$
        }
    }

    /**
     * Handles a Hello Response message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     */
    @GuardedBy( "getLock()" )
    @SuppressWarnings( "boxing" )
    private void handleHelloResponseMessage(
        /* @NonNull */
        final HelloResponseMessage message )
    {
        assert message != null;
        assert Thread.holdsLock( getLock() );

        if( message.getException() != null )
        {
            System.out.println( String.format( "ClientServiceHandler : received hello response (tag=%d) with exception: ", message.getTag() ) + message.getException() ); //$NON-NLS-1$
            close();
        }
        else
        {
            System.out.println( String.format( "ClientServiceHandler : received hello response (tag=%d) with chosen version '%d'", message.getTag(), message.getChosenProtocolVersion() ) ); //$NON-NLS-1$
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractServiceHandler#handleInputShutDown()
     */
    @Override
    void handleInputShutDown()
    {
        getNetworkInterface().getListener().networkInterfaceDisconnected();
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractServiceHandler#handleMessageEnvelope(org.gamegineer.table.internal.net.MessageEnvelope)
     */
    @Override
    void handleMessageEnvelope(
        final MessageEnvelope messageEnvelope )
    {
        assert messageEnvelope != null;
        assert Thread.holdsLock( getLock() );

        final AbstractMessage message;
        try
        {
            message = messageEnvelope.getBodyAsMessage();
        }
        catch( final IOException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.ClientServiceHandler_handleMessageEnvelope_deserializationError( messageEnvelope ), e );
            return;
        }
        catch( final ClassNotFoundException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.ClientServiceHandler_handleMessageEnvelope_deserializationError( messageEnvelope ), e );
            return;
        }

        // TODO: need to correlate all response message tags

        if( message instanceof HelloResponseMessage )
        {
            handleHelloResponseMessage( (HelloResponseMessage)message );
        }
        else if( message instanceof BeginAuthenticationRequestMessage )
        {
            handleBeginAuthenticationRequestMessage( (BeginAuthenticationRequestMessage)message );
        }
        else if( message instanceof EndAuthenticationMessage )
        {
            handleEndAuthenticationMessage( (EndAuthenticationMessage)message );
        }
        else
        {
            Loggers.getDefaultLogger().warning( Messages.ClientServiceHandler_handleMessageEnvelope_unknownMessage( messageEnvelope ) );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractServiceHandler#serviceHandlerClosed()
     */
    @Override
    void serviceHandlerClosed()
    {
        getNetworkInterface().getListener().networkInterfaceDisconnected();

        password_.dispose();
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractServiceHandler#serviceHandlerOpened()
     */
    @Override
    void serviceHandlerOpened()
    {
        assert Thread.holdsLock( getLock() );

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setTag( getNextMessageTag() );
        message.setSupportedProtocolVersion( ProtocolVersions.VERSION_1 );
        if( !sendMessage( message ) )
        {
            close();
        }
    }
}
