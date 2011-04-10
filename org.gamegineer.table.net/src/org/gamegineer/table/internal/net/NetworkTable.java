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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.internal.net.transport.ITransportLayerContext;
import org.gamegineer.table.internal.net.transport.ITransportLayerFactory;
import org.gamegineer.table.internal.net.transport.tcp.TcpTransportLayerFactory;
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
    implements INetworkTable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** A reference to the connection state. */
    private final AtomicReference<ConnectionState> connectionStateRef_;

    /** The collection of network table listeners. */
    private final CopyOnWriteArrayList<INetworkTableListener> listeners_;

    /**
     * A reference to the local player name or {@code null} if the network is
     * not connected.
     */
    private final AtomicReference<String> localPlayerNameRef_;

    /**
     * A reference to the server password or {@code null} if the network is not
     * connected.
     */
    private final AtomicReference<SecureString> passwordRef_;

    /**
     * The collection of service handlers associated with each player. The key
     * is the player name; the value is the service handler associated with the
     * player.
     */
    private final ConcurrentMap<String, IService> serviceHandlers_;

    /** The table to be attached to the network. */
    @SuppressWarnings( "unused" )
    private final ITable table_;

    /**
     * A reference to the active transport layer or {@code null} if the network
     * is not connected.
     */
    private final AtomicReference<ITransportLayer> transportLayerRef_;

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
     *         If {@code table} is {@code null}.
     */
    NetworkTable(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final ITransportLayerFactory transportLayerFactory )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$
        assert transportLayerFactory != null;

        connectionStateRef_ = new AtomicReference<ConnectionState>( ConnectionState.DISCONNECTED );
        listeners_ = new CopyOnWriteArrayList<INetworkTableListener>();
        localPlayerNameRef_ = new AtomicReference<String>( null );
        passwordRef_ = new AtomicReference<SecureString>( null );
        serviceHandlers_ = new ConcurrentHashMap<String, IService>();
        table_ = table;
        transportLayerRef_ = new AtomicReference<ITransportLayer>( null );
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
     * @param transportLayer
     *        The transport layer used to establish the connection; must not be
     *        {@code null}.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If the connection cannot be established or the network is already
     *         connected.
     */
    private void connect(
        /* @NonNull */
        final INetworkTableConfiguration configuration,
        /* @NonNull */
        final ITransportLayer transportLayer )
        throws NetworkTableException
    {
        assert configuration != null;
        assert transportLayer != null;

        if( connectionStateRef_.compareAndSet( ConnectionState.DISCONNECTED, ConnectionState.CONNECTING ) )
        {
            playerConnected( configuration.getLocalPlayerName(), new LocalService() );
            localPlayerNameRef_.set( configuration.getLocalPlayerName() );
            passwordRef_.set( configuration.getPassword() );
            transportLayer.open( configuration.getHostName(), configuration.getPort() );
            transportLayerRef_.set( transportLayer );
            connectionStateRef_.set( ConnectionState.CONNECTED );
            fireNetworkConnected();
        }
        else
        {
            throw new NetworkTableException( Messages.NetworkTable_connect_networkConnected );
        }
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#disconnect()
     */
    @Override
    public void disconnect()
    {
        if( connectionStateRef_.compareAndSet( ConnectionState.CONNECTED, ConnectionState.DISCONNECTING ) )
        {
            final ITransportLayer transportLayer = transportLayerRef_.getAndSet( null );
            transportLayer.close();
            connectionStateRef_.set( ConnectionState.DISCONNECTED );
            final SecureString password = passwordRef_.getAndSet( null );
            password.dispose();
            // TODO: need to gracefully shut down all remote clients
            playerDisconnected( localPlayerNameRef_.getAndSet( null ) );
            serviceHandlers_.clear();
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
     * Gets the local player name.
     * 
     * @return The local player name or {@code null} if the network is not
     *         connected.
     */
    /* @Nullable */
    String getLocalPlayerName()
    {
        return localPlayerNameRef_.get();
    }

    /**
     * Gets the server password.
     * 
     * @return The server password or {@code null} if the network is not
     *         connected.
     */
    /* @Nullable */
    SecureString getPassword()
    {
        final SecureString password = passwordRef_.get();
        return (password != null) ? new SecureString( password ) : null;
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

        connect( configuration, transportLayerFactory_.createPassiveTransportLayer( new ITransportLayerContext()
        {
            @Override
            public IService createService()
            {
                return new ServerService( NetworkTable.this );
            }

            @Override
            public void transportLayerDisconnected()
            {
                disconnect();
            }
        } ) );
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

        connect( configuration, transportLayerFactory_.createActiveTransportLayer( new ITransportLayerContext()
        {
            @Override
            public IService createService()
            {
                return new ClientService( NetworkTable.this );
            }

            @Override
            public void transportLayerDisconnected()
            {
                disconnect();
            }
        } ) );
    }

    /**
     * Invoked when a remote player has connected.
     * 
     * @param playerName
     *        The name of the remote player that has connected; must not be
     *        {@code null}.
     * @param serviceHandler
     *        The service handler associated with the remote player; must not be
     *        {@code null}.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    void playerConnected(
        /* @NonNull */
        final String playerName,
        /* @NonNull */
        final IService serviceHandler )
        throws NetworkTableException
    {
        assert playerName != null;
        assert serviceHandler != null;

        if( serviceHandlers_.putIfAbsent( playerName, serviceHandler ) == null )
        {
            Loggers.getDefaultLogger().info( Messages.NetworkTable_playerConnected_playerConnected( playerName ) );
        }
        else
        {
            throw new NetworkTableException( Messages.NetworkTable_playerConnected_playerRegistered );
        }
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

        if( serviceHandlers_.remove( playerName ) != null )
        {
            Loggers.getDefaultLogger().info( Messages.NetworkTable_playerDisconnected_playerDisconnected( playerName ) );
        }
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
