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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
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

    /** The executor service associated with the node layer. */
    private final ExecutorService executorService_;

    /**
     * The local player name or {@code null} if the table network is not
     * connected.
     */
    private String localPlayerName_;

    /** The node layer. */
    private final INodeLayer nodeLayer_;

    /** The node layer thread. */
    private final Thread nodeLayerThread_;

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
     * <p>
     * It is assumed that the thread that invokes this constructor is the node
     * layer thread and is managed by the specified executor service.
     * </p>
     * 
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * @param executorService
     *        The node layer executor service; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetworkController} or {@code executorService} is
     *         {@code null}.
     */
    protected AbstractNode(
        /* @NonNull */
        final ITableNetworkController tableNetworkController,
        /* @NonNull */
        final ExecutorService executorService )
    {
        assertArgumentNotNull( tableNetworkController, "tableNetworkController" ); //$NON-NLS-1$
        assertArgumentNotNull( executorService, "executorService" ); //$NON-NLS-1$

        executorService_ = executorService;
        localPlayerName_ = null;
        nodeLayer_ = new NodeLayer();
        nodeLayerThread_ = Thread.currentThread();
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
    protected final void assertConnected()
    {
        assert isNodeLayerThread();

        assertStateLegal( isConnected(), NonNlsMessages.AbstractNode_networkDisconnected );
    }

    /**
     * Asynchronously executes the specified task on the node layer thread.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @param <T>
     *        The return type of the task.
     * 
     * @param task
     *        The task to execute; must not be {@code null}.
     * 
     * @return An asynchronous completion token for the task; never {@code null}
     *         .
     */
    /* @NonNull */
    final <T> Future<T> asyncExec(
        /* @NonNull */
        final Callable<T> task )
    {
        assert task != null;

        final String playerName = ThreadPlayer.getPlayerName();
        return executorService_.submit( new Callable<T>()
        {
            @Override
            public T call()
                throws Exception
            {
                ThreadPlayer.setPlayerName( playerName );
                try
                {
                    return task.call();
                }
                finally
                {
                    ThreadPlayer.setPlayerName( null );
                }
            }
        } );
    }

    /**
     * Asynchronously executes the specified task on the node layer thread.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @param task
     *        The task to execute; must not be {@code null}.
     * 
     * @return An asynchronous completion token for the task; never {@code null}
     *         .
     */
    /* @NonNull */
    final Future<?> asyncExec(
        /* @NonNull */
        final Runnable task )
    {
        assert task != null;

        final String playerName = ThreadPlayer.getPlayerName();
        return executorService_.submit( new Runnable()
        {
            @Override
            public void run()
            {
                ThreadPlayer.setPlayerName( playerName );
                try
                {
                    task.run();
                }
                finally
                {
                    ThreadPlayer.setPlayerName( null );
                }
            }
        } );
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

        return Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Override
            public Void call()
                throws TableNetworkException
            {
                try
                {
                    try
                    {
                        try
                        {
                            syncExec( new Callable<Void>()
                            {
                                @Override
                                @SuppressWarnings( "synthetic-access" )
                                public Void call()
                                    throws TableNetworkException
                                {
                                    if( transportLayer_ != null )
                                    {
                                        throw new TableNetworkException( TableNetworkError.ILLEGAL_CONNECTION_STATE );
                                    }

                                    localPlayerName_ = configuration.getLocalPlayerName();
                                    password_ = configuration.getPassword();
                                    tables_.put( localPlayerName_, new LocalNetworkTable( nodeLayer_, createTableManagerDecoratorForLocalNetworkTable( getTableManager() ), configuration.getLocalTable() ) );

                                    connecting( configuration );

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

                        final ITransportLayer transportLayer = createTransportLayer();
                        try
                        {
                            transportLayer.endOpen( transportLayer.beginOpen( configuration.getHostName(), configuration.getPort() ) );
                        }
                        catch( final TransportException e )
                        {
                            throw new TableNetworkException( TableNetworkError.TRANSPORT_ERROR, e );
                        }

                        try
                        {
                            syncExec( new Callable<Void>()
                            {
                                @Override
                                @SuppressWarnings( "synthetic-access" )
                                public Void call()
                                {
                                    transportLayer_ = transportLayer;

                                    return null;
                                }
                            } );
                        }
                        catch( final ExecutionException e )
                        {
                            throw TaskUtils.launderThrowable( e.getCause() );
                        }

                        try
                        {
                            connected();
                        }
                        catch( final TableNetworkException e )
                        {
                            try
                            {
                                endDisconnect( syncExec( new Callable<Future<Void>>()
                                {
                                    @Override
                                    public Future<Void> call()
                                    {
                                        return beginDisconnect();
                                    }
                                } ) );
                            }
                            catch( final ExecutionException e2 )
                            {
                                // TODO: LOG
                            }

                            throw e;
                        }

                        return null;
                    }
                    catch( final TableNetworkException e )
                    {
                        try
                        {
                            syncExec( new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    dispose();
                                }
                            } );
                        }
                        catch( final ExecutionException e2 )
                        {
                            // XXX
                            // LOG
                        }
                        catch( final RejectedExecutionException e2 )
                        {
                            // XXX: this happening because connected() threw an exception
                            // and we've already shutdown the executor service...  find a
                            // better way to handle this....
                            // LOG
                        }

                        throw e;
                    }
                    catch( final RuntimeException e )
                    {
                        try
                        {
                            syncExec( new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    dispose();
                                }
                            } );
                        }
                        catch( final ExecutionException e2 )
                        {
                            // XXX
                            // LOG
                        }
                        catch( final RejectedExecutionException e2 )
                        {
                            // XXX
                            // LOG
                        }

                        throw e;
                    }
                }
                catch( final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                    // XXX: NOTE that it is possible this thread was interrupted AFTER
                    // we already established the connection, in which case, dispose() does
                    // not actually close the transport layer....
                    //
                    // we either need to have dispose() shutdown the transport layer if it
                    // is connected, or need to determine if we need to call disconnect()
                    // instead....  but that could get real messy..  may have to rethink the
                    // whole way connected() is implemented for the handshake to complete so
                    // we can report authentication errors during connection....????
                    //XXX:dispose();
                    // ---> can't call dispose() here because we then have to handle
                    // InterruptedException AGAIN!!!!
                    throw new TableNetworkException( TableNetworkError.INTERRUPTED, e );
                }

                // XXX: WE MUST CALL endConnect() here just like we did in transport layer??
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

        final ITransportLayer transportLayer = transportLayer_;
        return Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
            {
                try
                {
                    syncExec( new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if( transportLayer != null )
                            {
                                disconnecting();
                            }
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
                    // TODO
                    return null;
                }

                if( transportLayer != null )
                {
                    try
                    {
                        transportLayer.endClose( transportLayer.beginClose() );
                    }
                    catch( final InterruptedException e )
                    {
                        Thread.currentThread().interrupt();
                        Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Interrupted while waiting for transport layer to close", e ); //$NON-NLS-1$
                        // TODO
                        return null;
                    }
                }

                try
                {
                    syncExec( new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if( transportLayer != null )
                            {
                                transportLayer_ = null;

                                final INetworkTable table = tables_.remove( localPlayerName_ );
                                assert table != null;
                                table.dispose();

                                disconnected();
                                dispose();
                            }
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
                    // TODO
                    return null;
                }

                return null;

                // XXX: WE MUST CALL endDisconnect() here just like we did in transport layer ??
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
     *        The table manager for the local network table; must not be {@code
     *        null}.
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
     */
    /* @NonNull */
    protected abstract ITransportLayer createTransportLayer();

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
                tableNetworkController_.disconnect( error );
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

        executorService_.shutdown();
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

        return new ArrayList<RemoteNodeType>( remoteNodes_.values() );
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
        return new ArrayList<INetworkTable>( tables_.values() );
    }

    /**
     * Indicates the table network is connected.
     * 
     * @return {@code true} if the table network is connected; otherwise {@code
     *         false}.
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
        return Thread.currentThread() == nodeLayerThread_;
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

    /**
     * Synchronously executes the specified task on the node layer thread.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @param <T>
     *        The return type of the task.
     * 
     * @param task
     *        The task to execute; must not be {@code null}.
     * 
     * @return The return value of the task; may be {@code null}.
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the task to
     *         complete.
     * @throws java.util.concurrent.ExecutionException
     *         If an error occurs while executing the task.
     */
    /* @Nullable */
    final <T> T syncExec(
        /* @NonNull */
        final Callable<T> task )
        throws ExecutionException, InterruptedException
    {
        assert task != null;

        if( isNodeLayerThread() )
        {
            try
            {
                return task.call();
            }
            catch( final Exception e )
            {
                throw new ExecutionException( e );
            }
        }

        return asyncExec( task ).get();
    }

    /**
     * Synchronously executes the specified task on the node layer thread.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @param task
     *        The task to execute; must not be {@code null}.
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the task to
     *         complete.
     * @throws java.util.concurrent.ExecutionException
     *         If an error occurs while executing the task.
     */
    final void syncExec(
        /* @NonNull */
        final Runnable task )
        throws ExecutionException, InterruptedException
    {
        assert task != null;

        if( isNodeLayerThread() )
        {
            try
            {
                task.run();
            }
            catch( final Exception e )
            {
                throw new ExecutionException( e );
            }
        }
        else
        {
            asyncExec( task ).get();
        }
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
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Creates the node layer executor service.
         * 
         * @return The node layer executor service; never {@code null}.
         */
        /* @NonNull */
        private static ExecutorService createExecutorService()
        {
            return Executors.newSingleThreadExecutor( new ThreadFactory()
            {
                @Override
                public Thread newThread(
                    final Runnable r )
                {
                    return new Thread( r, NonNlsMessages.AbstractNode_nodeLayerThread_name );
                }
            } );
        }

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

            final ExecutorService executorService = createExecutorService();
            final Future<T> future = executorService.submit( new Callable<T>()
            {
                @Override
                public T call()
                {
                    return createNode( tableNetworkController, executorService );
                }
            } );

            try
            {
                return future.get();
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
         * @param tableNetworkController
         *        The table network controller; must not be {@code null}.
         * @param executorService
         *        The node layer executor service; must not be {@code null}.
         * 
         * @return A new table network node; never {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code tableNetworkController} or {@code executorService}
         *         is {@code null}.
         */
        /* @NonNull */
        protected abstract T createNode(
            /* @NonNull */
            ITableNetworkController tableNetworkController,
            /* @NonNull */
            ExecutorService executorService );
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
                asyncExec( new Runnable()
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
                asyncExec( new Runnable()
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
                asyncExec( new Runnable()
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
                asyncExec( new Runnable()
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
            try
            {
                asyncExec( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        getTableNetworkController().disconnect( (exception != null) ? TableNetworkError.TRANSPORT_ERROR : null );
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
            assert isNodeLayerThread();

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
            assert isNodeLayerThread();

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
            assert isNodeLayerThread();

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

    /**
     * Implementation of {@link INodeLayer}.
     */
    @Immutable
    private final class NodeLayer
        implements INodeLayer
    {
        // TODO: Extract to a package-private class, and extract nodeLayerThread_ and
        // executorService_ fields to that class.

        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code NodeLayer} class.
         */
        NodeLayer()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.net.node.INodeLayer#asyncExec(java.util.concurrent.Callable)
         */
        @Override
        public <T> Future<T> asyncExec(
            final Callable<T> task )
        {
            return AbstractNode.this.asyncExec( task );
        }

        /*
         * @see org.gamegineer.table.internal.net.node.INodeLayer#asyncExec(java.lang.Runnable)
         */
        @Override
        public Future<?> asyncExec(
            final Runnable task )
        {
            return AbstractNode.this.asyncExec( task );
        }

        /*
         * @see org.gamegineer.table.internal.net.node.INodeLayer#isNodeLayerThread()
         */
        @Override
        public boolean isNodeLayerThread()
        {
            return AbstractNode.this.isNodeLayerThread();
        }

        /*
         * @see org.gamegineer.table.internal.net.node.INodeLayer#syncExec(java.util.concurrent.Callable)
         */
        @Override
        public <T> T syncExec(
            final Callable<T> task )
            throws ExecutionException, InterruptedException
        {
            return AbstractNode.this.syncExec( task );
        }

        /*
         * @see org.gamegineer.table.internal.net.node.INodeLayer#syncExec(java.lang.Runnable)
         */
        @Override
        public void syncExec(
            final Runnable task )
            throws ExecutionException, InterruptedException
        {
            AbstractNode.this.syncExec( task );
        }
    }
}
