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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.IMementoOriginator;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.node.AbstractNode;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
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

    /** The collection of players connected to the table network. */
    @GuardedBy( "getLock()" )
    private final Collection<String> players_;


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
        players_ = new ArrayList<String>();
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
                        throw new TableNetworkException( TableNetworkError.TIME_OUT, Messages.ClientNode_handshake_timedOut );
                    }
                }
                catch( final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                    throw new TableNetworkException( TableNetworkError.INTERRUPTED, Messages.ClientNode_handshake_interrupted, e );
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
     * @see org.gamegineer.table.internal.net.node.INodeController#getPlayers()
     */
    @Override
    public Collection<String> getPlayers()
    {
        synchronized( getLock() )
        {
            return new ArrayList<String>( players_ );
        }
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
        final Collection<String> players )
    {
        assertArgumentNotNull( players, "players" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        assertConnected();
        players_.clear();
        players_.addAll( players );
        getTableNetworkController().playersUpdated();
    }

    /*
     * @see org.gamegineer.table.internal.net.node.client.IClientNode#setTableMemento(java.lang.Object)
     */
    @Override
    public void setTableMemento(
        final Object memento )
    {
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        assertConnected();
        updateLocalTable( memento );
    }

    /**
     * Updates the state of the local table using the specified memento.
     * 
     * @param memento
     *        The table memento; must not be {@code null}.
     */
    private void updateLocalTable(
        /* @NonNull */
        final Object memento )
    {
        assert memento != null;

        try
        {
            ((IMementoOriginator)getLocalTable()).setMemento( memento );
        }
        catch( final MementoException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.ClientNode_updateLocalTable_error, e );
        }
    }
}
