/*
 * Acceptor.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl.transport.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.impl.Loggers;

/**
 * An acceptor in the TCP transport layer Acceptor-Connector pattern
 * implementation.
 * 
 * <p>
 * An acceptor is responsible for passively connecting and initializing a
 * service.
 * </p>
 */
@NotThreadSafe
final class Acceptor
    extends AbstractEventHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Indicates the acceptor is registered with the dispatcher. */
    private boolean isRegistered_;

    /** The server socket channel on which incoming connections are accepted. */
    private ServerSocketChannel serverChannel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Acceptor} class.
     * 
     * @param transportLayer
     *        The transport layer associated with the acceptor; must not be
     *        {@code null}.
     */
    Acceptor(
        /* @NonNull */
        final AbstractTransportLayer transportLayer )
    {
        super( transportLayer );

        isRegistered_ = false;
        serverChannel_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked when the server channel is ready to accept a new connection.
     */
    private void accept()
    {
        final SocketChannel clientChannel;
        try
        {
            clientChannel = serverChannel_.accept();
            if( clientChannel == null )
            {
                return;
            }

            clientChannel.configureBlocking( false );

            final AbstractTransportLayer transportLayer = getTransportLayer();
            final ServiceHandler serviceHandler = new ServiceHandler( transportLayer, transportLayer.createService() );
            serviceHandler.open( clientChannel );
        }
        catch( final IOException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Acceptor_accept_ioError, e );
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
     */
    void bind(
        /* @NonNull */
        final String hostName,
        final int port )
        throws IOException
    {
        assert hostName != null;
        assert isTransportLayerThread();
        assert getState() == State.PRISTINE;

        try
        {
            serverChannel_ = createServerSocketChannel( hostName, port );
            setState( State.OPEN );

            getTransportLayer().getDispatcher().registerEventHandler( this );
            isRegistered_ = true;
        }
        catch( final IOException e )
        {
            close( e );
            throw e;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.tcp.AbstractEventHandler#close(java.lang.Exception)
     */
    @Override
    void close(
        final Exception exception )
    {
        assert isTransportLayerThread();

        final State previousState = getState();
        if( previousState == State.OPEN )
        {
            if( isRegistered_ )
            {
                isRegistered_ = false;
                getTransportLayer().getDispatcher().unregisterEventHandler( this );
            }

            try
            {
                serverChannel_.close();
            }
            catch( final IOException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.Acceptor_close_ioError, e );
            }
            finally
            {
                serverChannel_ = null;
            }
        }

        setState( State.CLOSED );

        if( previousState == State.OPEN )
        {
            getTransportLayer().disconnected( exception );
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
     * @see org.gamegineer.table.internal.net.impl.transport.tcp.AbstractEventHandler#getChannel()
     */
    @Override
    SelectableChannel getChannel()
    {
        assert isTransportLayerThread();

        return serverChannel_;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.tcp.AbstractEventHandler#getInterestOperations()
     */
    @Override
    int getInterestOperations()
    {
        assert isTransportLayerThread();

        return SelectionKey.OP_ACCEPT;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.tcp.AbstractEventHandler#run()
     */
    @Override
    void run()
    {
        assert isTransportLayerThread();

        final SelectionKey selectionKey = getSelectionKey();
        if( (selectionKey != null) && selectionKey.isAcceptable() )
        {
            accept();
        }
    }
}
