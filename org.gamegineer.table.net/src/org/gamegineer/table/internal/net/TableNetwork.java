/*
 * TableNetwork.java
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.net.transport.ITransportLayerFactory;
import org.gamegineer.table.internal.net.transport.tcp.TcpTransportLayerFactory;
import org.gamegineer.table.net.ITableNetwork;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.ITableNetworkListener;
import org.gamegineer.table.net.TableNetworkDisconnectedEvent;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkEvent;
import org.gamegineer.table.net.TableNetworkException;

/**
 * Implementation of {@link org.gamegineer.table.net.ITableNetwork}.
 */
@ThreadSafe
public final class TableNetwork
    implements ITableNetwork, ITableNetworkController
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** A reference to the connection state. */
    private final AtomicReference<ConnectionState> connectionStateRef_;

    /** The collection of table network listeners. */
    private final CopyOnWriteArrayList<ITableNetworkListener> listeners_;

    /**
     * A reference to the local table network node controller or {@code null} if
     * the table network is not connected.
     */
    private final AtomicReference<ITableNetworkNodeController> nodeControllerRef_;

    /** The table network node factory. */
    private final ITableNetworkNodeFactory nodeFactory_;

    /** The table to be attached to the network. */
    @SuppressWarnings( "unused" )
    private final ITable table_;

    /** The table network transport layer factory. */
    private final ITransportLayerFactory transportLayerFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetwork} class.
     * 
     * @param table
     *        The table to be attached to the network; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} is {@code null}.
     */
    public TableNetwork(
        /* @NonNull */
        final ITable table )
    {
        this( table, new DefaultTableNetworkNodeFactory(), new TcpTransportLayerFactory() );
    }

    /**
     * Initializes a new instance of the {@code TableNetwork} class using the
     * specified node factory and transport layer factory.
     * 
     * <p>
     * This constructor is only intended to support testing.
     * </p>
     * 
     * @param table
     *        The table to be attached to the network; must not be {@code null}.
     * @param nodeFactory
     *        The table network node factory; must not be {@code null}.
     * @param transportLayerFactory
     *        The table network transport layer factory; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table}, {@code nodeFactory}, or {@code
     *         transportLayerFactory} is {@code null}.
     */
    public TableNetwork(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final ITableNetworkNodeFactory nodeFactory,
        /* @NonNull */
        final ITransportLayerFactory transportLayerFactory )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$
        assertArgumentNotNull( nodeFactory, "nodeFactory" ); //$NON-NLS-1$
        assertArgumentNotNull( transportLayerFactory, "transportLayerFactory" ); //$NON-NLS-1$

        connectionStateRef_ = new AtomicReference<ConnectionState>( ConnectionState.DISCONNECTED );
        listeners_ = new CopyOnWriteArrayList<ITableNetworkListener>();
        nodeControllerRef_ = new AtomicReference<ITableNetworkNodeController>( null );
        nodeFactory_ = nodeFactory;
        table_ = table;
        transportLayerFactory_ = transportLayerFactory;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.ITableNetwork#addTableNetworkListener(org.gamegineer.table.net.ITableNetworkListener)
     */
    @Override
    public void addTableNetworkListener(
        final ITableNetworkListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", Messages.TableNetwork_addTableNetworkListener_listener_registered ); //$NON-NLS-1$
    }

    /**
     * Connects the specified table network node to the table network defined by
     * the specified configuration.
     * 
     * @param configuration
     *        The table network configuration; must not be {@code null}.
     * @param nodeController
     *        The control interface of the table network node; must not be
     *        {@code null}.
     * 
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the connection cannot be established or the table network is
     *         already connected.
     */
    private void connect(
        /* @NonNull */
        final ITableNetworkConfiguration configuration,
        /* @NonNull */
        final ITableNetworkNodeController nodeController )
        throws TableNetworkException
    {
        assert configuration != null;
        assert nodeController != null;

        if( connectionStateRef_.compareAndSet( ConnectionState.DISCONNECTED, ConnectionState.CONNECTING ) )
        {
            try
            {
                nodeController.connect( configuration );
                nodeControllerRef_.set( nodeController );
                connectionStateRef_.set( ConnectionState.CONNECTED );
                fireTableNetworkConnected();
            }
            catch( final TableNetworkException e )
            {
                connectionStateRef_.set( ConnectionState.DISCONNECTED );
                throw e;
            }
        }
        else
        {
            throw new TableNetworkException( TableNetworkError.ILLEGAL_CONNECTION_STATE );
        }
    }

    /*
     * @see org.gamegineer.table.net.ITableNetwork#disconnect()
     */
    @Override
    public void disconnect()
    {
        disconnect( null );
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableNetworkController#disconnect(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    public void disconnect(
        final TableNetworkError error )
    {
        if( connectionStateRef_.compareAndSet( ConnectionState.CONNECTED, ConnectionState.DISCONNECTING ) )
        {
            final ITableNetworkNodeController nodeController = nodeControllerRef_.getAndSet( null );
            nodeController.disconnect();
            connectionStateRef_.set( ConnectionState.DISCONNECTED );
            fireTableNetworkDisconnected( error );
        }
    }

    /**
     * Fires a table network connected event.
     */
    private void fireTableNetworkConnected()
    {
        final TableNetworkEvent event = new TableNetworkEvent( this );
        for( final ITableNetworkListener listener : listeners_ )
        {
            try
            {
                listener.tableNetworkConnected( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.TableNetwork_tableNetworkConnected_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a table network disconnected event.
     * 
     * @param error
     *        The error that caused the table network to be disconnected or
     *        {@code null} if the table network was disconnected normally.
     */
    private void fireTableNetworkDisconnected(
        /* @Nullable */
        final TableNetworkError error )
    {
        final TableNetworkDisconnectedEvent event = new TableNetworkDisconnectedEvent( this, error );
        for( final ITableNetworkListener listener : listeners_ )
        {
            try
            {
                listener.tableNetworkDisconnected( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.TableNetwork_tableNetworkDisconnected_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a table network players updated event.
     */
    void fireTableNetworkPlayersUpdated()
    {
        final TableNetworkEvent event = new TableNetworkEvent( this );
        for( final ITableNetworkListener listener : listeners_ )
        {
            try
            {
                listener.tableNetworkPlayersUpdated( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.TableNetwork_tableNetworkPlayersUpdated_unexpectedException, e );
            }
        }
    }

    /*
     * @see org.gamegineer.table.net.ITableNetwork#getPlayers()
     */
    @Override
    public Collection<String> getPlayers()
    {
        final ITableNetworkNodeController nodeController = nodeControllerRef_.get();
        if( nodeController != null )
        {
            return nodeController.getPlayers();
        }

        return new ArrayList<String>();
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableNetworkController#getTransportLayerFactory()
     */
    @Override
    public ITransportLayerFactory getTransportLayerFactory()
    {
        return transportLayerFactory_;
    }

    /*
     * @see org.gamegineer.table.net.ITableNetwork#host(org.gamegineer.table.net.ITableNetworkConfiguration)
     */
    @Override
    public void host(
        final ITableNetworkConfiguration configuration )
        throws TableNetworkException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        connect( configuration, nodeFactory_.createServerTableNetworkNode( this ) );
    }

    /*
     * @see org.gamegineer.table.net.ITableNetwork#isConnected()
     */
    @Override
    public boolean isConnected()
    {
        return connectionStateRef_.get() == ConnectionState.CONNECTED;
    }

    /*
     * @see org.gamegineer.table.net.ITableNetwork#join(org.gamegineer.table.net.ITableNetworkConfiguration)
     */
    @Override
    public void join(
        final ITableNetworkConfiguration configuration )
        throws TableNetworkException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        connect( configuration, nodeFactory_.createClientTableNetworkNode( this ) );
    }

    /*
     * @see org.gamegineer.table.net.ITableNetwork#removeTableNetworkListener(org.gamegineer.table.net.ITableNetworkListener)
     */
    @Override
    public void removeTableNetworkListener(
        final ITableNetworkListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", Messages.TableNetwork_removeTableNetworkListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableNetworkController#playersUpdated()
     */
    @Override
    public void playersUpdated()
    {
        if( isConnected() )
        {
            fireTableNetworkPlayersUpdated();
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The possible table network connection states.
     */
    private enum ConnectionState
    {
        /** The table network is processing a connect request. */
        CONNECTING,

        /** The table network is connected. */
        CONNECTED,

        /** The table network is processing a disconnect request. */
        DISCONNECTING,

        /** The table network is disconnected. */
        DISCONNECTED;
    }
}