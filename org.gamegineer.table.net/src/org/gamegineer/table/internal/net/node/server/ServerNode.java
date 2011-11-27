/*
 * ServerNode.java
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
 * Created on Apr 9, 2011 at 10:45:14 PM.
 */

package org.gamegineer.table.internal.net.node.server;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TableFactory;
import org.gamegineer.table.internal.net.Debug;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.Player;
import org.gamegineer.table.internal.net.node.AbstractNode;
import org.gamegineer.table.internal.net.node.CardIncrement;
import org.gamegineer.table.internal.net.node.CardPileIncrement;
import org.gamegineer.table.internal.net.node.INetworkTable;
import org.gamegineer.table.internal.net.node.INodeLayer;
import org.gamegineer.table.internal.net.node.ITableManager;
import org.gamegineer.table.internal.net.node.NetworkTableUtils;
import org.gamegineer.table.internal.net.node.TableIncrement;
import org.gamegineer.table.internal.net.node.ThreadPlayer;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.internal.net.transport.TransportException;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.PlayerRole;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A server node in a table network.
 */
@NotThreadSafe
public final class ServerNode
    extends AbstractNode<IRemoteClientNode>
    implements IServerNode
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The master table for the table network. */
    private ITable masterTable_;

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
     *        The node layer; must not be {@code null}.
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code nodeLayer} or {@code tableNetworkController} is {@code
     *         null}.
     */
    private ServerNode(
        /* @NonNull */
        final INodeLayer nodeLayer,
        /* @NonNull */
        final ITableNetworkController tableNetworkController )
    {
        super( nodeLayer, tableNetworkController );

        masterTable_ = null;
        players_ = new HashMap<String, Player>();
        tableManager_ = new ServerTableManager();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Binds the specified player to the table network.
     * 
     * @param player
     *        The player to bind to the table network; must not be {@code null}.
     */
    private void bindPlayer(
        /* @NonNull */
        final Player player )
    {
        assert player != null;

        assert !players_.containsKey( player.getName() );
        players_.put( player.getName(), player );
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Player '%s' has connected", player.getName() ) ); //$NON-NLS-1$
        notifyPlayersUpdated();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#cancelControlRequest()
     * @see org.gamegineer.table.internal.net.node.server.IServerNode#cancelControlRequest()
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
     * @see org.gamegineer.table.internal.net.node.AbstractNode#connecting(org.gamegineer.table.net.ITableNetworkConfiguration)
     */
    @Override
    protected void connecting(
        final ITableNetworkConfiguration configuration )
        throws TableNetworkException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$
        assert isNodeLayerThread();

        super.connecting( configuration );

        initializeMasterTable( configuration.getLocalTable() );
        final Player player = new Player( getPlayerName() );
        player.addRoles( EnumSet.of( PlayerRole.EDITOR, PlayerRole.HOST, PlayerRole.LOCAL ) );
        bindPlayer( player );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNode#createTableManagerDecoratorForLocalNetworkTable(org.gamegineer.table.internal.net.node.ITableManager)
     */
    @Override
    protected ITableManager createTableManagerDecoratorForLocalNetworkTable(
        final ITableManager tableManager )
    {
        assertArgumentNotNull( tableManager, "tableManager" ); //$NON-NLS-1$
        assert isNodeLayerThread();

        return new ServerTableManagerDecorator( tableManager, getPlayerName() );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNode#createTransportLayer()
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
     * @see org.gamegineer.table.internal.net.node.AbstractNode#disconnected()
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
     * @see org.gamegineer.table.internal.net.node.AbstractNode#dispose()
     */
    @Override
    protected void dispose()
    {
        assert isNodeLayerThread();

        players_.clear();

        super.dispose();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#getPlayer()
     */
    @Override
    public Player getPlayer()
    {
        assert isNodeLayerThread();

        return isConnected() ? players_.get( getPlayerName() ) : null;
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#getPlayers()
     */
    @Override
    public Collection<IPlayer> getPlayers()
    {
        assert isNodeLayerThread();

        return new ArrayList<IPlayer>( players_.values() );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INode#getTableManager()
     */
    @Override
    public ITableManager getTableManager()
    {
        return tableManager_;
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#giveControl(java.lang.String)
     * @see org.gamegineer.table.internal.net.node.server.IServerNode#giveControl(java.lang.String)
     */
    @Override
    public void giveControl(
        final String playerName )
    {
        assertArgumentNotNull( playerName, "playerName" ); //$NON-NLS-1$
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
     *        The table used to specify the master table; must not be {@code
     *        null}.
     * 
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If an error occurs.
     */
    private void initializeMasterTable(
        /* @NonNull */
        final ITable table )
        throws TableNetworkException
    {
        assert table != null;

        final ITable masterTable;
        try
        {
            masterTable = TableFactory.createTable();
            masterTable.setMemento( table.createMemento() );
        }
        catch( final MementoException e )
        {
            throw new TableNetworkException( TableNetworkError.UNSPECIFIED_ERROR, e );
        }

        masterTable_ = masterTable;
    }

    /*
     * @see org.gamegineer.table.internal.net.node.server.IServerNode#isPlayerConnected(java.lang.String)
     */
    @Override
    public boolean isPlayerConnected(
        final String playerName )
    {
        assertArgumentNotNull( playerName, "playerName" ); //$NON-NLS-1$
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
     * @see org.gamegineer.table.internal.net.node.AbstractNode#remoteNodeBound(org.gamegineer.table.internal.net.node.IRemoteNode)
     */
    @Override
    protected void remoteNodeBound(
        final IRemoteClientNode remoteNode )
    {
        assertArgumentNotNull( remoteNode, "remoteNode" ); //$NON-NLS-1$
        assert isNodeLayerThread();

        super.remoteNodeBound( remoteNode );

        bindPlayer( new Player( remoteNode.getPlayerName() ) );
        synchronizeRemoteTable( remoteNode );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNode#remoteNodeUnbound(org.gamegineer.table.internal.net.node.IRemoteNode)
     */
    @Override
    protected void remoteNodeUnbound(
        final IRemoteClientNode remoteNode )
    {
        assertArgumentNotNull( remoteNode, "remoteNode" ); //$NON-NLS-1$
        assert isNodeLayerThread();

        super.remoteNodeUnbound( remoteNode );

        unbindPlayer( remoteNode.getPlayerName() );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#requestControl()
     * @see org.gamegineer.table.internal.net.node.server.IServerNode#requestControl()
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
     *        The remote node; must not be {@code null}.
     */
    private void synchronizeRemoteTable(
        /* @NonNull */
        final IRemoteClientNode remoteNode )
    {
        assert remoteNode != null;

        remoteNode.getTable().setTableState( masterTable_.createMemento() );
    }

    /**
     * Unbinds the specified player from the table network.
     * 
     * @param playerName
     *        The name of the player to unbind from the table network; must not
     *        be {@code null}.
     */
    private void unbindPlayer(
        /* @NonNull */
        final String playerName )
    {
        assert playerName != null;

        assert players_.containsKey( playerName );
        final Player player = players_.remove( playerName );
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
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.net.node.AbstractNode.AbstractFactory#createNode(org.gamegineer.table.internal.net.node.INodeLayer, org.gamegineer.table.internal.net.ITableNetworkController)
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
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.net.node.AbstractNode.TableManager#incrementCardPileState(org.gamegineer.table.internal.net.node.INetworkTable, int, org.gamegineer.table.internal.net.node.CardPileIncrement)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void incrementCardPileState(
            final INetworkTable sourceTable,
            final int cardPileIndex,
            final CardPileIncrement cardPileIncrement )
        {
            assertArgumentLegal( cardPileIndex >= 0, "cardPileIndex" ); //$NON-NLS-1$
            assertArgumentNotNull( cardPileIncrement, "cardPileIncrement" ); //$NON-NLS-1$

            if( verifyRequestingPlayerIsEditor() )
            {
                NetworkTableUtils.incrementCardPileState( masterTable_, cardPileIndex, cardPileIncrement );
                super.incrementCardPileState( sourceTable, cardPileIndex, cardPileIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.node.AbstractNode.TableManager#incrementCardState(org.gamegineer.table.internal.net.node.INetworkTable, int, int, org.gamegineer.table.internal.net.node.CardIncrement)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void incrementCardState(
            final INetworkTable sourceTable,
            final int cardPileIndex,
            final int cardIndex,
            final CardIncrement cardIncrement )
        {
            assertArgumentNotNull( sourceTable, "sourceTable" ); //$NON-NLS-1$
            assertArgumentLegal( cardPileIndex >= 0, "cardPileIndex" ); //$NON-NLS-1$
            assertArgumentLegal( cardIndex >= 0, "cardIndex" ); //$NON-NLS-1$
            assertArgumentNotNull( cardIncrement, "cardIncrement" ); //$NON-NLS-1$

            if( verifyRequestingPlayerIsEditor() )
            {
                NetworkTableUtils.incrementCardState( masterTable_, cardPileIndex, cardIndex, cardIncrement );
                super.incrementCardState( sourceTable, cardPileIndex, cardIndex, cardIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.node.AbstractNode.TableManager#incrementTableState(org.gamegineer.table.internal.net.node.INetworkTable, org.gamegineer.table.internal.net.node.TableIncrement)
         */
        @SuppressWarnings( "synthetic-access" )
        @Override
        public void incrementTableState(
            final INetworkTable sourceTable,
            final TableIncrement tableIncrement )
        {
            assertArgumentNotNull( tableIncrement, "tableIncrement" ); //$NON-NLS-1$

            if( verifyRequestingPlayerIsEditor() )
            {
                NetworkTableUtils.incrementTableState( masterTable_, tableIncrement );
                super.incrementTableState( sourceTable, tableIncrement );
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.node.AbstractNode.TableManager#setTableState(org.gamegineer.table.internal.net.node.INetworkTable, java.lang.Object)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void setTableState(
            final INetworkTable sourceTable,
            final Object tableMemento )
        {
            assertArgumentNotNull( sourceTable, "sourceTable" ); //$NON-NLS-1$
            assertArgumentNotNull( tableMemento, "tableMemento" ); //$NON-NLS-1$

            if( verifyRequestingPlayerIsEditor() )
            {
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
        @SuppressWarnings( "synthetic-access" )
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
         *        The decorated table manager; must not be {@code null}.
         * @param localPlayerName
         *        The name of the local player; must not be {@code null}.
         */
        ServerTableManagerDecorator(
            /* @NonNull */
            final ITableManager tableManagerDecoratee,
            /* @NonNull */
            final String localPlayerName )
        {
            assert tableManagerDecoratee != null;
            assert localPlayerName != null;

            localPlayerName_ = localPlayerName;
            tableManagerDecoratee_ = tableManagerDecoratee;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.net.node.ITableManager#incrementCardPileState(org.gamegineer.table.internal.net.node.INetworkTable, int, org.gamegineer.table.internal.net.node.CardPileIncrement)
         */
        @Override
        public void incrementCardPileState(
            final INetworkTable sourceTable,
            final int cardPileIndex,
            final CardPileIncrement cardPileIncrement )
        {
            ThreadPlayer.setPlayerName( localPlayerName_ );
            try
            {
                tableManagerDecoratee_.incrementCardPileState( sourceTable, cardPileIndex, cardPileIncrement );
            }
            finally
            {
                ThreadPlayer.setPlayerName( null );
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.node.ITableManager#incrementCardState(org.gamegineer.table.internal.net.node.INetworkTable, int, int, org.gamegineer.table.internal.net.node.CardIncrement)
         */
        @Override
        public void incrementCardState(
            final INetworkTable sourceTable,
            final int cardPileIndex,
            final int cardIndex,
            final CardIncrement cardIncrement )
        {
            ThreadPlayer.setPlayerName( localPlayerName_ );
            try
            {
                tableManagerDecoratee_.incrementCardState( sourceTable, cardPileIndex, cardIndex, cardIncrement );
            }
            finally
            {
                ThreadPlayer.setPlayerName( null );
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.node.ITableManager#incrementTableState(org.gamegineer.table.internal.net.node.INetworkTable, org.gamegineer.table.internal.net.node.TableIncrement)
         */
        @Override
        public void incrementTableState(
            final INetworkTable sourceTable,
            final TableIncrement tableIncrement )
        {
            ThreadPlayer.setPlayerName( localPlayerName_ );
            try
            {
                tableManagerDecoratee_.incrementTableState( sourceTable, tableIncrement );
            }
            finally
            {
                ThreadPlayer.setPlayerName( null );
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.node.ITableManager#setTableState(org.gamegineer.table.internal.net.node.INetworkTable, java.lang.Object)
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
