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

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import net.jcip.annotations.ThreadSafe;

/**
 * A service handler that represents the server half of a network table
 * connection.
 */
@ThreadSafe
final class ServerServiceHandler
    extends AbstractServiceHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServerServiceHandler} class.
     * 
     * @param dispatcher
     *        The dispatcher associated with the service handler; must not be
     *        {@code null}.
     */
    ServerServiceHandler(
        /* @NonNull */
        final Dispatcher dispatcher )
    {
        super( dispatcher );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractServiceHandler#getNextMessage()
     */
    @Override
    ByteBuffer getNextMessage()
    {
        // TODO: Temporary protocol

        final InputQueue inputQueue = getInputQueue();
        final int newLinePosition = inputQueue.indexOf( (byte)'\n' );
        if( newLinePosition == -1 )
        {
            return null;
        }

        return inputQueue.dequeueBytes( newLinePosition + 1 );
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractServiceHandler#handleMessage(java.nio.ByteBuffer)
     */
    @Override
    void handleMessage(
        final ByteBuffer message )
    {
        assert message != null;

        // TODO: Temporary protocol

        byte[] messageAsBytes = new byte[ message.remaining() ];
        message.get( messageAsBytes );
        final String messageAsString = new String( messageAsBytes, Charset.forName( "US-ASCII" ) ); //$NON-NLS-1$
        System.out.println( String.format( "ServerServiceHandler received message '%s'", messageAsString ) ); //$NON-NLS-1$

        getOutputQueue().enqueueBytes( ByteBuffer.wrap( messageAsBytes ) );
    }
}
