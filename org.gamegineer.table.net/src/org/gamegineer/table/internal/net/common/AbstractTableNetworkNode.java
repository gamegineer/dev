/*
 * AbstractTableNetworkNode.java
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

package org.gamegineer.table.internal.net.common;

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
import org.gamegineer.table.internal.net.ITableNetworkNode;
import org.gamegineer.table.internal.net.ITableNetworkNodeController;
import org.gamegineer.table.internal.net.ITableProxy;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.internal.net.transport.ITransportLayerContext;
import org.gamegineer.table.internal.net.transport.TransportException;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * Superclass for all table network nodes.
 * 
 * <p>
 * Implementations of this class should not be reused for multiple connections.
 * </p>
 */
@ThreadSafe
public abstract class AbstractTableNetworkNode
    implements ITableNetworkNode, ITableNetworkNodeController
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The local client table proxy or {@code null} if the network is not
     * connected.
     */
    @GuardedBy( "getLock()" )
    private ITableProxy localClientTableProxy_;

    /** The local player name or {@code null} if the network is not connected. */
    @GuardedBy( "getLock()" )
    private String localPlayerName_;

    /** The instance lock. */
    private final Object lock_;

    /** The network password or {@code null} if the network is not connected. */
    @GuardedBy( "getLock()" )
    private SecureString password_;

    /** The table network controller. */
    private final ITableNetworkController tableNetworkController_;

    /** The collection of registered table proxies. */
    @GuardedBy( "getLock()" )
    private final Map<String, ITableProxy> tableProxies_;

    /** The transport layer or {@code null} if the network is not connected. */
    @GuardedBy( "getLock()" )
    private ITransportLayer transportLayer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableNetworkNode} class.
     * 
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetworkController} is {@code null}.
     */
    protected AbstractTableNetworkNode(
        /* @NonNull */
        final ITableNetworkController tableNetworkController )
    {
        assertArgumentNotNull( tableNetworkController, "tableNetworkController" ); //$NON-NLS-1$

        localClientTableProxy_ = null;
        localPlayerName_ = null;
        lock_ = new Object();
        password_ = null;
        tableNetworkController_ = tableNetworkController;
        tableProxies_ = new HashMap<String, ITableProxy>();
        transportLayer_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.ITableNetworkNode#addTableProxy(org.gamegineer.table.internal.net.ITableProxy)
     */
    @Override
    public final void addTableProxy(
        final ITableProxy tableProxy )
    {
        assertArgumentNotNull( tableProxy, "tableProxy" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        assertArgumentLegal( !tableProxies_.containsKey( tableProxy.getPlayerName() ), "tableProxy", Messages.AbstractTableNetworkNode_addTableProxy_tableProxyRegistered ); //$NON-NLS-1$ 

        tableProxies_.put( tableProxy.getPlayerName(), tableProxy );
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Table proxy registered for player '%s'.", tableProxy.getPlayerName() ) ); //$NON-NLS-1$
        tableProxyAdded( tableProxy );
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableNetworkNodeController#connect(org.gamegineer.table.net.ITableNetworkConfiguration)
     */
    @Override
    public final void connect(
        final ITableNetworkConfiguration configuration )
        throws TableNetworkException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        synchronized( getLock() )
        {
            if( transportLayer_ != null )
            {
                throw new TableNetworkException( TableNetworkError.ILLEGAL_CONNECTION_STATE );
            }

            localPlayerName_ = configuration.getLocalPlayerName();
            password_ = configuration.getPassword();
            localClientTableProxy_ = new LocalClientTableProxy( localPlayerName_ );
            addTableProxy( localClientTableProxy_ );
            connecting();

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
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If an error occurs.
     */
    @GuardedBy( "getLock()" )
    protected void connecting()
        throws TableNetworkException
    {
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
     * @see org.gamegineer.table.internal.net.ITableNetworkNodeController#disconnect()
     */
    @Override
    public final void disconnect()
    {
        synchronized( getLock() )
        {
            if( transportLayer_ != null )
            {
                disconnecting();

                transportLayer_.close();
                transportLayer_ = null;

                removeTableProxy( localClientTableProxy_ );
                localClientTableProxy_ = null;
                disconnected();
                dispose();
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableNetworkNode#disconnect(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    public final void disconnect(
        final TableNetworkError error )
    {
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
     * This implementation does nothing.
     * </p>
     */
    @GuardedBy( "getLock()" )
    protected void disconnecting()
    {
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /**
     * Template method invoked when the table network node is disconnecting for
     * the specified cause.
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
     * @param error
     *        The error that caused the table network node to be disconnected or
     *        {@code null} if the table network node was disconnected normally.
     */
    protected void disconnecting(
        final TableNetworkError error )
    {
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

        tableProxies_.clear();
        localClientTableProxy_ = null;
        localPlayerName_ = null;
        password_.dispose();
        password_ = null;
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the network is not connected.
     * 
     * @see org.gamegineer.table.internal.net.ITableNetworkNode#getLocalPlayerName()
     */
    @Override
    public final String getLocalPlayerName()
    {
        assert Thread.holdsLock( getLock() );

        assertStateLegal( localPlayerName_ != null, Messages.AbstractTableNetworkNode_networkDisconnected );
        return localPlayerName_;
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableNetworkNode#getLock()
     */
    @Override
    public final Object getLock()
    {
        return lock_;
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the network is not connected.
     * 
     * @see org.gamegineer.table.internal.net.ITableNetworkNode#getPassword()
     */
    @Override
    public final SecureString getPassword()
    {
        assert Thread.holdsLock( getLock() );

        assertStateLegal( password_ != null, Messages.AbstractTableNetworkNode_networkDisconnected );
        return new SecureString( password_ );
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
     * Gets the collection of registered table proxies.
     * 
     * @return The collection of registered table proxies; never {@code null}.
     */
    /* @NonNull */
    protected final Collection<ITableProxy> getTableProxies()
    {
        synchronized( getLock() )
        {
            return new ArrayList<ITableProxy>( tableProxies_.values() );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableNetworkNode#isTableProxyPresent(java.lang.String)
     */
    @Override
    public final boolean isTableProxyPresent(
        final String playerName )
    {
        assertArgumentNotNull( playerName, "playerName" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        return tableProxies_.containsKey( playerName );
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableNetworkNode#removeTableProxy(org.gamegineer.table.internal.net.ITableProxy)
     */
    @Override
    public final void removeTableProxy(
        final ITableProxy tableProxy )
    {
        assertArgumentNotNull( tableProxy, "tableProxy" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        assertArgumentLegal( tableProxies_.remove( tableProxy.getPlayerName() ) != null, "tableProxy", Messages.AbstractTableNetworkNode_removeTableProxy_tableProxyNotRegistered ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Table proxy unregistered for player '%s'.", tableProxy.getPlayerName() ) ); //$NON-NLS-1$
        tableProxyRemoved( tableProxy );
    }

    /**
     * Template method invoked when a table proxy has been added to the table
     * network node.
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
     * @param tableProxy
     *        The table proxy that was added; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableProxy} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    protected void tableProxyAdded(
        /* @NonNull */
        final ITableProxy tableProxy )
    {
        assertArgumentNotNull( tableProxy, "tableProxy" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /**
     * Template method invoked when a table proxy has been removed from the
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
     * @param tableProxy
     *        The table proxy that was removed; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableProxy} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    protected void tableProxyRemoved(
        /* @NonNull */
        final ITableProxy tableProxy )
    {
        assertArgumentNotNull( tableProxy, "tableProxy" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        // do nothing
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
}
