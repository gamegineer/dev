/*
 * Connector.java
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
 * Created on Jan 7, 2011 at 10:31:40 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A connector in the TCP network interface Acceptor-Connector pattern
 * implementation.
 * 
 * <p>
 * A connector is responsible for actively connecting and initializing a
 * client-side service handler.
 * </p>
 */
@ThreadSafe
final class Connector
    extends AbstractEventHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dispatcher associated with the connector. */
    private final Dispatcher dispatcher_;

    /** The instance lock. */
    private final Object lock_;

    /** The connector state. */
    @GuardedBy( "lock_" )
    private State state_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Connector} class.
     * 
     * @param dispatcher
     *        The dispatcher associated with the connector; must not be {@code
     *        null}.
     */
    Connector(
        /* @NonNull */
        final Dispatcher dispatcher )
    {
        assert dispatcher != null;

        dispatcher_ = dispatcher;
        lock_ = new Object();
        state_ = State.PRISTINE;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#close()
     */
    @Override
    void close()
    {
        synchronized( lock_ )
        {
            state_ = State.CLOSED;
        }
    }

    /**
     * Opens the connector and connects to the peer host.
     * 
     * <p>
     * This method blocks until the connection is established or an error
     * occurs.
     * </p>
     * 
     * @param configuration
     *        The network table configuration; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If an attempt has already been made to connect to the peer host.
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs
     */
    void connect(
        /* @NonNull */
        final INetworkTableConfiguration configuration )
        throws NetworkTableException
    {
        assert configuration != null;

        synchronized( lock_ )
        {
            assertStateLegal( state_ == State.PRISTINE, Messages.Connector_state_notPristine );

            final SocketChannel channel;
            try
            {
                channel = createSocketChannel( configuration );
            }
            catch( final IOException e )
            {
                throw new NetworkTableException( Messages.Connector_connect_ioError, e );
            }
            finally
            {
                state_ = State.CLOSED;
            }

            final AbstractServiceHandler serviceHandler = new ClientServiceHandler( dispatcher_ );
            serviceHandler.open( channel );
        }
    }

    /**
     * Creates and connects a new socket channel.
     * 
     * @param configuration
     *        The network table configuration used to initialize the socket
     *        channel; must not be {@code null}.
     * 
     * @return A new socket channel; never {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If any other error occurs.
     */
    /* @NonNull */
    private static SocketChannel createSocketChannel(
        /* @NonNull */
        final INetworkTableConfiguration configuration )
        throws IOException, NetworkTableException
    {
        assert configuration != null;

        final InetSocketAddress address = new InetSocketAddress( configuration.getHostName(), configuration.getPort() );
        if( address.isUnresolved() )
        {
            throw new NetworkTableException( Messages.Connector_createSocketChannel_addressUnresolved );
        }

        final SocketChannel channel = SocketChannel.open();
        channel.configureBlocking( true );
        channel.connect( address );
        channel.configureBlocking( false );
        return channel;
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#getEvents()
     */
    @Override
    int getEvents()
    {
        throw new UnsupportedOperationException( "asynchronous connection not supported" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#getTransportHandle()
     */
    @Override
    SelectableChannel getTransportHandle()
    {
        throw new UnsupportedOperationException( "asynchronous connection not supported" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#handleEvent(java.nio.channels.SelectionKey)
     */
    @Override
    void handleEvent(
        final SelectionKey event )
    {
        assert event != null;

        throw new UnsupportedOperationException( "asynchronous connection not supported" ); //$NON-NLS-1$
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The possible connector states.
     */
    private enum State
    {
        /** The connector has never been used. */
        PRISTINE,

        /** The connector is closed. */
        CLOSED;
    }
}