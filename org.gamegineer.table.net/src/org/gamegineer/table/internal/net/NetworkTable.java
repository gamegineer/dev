/*
 * NetworkTable.java
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
 * Created on Nov 6, 2010 at 2:01:23 PM.
 */

package org.gamegineer.table.internal.net;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.net.client.ClientNetworkTableStrategy;
import org.gamegineer.table.internal.net.server.ServerNetworkTableStrategy;
import org.gamegineer.table.internal.net.transport.ITransportLayerFactory;
import org.gamegineer.table.internal.net.transport.tcp.TcpTransportLayerFactory;
import org.gamegineer.table.net.INetworkTable;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.INetworkTableListener;
import org.gamegineer.table.net.NetworkTableDisconnectedEvent;
import org.gamegineer.table.net.NetworkTableError;
import org.gamegineer.table.net.NetworkTableEvent;
import org.gamegineer.table.net.NetworkTableException;

/**
 * Implementation of {@link org.gamegineer.table.net.INetworkTable}.
 */
@ThreadSafe
public final class NetworkTable
    implements INetworkTable, INetworkTableStrategyContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** A reference to the connection state. */
    private final AtomicReference<ConnectionState> connectionStateRef_;

    /** The collection of network table listeners. */
    private final CopyOnWriteArrayList<INetworkTableListener> listeners_;

    /**
     * A reference to the active network table strategy or {@code null} if the
     * network is not connected.
     */
    private final AtomicReference<INetworkTableStrategy> strategyRef_;

    /** The table to be attached to the network. */
    @SuppressWarnings( "unused" )
    private final ITable table_;

    /** The network table transport layer factory. */
    private final ITransportLayerFactory transportLayerFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTable} class using the
     * default transport layer factory.
     * 
     * @param table
     *        The table to be attached to the network; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} is {@code null}.
     */
    public NetworkTable(
        /* @NonNull */
        final ITable table )
    {
        this( table, new TcpTransportLayerFactory() );
    }

    /**
     * Initializes a new instance of the {@code NetworkTable} class using the
     * specified transport layer factory.
     * 
     * @param table
     *        The table to be attached to the network; must not be {@code null}.
     * @param transportLayerFactory
     *        The network table transport layer factory; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} or {@code transportLayerFactory} is {@code null}
     *         .
     */
    public NetworkTable(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final ITransportLayerFactory transportLayerFactory )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$
        assertArgumentNotNull( transportLayerFactory, "transportLayerFactory" ); //$NON-NLS-1$

        connectionStateRef_ = new AtomicReference<ConnectionState>( ConnectionState.DISCONNECTED );
        listeners_ = new CopyOnWriteArrayList<INetworkTableListener>();
        strategyRef_ = new AtomicReference<INetworkTableStrategy>( null );
        table_ = table;
        transportLayerFactory_ = transportLayerFactory;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.INetworkTable#addNetworkTableListener(org.gamegineer.table.net.INetworkTableListener)
     */
    @Override
    public void addNetworkTableListener(
        final INetworkTableListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", Messages.NetworkTable_addNetworkTableListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Connects the network table using the specified transport layer.
     * 
     * @param configuration
     *        The network table configuration; must not be {@code null}.
     * @param strategy
     *        The network table strategy; must not be {@code null}.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If the connection cannot be established or the network is already
     *         connected.
     */
    private void connect(
        /* @NonNull */
        final INetworkTableConfiguration configuration,
        /* @NonNull */
        final INetworkTableStrategy strategy )
        throws NetworkTableException
    {
        assert configuration != null;
        assert strategy != null;

        if( connectionStateRef_.compareAndSet( ConnectionState.DISCONNECTED, ConnectionState.CONNECTING ) )
        {
            strategy.connect( configuration );
            strategyRef_.set( strategy );
            connectionStateRef_.set( ConnectionState.CONNECTED );
            fireNetworkConnected();
        }
        else
        {
            throw new NetworkTableException( NetworkTableError.ILLEGAL_CONNECTION_STATE );
        }
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#disconnect()
     */
    @Override
    public void disconnect()
    {
        disconnect( null );
    }

    /**
     * Disconnects from the network due to the specified error.
     * 
     * @param error
     *        The error that caused the network table to be disconnected from
     *        the network or {@code null} if the network table was disconnected
     *        normally.
     */
    public void disconnect(
        /* @Nullable */
        final NetworkTableError error )
    {
        if( connectionStateRef_.compareAndSet( ConnectionState.CONNECTED, ConnectionState.DISCONNECTING ) )
        {
            final INetworkTableStrategy strategy = strategyRef_.getAndSet( null );
            strategy.disconnect();
            connectionStateRef_.set( ConnectionState.DISCONNECTED );
            fireNetworkDisconnected( error );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkTableStrategyContext#disconnectNetworkTable(org.gamegineer.table.net.NetworkTableError)
     */
    @Override
    public void disconnectNetworkTable(
        final NetworkTableError error )
    {
        disconnect( error );
    }

    /**
     * Fires a network connected event.
     */
    private void fireNetworkConnected()
    {
        final NetworkTableEvent event = new NetworkTableEvent( this );
        for( final INetworkTableListener listener : listeners_ )
        {
            try
            {
                listener.networkConnected( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.NetworkTable_networkConnected_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a network disconnected event.
     * 
     * @param error
     *        The error that caused the network table to be disconnected or
     *        {@code null} if the network table was disconnected normally.
     */
    private void fireNetworkDisconnected(
        /* @Nullable */
        final NetworkTableError error )
    {
        final NetworkTableDisconnectedEvent event = new NetworkTableDisconnectedEvent( this, error );
        for( final INetworkTableListener listener : listeners_ )
        {
            try
            {
                listener.networkDisconnected( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.NetworkTable_networkDisconnected_unexpectedException, e );
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkTableStrategyContext#getTransportLayerFactory()
     */
    @Override
    public ITransportLayerFactory getTransportLayerFactory()
    {
        return transportLayerFactory_;
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#host(org.gamegineer.table.net.INetworkTableConfiguration)
     */
    @Override
    public void host(
        final INetworkTableConfiguration configuration )
        throws NetworkTableException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        connect( configuration, new ServerNetworkTableStrategy( this ) );
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#isConnected()
     */
    @Override
    public boolean isConnected()
    {
        return connectionStateRef_.get() == ConnectionState.CONNECTED;
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#join(org.gamegineer.table.net.INetworkTableConfiguration)
     */
    @Override
    public void join(
        final INetworkTableConfiguration configuration )
        throws NetworkTableException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        connect( configuration, new ClientNetworkTableStrategy( this ) );
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#removeNetworkTableListener(org.gamegineer.table.net.INetworkTableListener)
     */
    @Override
    public void removeNetworkTableListener(
        final INetworkTableListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", Messages.NetworkTable_removeNetworkTableListener_listener_notRegistered ); //$NON-NLS-1$
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The possible network table connection states.
     */
    private enum ConnectionState
    {
        /** The network table is processing a connect request. */
        CONNECTING,

        /** The network table is connected. */
        CONNECTED,

        /** The network table is processing a disconnect request. */
        DISCONNECTING,

        /** The network table is disconnected. */
        DISCONNECTED;
    }
}
