/*
 * Connector.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Jan 7, 2011 at 10:31:40 PM.
 */

package org.gamegineer.table.internal.net.impl.transport.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A connector in the TCP transport layer Acceptor-Connector pattern
 * implementation.
 * 
 * <p>
 * A connector is responsible for actively connecting and initializing a
 * service.
 * </p>
 */
@NotThreadSafe
final class Connector
    extends AbstractEventHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Connector} class.
     * 
     * @param transportLayer
     *        The transport layer associated with the connector.
     */
    Connector(
        final AbstractTransportLayer transportLayer )
    {
        super( transportLayer );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.tcp.AbstractEventHandler#close(java.lang.Exception)
     */
    @Override
    void close(
        final @Nullable Exception exception )
    {
        assert exception == null : "asynchronous connection not supported"; //$NON-NLS-1$
        assert isTransportLayerThread();

        setState( State.CLOSED );
    }

    /**
     * Opens the connector and connects to the peer host.
     * 
     * <p>
     * This method blocks until the connection is established or an error
     * occurs.
     * </p>
     * 
     * @param hostName
     *        The host name of the remote peer.
     * @param port
     *        The port of the remote peer.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs
     */
    void connect(
        final String hostName,
        final int port )
        throws IOException
    {
        assert isTransportLayerThread();
        assert getState() == State.PRISTINE;

        try
        {
            final SocketChannel channel = createSocketChannel( hostName, port );

            final AbstractTransportLayer transportLayer = getTransportLayer();
            final ServiceHandler serviceHandler = new ServiceHandler( transportLayer, transportLayer.createService() );
            serviceHandler.open( channel );
        }
        finally
        {
            close();
        }
    }

    /**
     * Creates and connects a new socket channel.
     * 
     * @param hostName
     *        The host name of the remote peer.
     * @param port
     *        The port of the remote peer.
     * 
     * @return A new socket channel.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    private static SocketChannel createSocketChannel(
        final String hostName,
        final int port )
        throws IOException
    {
        final InetSocketAddress address = new InetSocketAddress( hostName, port );
        if( address.isUnresolved() )
        {
            throw new IOException( NonNlsMessages.Connector_createSocketChannel_addressUnresolved );
        }

        final SocketChannel channel = SocketChannel.open();
        channel.configureBlocking( true );
        channel.connect( address );
        channel.configureBlocking( false );
        return channel;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.tcp.AbstractEventHandler#getChannel()
     */
    @Override
    @Nullable SelectableChannel getChannel()
    {
        assert isTransportLayerThread();

        throw new UnsupportedOperationException( "asynchronous connection not supported" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.tcp.AbstractEventHandler#getInterestOperations()
     */
    @Override
    int getInterestOperations()
    {
        assert isTransportLayerThread();

        throw new UnsupportedOperationException( "asynchronous connection not supported" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.tcp.AbstractEventHandler#run()
     */
    @Override
    void run()
    {
        assert isTransportLayerThread();

        throw new UnsupportedOperationException( "asynchronous connection not supported" ); //$NON-NLS-1$
    }
}
