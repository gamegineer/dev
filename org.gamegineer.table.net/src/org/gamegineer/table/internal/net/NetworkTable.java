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
import org.gamegineer.table.internal.net.tcp.TcpNetworkInterfaceFactory;
import org.gamegineer.table.net.INetworkTable;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.INetworkTableListener;
import org.gamegineer.table.net.NetworkTableEvent;
import org.gamegineer.table.net.NetworkTableException;

/**
 * Implementation of {@link org.gamegineer.table.net.INetworkTable}.
 */
@ThreadSafe
public final class NetworkTable
    implements INetworkTable, INetworkInterfaceContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * A reference to the active configuration or {@code null} if the network is
     * not connected.
     */
    private final AtomicReference<INetworkTableConfiguration> configurationRef_;

    /** A reference to the connection state. */
    private final AtomicReference<ConnectionState> connectionStateRef_;

    /** The collection of network table listeners. */
    private final CopyOnWriteArrayList<INetworkTableListener> listeners_;

    /**
     * A reference to the active network interface or {@code null} if the
     * network is not connected.
     */
    private final AtomicReference<INetworkInterface> networkInterfaceRef_;

    /** The network table network interface factory. */
    private final INetworkInterfaceFactory networkInterfaceFactory_;

    /** The table to be attached to the network. */
    @SuppressWarnings( "unused" )
    private final ITable table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTable} class using the
     * default strategy factory.
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
        this( table, new TcpNetworkInterfaceFactory() );
    }

    /**
     * Initializes a new instance of the {@code NetworkTable} class using the
     * specified network interface factory.
     * 
     * @param table
     *        The table to be attached to the network; must not be {@code null}.
     * @param networkInterfaceFactory
     *        The network table network interface factory; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} is {@code null}.
     */
    NetworkTable(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final INetworkInterfaceFactory networkInterfaceFactory )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$
        assert networkInterfaceFactory != null;

        configurationRef_ = new AtomicReference<INetworkTableConfiguration>( null );
        connectionStateRef_ = new AtomicReference<ConnectionState>( ConnectionState.DISCONNECTED );
        listeners_ = new CopyOnWriteArrayList<INetworkTableListener>();
        networkInterfaceRef_ = new AtomicReference<INetworkInterface>( null );
        networkInterfaceFactory_ = networkInterfaceFactory;
        table_ = table;
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
     * Connects the network table using the specified network interface.
     * 
     * @param configuration
     *        The network table configuration; must not be {@code null}.
     * @param networkInterface
     *        The network interface used to establish the connection; must not
     *        be {@code null}.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If the connection cannot be established or the network is already
     *         connected.
     */
    private void connect(
        /* @NonNull */
        final INetworkTableConfiguration configuration,
        /* @NonNull */
        final INetworkInterface networkInterface )
        throws NetworkTableException
    {
        assert configuration != null;
        assert networkInterface != null;

        if( connectionStateRef_.compareAndSet( ConnectionState.DISCONNECTED, ConnectionState.CONNECTING ) )
        {
            configurationRef_.set( configuration );
            networkInterface.open( configuration.getHostName(), configuration.getPort() );
            networkInterfaceRef_.set( networkInterface );
            connectionStateRef_.set( ConnectionState.CONNECTED );
            fireNetworkConnected();
        }
        else
        {
            throw new NetworkTableException( Messages.NetworkTable_connect_networkConnected );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkInterfaceContext#createClientNetworkServiceHandler()
     */
    @Override
    public INetworkServiceHandler createClientNetworkServiceHandler()
    {
        return new ClientNetworkServiceHandler( this );
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkInterfaceContext#createServerNetworkServiceHandler()
     */
    @Override
    public INetworkServiceHandler createServerNetworkServiceHandler()
    {
        return new ServerNetworkServiceHandler( this );
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#disconnect()
     */
    @Override
    public void disconnect()
    {
        if( connectionStateRef_.compareAndSet( ConnectionState.CONNECTED, ConnectionState.DISCONNECTING ) )
        {
            final INetworkInterface networkInterface = networkInterfaceRef_.getAndSet( null );
            networkInterface.close();
            connectionStateRef_.set( ConnectionState.DISCONNECTED );
            configurationRef_.set( null );
            fireNetworkDisconnected();
        }
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
     */
    private void fireNetworkDisconnected()
    {
        final NetworkTableEvent event = new NetworkTableEvent( this );
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

    /**
     * Gets the active configuration.
     * 
     * @return The active configuration or {@code null} if the network is not
     *         connected.
     */
    /* @Nullable */
    INetworkTableConfiguration getConfiguration()
    {
        return configurationRef_.get();
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

        connect( configuration, networkInterfaceFactory_.createServerNetworkInterface( this ) );
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

        connect( configuration, networkInterfaceFactory_.createClientNetworkInterface( this ) );
    }

    /*
     * @see org.gamegineer.table.internal.net.INetworkInterfaceContext#networkInterfaceDisconnected()
     */
    @Override
    public void networkInterfaceDisconnected()
    {
        disconnect();
    }

    /**
     * Invoked when a remote player has connected.
     * 
     * @param playerName
     *        The name of the remote player that has connected; must not be
     *        {@code null}.
     */
    void playerConnected(
        /* @NonNull */
        final String playerName )
    {
        assert playerName != null;

        // TODO
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Player '%s' has connected", playerName ) ); //$NON-NLS-1$
    }

    /**
     * Invoked when a remote player has disconnected.
     * 
     * @param playerName
     *        The name of the remote player that has disconnected; must not be
     *        {@code null}.
     */
    void playerDisconnected(
        /* @NonNull */
        final String playerName )
    {
        assert playerName != null;

        // TODO
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Player '%s' has disconnected", playerName ) ); //$NON-NLS-1$
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
