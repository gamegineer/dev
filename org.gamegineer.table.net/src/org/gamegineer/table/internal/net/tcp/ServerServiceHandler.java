/*
 * ServerServiceHandler.java
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
 * Created on Jan 7, 2011 at 10:32:02 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import java.io.IOException;
import java.util.Arrays;
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
import org.gamegineer.table.net.NetworkTableException;

/**
 * A service handler that represents the server half of a network table
 * connection.
 */
@ThreadSafe
final class ServerServiceHandler
    extends AbstractServiceHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Indicates the remote player is connected. */
    @GuardedBy( "getLock()" )
    private boolean isConnected_;

    /** The network table password. */
    private final SecureString password_;

    /**
     * The name of the remote player or {@code null} if the player has not yet
     * been authenticated.
     */
    @GuardedBy( "getLock()" )
    private String playerName_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServerServiceHandler} class.
     * 
     * @param networkInterface
     *        The network interface associated with the service handler; must
     *        not be {@code null}.
     * @param password
     *        The network table password; must not be {@code null}.
     */
    ServerServiceHandler(
        /* @NonNull */
        final AbstractNetworkInterface networkInterface,
        /* @NonNull */
        final SecureString password )
    {
        super( networkInterface );

        assert password != null;

        isConnected_ = false;
        password_ = new SecureString( password );
        playerName_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        endAuthenticationMessage.setTag( AbstractMessage.NO_TAG );

        // TODO: need to verify challenge response
        if( Arrays.equals( message.getResponse(), new byte[] {
            7, 6, 5, 4, 3, 2, 1, 0
        } ) )
        {
            System.out.println( String.format( "ServerServiceHandler : client authenticated (tag=%d)", message.getTag() ) ); //$NON-NLS-1$
            isConnected_ = true;
            playerName_ = message.getPlayerName();
            // TODO: this method has to be atomic with respect to allowing the network table
            // to throw an exception if the player name is already taken
            getNetworkInterface().getListener().playerConnected( playerName_ );
        }
        else
        {
            System.out.println( String.format( "ServerServiceHandler : client failed authentication (tag=%d)", message.getTag() ) ); //$NON-NLS-1$
            endAuthenticationMessage.setException( new NetworkTableException( "failed authentication" ) ); //$NON-NLS-1$
        }

        if( sendMessage( endAuthenticationMessage ) )
        {
            if( endAuthenticationMessage.getException() != null )
            {
                shutDownOutputQueue();
            }
        }
        else
        {
            close();
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

        System.out.println( String.format( "ServerServiceHandler : received hello request (tag=%d) with supported version '%d'", message.getTag(), message.getSupportedProtocolVersion() ) ); //$NON-NLS-1$

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

        if( !sendMessage( response ) )
        {
            close();
            return;
        }

        if( response.getException() == null )
        {
            final BeginAuthenticationRequestMessage beginAuthenticationRequest = new BeginAuthenticationRequestMessage();
            // TODO: generate cryptographically strong random data
            beginAuthenticationRequest.setTag( getNextMessageTag() );
            beginAuthenticationRequest.setChallenge( new byte[] {
                0, 1, 2, 3, 4, 5, 6, 7
            } );
            beginAuthenticationRequest.setSalt( new byte[] {
                8, 9, 10, 11, 12, 13, 14, 15
            } );
            beginAuthenticationRequest.setIterationCount( 1000 );

            if( !sendMessage( beginAuthenticationRequest ) )
            {
                close();
            }
        }
        else
        {
            System.out.println( String.format( "ServerServiceHandler : received hello request (tag=%d) but the requested version is unsupported", message.getTag() ) ); //$NON-NLS-1$
            shutDownOutputQueue();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractServiceHandler#handleInputShutDown()
     */
    @Override
    void handleInputShutDown()
    {
        if( isConnected_ )
        {
            isConnected_ = false;
            getNetworkInterface().getListener().playerDisconnected( playerName_ );
        }
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
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.ServerServiceHandler_handleMessageEnvelope_deserializationError( messageEnvelope ), e );
            return;
        }
        catch( final ClassNotFoundException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.ServerServiceHandler_handleMessageEnvelope_deserializationError( messageEnvelope ), e );
            return;
        }

        // TODO: need to correlate all response message tags

        if( message instanceof HelloRequestMessage )
        {
            handleHelloRequestMessage( (HelloRequestMessage)message );
        }
        else if( message instanceof BeginAuthenticationResponseMessage )
        {
            handleBeginAuthenticationResponseMessage( (BeginAuthenticationResponseMessage)message );
        }
        else
        {
            Loggers.getDefaultLogger().warning( Messages.ServerServiceHandler_handleMessageEnvelope_unknownMessage( messageEnvelope ) );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractServiceHandler#serviceHandlerClosed()
     */
    @Override
    void serviceHandlerClosed()
    {
        synchronized( getLock() )
        {
            if( isConnected_ )
            {
                isConnected_ = false;
                getNetworkInterface().getListener().playerDisconnected( playerName_ );
            }
        }

        password_.dispose();
    }
}
