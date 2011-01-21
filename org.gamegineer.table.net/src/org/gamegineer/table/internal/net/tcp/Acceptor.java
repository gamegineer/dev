/*
 * Acceptor.java
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
 * Created on Jan 7, 2011 at 10:31:34 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableException;

/**
 * An acceptor in the TCP network interface Acceptor-Connector pattern
 * implementation.
 * 
 * <p>
 * An acceptor is responsible for passively connecting and initializing a
 * server-side service handler.
 * </p>
 */
@ThreadSafe
final class Acceptor
    extends AbstractEventHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dispatcher associated with the acceptor. */
    private final Dispatcher dispatcher_;

    /** The instance lock. */
    private final Object lock_;

    /** The server socket channel on which incoming connections are accepted. */
    @GuardedBy( "lock_" )
    private ServerSocketChannel serverChannel_;

    /** The acceptor state. */
    @GuardedBy( "lock_" )
    private State state_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Acceptor} class.
     * 
     * @param dispatcher
     *        The dispatcher associated with the acceptor; must not be {@code
     *        null}.
     */
    Acceptor(
        /* @NonNull */
        final Dispatcher dispatcher )
    {
        assert dispatcher != null;

        dispatcher_ = dispatcher;
        lock_ = new Object();
        serverChannel_ = null;
        state_ = State.PRISTINE;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked when the server channel is ready to accept a new connection.
     */
    @GuardedBy( "lock_" )
    private void accept()
    {
        assert Thread.holdsLock( lock_ );
        assert state_ == State.OPENED;
        assert serverChannel_ != null;

        final SocketChannel clientChannel;
        try
        {
            clientChannel = serverChannel_.accept();
            // XXX: how is this possible?  do we have to handle this?
            // for sure might have to do it in the case the dispatcher gets shut down
            // but verify after we impl the logic to unregister any open service handlers
            if( clientChannel == null )
            {
                return;
            }

            clientChannel.configureBlocking( false );
        }
        catch( final IOException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Acceptor_accept_ioError, e );
            return;
        }

        final AbstractServiceHandler serviceHandler = new ServerServiceHandler( dispatcher_ );
        serviceHandler.open( clientChannel );
    }

    /**
     * Opens the acceptor and binds the acceptor transport handle.
     * 
     * <p>
     * This method blocks until the acceptor transport handle is bound or an
     * error occurs.
     * </p>
     * 
     * @param configuration
     *        The network table configuration; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If an attempt has already been made to bind the acceptor
     *         transport handle.
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs
     */
    void bind(
        /* @NonNull */
        final INetworkTableConfiguration configuration )
        throws NetworkTableException
    {
        assert configuration != null;

        synchronized( lock_ )
        {
            assertStateLegal( state_ == State.PRISTINE, Messages.Acceptor_state_notPristine );

            try
            {
                serverChannel_ = createServerSocketChannel( configuration );
            }
            catch( final IOException e )
            {
                state_ = State.CLOSED;
                throw new NetworkTableException( Messages.Acceptor_bind_ioError, e );
            }

            state_ = State.OPENED;

            dispatcher_.registerEventHandler( this );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#close()
     */
    @Override
    void close()
    {
        synchronized( lock_ )
        {
            if( state_ == State.OPENED )
            {
                dispatcher_.unregisterEventHandler( this );

                try
                {
                    serverChannel_.close();
                }
                catch( final IOException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Acceptor_close_ioError, e );
                }
                finally
                {
                    serverChannel_ = null;
                }
            }

            state_ = State.CLOSED;
        }
    }

    /**
     * Creates and initializes a new server socket channel.
     * 
     * @param configuration
     *        The network table configuration used to initialize the server
     *        socket channel; must not be {@code null}.
     * 
     * @return A new server socket channel; never {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    /* @NonNull */
    private static ServerSocketChannel createServerSocketChannel(
        /* @NonNull */
        final INetworkTableConfiguration configuration )
        throws IOException
    {
        assert configuration != null;

        final ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking( false );
        channel.socket().bind( new InetSocketAddress( configuration.getHostName(), configuration.getPort() ) );
        return channel;
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#getEvents()
     */
    @Override
    int getEvents()
    {
        return SelectionKey.OP_ACCEPT;
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#getTransportHandle()
     */
    @Override
    SelectableChannel getTransportHandle()
    {
        synchronized( lock_ )
        {
            return serverChannel_;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.tcp.AbstractEventHandler#handleEvent(java.nio.channels.SelectionKey)
     */
    @Override
    void handleEvent(
        final SelectionKey event )
    {
        assert event != null;

        synchronized( lock_ )
        {
            // Handle race condition when acceptor is closed after an event has been
            // dispatched but before this method is called
            if( state_ != State.OPENED )
            {
                return;
            }

            if( event.isAcceptable() )
            {
                accept();
            }
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The possible acceptor states.
     */
    private enum State
    {
        /** The acceptor has never been used. */
        PRISTINE,

        /** The acceptor is open. */
        OPENED,

        /** The acceptor is closed. */
        CLOSED;
    }
}
