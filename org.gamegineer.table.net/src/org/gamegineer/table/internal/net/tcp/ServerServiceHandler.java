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
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.AbstractMessage;
import org.gamegineer.table.internal.net.MessageEnvelope;
import org.gamegineer.table.internal.net.messages.EchoRequestMessage;
import org.gamegineer.table.internal.net.messages.EchoResponseMessage;

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

        // TODO: Temporary protocol

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
            System.out.println( String.format( "ServerServiceHandler : failed to deserialize a message (id=%d, tag=%d): ", messageEnvelope.getId(), messageEnvelope.getTag() ) + e ); //$NON-NLS-1$
            return;
        }
        catch( final ClassNotFoundException e )
        {
            System.out.println( String.format( "ServerServiceHandler : failed to deserialize a message (id=%d, tag=%d): ", messageEnvelope.getId(), messageEnvelope.getTag() ) + e ); //$NON-NLS-1$
            return;
        }

        if( message instanceof EchoRequestMessage )
        {
            final EchoRequestMessage request = (EchoRequestMessage)message;
            System.out.println( String.format( "ServerServiceHandler : received echo request (tag=%d) '%s'", request.getTag(), request.getContent() ) ); //$NON-NLS-1$

            final EchoResponseMessage response = new EchoResponseMessage();
            response.setTag( request.getTag() );
            response.setContent( request.getContent() );
            try
            {
                getOutputQueue().enqueueMessageEnvelope( MessageEnvelope.fromMessage( response ) );
            }
            catch( final IOException e )
            {
                System.out.println( "ServerServiceHandler : failed to send echo response: " + e ); //$NON-NLS-1$
            }
        }
        else
        {
            System.out.println( String.format( "ServerServiceHandler : received unknown message (id=%d, tag=%d)", message.getId(), message.getTag() ) ); //$NON-NLS-1$
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
