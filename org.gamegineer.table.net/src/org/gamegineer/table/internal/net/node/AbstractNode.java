/*
 * AbstractNode.java
 * Copyright 2008-2013 Gamegineer.org
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.core.ComponentPath;
import org.gamegineer.table.internal.net.Activator;
import org.gamegineer.table.internal.net.Debug;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.internal.net.transport.ITransportLayerContext;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;
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
 * <p>
 * All methods of this class are expected to be invoked on the associated node
 * layer thread except where explicitly noted.
 * </p>
 * 
 * @param <RemoteNodeType>
 *        The type of the remote node managed by the local table network node.
 */
@NotThreadSafe
public abstract class AbstractNode<RemoteNodeType extends IRemoteNode>
    implements INode<RemoteNodeType>, INodeController
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The local player name or {@code null} if the table network is not
     * connected.
     */
    private String localPlayerName_;

    /** The node layer. */
    private final INodeLayer nodeLayer_;

    /**
     * The table network password or {@code null} if the table network is not
     * connected.
     */
    private SecureString password_;

    /**
     * The collection of bound remote nodes. The key is the name of the player
     * associated with the remote node. The value is the remote node.
     */
    private final Map<String, RemoteNodeType> remoteNodes_;

    /** The table network controller. */
    private final ITableNetworkController tableNetworkController_;

    /**
     * The collection of bound tables. The key is the name of the player
     * associated with the table. The value is the table.
     */
    private final Map<String, INetworkTable> tables_;

    /**
     * The transport layer or {@code null} if the table network is not
     * connected.
     */
    private ITransportLayer transportLayer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNode} class.
     * 
     * @param nodeLayer
     *        The node layer; must not be {@code null}.
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code nodeLayer} or {@code tableNetworkController} is
     *         {@code null}.
     */
    protected AbstractNode(
        /* @NonNull */
        final INodeLayer nodeLayer,
        /* @NonNull */
        final ITableNetworkController tableNetworkController )
    {
        assertArgumentNotNull( nodeLayer, "nodeLayer" ); //$NON-NLS-1$
        assertArgumentNotNull( tableNetworkController, "tableNetworkController" ); //$NON-NLS-1$

        localPlayerName_ = null;
        nodeLayer_ = nodeLayer;
        password_ = null;
        remoteNodes_ = new HashMap<>();
        tableNetworkController_ = tableNetworkController;
        tables_ = new HashMap<>();
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
    protected final void assertConnected()
    {
        assert isNodeLayerThread();

        assertStateLegal( isConnected(), NonNlsMessages.AbstractNode_networkDisconnected );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#beginConnect(org.gamegineer.table.net.ITableNetworkConfiguration)
     */
    @Override
    public final Future<Void> beginConnect(
        final ITableNetworkConfiguration configuration )
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$
        assert isNodeLayerThread();

        final Connecter connecter = new Connecter( configuration );
        return Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Override
            public Void call()
                throws TableNetworkException
            {
                connecter.connect();

                return null;
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INodeController#beginDisconnect()
     */
    @Override
    public Future<Void> beginDisconnect()
    {
        assert isNodeLayerThread();

        final Disconnecter disconnecter = new Disconnecter();
        return Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Override
            public Void call()
            {
                disconnecter.disconnect();

                return null;
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INode#bindRemoteNode(org.gamegineer.table.internal.net.node.IRemoteNode)
     */
    @Override
    public final void bindRemoteNode(
        final RemoteNodeType remoteNode )
    {
        assertArgumentNotNull( remoteNode, "remoteNode" ); //$NON-NLS-1$
        assert isNodeLayerThread();

        assertConnected();
        assertArgumentLegal( !remoteNodes_.containsKey( remoteNode.getPlayerName() ), "remoteNode", NonNlsMessages.AbstractNode_bindRemoteNode_remoteNodeBound ); //$NON-NLS-1$ 
        remoteNodes_.put( remoteNode.getPlayerName(), remoteNode );
        assert !tables_.containsKey( remoteNode.getPlayerName() );
        tables_.put( remoteNode.getPlayerName(), remoteNode.getTable() );
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Remote node bound for player '%s'", remoteNode.getPlayerName() ) ); //$NON-NLS-1$
        remoteNodeBound( remoteNode );
    }

    /**
     * Template method invoked when the table network node has connected.
     * 
     * <p>
     * This method is <b>not</b> invoked on the node layer thread. Subclasses
     * must always invoke the superclass method.
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
        assert !isNodeLayerThread();

        // do nothing
    }

    /**
     * Template method invoked when the table network node is connecting.
     * 
     * <p>
     * Subclasses must always invoke the superclass method.
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
    protected void connecting(
        /* @NonNull */
        final ITableNetworkConfiguration configuration )
        throws TableNetworkException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$
        assert isNodeLayerThread();

        // do nothing
    }

    /**
     * Template method invoked to create a decorator for the table manager used
     * by the local network table.
     * 
     * <p>
     * Subclasses are not required to invoke the superclass method.
     * </p>
     * 
     * <p>
     * This implementation returns the table manager without decoration.
     * </p>
     * 
     * @param tableManager
     *        The table manager for the local network table; must not be
     *        {@code null}.
     * 
     * @return A decorator for the table manager used by the local network
     *         table; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableManager} is {@code null}.
     */
    /* @NonNull */
    protected ITableManager createTableManagerDecoratorForLocalNetworkTable(
        /* @NonNull */
        final ITableManager tableManager )
    {
        assertArgumentNotNull( tableManager, "tableManager" ); //$NON-NLS-1$
        assert isNodeLayerThread();

        return tableManager;
    }

    /**
     * Template method invoked to create the transport layer for this node using
     * the table network transport layer factory.
     * 
     * <p>
     * This method is <b>not</b> invoked on the node layer thread.
     * </p>
     * 
     * @return The transport layer for this node; never {@code null}.
     * 
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the transport layer cannot be created.
     */
    /* @NonNull */
    protected abstract ITransportLayer createTransportLayer()
        throws TableNetworkException;

    /*
     * @see org.gamegineer.table.internal.net.node.INode#disconnect(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    public final void disconnect(
        final TableNetworkError error )
    {
        assert isNodeLayerThread();

        disconnecting( error );

        // NB: Initiate disconnection through the table network, but this must
        // be done on a thread other than the node layer thread because the
        // table network controller disconnect() method blocks waiting for the
        // node layer to disconnect.
        Activator.getDefault().getExecutorService().submit( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                try
                {
                    tableNetworkController_.disconnect( error );
                }
                catch( final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }
            }
        } );
    }

    /**
     * Template method invoked when the table network node has disconnected.
     * 
     * <p>
     * Subclasses must always invoke the superclass method.
     * </p>
     * 
     * <p>
     * This implementation does nothing.
     * </p>
     */
    protected void disconnected()
    {
        assert isNodeLayerThread();

        // do nothing
    }

    /**
     * Template method invoked when the table network node is disconnecting.
     * 
     * <p>
     * Subclasses must always invoke the superclass method.
     * </p>
     * 
     * <p>
     * This implementation sends a goodbye message to all remote nodes.
     * </p>
     */
    protected void disconnecting()
    {
        assert isNodeLayerThread();

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
     * Subclasses must always invoke the superclass method.
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
    protected void disconnecting(
        /* @Nullable */
        @SuppressWarnings( "unused" )
        final TableNetworkError error )
    {
        assert isNodeLayerThread();

        // do nothing
    }

    /**
     * Disposes of the resources managed by the node.
     * 
     * <p>
     * Subclasses must always invoke the superclass method.
     * </p>
     */
    protected void dispose()
    {
        assert isNodeLayerThread();

        localPlayerName_ = null;
        if( password_ != null )
        {
            password_.dispose();
            password_ = null;
        }

        for( final INetworkTable table : tables_.values() )
        {
            table.dispose();
        }
        tables_.clear();

        remoteNodes_.clear();

        transportLayer_ = null;

        nodeLayer_.dispose();
    }

    /**
     * This method may be called from any thread. It must not be called on the
     * node layer thread if the operation is not done.
     * 
     * @see org.gamegineer.table.internal.net.node.INodeController#endConnect(java.util.concurrent.Future)
     */
    @Override
    public final void endConnect(
        final Future<Void> future )
        throws TableNetworkException, InterruptedException
    {
        assertArgumentNotNull( future, "future" ); //$NON-NLS-1$
        assert !isNodeLayerThread() || future.isDone();

        try
        {
            future.get();
        }
        catch( final ExecutionException e )
        {
            final Throwable cause = e.getCause();
            if( cause instanceof TableNetworkException )
            {
                throw (TableNetworkException)cause;
            }

            throw TaskUtils.launderThrowable( cause );
        }
    }

    /**
     * This method may be called from any thread. It must not be called on the
     * node layer thread if the operation is not done.
     * 
     * @see org.gamegineer.table.internal.net.node.INodeController#endDisconnect(java.util.concurrent.Future)
     */
    @Override
    public final void endDisconnect(
        final Future<Void> future )
        throws InterruptedException
    {
        assertArgumentNotNull( future, "future" ); //$NON-NLS-1$
        assert !isNodeLayerThread() || future.isDone();

        try
        {
            future.get();
        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
    }

    /**
     * Gets the node layer.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @return The node layer; never {@code null}.
     */
    /* @NonNull */
    protected final INodeLayer getNodeLayer()
    {
        return nodeLayer_;
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INode#getPassword()
     */
    @Override
    public final SecureString getPassword()
    {
        assert isNodeLayerThread();

        assertStateLegal( password_ != null, NonNlsMessages.AbstractNode_networkDisconnected );
        return new SecureString( password_ );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INode#getPlayerName()
     */
    @Override
    public final String getPlayerName()
    {
        isNodeLayerThread();

        assertStateLegal( localPlayerName_ != null, NonNlsMessages.AbstractNode_networkDisconnected );
        return localPlayerName_;
    }

    /**
     * Gets the bound remote node associated with the specified player.
     * 
     * @param playerName
     *        The name of the player associated with the bound remote node; must
     *        not be {@code null}.
     * 
     * @return The bound remote node associated with the specified player or
     *         {@code null} if no remote node is bound for the specified player.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code playerName} is {@code null}.
     */
    /* @Nullable */
    protected final RemoteNodeType getRemoteNode(
        /* @NonNull */
        final String playerName )
    {
        assertArgumentNotNull( playerName, "playerName" ); //$NON-NLS-1$
        assert isNodeLayerThread();

        return remoteNodes_.get( playerName );
    }

    /**
     * Gets the collection of bound remote nodes.
     * 
     * @return The collection of bound remote nodes; never {@code null}.
     */
    /* @NonNull */
    protected final Collection<RemoteNodeType> getRemoteNodes()
    {
        assert isNodeLayerThread();

        return new ArrayList<>( remoteNodes_.values() );
    }

    /**
     * Gets the table network controller.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
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
        assert isNodeLayerThread();

        assert localPlayerName_ != null;
        return tables_.get( localPlayerName_ );
    }

    /**
     * Gets the collection of bound tables.
     * 
     * @return The collection of bound tables; never {@code null}.
     */
    /* @NonNull */
    private Collection<INetworkTable> getTables()
    {
        return new ArrayList<>( tables_.values() );
    }

    /**
     * Indicates the table network is connected.
     * 
     * @return {@code true} if the table network is connected; otherwise
     *         {@code false}.
     */
    protected final boolean isConnected()
    {
        assert isNodeLayerThread();

        return transportLayer_ != null;
    }

    /**
     * Indicates the current thread is the node layer thread.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @return {@code true} if the current thread is the node layer thread;
     *         otherwise {@code false}.
     */
    protected final boolean isNodeLayerThread()
    {
        return nodeLayer_.isNodeLayerThread();
    }

    /**
     * Template method invoked when a remote node has been bound to the local
     * table network node.
     * 
     * <p>
     * Subclasses must always invoke the superclass method.
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
    protected void remoteNodeBound(
        /* @NonNull */
        final RemoteNodeType remoteNode )
    {
        assertArgumentNotNull( remoteNode, "remoteNode" ); //$NON-NLS-1$
        assert isNodeLayerThread();

        // do nothing
    }

    /**
     * Template method invoked when a remote node has been unbound from the
     * local table network node.
     * 
     * <p>
     * Subclasses must always invoke the superclass method.
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
    protected void remoteNodeUnbound(
        /* @NonNull */
        final RemoteNodeType remoteNode )
    {
        assertArgumentNotNull( remoteNode, "remoteNode" ); //$NON-NLS-1$
        assert isNodeLayerThread();

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
        assert isNodeLayerThread();

        assertConnected();
        assertArgumentLegal( remoteNodes_.remove( remoteNode.getPlayerName() ) != null, "remoteNode", NonNlsMessages.AbstractNode_unbindRemoteNode_remoteNodeNotBound ); //$NON-NLS-1$
        final INetworkTable table = tables_.remove( remoteNode.getPlayerName() );
        assert table != null;
        table.dispose();
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Remote node unbound for player '%s'", remoteNode.getPlayerName() ) ); //$NON-NLS-1$
        remoteNodeUnbound( remoteNode );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Superclass for all table network node factories.
     * 
     * <p>
     * Instances of this class guarantee that a table network node is
     * constructed on the node layer thread.
     * </p>
     * 
     * @param <T>
     *        The type of the table network node.
     */
    @Immutable
    public static abstract class AbstractFactory<T extends AbstractNode<?>>
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractFactory} class.
         */
        protected AbstractFactory()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Creates a new table network node.
         * 
         * @param tableNetworkController
         *        The table network controller; must not be {@code null}.
         * 
         * @return A new table network node; never {@code null}.
         * 
         * @throws org.gamegineer.table.net.TableNetworkException
         *         If the table network node cannot be created.
         */
        /* @NonNull */
        public final T createNode(
            /* @NonNull */
            final ITableNetworkController tableNetworkController )
            throws TableNetworkException
        {
            assertArgumentNotNull( tableNetworkController, "tableNetworkController" ); //$NON-NLS-1$

            final INodeLayer nodeLayer = new NodeLayer();
            try
            {
                return nodeLayer.syncExec( new Callable<T>()
                {
                    @Override
                    public T call()
                    {
                        return createNode( nodeLayer, tableNetworkController );
                    }
                } );
            }
            catch( final ExecutionException e )
            {
                throw TaskUtils.launderThrowable( e.getCause() );
            }
            catch( final InterruptedException e )
            {
                Thread.currentThread().interrupt();
                throw new TableNetworkException( TableNetworkError.INTERRUPTED, e );
            }
        }

        /**
         * Template method invoked to create a new table network node.
         * 
         * <p>
         * This method is guaranteed to be invoked on the node layer thread.
         * </p>
         * 
         * @param nodeLayer
         *        The node layer; must not be {@code null}.
         * @param tableNetworkController
         *        The table network controller; must not be {@code null}.
         * 
         * @return A new table network node; never {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code nodeLayer} or {@code tableNetworkController} is
         *         {@code null}.
         */
        /* @NonNull */
        protected abstract T createNode(
            /* @NonNull */
            INodeLayer nodeLayer,
            /* @NonNull */
            ITableNetworkController tableNetworkController );
    }

    /**
     * Superclass for transport layer service proxies that ensure all methods
     * are executed on the node layer thread.
     */
    @NotThreadSafe
    protected abstract class AbstractServiceProxy
        implements IService
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The actual service. */
        private IService service_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractServiceProxy} class.
         */
        protected AbstractServiceProxy()
        {
            service_ = null;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Creates the actual service.
         * 
         * @return The actual service; never {@code null}.
         */
        /* @NonNull */
        protected abstract IService createActualService();

        /**
         * Gets the actual service.
         * 
         * @return The actual service; never {@code null}.
         */
        /* @NonNull */
        private IService getActualService()
        {
            if( service_ == null )
            {
                service_ = createActualService();
            }

            return service_;
        }

        /*
         * @see org.gamegineer.table.internal.net.transport.IService#messageReceived(org.gamegineer.table.internal.net.transport.MessageEnvelope)
         */
        @Override
        public void messageReceived(
            final MessageEnvelope messageEnvelope )
        {
            try
            {
                getNodeLayer().asyncExec( new Runnable()
                {
                    @Override
                    @SuppressWarnings( "synthetic-access" )
                    public void run()
                    {
                        getActualService().messageReceived( messageEnvelope );
                    }
                } );
            }
            catch( final RejectedExecutionException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.AbstractNode_nodeLayer_shutdown, e );
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.transport.IService#peerStopped()
         */
        @Override
        public void peerStopped()
        {
            try
            {
                getNodeLayer().asyncExec( new Runnable()
                {
                    @Override
                    @SuppressWarnings( "synthetic-access" )
                    public void run()
                    {
                        getActualService().peerStopped();
                    }
                } );
            }
            catch( final RejectedExecutionException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.AbstractNode_nodeLayer_shutdown, e );
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.transport.IService#started(org.gamegineer.table.internal.net.transport.IServiceContext)
         */
        @Override
        public void started(
            final IServiceContext context )
        {
            try
            {
                getNodeLayer().asyncExec( new Runnable()
                {
                    @Override
                    @SuppressWarnings( "synthetic-access" )
                    public void run()
                    {
                        getActualService().started( context );
                    }
                } );
            }
            catch( final RejectedExecutionException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.AbstractNode_nodeLayer_shutdown, e );
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.transport.IService#stopped(java.lang.Exception)
         */
        @Override
        public void stopped(
            final Exception exception )
        {
            try
            {
                getNodeLayer().asyncExec( new Runnable()
                {
                    @Override
                    @SuppressWarnings( "synthetic-access" )
                    public void run()
                    {
                        getActualService().stopped( exception );
                    }
                } );
            }
            catch( final RejectedExecutionException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.AbstractNode_nodeLayer_shutdown, e );
            }
        }
    }

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
         * Initializes a new instance of the
         * {@code AbstractTransportLayerContext} class.
         */
        protected AbstractTransportLayerContext()
        {
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
            try
            {
                getNodeLayer().asyncExec( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            getTableNetworkController().disconnect( (exception != null) ? TableNetworkError.TRANSPORT_ERROR : null );
                        }
                        catch( final InterruptedException e )
                        {
                            Thread.currentThread().interrupt();
                        }
                    }
                } );
            }
            catch( final RejectedExecutionException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.AbstractNode_nodeLayer_shutdown, e );
            }
        }
    }

    /**
     * Responsible for connecting the table network node to the table network.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
    private final class Connecter
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The table network configuration. */
        private final ITableNetworkConfiguration configuration_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code Connecter} class.
         * 
         * <p>
         * This constructor must be called on the node layer thread.
         * </p>
         * 
         * @param configuration
         *        The table network configuration; must not be {@code null}.
         */
        Connecter(
            /* @NonNull */
            final ITableNetworkConfiguration configuration )
        {
            assert configuration != null;
            assert isNodeLayerThread();

            configuration_ = configuration;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Begins an asynchronous operation to disconnect the table network node
         * from the table network.
         */
        /* @NonNull */
        private void asyncDisconnect()
        {
            nodeLayer_.asyncExec( new Runnable()
            {
                @Override
                public void run()
                {
                    beginDisconnect();
                }
            } );
        }

        /**
         * Connects the table network node to the table network.
         * 
         * <p>
         * This method must not be called on the node layer thread.
         * </p>
         * 
         * @throws org.gamegineer.table.net.TableNetworkException
         *         If the connection cannot be established or the table network
         *         node is already connected.
         */
        void connect()
            throws TableNetworkException
        {
            assert !isNodeLayerThread();

            try
            {
                try
                {
                    connecting();
                    openTransportLayer();
                    connected();
                }
                catch( final TableNetworkException e )
                {
                    syncDisconnect();
                    throw e;
                }
            }
            catch( final InterruptedException e )
            {
                asyncDisconnect();
                Thread.currentThread().interrupt();
                throw new TableNetworkException( TableNetworkError.INTERRUPTED, e );
            }
        }

        /**
         * Invoked when the table network node has connected to the table
         * network.
         * 
         * @throws java.lang.InterruptedException
         *         If this thread is interrupted while waiting for the operation
         *         to complete.
         * @throws org.gamegineer.table.net.TableNetworkException
         *         If an error occurs.
         */
        private void connected()
            throws TableNetworkException, InterruptedException
        {
            AbstractNode.this.connected();
        }

        /**
         * Invoked when the table network node is about to connect to the table
         * network.
         * 
         * @throws java.lang.InterruptedException
         *         If this thread is interrupted while waiting for the operation
         *         to complete.
         * @throws org.gamegineer.table.net.TableNetworkException
         *         If an error occurs.
         */
        private void connecting()
            throws TableNetworkException, InterruptedException
        {
            try
            {
                nodeLayer_.syncExec( new Callable<Void>()
                {
                    @Override
                    public Void call()
                        throws TableNetworkException
                    {
                        if( transportLayer_ != null )
                        {
                            throw new TableNetworkException( TableNetworkError.ILLEGAL_CONNECTION_STATE );
                        }

                        localPlayerName_ = configuration_.getLocalPlayerName();
                        password_ = configuration_.getPassword();
                        tables_.put( localPlayerName_, new LocalNetworkTable( nodeLayer_, createTableManagerDecoratorForLocalNetworkTable( getTableManager() ), configuration_.getLocalTable() ) );

                        AbstractNode.this.connecting( configuration_ );

                        return null;
                    }
                } );
            }
            catch( final ExecutionException e )
            {
                final Throwable cause = e.getCause();
                if( cause instanceof TableNetworkException )
                {
                    throw (TableNetworkException)cause;
                }

                throw TaskUtils.launderThrowable( cause );
            }
        }

        /**
         * Opens the transport layer associated with the table network node.
         * 
         * @throws java.lang.InterruptedException
         *         If this thread is interrupted while waiting for the operation
         *         to complete.
         * @throws org.gamegineer.table.net.TableNetworkException
         *         If an error occurs.
         */
        private void openTransportLayer()
            throws TableNetworkException, InterruptedException
        {
            final ITransportLayer transportLayer = createTransportLayer();
            try
            {
                transportLayer.endOpen( transportLayer.beginOpen( configuration_.getHostName(), configuration_.getPort() ) );
            }
            catch( final TransportException e )
            {
                throw new TableNetworkException( TableNetworkError.TRANSPORT_ERROR, e );
            }

            try
            {
                nodeLayer_.syncExec( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        transportLayer_ = transportLayer;
                    }
                } );
            }
            catch( final ExecutionException e )
            {
                throw TaskUtils.launderThrowable( e.getCause() );
            }
        }

        /**
         * Synchronously disconnects the table network node from the table
         * network.
         * 
         * @throws java.lang.InterruptedException
         *         If this thread is interrupted while waiting for the operation
         *         to complete.
         */
        private void syncDisconnect()
            throws InterruptedException
        {
            try
            {
                endDisconnect( nodeLayer_.syncExec( new Callable<Future<Void>>()
                {
                    @Override
                    public Future<Void> call()
                    {
                        return beginDisconnect();
                    }
                } ) );
            }
            catch( final ExecutionException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.AbstractNode_connect_disconnectError, e );
            }
        }
    }

    /**
     * Responsible for disconnecting the table network node from the table
     * network.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
    private final class Disconnecter
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The transport layer associated with the table network node. */
        @SuppressWarnings( "hiding" )
        private final ITransportLayer transportLayer_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code Disconnecter} class.
         * 
         * <p>
         * This constructor must be called on the node layer thread.
         * </p>
         */
        Disconnecter()
        {
            assert isNodeLayerThread();

            transportLayer_ = AbstractNode.this.transportLayer_;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Closes the transport layer associated with the table network node.
         * 
         * @throws java.lang.InterruptedException
         *         If this thread is interrupted while waiting for the operation
         *         to complete.
         */
        private void closeTransportLayer()
            throws InterruptedException
        {
            if( transportLayer_ != null )
            {
                transportLayer_.endClose( transportLayer_.beginClose() );
            }
        }

        /**
         * Disconnects the table network node from the table network.
         * 
         * <p>
         * This method must not be called on the node layer thread.
         * </p>
         */
        void disconnect()
        {
            assert !isNodeLayerThread();

            try
            {
                disconnecting();
                closeTransportLayer();
                disconnected();
            }
            catch( final InterruptedException e )
            {
                Thread.currentThread().interrupt();
            }
        }

        /**
         * Invoked when the table network node has disconnected from the table
         * network.
         * 
         * @throws java.lang.InterruptedException
         *         If this thread is interrupted while waiting for the operation
         *         to complete.
         */
        private void disconnected()
            throws InterruptedException
        {
            try
            {
                nodeLayer_.syncExec( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if( transportLayer_ != null )
                        {
                            AbstractNode.this.transportLayer_ = null;

                            final INetworkTable table = tables_.remove( localPlayerName_ );
                            assert table != null;
                            table.dispose();

                            AbstractNode.this.disconnected();
                        }

                        dispose();
                    }
                } );
            }
            catch( final ExecutionException e )
            {
                throw TaskUtils.launderThrowable( e.getCause() );
            }
        }

        /**
         * Invoked when the table network is about to disconnect from the table
         * network.
         * 
         * @throws java.lang.InterruptedException
         *         If this thread is interrupted while waiting for the operation
         *         to complete.
         */
        private void disconnecting()
            throws InterruptedException
        {
            try
            {
                nodeLayer_.syncExec( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if( transportLayer_ != null )
                        {
                            AbstractNode.this.disconnecting();
                        }
                    }
                } );
            }
            catch( final ExecutionException e )
            {
                throw TaskUtils.launderThrowable( e.getCause() );
            }
        }
    }

    /**
     * Standard implementation of {@link ITableManager} that forwards each
     * method call to all tables connected to the node, not including the
     * originator of the request.
     */
    @Immutable
    @SuppressWarnings( "synthetic-access" )
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
         * @see org.gamegineer.table.internal.net.node.ITableManager#incrementComponentState(org.gamegineer.table.internal.net.node.INetworkTable,
         *      org.gamegineer.table.core.ComponentPath,
         *      org.gamegineer.table.internal.net.node.ComponentIncrement)
         */
        @Override
        public void incrementComponentState(
            final INetworkTable sourceTable,
            final ComponentPath componentPath,
            final ComponentIncrement componentIncrement )
        {
            assertArgumentNotNull( sourceTable, "sourceTable" ); //$NON-NLS-1$
            assertArgumentNotNull( componentPath, "componentPath" ); //$NON-NLS-1$
            assertArgumentNotNull( componentIncrement, "componentIncrement" ); //$NON-NLS-1$
            assert isNodeLayerThread();

            for( final INetworkTable table : getTables() )
            {
                if( table != sourceTable )
                {
                    table.incrementComponentState( componentPath, componentIncrement );
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
        public void setTableState(
            final INetworkTable sourceTable,
            final Object tableMemento )
        {
            assertArgumentNotNull( sourceTable, "sourceTable" ); //$NON-NLS-1$
            assertArgumentNotNull( tableMemento, "tableMemento" ); //$NON-NLS-1$
            assert isNodeLayerThread();

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
