/*
 * ServerNode.java
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
 * Created on Apr 9, 2011 at 10:45:14 PM.
 */

package org.gamegineer.table.internal.net.impl.node.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.core.ITableEnvironmentFactory;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.internal.net.impl.Activator;
import org.gamegineer.table.internal.net.impl.Debug;
import org.gamegineer.table.internal.net.impl.ITableNetworkController;
import org.gamegineer.table.internal.net.impl.Loggers;
import org.gamegineer.table.internal.net.impl.Player;
import org.gamegineer.table.internal.net.impl.node.AbstractNode;
import org.gamegineer.table.internal.net.impl.node.ComponentIncrement;
import org.gamegineer.table.internal.net.impl.node.INetworkTable;
import org.gamegineer.table.internal.net.impl.node.INodeLayer;
import org.gamegineer.table.internal.net.impl.node.ITableManager;
import org.gamegineer.table.internal.net.impl.node.NetworkTableUtils;
import org.gamegineer.table.internal.net.impl.node.ThreadPlayer;
import org.gamegineer.table.internal.net.impl.transport.IService;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayer;
import org.gamegineer.table.internal.net.impl.transport.TransportException;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.PlayerRole;
import org.gamegineer.table.net.TableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A server node in a table network.
 */
@NotThreadSafe
public final class ServerNode
    extends AbstractNode<@NonNull IRemoteClientNode>
    implements IServerNode
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The master table for the table network. */
    private @Nullable ITable masterTable_;

    /**
     * The collection of players connected to the table network. The key is the
     * player name. The value is the player.
     */
    private final Map<String, Player> players_;

    /** The table manager. */
    private final ITableManager tableManager_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServerNode} class.
     * 
     * @param nodeLayer
     *        The node layer.
     * @param tableNetworkController
     *        The table network controller.
     */
    private ServerNode(
        final INodeLayer nodeLayer,
        final ITableNetworkController tableNetworkController )
    {
        super( nodeLayer, tableNetworkController );

        masterTable_ = null;
        players_ = new HashMap<>();
        tableManager_ = new ServerTableManager();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Binds the specified player to the table network.
     * 
     * @param player
     *        The player to bind to the table network.
     */
    private void bindPlayer(
        final Player player )
    {
        assert !players_.containsKey( player.getName() );
        players_.put( player.getName(), player );
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Player '%s' has connected", player.getName() ) ); //$NON-NLS-1$
        notifyPlayersUpdated();
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#cancelControlRequest()
     * @see org.gamegineer.table.internal.net.impl.node.server.IServerNode#cancelControlRequest()
     */
    @Override
    public void cancelControlRequest()
    {
        assert isNodeLayerThread();

        final String requestingPlayerName = ThreadPlayer.getPlayerName();
        assert requestingPlayerName != null;

        final Player requestingPlayer = players_.get( requestingPlayerName );
        if( (requestingPlayer == null) || !requestingPlayer.hasRole( PlayerRole.EDITOR_REQUESTER ) )
        {
            return;
        }

        requestingPlayer.removeRoles( EnumSet.of( PlayerRole.EDITOR_REQUESTER ) );

        notifyPlayersUpdated();
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

        initializeMasterTable( configuration.getLocalTable() );
        final Player player = new Player( getPlayerName() );
        player.addRoles( EnumSet.of( PlayerRole.EDITOR, PlayerRole.HOST, PlayerRole.LOCAL ) );
        bindPlayer( player );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractNode#createTableManagerDecoratorForLocalNetworkTable(org.gamegineer.table.internal.net.impl.node.ITableManager)
     */
    @Override
    protected ITableManager createTableManagerDecoratorForLocalNetworkTable(
        final ITableManager tableManager )
    {
        assert isNodeLayerThread();

        return new ServerTableManagerDecorator( tableManager, getPlayerName() );
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
            return getTableNetworkController().getTransportLayerFactory().createPassiveTransportLayer( new AbstractTransportLayerContext()
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

                            return new RemoteClientNode( getNodeLayer(), ServerNode.this );
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
     * @see org.gamegineer.table.internal.net.impl.node.AbstractNode#disconnected()
     */
    @Override
    protected void disconnected()
    {
        assert isNodeLayerThread();

        super.disconnected();

        unbindPlayer( getPlayerName() );
        masterTable_ = null;
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
    public @Nullable Player getPlayer()
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
     * @see org.gamegineer.table.internal.net.impl.node.server.IServerNode#giveControl(java.lang.String)
     */
    @Override
    public void giveControl(
        final String playerName )
    {
        assert isNodeLayerThread();

        final String requestingPlayerName = ThreadPlayer.getPlayerName();
        assert requestingPlayerName != null;

        final Player player = players_.get( playerName );
        if( player == null )
        {
            return;
        }

        final Player requestingPlayer = players_.get( requestingPlayerName );
        if( (requestingPlayer == null) || !requestingPlayer.hasRole( PlayerRole.EDITOR ) )
        {
            return;
        }

        requestingPlayer.removeRoles( EnumSet.of( PlayerRole.EDITOR ) );
        player.removeRoles( EnumSet.of( PlayerRole.EDITOR_REQUESTER ) );
        player.addRoles( EnumSet.of( PlayerRole.EDITOR ) );

        notifyPlayersUpdated();
    }

    /**
     * Initializes the master table for the table network using the specified
     * table.
     * 
     * @param table
     *        The table used to specify the master table.
     * 
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If an error occurs.
     */
    private void initializeMasterTable(
        final ITable table )
        throws TableNetworkException
    {
        final ITableEnvironmentFactory tableEnvironmentFactory = Activator.getDefault().getTableEnvironmentFactory();
        if( tableEnvironmentFactory == null )
        {
            throw new TableNetworkException( TableNetworkError.UNSPECIFIED_ERROR, NonNlsMessages.ServerNode_initializeMasterTable_tableEnvironmentFactoryNotAvailable );
        }

        final ITableEnvironment tableEnvironment = tableEnvironmentFactory.createTableEnvironment( new SingleThreadedTableEnvironmentContext() );
        final ITable masterTable = tableEnvironment.createTable();
        try
        {
            masterTable.setMemento( table.createMemento() );
        }
        catch( final MementoException e )
        {
            throw new TableNetworkException( TableNetworkError.UNSPECIFIED_ERROR, e );
        }

        masterTable_ = masterTable;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.server.IServerNode#isPlayerConnected(java.lang.String)
     */
    @Override
    public boolean isPlayerConnected(
        final String playerName )
    {
        assert isNodeLayerThread();

        assertConnected();
        return players_.containsKey( playerName );
    }

    /**
     * Notifies all remote nodes and the local table network controller that the
     * collection of players connected to the table network has been updated.
     */
    private void notifyPlayersUpdated()
    {
        final Collection<IPlayer> players = getPlayers();
        for( final IRemoteClientNode remoteNode : getRemoteNodes() )
        {
            remoteNode.setPlayers( players );
        }

        getTableNetworkController().playersUpdated();
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractNode#remoteNodeBound(org.gamegineer.table.internal.net.impl.node.IRemoteNode)
     */
    @Override
    protected void remoteNodeBound(
        final IRemoteClientNode remoteNode )
    {
        assert isNodeLayerThread();

        super.remoteNodeBound( remoteNode );

        bindPlayer( new Player( remoteNode.getPlayerName() ) );
        synchronizeRemoteTable( remoteNode );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractNode#remoteNodeUnbound(org.gamegineer.table.internal.net.impl.node.IRemoteNode)
     */
    @Override
    protected void remoteNodeUnbound(
        final IRemoteClientNode remoteNode )
    {
        assert isNodeLayerThread();

        super.remoteNodeUnbound( remoteNode );

        unbindPlayer( remoteNode.getPlayerName() );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.INodeController#requestControl()
     * @see org.gamegineer.table.internal.net.impl.node.server.IServerNode#requestControl()
     */
    @Override
    public void requestControl()
    {
        assert isNodeLayerThread();

        final String requestingPlayerName = ThreadPlayer.getPlayerName();
        assert requestingPlayerName != null;

        final Player requestingPlayer = players_.get( requestingPlayerName );
        if( (requestingPlayer == null) //
            || requestingPlayer.hasRole( PlayerRole.EDITOR ) //
            || requestingPlayer.hasRole( PlayerRole.EDITOR_REQUESTER ) )
        {
            return;
        }

        requestingPlayer.addRoles( EnumSet.of( PlayerRole.EDITOR_REQUESTER ) );

        notifyPlayersUpdated();
    }

    /**
     * Synchronizes the state of the table at the specified remote node with the
     * master table.
     * 
     * @param remoteNode
     *        The remote node.
     */
    private void synchronizeRemoteTable(
        final IRemoteClientNode remoteNode )
    {
        final ITable masterTable = masterTable_;
        assert masterTable != null;
        remoteNode.getTable().setTableState( masterTable.createMemento() );
    }

    /**
     * Unbinds the specified player from the table network.
     * 
     * @param playerName
     *        The name of the player to unbind from the table network.
     */
    private void unbindPlayer(
        final String playerName )
    {
        final Player player = players_.remove( playerName );
        assert player != null;
        if( player.hasRole( PlayerRole.EDITOR ) )
        {
            final Player hostPlayer = getPlayer();
            if( hostPlayer != null )
            {
                assert hostPlayer.hasRole( PlayerRole.HOST );
                hostPlayer.addRoles( EnumSet.of( PlayerRole.EDITOR ) );
            }
        }

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Player '%s' has disconnected", playerName ) ); //$NON-NLS-1$
        notifyPlayersUpdated();
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A factory for creating instances of {@link ServerNode}.
     */
    @Immutable
    public static final class Factory
        extends AbstractFactory<ServerNode>
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
        protected ServerNode createNode(
            final INodeLayer nodeLayer,
            final ITableNetworkController tableNetworkController )
        {
            return new ServerNode( nodeLayer, tableNetworkController );
        }
    }

    /**
     * Implementation of {@link ITableManager} that keeps the master table
     * synchronized, in addition to all tables connected to the node.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
    private final class ServerTableManager
        extends TableManager
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ServerTableManager} class.
         */
        ServerTableManager()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.net.impl.node.AbstractNode.TableManager#incrementComponentState(org.gamegineer.table.internal.net.impl.node.INetworkTable, org.gamegineer.table.core.ComponentPath, org.gamegineer.table.internal.net.impl.node.ComponentIncrement)
         */
        @Override
        public void incrementComponentState(
            final INetworkTable sourceTable,
            final ComponentPath componentPath,
            final ComponentIncrement componentIncrement )
        {
            if( verifyRequestingPlayerIsEditor() )
            {
                assert masterTable_ != null;
                NetworkTableUtils.incrementComponentState( masterTable_, componentPath, componentIncrement );
                super.incrementComponentState( sourceTable, componentPath, componentIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.impl.node.AbstractNode.TableManager#setTableState(org.gamegineer.table.internal.net.impl.node.INetworkTable, java.lang.Object)
         */
        @Override
        public void setTableState(
            final INetworkTable sourceTable,
            final Object tableMemento )
        {
            if( verifyRequestingPlayerIsEditor() )
            {
                assert masterTable_ != null;
                NetworkTableUtils.setTableState( masterTable_, tableMemento );
                super.setTableState( sourceTable, tableMemento );
            }
        }

        /**
         * Verifies the requesting player is the network table editor.
         * 
         * @return {@code true} if the requesting player is the network table
         *         editor; otherwise {@code false}.
         */
        private boolean verifyRequestingPlayerIsEditor()
        {
            final String requestingPlayerName = ThreadPlayer.getPlayerName();
            assert requestingPlayerName != null;
            final Player requestingPlayer = players_.get( requestingPlayerName );
            if( (requestingPlayer != null) && requestingPlayer.hasRole( PlayerRole.EDITOR ) )
            {
                return true;
            }

            Loggers.getDefaultLogger().warning( NonNlsMessages.ServerNode_networkTableModification_playerNotEditor( requestingPlayerName ) );
            return false;
        }
    }

    /**
     * A table manager decorator to be used by the server local network table.
     */
    @Immutable
    private final class ServerTableManagerDecorator
        implements ITableManager
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The name of the local player. */
        private final String localPlayerName_;

        /** The decorated table manager. */
        private final ITableManager tableManagerDecoratee_;

        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code ServerTableManagerDecorator}
         * class.
         * 
         * @param tableManagerDecoratee
         *        The decorated table manager.
         * @param localPlayerName
         *        The name of the local player.
         */
        ServerTableManagerDecorator(
            final ITableManager tableManagerDecoratee,
            final String localPlayerName )
        {
            localPlayerName_ = localPlayerName;
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
            ThreadPlayer.setPlayerName( localPlayerName_ );
            try
            {
                tableManagerDecoratee_.incrementComponentState( sourceTable, componentPath, componentIncrement );
            }
            finally
            {
                ThreadPlayer.setPlayerName( null );
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
            ThreadPlayer.setPlayerName( localPlayerName_ );
            try
            {
                tableManagerDecoratee_.setTableState( sourceTable, tableMemento );
            }
            finally
            {
                ThreadPlayer.setPlayerName( null );
            }
        }
    }
}
