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
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.AbstractMessage;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.MessageEnvelope;
import org.gamegineer.table.internal.net.ProtocolVersions;
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

        if( message instanceof HelloResponseMessage )
        {
            final HelloResponseMessage response = (HelloResponseMessage)message;
            if( response.getException() != null )
            {
                System.out.println( String.format( "ClientServiceHandler : received hello response (tag=%d) with exception: ", message.getTag() ) + response.getException() ); //$NON-NLS-1$
                close();
            }
            else
            {
                System.out.println( String.format( "ClientServiceHandler : received hello response (tag=%d) with chosen version '%d'", response.getTag(), response.getChosenProtocolVersion() ) ); //$NON-NLS-1$
            }
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

        try
        {
            getOutputQueue().enqueueMessageEnvelope( MessageEnvelope.fromMessage( message ) );
        }
        catch( final IOException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.ClientServiceHandler_sendMessage_ioError( message ), e );
            close();
        }
    }
}
