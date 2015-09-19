/*
 * ClientNode.java
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
 * Created on Apr 9, 2011 at 10:45:05 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.internal.net.impl.ITableNetworkController;
import org.gamegineer.table.internal.net.impl.Player;
import org.gamegineer.table.internal.net.impl.node.AbstractNode;
import org.gamegineer.table.internal.net.impl.node.ComponentIncrement;
import org.gamegineer.table.internal.net.impl.node.INetworkTable;
import org.gamegineer.table.internal.net.impl.node.INodeLayer;
import org.gamegineer.table.internal.net.impl.node.ITableManager;
import org.gamegineer.table.internal.net.impl.transport.IService;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayer;
import org.gamegineer.table.internal.net.impl.transport.TransportException;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.PlayerRole;
import org.gamegineer.table.net.TableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A client node in a table network.
 */
@NotThreadSafe
public final class ClientNode
    extends AbstractNode<@NonNull IRemoteServerNode>
    implements IClientNode
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The condition variable used to signal when the handshake is complete. */
    private final Condition handshakeCondition_;

    /**
     * The error that occurred during the handshake or {@code null} if no error
     * occurred.
     */
    @GuardedBy( "handshakeLock_" )
    private @Nullable TableNetworkError handshakeError_;

    /** The handshake lock. */
    private final Lock handshakeLock_;

    /** Indicates the handshake is complete. */
    @GuardedBy( "handshakeLock_" )
    private boolean isHandshakeComplete_;

    /**
     * The collection of players connected to the table network. The key is the
     * player name. The value is the player.
     */
    private final Map<String, IPlayer> players_;

    /** The table manager. */
    private final ITableManager tableManager_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientNode} class.
     * 
     * @param nodeLayer
     *        The node layer; must not be {@code null}.
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     */
    private ClientNode(
        final INodeLayer nodeLayer,
        final ITableNetworkController tableNetworkController )
    {
        super( nodeLayer, tableNetworkController );

        handshakeLock_ = new ReentrantLock();
        handshakeCondition_ = handshakeLock_.newCondition();
        handshakeError_ = null;
        isHandshakeComplete_ = false;
        players_ = new HashMap<>();
        tableManager_ = new ClientTableManager();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#cancelControlRequest()
     */
    @Override
    public void cancelControlRequest()
    {
        assert isNodeLayerThread();

        getRemoteServerNode().cancelControlRequest();
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractNode#connected()
     */
    @Override
    protected void connected()
        throws TableNetworkException
    {
        assert !isNodeLayerThread();

        super.connected();

        handshakeLock_.lock();
        try
        {
            while( !isHandshakeComplete_ )
            {
                try
                {
                    if( !handshakeCondition_.await( 30L, TimeUnit.SECONDS ) )
                    {
                        throw new TableNetworkException( TableNetworkError.TIME_OUT, NonNlsMessages.ClientNode_handshake_timedOut );
                    }
                }
                catch( final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                    throw new TableNetworkException( TableNetworkError.INTERRUPTED, NonNlsMessages.ClientNode_handshake_interrupted, e );
                }
            }

            if( handshakeError_ != null )
            {
                throw new TableNetworkException( handshakeError_ );
            }
        }
        finally
        {
            handshakeLock_.unlock();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractNode#connecting(org.gamegineer.table.net.TableNetworkConfiguration)
     */
    @Override
    protected void connecting(
        final TableNetworkConfiguration configuration )
        throws TableNetworkException
    {
        assert isNodeLayerThread();

        super.connecting( configuration );

        // Temporarily add local player until we receive the player list from the server
        final Player player = new Player( getPlayerName() );
        player.addRoles( EnumSet.of( PlayerRole.LOCAL ) );
        players_.put( player.getName(), player );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractNode#createTableManagerDecoratorForLocalNetworkTable(org.gamegineer.table.internal.net.impl.node.ITableManager)
     */
    @Override
    protected ITableManager createTableManagerDecoratorForLocalNetworkTable(
        final ITableManager tableManager )
    {
        assert isNodeLayerThread();

        return new ClientTableManagerDecorator( tableManager );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractNode#createTransportLayer()
     */
    @Override
    protected ITransportLayer createTransportLayer()
        throws TableNetworkException
    {
        assert !isNodeLayerThread();

        try
        {
            return getTableNetworkController().getTransportLayerFactory().createActiveTransportLayer( new AbstractTransportLayerContext()
            {
                @Override
                public IService createService()
                {
                    return new AbstractServiceProxy()
                    {
                        @Override
                        @SuppressWarnings( "synthetic-access" )
                        protected IService createActualService()
                        {
                            assert isNodeLayerThread();

                            return new RemoteServerNode( getNodeLayer(), ClientNode.this );
                        }
                    };
                }
            } );
        }
        catch( final TransportException e )
        {
            throw new TableNetworkException( TableNetworkError.TRANSPORT_ERROR, e );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractNode#disconnecting(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    protected void disconnecting(
        final @Nullable TableNetworkError error )
    {
        assert isNodeLayerThread();

        super.disconnecting( error );

        setHandshakeComplete( error );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractNode#dispose()
     */
    @Override
    protected void dispose()
    {
        assert isNodeLayerThread();

        players_.clear();

        super.dispose();
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#getPlayer()
     */
    @Override
    public @Nullable IPlayer getPlayer()
    {
        assert isNodeLayerThread();

        return isConnected() ? players_.get( getPlayerName() ) : null;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#getPlayers()
     */
    @Override
    public Collection<IPlayer> getPlayers()
    {
        assert isNodeLayerThread();

        return new ArrayList<>( players_.values() );
    }

    /**
     * Gets the remote node associated with the server.
     * 
     * @return The remote node associated with the server; never {@code null}.
     */
    private IRemoteServerNode getRemoteServerNode()
    {
        final IRemoteServerNode remoteNode = getRemoteNode( ClientNodeConstants.SERVER_PLAYER_NAME );
        assert remoteNode != null;
        return remoteNode;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INode#getTableManager()
     */
    @Override
    public ITableManager getTableManager()
    {
        return tableManager_;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#giveControl(java.lang.String)
     */
    @Override
    public void giveControl(
        final String playerName )
    {
        assert isNodeLayerThread();

        getRemoteServerNode().giveControl( playerName );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractNode#remoteNodeBound(org.gamegineer.table.internal.net.impl.node.IRemoteNode)
     */
    @Override
    protected void remoteNodeBound(
        final IRemoteServerNode remoteNode )
    {
        assert isNodeLayerThread();

        super.remoteNodeBound( remoteNode );

        setHandshakeComplete( null );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#requestControl()
     */
    @Override
    public void requestControl()
    {
        assert isNodeLayerThread();

        getRemoteServerNode().requestControl();
    }

    /**
     * Sets the condition that indicates the handshake is complete.
     * 
     * <p>
     * This method may be called from any thread. It should not be called
     * outside of this class except when required for testing.
     * </p>
     * 
     * @param error
     *        The error that caused the handshake to fail or {@code null} if the
     *        handshake completed successfully.
     */
    void setHandshakeComplete(
        final @Nullable TableNetworkError error )
    {
        handshakeLock_.lock();
        try
        {
            if( !isHandshakeComplete_ )
            {
                isHandshakeComplete_ = true;
                handshakeError_ = error;
                handshakeCondition_.signalAll();
            }
        }
        finally
        {
            handshakeLock_.unlock();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.client.IClientNode#setPlayers(java.util.Collection)
     */
    @Override
    public void setPlayers(
        final Collection<IPlayer> players )
    {
        assert isNodeLayerThread();

        assertConnected();
        players_.clear();
        for( final IPlayer player : players )
        {
            players_.put( player.getName(), player );
        }
        getTableNetworkController().playersUpdated();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Implementation of {@link ITableManager} that keeps the client node
     * synchronized with the master table on the server.
     */
    @Immutable
    private final class ClientTableManager
        extends TableManager
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ClientTableManager} class.
         */
        ClientTableManager()
        {
        }
    }

    /**
     * A table manager decorator to be used by the client local network table.
     */
    @Immutable
    private final class ClientTableManagerDecorator
        implements ITableManager
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The decorated table manager. */
        private final ITableManager tableManagerDecoratee_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ClientTableManagerDecorator}
         * class.
         * 
         * @param tableManagerDecoratee
         *        The decorated table manager; must not be {@code null}.
         */
        ClientTableManagerDecorator(
            final ITableManager tableManagerDecoratee )
        {
            tableManagerDecoratee_ = tableManagerDecoratee;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.net.impl.node.ITableManager#incrementComponentState(org.gamegineer.table.internal.net.impl.node.INetworkTable, org.gamegineer.table.core.ComponentPath, org.gamegineer.table.internal.net.impl.node.ComponentIncrement)
         */
        @Override
        public void incrementComponentState(
            final INetworkTable sourceTable,
            final ComponentPath componentPath,
            final ComponentIncrement componentIncrement )
        {
            final IPlayer player = getPlayer();
            assert player != null;
            if( player.hasRole( PlayerRole.EDITOR ) )
            {
                tableManagerDecoratee_.incrementComponentState( sourceTable, componentPath, componentIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.impl.node.ITableManager#setTableState(org.gamegineer.table.internal.net.impl.node.INetworkTable, java.lang.Object)
         */
        @Override
        public void setTableState(
            final INetworkTable sourceTable,
            final Object tableMemento )
        {
            final IPlayer player = getPlayer();
            assert player != null;
            if( player.hasRole( PlayerRole.EDITOR ) )
            {
                tableManagerDecoratee_.setTableState( sourceTable, tableMemento );
            }
        }
    }

    /**
     * A factory for creating instances of {@link ClientNode}.
     */
    @Immutable
    public static final class Factory
        extends AbstractFactory<ClientNode>
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code Factory} class.
         */
        public Factory()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.net.impl.node.AbstractNode.AbstractFactory#createNode(org.gamegineer.table.internal.net.impl.node.INodeLayer, org.gamegineer.table.internal.net.impl.ITableNetworkController)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        protected ClientNode createNode(
            final INodeLayer nodeLayer,
            final ITableNetworkController tableNetworkController )
        {
            return new ClientNode( nodeLayer, tableNetworkController );
        }
    }
}
