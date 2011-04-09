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

package org.gamegineer.table.internal.net.transport.tcp;

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

    /** Indicates the acceptor is registered with the dispatcher. */
    @GuardedBy( "getLock()" )
    private boolean isRegistered_;

    /** The network interface associated with the acceptor. */
    private final AbstractNetworkInterface networkInterface_;

    /** The server socket channel on which incoming connections are accepted. */
    @GuardedBy( "getLock()" )
    private ServerSocketChannel serverChannel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Acceptor} class.
     * 
     * @param networkInterface
     *        The network interface associated with the acceptor; must not be
     *        {@code null}.
     */
    Acceptor(
        /* @NonNull */
        final AbstractNetworkInterface networkInterface )
    {
        assert networkInterface != null;

        isRegistered_ = false;
        networkInterface_ = networkInterface;
        serverChannel_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked when the server channel is ready to accept a new connection.
     */
    @GuardedBy( "getLock()" )
    private void accept()
    {
        assert Thread.holdsLock( getLock() );

        final SocketChannel clientChannel;
        try
        {
            clientChannel = serverChannel_.accept();
            if( clientChannel == null )
            {
                return;
            }

            clientChannel.configureBlocking( false );

            final ServiceHandlerAdapter serviceHandlerAdapter = new ServiceHandlerAdapter( networkInterface_, networkInterface_.createNetworkServiceHandler() );
            serviceHandlerAdapter.open( clientChannel );
        }
        catch( final IOException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.Acceptor_accept_ioError, e );
        }
    }

    /**
     * Opens the acceptor and binds the acceptor channel.
     * 
     * <p>
     * This method blocks until the acceptor channel is bound or an error
     * occurs.
     * </p>
     * 
     * @param hostName
     *        The host name to which the acceptor will be bound; must not be
     *        {@code null}.
     * @param port
     *        The port to which the acceptor will be bound.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs
     * @throws java.lang.IllegalStateException
     *         If an attempt has already been made to bind the acceptor channel.
     */
    void bind(
        /* @NonNull */
        final String hostName,
        final int port )
        throws IOException
    {
        assert hostName != null;

        synchronized( getLock() )
        {
            assertStateLegal( getState() == State.PRISTINE, Messages.Acceptor_state_notPristine );

            try
            {
                serverChannel_ = createServerSocketChannel( hostName, port );
                setState( State.OPEN );

                networkInterface_.getDispatcher().registerEventHandler( this );
                isRegistered_ = true;
            }
            catch( final IOException e )
            {
                close();
                throw e;
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#close()
     */
    @Override
    void close()
    {
        final State state;

        synchronized( getLock() )
        {
            if( (state = getState()) == State.OPEN )
            {
                if( isRegistered_ )
                {
                    isRegistered_ = false;
                    networkInterface_.getDispatcher().unregisterEventHandler( this );
                }

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

            setState( State.CLOSED );
        }

        if( state == State.OPEN )
        {
            networkInterface_.disconnected();
        }
    }

    /**
     * Creates and initializes a new server socket channel.
     * 
     * @param hostName
     *        The host name to which the acceptor will be bound; must not be
     *        {@code null}.
     * @param port
     *        The port to which the acceptor will be bound.
     * 
     * @return A new server socket channel; never {@code null}.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    /* @NonNull */
    private static ServerSocketChannel createServerSocketChannel(
        /* @NonNull */
        final String hostName,
        final int port )
        throws IOException
    {
        assert hostName != null;

        final ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking( false );
        channel.socket().bind( new InetSocketAddress( hostName, port ) );
        return channel;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#getChannel()
     */
    @Override
    SelectableChannel getChannel()
    {
        synchronized( getLock() )
        {
            return serverChannel_;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#getInterestOperations()
     */
    @Override
    int getInterestOperations()
    {
        return SelectionKey.OP_ACCEPT;
    }

    /*
     * @see org.gamegineer.table.internal.net.transport.tcp.AbstractEventHandler#run()
     */
    @Override
    void run()
    {
        synchronized( getLock() )
        {
            // Handle race condition when acceptor is closed after an event has been
            // dispatched but before this method is called
            if( getState() == State.OPEN )
            {
                if( getSelectionKey().isAcceptable() )
                {
                    accept();
                }
            }
        }
    }
}
