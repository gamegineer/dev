/*
 * TableNetwork.java
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
 * Created on Nov 6, 2010 at 2:01:23 PM.
 */

package org.gamegineer.table.internal.net.impl;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.net.impl.node.DefaultNodeFactory;
import org.gamegineer.table.internal.net.impl.node.INodeController;
import org.gamegineer.table.internal.net.impl.node.INodeFactory;
import org.gamegineer.table.internal.net.impl.node.ThreadPlayer;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayerFactory;
import org.gamegineer.table.internal.net.impl.transport.tcp.TcpTransportLayerFactory;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetwork;
import org.gamegineer.table.net.ITableNetworkListener;
import org.gamegineer.table.net.TableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkDisconnectedEvent;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkEvent;
import org.gamegineer.table.net.TableNetworkException;

/**
 * Implementation of {@link ITableNetwork}.
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
    private final AtomicReference<@Nullable INodeController> nodeControllerRef_;

    /** The table network node factory. */
    private final INodeFactory nodeFactory_;

    /** The table network transport layer factory. */
    private final ITransportLayerFactory transportLayerFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetwork} class.
     */
    public TableNetwork()
    {
        this( new DefaultNodeFactory(), new TcpTransportLayerFactory() );
    }

    /**
     * Initializes a new instance of the {@code TableNetwork} class using the
     * specified node factory and transport layer factory.
     * 
     * <p>
     * This constructor is only intended to support testing.
     * </p>
     * 
     * @param nodeFactory
     *        The table network node factory; must not be {@code null}.
     * @param transportLayerFactory
     *        The table network transport layer factory; must not be
     *        {@code null}.
     */
    TableNetwork(
        final INodeFactory nodeFactory,
        final ITransportLayerFactory transportLayerFactory )
    {
        connectionStateRef_ = new AtomicReference<>( ConnectionState.DISCONNECTED );
        listeners_ = new CopyOnWriteArrayList<>();
        nodeControllerRef_ = new AtomicReference<>( null );
        nodeFactory_ = nodeFactory;
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
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", NonNlsMessages.TableNetwork_addTableNetworkListener_listener_registered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.net.ITableNetwork#cancelControlRequest()
     */
    @Override
    public void cancelControlRequest()
    {
        final INodeController nodeController = nodeControllerRef_.get();
        if( nodeController != null )
        {
            final IPlayer nodeControllerPlayer = nodeController.getPlayer();
            assert nodeControllerPlayer != null;
            ThreadPlayer.setPlayerName( nodeControllerPlayer.getName() );
            try
            {
                nodeController.cancelControlRequest();
            }
            finally
            {
                ThreadPlayer.setPlayerName( null );
            }
        }
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
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the table network
     *         to connect.
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the connection cannot be established or the table network is
     *         already connected.
     */
    private void connect(
        final TableNetworkConfiguration configuration,
        final INodeController nodeController )
        throws TableNetworkException, InterruptedException
    {
        if( connectionStateRef_.compareAndSet( ConnectionState.DISCONNECTED, ConnectionState.CONNECTING ) )
        {
            try
            {
                nodeController.endConnect( nodeController.beginConnect( configuration ) );
                nodeControllerRef_.set( nodeController );
                connectionStateRef_.set( ConnectionState.CONNECTED );
                fireTableNetworkConnected();
            }
            catch( final TableNetworkException e )
            {
                connectionStateRef_.set( ConnectionState.DISCONNECTED );
                throw e;
            }
            catch( final InterruptedException e )
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
        throws InterruptedException
    {
        disconnect( null );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.ITableNetworkController#disconnect(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    public void disconnect(
        final @Nullable TableNetworkError error )
        throws InterruptedException
    {
        if( connectionStateRef_.compareAndSet( ConnectionState.CONNECTED, ConnectionState.DISCONNECTING ) )
        {
            final INodeController nodeController = nodeControllerRef_.getAndSet( null );
            try
            {
                if( nodeController != null )
                {
                    nodeController.endDisconnect( nodeController.beginDisconnect() );
                }
            }
            finally
            {
                connectionStateRef_.set( ConnectionState.DISCONNECTED );
                fireTableNetworkDisconnected( error );
            }
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableNetwork_tableNetworkConnected_unexpectedException, e );
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
        final @Nullable TableNetworkError error )
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableNetwork_tableNetworkDisconnected_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a table network players updated event.
     */
    private void fireTableNetworkPlayersUpdated()
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
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableNetwork_tableNetworkPlayersUpdated_unexpectedException, e );
            }
        }
    }

    /*
     * @see org.gamegineer.table.net.ITableNetwork#getLocalPlayer()
     */
    @Override
    public @Nullable IPlayer getLocalPlayer()
    {
        final INodeController nodeController = nodeControllerRef_.get();
        if( nodeController != null )
        {
            return nodeController.getPlayer();
        }

        return null;
    }

    /*
     * @see org.gamegineer.table.net.ITableNetwork#getPlayers()
     */
    @Override
    public Collection<IPlayer> getPlayers()
    {
        final INodeController nodeController = nodeControllerRef_.get();
        if( nodeController != null )
        {
            return nodeController.getPlayers();
        }

        return new ArrayList<>();
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.ITableNetworkController#getTransportLayerFactory()
     */
    @Override
    public ITransportLayerFactory getTransportLayerFactory()
    {
        return transportLayerFactory_;
    }

    /*
     * @see org.gamegineer.table.net.ITableNetwork#giveControl(org.gamegineer.table.net.IPlayer)
     */
    @Override
    public void giveControl(
        final IPlayer player )
    {
        final INodeController nodeController = nodeControllerRef_.get();
        if( nodeController != null )
        {
            final IPlayer nodeControllerPlayer = nodeController.getPlayer();
            assert nodeControllerPlayer != null;
            ThreadPlayer.setPlayerName( nodeControllerPlayer.getName() );
            try
            {
                nodeController.giveControl( player.getName() );
            }
            finally
            {
                ThreadPlayer.setPlayerName( null );
            }
        }
    }

    /*
     * @see org.gamegineer.table.net.ITableNetwork#host(org.gamegineer.table.net.TableNetworkConfiguration)
     */
    @Override
    public void host(
        final TableNetworkConfiguration configuration )
        throws TableNetworkException, InterruptedException
    {
        connect( configuration, nodeFactory_.createServerNode( this ) );
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
     * @see org.gamegineer.table.net.ITableNetwork#join(org.gamegineer.table.net.TableNetworkConfiguration)
     */
    @Override
    public void join(
        final TableNetworkConfiguration configuration )
        throws TableNetworkException, InterruptedException
    {
        connect( configuration, nodeFactory_.createClientNode( this ) );
    }

    /*
     * @see org.gamegineer.table.net.ITableNetwork#removeTableNetworkListener(org.gamegineer.table.net.ITableNetworkListener)
     */
    @Override
    public void removeTableNetworkListener(
        final ITableNetworkListener listener )
    {
        assertArgumentLegal( listeners_.remove( listener ), "listener", NonNlsMessages.TableNetwork_removeTableNetworkListener_listener_notRegistered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.ITableNetworkController#playersUpdated()
     */
    @Override
    public void playersUpdated()
    {
        if( isConnected() )
        {
            fireTableNetworkPlayersUpdated();
        }
    }

    /*
     * @see org.gamegineer.table.net.ITableNetwork#requestControl()
     */
    @Override
    public void requestControl()
    {
        final INodeController nodeController = nodeControllerRef_.get();
        if( nodeController != null )
        {
            final IPlayer nodeControllerPlayer = nodeController.getPlayer();
            assert nodeControllerPlayer != null;
            ThreadPlayer.setPlayerName( nodeControllerPlayer.getName() );
            try
            {
                nodeController.requestControl();
            }
            finally
            {
                ThreadPlayer.setPlayerName( null );
            }
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
