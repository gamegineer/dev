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
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.AbstractMessage;
import org.gamegineer.table.internal.net.MessageEnvelope;
import org.gamegineer.table.internal.net.messages.EchoRequestMessage;
import org.gamegineer.table.internal.net.messages.EchoResponseMessage;

/**
 * A service handler that represents the client half of a network table
 * connection.
 */
@ThreadSafe
final class ClientServiceHandler
    extends AbstractServiceHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientServiceHandler} class.
     * 
     * @param networkInterface
     *        The network interface associated with the service handler; must
     *        not be {@code null}.
     */
    ClientServiceHandler(
        /* @NonNull */
        final AbstractNetworkInterface networkInterface )
    {
        super( networkInterface );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractServiceHandler#doOpen()
     */
    @Override
    void doOpen()
    {
        final Thread t = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    int nextTag = AbstractMessage.MIN_TAG;

                    while( true )
                    {
                        Thread.sleep( 5000L );

                        final EchoRequestMessage message = new EchoRequestMessage();
                        message.setTag( nextTag );
                        message.setContent( "a message" ); //$NON-NLS-1$
                        getOutputQueue().enqueueMessageEnvelope( MessageEnvelope.fromMessage( message ) );

                        if( ++nextTag > AbstractMessage.MAX_TAG )
                        {
                            nextTag = AbstractMessage.MIN_TAG;
                        }
                    }
                }
                catch( final Exception e )
                {
                    e.printStackTrace();
                }
            }
        };
        t.setDaemon( true );
        t.start();
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
    @SuppressWarnings( "boxing" )
    void handleMessageEnvelope(
        final MessageEnvelope messageEnvelope )
    {
        assert messageEnvelope != null;
        assert Thread.holdsLock( getLock() );

        // TODO: Temporary protocol

        final AbstractMessage message;
        try
        {
            message = messageEnvelope.getBodyAsMessage();
        }
        catch( final IOException e )
        {
            System.out.println( String.format( "ClientServiceHandler : failed to deserialize a message (id=%d, tag=%d): ", messageEnvelope.getId(), messageEnvelope.getTag() ) + e ); //$NON-NLS-1$
            return;
        }
        catch( final ClassNotFoundException e )
        {
            System.out.println( String.format( "ClientServiceHandler : failed to deserialize a message (id=%d, tag=%d): ", messageEnvelope.getId(), messageEnvelope.getTag() ) + e ); //$NON-NLS-1$
            return;
        }

        if( message instanceof EchoResponseMessage )
        {
            final EchoResponseMessage response = (EchoResponseMessage)message;
            System.out.println( String.format( "ClientServiceHandler : received echo response (tag=%d) '%s'", response.getTag(), response.getContent() ) ); //$NON-NLS-1$
        }
        else
        {
            System.out.println( String.format( "ClientServiceHandler : received unknown message (id=%d, tag=%d)", message.getId(), message.getTag() ) ); //$NON-NLS-1$
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractServiceHandler#serviceHandlerClosed()
     */
    @Override
    void serviceHandlerClosed()
    {
        getNetworkInterface().getListener().networkInterfaceDisconnected();
    }
}
