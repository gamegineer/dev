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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.AbstractMessage;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.MessageEnvelope;
import org.gamegineer.table.internal.net.ProtocolVersions;
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

    /** The next available player identifier. */
    private static final AtomicInteger nextPlayerId_ = new AtomicInteger( 0 );

    /** Indicates the remote player is connected. */
    @GuardedBy( "getLock()" )
    private boolean isConnected_;

    /**
     * The identifier of the remote player or {@code null} if the player
     * identity has not yet been established.
     */
    @GuardedBy( "getLock()" )
    private String playerId_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServerServiceHandler} class.
     * 
     * @param networkInterface
     *        The network interface associated with the service handler; must
     *        not be {@code null}.
     */
    ServerServiceHandler(
        /* @NonNull */
        final AbstractNetworkInterface networkInterface )
    {
        super( networkInterface );

        isConnected_ = false;
        playerId_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractServiceHandler#handleInputShutDown()
     */
    @Override
    void handleInputShutDown()
    {
        if( isConnected_ )
        {
            isConnected_ = false;
            getNetworkInterface().getListener().playerDisconnected( playerId_ );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractServiceHandler#handleMessageEnvelope(org.gamegineer.table.internal.net.MessageEnvelope)
     */
    @Override
    @SuppressWarnings( "boxing" )
    void handleMessageEnvelope(
        final MessageEnvelope messageEnvelope )
    {
        assert messageEnvelope != null;
        assert Thread.holdsLock( getLock() );

        if( !isConnected_ )
        {
            // TODO: Player ID will be obtained via protocol.
            isConnected_ = true;
            playerId_ = String.format( "player-%d", new Integer( nextPlayerId_.incrementAndGet() ) ); //$NON-NLS-1$
            getNetworkInterface().getListener().playerConnected( playerId_ );
        }

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

        if( message instanceof HelloRequestMessage )
        {
            final HelloRequestMessage request = (HelloRequestMessage)message;
            System.out.println( String.format( "ServerServiceHandler : received hello request (tag=%d) with supported version '%d'", request.getTag(), request.getSupportedProtocolVersion() ) ); //$NON-NLS-1$

            final HelloResponseMessage response = new HelloResponseMessage();
            response.setTag( request.getTag() );
            if( request.getSupportedProtocolVersion() >= ProtocolVersions.VERSION_1 )
            {
                response.setChosenProtocolVersion( ProtocolVersions.VERSION_1 );
            }
            else
            {
                response.setException( new NetworkTableException( "unsupported protocol version" ) ); //$NON-NLS-1$
            }

            try
            {
                getOutputQueue().enqueueMessageEnvelope( MessageEnvelope.fromMessage( response ) );
            }
            catch( final IOException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.ServerServiceHandler_sendMessage_ioError( message ), e );
                close();
                return;
            }

            if( response.getException() != null )
            {
                System.out.println( String.format( "ServerServiceHandler : received hello request (tag=%d) with an unsupported version", request.getTag() ) ); //$NON-NLS-1$
                // TODO: Cannot call close() here because the response will never be sent.
                // Need a mechanism for signaling this handler is dead and should be removed
                // as soon as its output queue is clear.
                //close();
            }
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
                getNetworkInterface().getListener().playerDisconnected( playerId_ );
            }
        }
    }
}
