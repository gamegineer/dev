/*
 * AbstractNode.java
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
 * Created on Apr 9, 2011 at 11:53:12 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.Debug;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.internal.net.transport.ITransportLayerContext;
import org.gamegineer.table.internal.net.transport.TransportException;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * Superclass for all local nodes in a table network.
 * 
 * <p>
 * Implementations of this class should not be reused for multiple connections.
 * </p>
 * 
 * @param <RemoteNodeType>
 *        The type of the remote node managed by the local table network node.
 */
@ThreadSafe
public abstract class AbstractNode<RemoteNodeType extends IRemoteNode>
    implements INode<RemoteNodeType>, INodeController
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The lock used to serialize access to methods invoked through the
     * {@link INodeController} interface.
     */
    private final Object controllerLock_;

    /**
     * The local player name or {@code null} if the table network is not
     * connected.
     */
    @GuardedBy( "getLock()" )
    private String localPlayerName_;

    /** The instance lock. */
    private final Object lock_;

    /**
     * The table network password or {@code null} if the table network is not
     * connected.
     */
    @GuardedBy( "getLock()" )
    private SecureString password_;

    /**
     * The collection of bound remote nodes. The key is the name of the player
     * associated with the remote node. The value is the remote node.
     */
    @GuardedBy( "getLock()" )
    private final Map<String, RemoteNodeType> remoteNodes_;

    /** The table network controller. */
    private final ITableNetworkController tableNetworkController_;

    /**
     * The collection of bound tables. The key is the name of the player
     * associated with the table. The value is the table.
     */
    @GuardedBy( "getLock()" )
    private final Map<String, INetworkTable> tables_;

    /**
     * The transport layer or {@code null} if the table network is not
     * connected.
     */
    @GuardedBy( "getLock()" )
    private ITransportLayer transportLayer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNode} class.
     * 
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetworkController} is {@code null}.
     */
    protected AbstractNode(
        /* @NonNull */
        final ITableNetworkController tableNetworkController )
    {
        assertArgumentNotNull( tableNetworkController, "tableNetworkController" ); //$NON-NLS-1$

        controllerLock_ = new Object();
        localPlayerName_ = null;
        lock_ = new Object();
        password_ = null;
        remoteNodes_ = new HashMap<String, RemoteNodeType>();
        tableNetworkController_ = tableNetworkController;
        tables_ = new HashMap<String, INetworkTable>();
        transportLayer_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts the table network node is connected to the table network.
     * 
     * @throws java.lang.IllegalStateException
     *         If the table network is not connected.
     */
    @GuardedBy( "getLock()" )
    protected final void assertConnected()
    {
        assert Thread.holdsLock( getLock() );

        assertStateLegal( transportLayer_ != null, Messages.AbstractNode_networkDisconnected );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INode#bindRemoteNode(org.gamegineer.table.internal.net.node.IRemoteNode)
     */
    @Override
    public final void bindRemoteNode(
        final RemoteNodeType remoteNode )
    {
        assertArgumentNotNull( remoteNode, "remoteNode" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        assertConnected();
        assertArgumentLegal( !remoteNodes_.containsKey( remoteNode.getPlayerName() ), "remoteNode", Messages.AbstractNode_bindRemoteNode_remoteNodeBound ); //$NON-NLS-1$ 
        remoteNodes_.put( remoteNode.getPlayerName(), remoteNode );
        assert !tables_.containsKey( remoteNode.getPlayerName() );
        tables_.put( remoteNode.getPlayerName(), remoteNode.getTable() );
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Remote node bound for player '%s'", remoteNode.getPlayerName() ) ); //$NON-NLS-1$
        remoteNodeBound( remoteNode );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#connect(org.gamegineer.table.net.ITableNetworkConfiguration)
     */
    @Override
    public final void connect(
        final ITableNetworkConfiguration configuration )
        throws TableNetworkException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        synchronized( controllerLock_ )
        {
            synchronized( getLock() )
            {
                if( transportLayer_ != null )
                {
                    throw new TableNetworkException( TableNetworkError.ILLEGAL_CONNECTION_STATE );
                }

                localPlayerName_ = configuration.getLocalPlayerName();
                password_ = configuration.getPassword();
                tables_.put( localPlayerName_, new LocalNetworkTable( getTableManager(), configuration.getLocalTable() ) );

                connecting( configuration );

                final ITransportLayer transportLayer = createTransportLayer();
                try
                {
                    transportLayer.open( configuration.getHostName(), configuration.getPort() );
                }
                catch( final TransportException e )
                {
                    dispose();
                    throw new TableNetworkException( TableNetworkError.TRANSPORT_ERROR, e );
                }

                transportLayer_ = transportLayer;
            }

            try
            {
                connected();
            }
            catch( final TableNetworkException e )
            {
                disconnect();
                throw e;
            }
        }
    }

    /**
     * Template method invoked when the table network node has connected.
     * 
     * <p>
     * This method is <b>not</b> invoked while the instance lock is held.
     * Subclasses must always invoke the superclass method.
     * </p>
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     * 
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If an error occurs.
     */
    protected void connected()
        throws TableNetworkException
    {
        // do nothing
    }

    /**
     * Template method invoked when the table network node is connecting.
     * 
     * <p>
     * This method is invoked while the instance lock is held. Subclasses must
     * always invoke the superclass method.
     * </p>
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     * 
     * @param configuration
     *        The table network configuration; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code configuration} is {@code null}.
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If an error occurs.
     */
    @GuardedBy( "getLock()" )
    protected void connecting(
        /* @NonNull */
        final ITableNetworkConfiguration configuration )
        throws TableNetworkException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /**
     * Template method invoked to create the transport layer for this node using
     * the table network transport layer factory.
     * 
     * <p>
     * This method is invoked while the instance lock is held.
     * </p>
     * 
     * @return The transport layer for this node; never {@code null}.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    protected abstract ITransportLayer createTransportLayer();

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#disconnect()
     */
    @Override
    public final void disconnect()
    {
        synchronized( controllerLock_ )
        {
            final ITransportLayer transportLayer;
            synchronized( getLock() )
            {
                transportLayer = transportLayer_;
                if( transportLayer != null )
                {
                    disconnecting();
                }
            }

            // NB: Do not hold instance lock when calling into transport layer!
            if( transportLayer != null )
            {
                transportLayer.close();
            }

            synchronized( getLock() )
            {
                if( transportLayer != null )
                {
                    transportLayer_ = null;
                    tables_.remove( localPlayerName_ );

                    disconnected();
                    dispose();
                }
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INode#disconnect(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    public final void disconnect(
        final TableNetworkError error )
    {
        assert Thread.holdsLock( getLock() );

        disconnecting( error );
        tableNetworkController_.disconnect( error );
    }

    /**
     * Template method invoked when the table network node has disconnected.
     * 
     * <p>
     * This method is invoked while the instance lock is held. Subclasses must
     * always invoke the superclass method.
     * </p>
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     */
    @GuardedBy( "getLock()" )
    protected void disconnected()
    {
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /**
     * Template method invoked when the table network node is disconnecting.
     * 
     * <p>
     * This method is invoked while the instance lock is held. Subclasses must
     * always invoke the superclass method.
     * </p>
     * 
     * <p>
     * This implementation sends a goodbye message to all remote nodes.
     * </p>
     */
    @GuardedBy( "getLock()" )
    protected void disconnecting()
    {
        assert Thread.holdsLock( getLock() );

        for( final IRemoteNode remoteNode : remoteNodes_.values() )
        {
            remoteNode.goodbye();
        }
    }

    /**
     * Template method invoked when the table network node is disconnecting for
     * the specified cause.
     * 
     * <p>
     * This method is invoked while the instance lock is held. Subclasses must
     * always invoke the superclass method.
     * </p>
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     * 
     * @param error
     *        The error that caused the table network node to be disconnected or
     *        {@code null} if the table network node was disconnected normally.
     */
    @GuardedBy( "getLock()" )
    protected void disconnecting(
        final TableNetworkError error )
    {
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /**
     * Disposes of the resources managed by the node.
     * 
     * <p>
     * This method is invoked while the instance lock is held. Subclasses must
     * always invoke the superclass method.
     * </p>
     */
    @GuardedBy( "getLock()" )
    protected void dispose()
    {
        assert Thread.holdsLock( getLock() );

        localPlayerName_ = null;
        if( password_ != null )
        {
            password_.dispose();
            password_ = null;
        }
        remoteNodes_.clear();
        tables_.clear();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INode#getLock()
     */
    @Override
    public final Object getLock()
    {
        return lock_;
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INode#getPassword()
     */
    @Override
    public final SecureString getPassword()
    {
        assert Thread.holdsLock( getLock() );

        assertStateLegal( password_ != null, Messages.AbstractNode_networkDisconnected );
        return new SecureString( password_ );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INode#getPlayerName()
     */
    @Override
    public final String getPlayerName()
    {
        assert Thread.holdsLock( getLock() );

        assertStateLegal( localPlayerName_ != null, Messages.AbstractNode_networkDisconnected );
        return localPlayerName_;
    }

    /**
     * Gets the collection of bound remote nodes.
     * 
     * @return The collection of bound remote nodes; never {@code null}.
     */
    /* @NonNull */
    protected final Collection<RemoteNodeType> getRemoteNodes()
    {
        synchronized( getLock() )
        {
            return new ArrayList<RemoteNodeType>( remoteNodes_.values() );
        }
    }

    /**
     * Gets the table network controller.
     * 
     * @return The table network controller; never {@code null}.
     */
    /* @NonNull */
    protected final ITableNetworkController getTableNetworkController()
    {
        return tableNetworkController_;
    }

    /**
     * Gets the bound table associated with the local table network node.
     * 
     * @return The bound table associated with the local table network node;
     *         never {@code null}.
     */
    /* @NonNull */
    protected final INetworkTable getTable()
    {
        assert localPlayerName_ != null;

        synchronized( getLock() )
        {
            return tables_.get( localPlayerName_ );
        }
    }

    /**
     * Gets the collection of bound tables.
     * 
     * @return The collection of bound tables; never {@code null}.
     */
    /* @NonNull */
    private Collection<INetworkTable> getTables()
    {
        synchronized( getLock() )
        {
            return new ArrayList<INetworkTable>( tables_.values() );
        }
    }

    /**
     * Template method invoked when a remote node has been bound to the local
     * table network node.
     * 
     * <p>
     * This method is invoked while the instance lock is held. Subclasses must
     * always invoke the superclass method.
     * </p>
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     * 
     * @param remoteNode
     *        The remote node that was bound; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code remoteNode} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    protected void remoteNodeBound(
        /* @NonNull */
        final RemoteNodeType remoteNode )
    {
        assertArgumentNotNull( remoteNode, "remoteNode" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /**
     * Template method invoked when a remote node has been unbound from the
     * local table network node.
     * 
     * <p>
     * This method is invoked while the instance lock is held. Subclasses must
     * always invoke the superclass method.
     * </p>
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     * 
     * @param remoteNode
     *        The remote node that was unbound; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code remoteNode} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    protected void remoteNodeUnbound(
        /* @NonNull */
        final RemoteNodeType remoteNode )
    {
        assertArgumentNotNull( remoteNode, "remoteNode" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INode#unbindRemoteNode(org.gamegineer.table.internal.net.node.IRemoteNode)
     */
    @Override
    public final void unbindRemoteNode(
        final RemoteNodeType remoteNode )
    {
        assertArgumentNotNull( remoteNode, "remoteNode" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        assertConnected();
        assertArgumentLegal( remoteNodes_.remove( remoteNode.getPlayerName() ) != null, "remoteNode", Messages.AbstractNode_unbindRemoteNode_remoteNodeNotBound ); //$NON-NLS-1$
        assert tables_.containsKey( remoteNode.getPlayerName() );
        tables_.remove( remoteNode.getPlayerName() );
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Remote node unbound for player '%s'", remoteNode.getPlayerName() ) ); //$NON-NLS-1$
        remoteNodeUnbound( remoteNode );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Superclass for implementations of the {@link ITransportLayerContext}
     * associated with a table network node.
     */
    @Immutable
    protected abstract class AbstractTransportLayerContext
        implements ITransportLayerContext
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code
         * AbstractTransportLayerContext} class.
         */
        protected AbstractTransportLayerContext()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.net.transport.ITransportLayerContext#transportLayerDisconnected(java.lang.Exception)
         */
        @Override
        public final void transportLayerDisconnected(
            final Exception exception )
        {
            getTableNetworkController().disconnect( (exception != null) ? TableNetworkError.TRANSPORT_ERROR : null );
        }
    }

    /**
     * Standard implementation of {@link ITableManager} that forwards each
     * method call to all tables connected to the node, not including the
     * originator of the request.
     */
    @Immutable
    protected class TableManager
        implements ITableManager
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableManager} class.
         */
        public TableManager()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * This implementation forwards the request to all tables connected to
         * the node, not including the originator of the request.
         * 
         * <p>
         * Subclasses may override but must call the superclass implementation.
         * </p>
         * 
         * @see org.gamegineer.table.internal.net.node.ITableManager#incrementCardPileState(org.gamegineer.table.internal.net.node.INetworkTable,
         *      int, org.gamegineer.table.internal.net.node.CardPileIncrement)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void incrementCardPileState(
            final INetworkTable sourceTable,
            final int cardPileIndex,
            final CardPileIncrement cardPileIncrement )
        {
            assertArgumentNotNull( sourceTable, "sourceTable" ); //$NON-NLS-1$
            assertArgumentLegal( cardPileIndex >= 0, "cardPileIndex" ); //$NON-NLS-1$
            assertArgumentNotNull( cardPileIncrement, "cardPileIncrement" ); //$NON-NLS-1$

            for( final INetworkTable table : getTables() )
            {
                if( table != sourceTable )
                {
                    table.incrementCardPileState( cardPileIndex, cardPileIncrement );
                }
            }
        }

        /**
         * This implementation forwards the request to all tables connected to
         * the node, not including the originator of the request.
         * 
         * <p>
         * Subclasses may override but must call the superclass implementation.
         * </p>
         * 
         * @see org.gamegineer.table.internal.net.node.ITableManager#incrementCardState(org.gamegineer.table.internal.net.node.INetworkTable,
         *      int, int, org.gamegineer.table.internal.net.node.CardIncrement)
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

            for( final INetworkTable table : getTables() )
            {
                if( table != sourceTable )
                {
                    table.incrementCardState( cardPileIndex, cardIndex, cardIncrement );
                }
            }
        }

        /**
         * This implementation forwards the request to all tables connected to
         * the node, not including the originator of the request.
         * 
         * <p>
         * Subclasses may override but must call the superclass implementation.
         * </p>
         * 
         * @see org.gamegineer.table.internal.net.node.ITableManager#incrementTableState(org.gamegineer.table.internal.net.node.INetworkTable,
         *      org.gamegineer.table.internal.net.node.TableIncrement)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void incrementTableState(
            final INetworkTable sourceTable,
            final TableIncrement tableIncrement )
        {
            assertArgumentNotNull( sourceTable, "sourceTable" ); //$NON-NLS-1$
            assertArgumentNotNull( tableIncrement, "tableIncrement" ); //$NON-NLS-1$

            for( final INetworkTable table : getTables() )
            {
                if( table != sourceTable )
                {
                    table.incrementTableState( tableIncrement );
                }
            }
        }

        /**
         * This implementation forwards the request to all tables connected to
         * the node, not including the originator of the request.
         * 
         * <p>
         * Subclasses may override but must call the superclass implementation.
         * </p>
         * 
         * @see org.gamegineer.table.internal.net.node.ITableManager#setTableState(org.gamegineer.table.internal.net.node.INetworkTable,
         *      java.lang.Object)
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void setTableState(
            final INetworkTable sourceTable,
            final Object tableMemento )
        {
            assertArgumentNotNull( sourceTable, "sourceTable" ); //$NON-NLS-1$
            assertArgumentNotNull( tableMemento, "tableMemento" ); //$NON-NLS-1$

            for( final INetworkTable table : getTables() )
            {
                if( table != sourceTable )
                {
                    table.setTableState( tableMemento );
                }
            }
        }
    }
}
