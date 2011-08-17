/*
 * ClientNode.java
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
 * Created on Apr 9, 2011 at 10:45:05 PM.
 */

package org.gamegineer.table.internal.net.node.client;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
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
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.Player;
import org.gamegineer.table.internal.net.node.AbstractNode;
import org.gamegineer.table.internal.net.node.CardIncrement;
import org.gamegineer.table.internal.net.node.CardPileIncrement;
import org.gamegineer.table.internal.net.node.INetworkTable;
import org.gamegineer.table.internal.net.node.ITableManager;
import org.gamegineer.table.internal.net.node.TableIncrement;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.PlayerRole;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A client node in a table network.
 */
@ThreadSafe
public final class ClientNode
    extends AbstractNode<IRemoteServerNode>
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
    private TableNetworkError handshakeError_;

    /** The handshake lock. */
    private final Lock handshakeLock_;

    /** Indicates the handshake is complete. */
    @GuardedBy( "handshakeLock_" )
    private boolean isHandshakeComplete_;

    /**
     * The collection of players connected to the table network. The key is the
     * player name. The value is the player.
     */
    @GuardedBy( "getLock()" )
    private final Map<String, IPlayer> players_;

    /** The table manager. */
    private final ITableManager tableManager_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientNode} class.
     * 
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetworkController} is {@code null}.
     */
    public ClientNode(
        /* @NonNull */
        final ITableNetworkController tableNetworkController )
    {
        this( tableNetworkController, true );
    }

    /**
     * Initializes a new instance of the {@code ClientNode} class and indicates
     * whether or not the node should wait for handshake to complete before
     * indicating the connection has been established.
     * 
     * <p>
     * This constructor is only intended to support testing.
     * </p>
     * 
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * @param waitForHandshakeCompletion
     *        {@code true} if the node should wait for the handshake to complete
     *        before indicating the connection has been established; otherwise
     *        {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetworkController} is {@code null}.
     */
    public ClientNode(
        /* @NonNull */
        final ITableNetworkController tableNetworkController,
        final boolean waitForHandshakeCompletion )
    {
        super( tableNetworkController );

        handshakeLock_ = new ReentrantLock();
        handshakeCondition_ = handshakeLock_.newCondition();
        handshakeError_ = null;
        isHandshakeComplete_ = waitForHandshakeCompletion ? false : true;
        players_ = new HashMap<String, IPlayer>();
        tableManager_ = new ClientTableManager();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNode#connected()
     */
    @Override
    protected void connected()
        throws TableNetworkException
    {
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
     * @see org.gamegineer.table.internal.net.node.AbstractNode#connecting(org.gamegineer.table.net.ITableNetworkConfiguration)
     */
    @Override
    protected void connecting(
        final ITableNetworkConfiguration configuration )
        throws TableNetworkException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        super.connecting( configuration );

        // Temporarily add local player until we receive the player list from the server
        final Player player = new Player( getPlayerName() );
        player.addRoles( EnumSet.of( PlayerRole.LOCAL ) );
        players_.put( player.getName(), player );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNode#createTransportLayer()
     */
    @Override
    protected ITransportLayer createTransportLayer()
    {
        assert Thread.holdsLock( getLock() );

        return getTableNetworkController().getTransportLayerFactory().createActiveTransportLayer( new AbstractTransportLayerContext()
        {
            @Override
            public IService createService()
            {
                return new RemoteServerNode( ClientNode.this );
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNode#disconnecting(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    protected void disconnecting(
        final TableNetworkError error )
    {
        assert Thread.holdsLock( getLock() );

        super.disconnecting( error );

        setHandshakeComplete( error );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNode#dispose()
     */
    @Override
    protected void dispose()
    {
        assert Thread.holdsLock( getLock() );

        players_.clear();

        super.dispose();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#getPlayer()
     */
    @Override
    public IPlayer getPlayer()
    {
        synchronized( getLock() )
        {
            return isConnected() ? players_.get( getPlayerName() ) : null;
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#getPlayers()
     */
    @Override
    public Collection<IPlayer> getPlayers()
    {
        synchronized( getLock() )
        {
            return new ArrayList<IPlayer>( players_.values() );
        }
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
     * @see org.gamegineer.table.internal.net.node.AbstractNode#remoteNodeBound(org.gamegineer.table.internal.net.node.IRemoteNode)
     */
    @Override
    protected void remoteNodeBound(
        final IRemoteServerNode remoteNode )
    {
        assertArgumentNotNull( remoteNode, "remoteNode" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        super.remoteNodeBound( remoteNode );

        setHandshakeComplete( null );
    }

    /**
     * Sets the condition that indicates the handshake is complete.
     * 
     * @param error
     *        The error that caused the handshake to fail or {@code null} if the
     *        handshake completed successfully.
     */
    private void setHandshakeComplete(
        /* @Nullable */
        final TableNetworkError error )
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
     * @see org.gamegineer.table.internal.net.node.client.IClientNode#setPlayers(java.util.Collection)
     */
    @Override
    public void setPlayers(
        final Collection<IPlayer> players )
    {
        assertArgumentNotNull( players, "players" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

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
     * Implementation of {@link ITableManager} that ensures changes to the local
     * table are not broadcast to the server.
     * 
     * <p>
     * THIS IS A TEMPORARY IMPLEMENTATION UNTIL WE SUPPORT CLIENTS BEING
     * "PASSED THE BALL" AND HAVING THE AUTHORITY TO MAKE CHANGES TO THE NETWORK
     * TABLE.
     * </p>
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

            if( sourceTable != getTable() )
            {
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

            if( sourceTable != getTable() )
            {
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

            if( sourceTable != getTable() )
            {
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

            if( sourceTable != getTable() )
            {
                super.setTableState( sourceTable, tableMemento );
            }
        }
    }
}
