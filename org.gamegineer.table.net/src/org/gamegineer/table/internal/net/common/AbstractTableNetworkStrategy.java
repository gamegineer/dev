/*
 * AbstractTableNetworkStrategy.java
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
import org.gamegineer.table.internal.net.ITableGateway;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.ITableNetworkStrategy;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.internal.net.transport.ITransportLayerContext;
import org.gamegineer.table.internal.net.transport.TransportException;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * Superclass for all implementations of
 * {@link org.gamegineer.table.internal.net.ITableNetworkStrategy}.
 * 
 * <p>
 * Implementations of this class should not be reused for multiple connections.
 * </p>
 */
@ThreadSafe
public abstract class AbstractTableNetworkStrategy
    implements ITableNetworkStrategy, ITableGatewayContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The local client table gateway or {@code null} if the network is not
     * connected.
     */
    @GuardedBy( "getLock()" )
    private ITableGateway localClientTableGateway_;

    /** The local player name or {@code null} if the network is not connected. */
    @GuardedBy( "getLock()" )
    private String localPlayerName_;

    /** The instance lock. */
    private final Object lock_;

    /** The network password or {@code null} if the network is not connected. */
    @GuardedBy( "getLock()" )
    private SecureString password_;

    /** The collection of registered table gateways. */
    @GuardedBy( "getLock()" )
    private final Map<String, ITableGateway> tableGateways_;

    /** The table network controller. */
    private final ITableNetworkController tableNetworkController_;

    /** The transport layer or {@code null} if the network is not connected. */
    @GuardedBy( "getLock()" )
    private ITransportLayer transportLayer_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableNetworkStrategy}
     * class.
     * 
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetworkController} is {@code null}.
     */
    protected AbstractTableNetworkStrategy(
        /* @NonNull */
        final ITableNetworkController tableNetworkController )
    {
        assertArgumentNotNull( tableNetworkController, "tableNetworkController" ); //$NON-NLS-1$

        localClientTableGateway_ = null;
        localPlayerName_ = null;
        lock_ = new Object();
        password_ = null;
        tableGateways_ = new HashMap<String, ITableGateway>();
        tableNetworkController_ = tableNetworkController;
        transportLayer_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.ITableGatewayContext#addTableGateway(org.gamegineer.table.internal.net.ITableGateway)
     */
    @Override
    public final void addTableGateway(
        final ITableGateway tableGateway )
    {
        assertArgumentNotNull( tableGateway, "tableGateway" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        assertArgumentLegal( !tableGateways_.containsKey( tableGateway.getPlayerName() ), "tableGateway", Messages.AbstractTableNetworkStrategy_addTableGateway_tableGatewayRegistered ); //$NON-NLS-1$ 

        tableGateways_.put( tableGateway.getPlayerName(), tableGateway );
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Table gateway registered for player '%s'.", tableGateway.getPlayerName() ) ); //$NON-NLS-1$
        tableGatewayAdded( tableGateway );
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableNetworkStrategy#connect(org.gamegineer.table.net.ITableNetworkConfiguration)
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
            localClientTableGateway_ = new LocalClientTableGateway( localPlayerName_ );
            addTableGateway( localClientTableGateway_ );
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
     * Template method invoked when the table network has connected.
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
     * Template method invoked when the table network is connecting.
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
     * Template method invoked to create the transport layer for this strategy
     * using the context transport layer factory.
     * 
     * <p>
     * This method is invoked while the instance lock is held.
     * </p>
     * 
     * @return The transport layer for this strategy; never {@code null}.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    protected abstract ITransportLayer createTransportLayer();

    /*
     * @see org.gamegineer.table.internal.net.ITableNetworkStrategy#disconnect()
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

                removeTableGateway( localClientTableGateway_ );
                localClientTableGateway_ = null;
                disconnected();
                dispose();
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableGatewayContext#disconnectTableNetwork(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    public final void disconnectTableNetwork(
        final TableNetworkError error )
    {
        disconnectingTableNetwork( error );
        tableNetworkController_.disconnect( error );
    }

    /**
     * Template method invoked when the table network is disconnecting.
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
     *        The error that caused the table network to be disconnected or
     *        {@code null} if the table network was disconnected normally.
     */
    protected void disconnectingTableNetwork(
        final TableNetworkError error )
    {
        // do nothing
    }

    /**
     * Template method invoked when the table network has disconnected.
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
     * Template method invoked when the table network is disconnecting.
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
     * Disposes of the resources managed by the strategy.
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

        tableGateways_.clear();
        localClientTableGateway_ = null;
        localPlayerName_ = null;
        password_.dispose();
        password_ = null;
    }

    /**
     * @throws java.lang.IllegalStateException
     *         If the network is not connected.
     * 
     * @see org.gamegineer.table.internal.net.ITableGatewayContext#getLocalPlayerName()
     */
    @Override
    public final String getLocalPlayerName()
    {
        assert Thread.holdsLock( getLock() );

        assertStateLegal( localPlayerName_ != null, Messages.AbstractTableNetworkStrategy_networkDisconnected );
        return localPlayerName_;
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableGatewayContext#getLock()
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
     * @see org.gamegineer.table.internal.net.ITableGatewayContext#getPassword()
     */
    @Override
    public final SecureString getPassword()
    {
        assert Thread.holdsLock( getLock() );

        assertStateLegal( password_ != null, Messages.AbstractTableNetworkStrategy_networkDisconnected );
        return new SecureString( password_ );
    }

    /**
     * Gets the collection of registered table gateways.
     * 
     * @return The collection of registered table gateways; never {@code null}.
     */
    /* @NonNull */
    protected final Collection<ITableGateway> getTableGateways()
    {
        synchronized( getLock() )
        {
            return new ArrayList<ITableGateway>( tableGateways_.values() );
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

    /*
     * @see org.gamegineer.table.internal.net.ITableGatewayContext#isTableGatewayPresent(java.lang.String)
     */
    @Override
    public final boolean isTableGatewayPresent(
        final String playerName )
    {
        assertArgumentNotNull( playerName, "playerName" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        return tableGateways_.containsKey( playerName );
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableGatewayContext#removeTableGateway(org.gamegineer.table.internal.net.ITableGateway)
     */
    @Override
    public final void removeTableGateway(
        final ITableGateway tableGateway )
    {
        assertArgumentNotNull( tableGateway, "tableGateway" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        assertArgumentLegal( tableGateways_.remove( tableGateway.getPlayerName() ) != null, "tableGateway", Messages.AbstractTableNetworkStrategy_removeTableGateway_tableGatewayNotRegistered ); //$NON-NLS-1$

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Table gateway unregistered for player '%s'.", tableGateway.getPlayerName() ) ); //$NON-NLS-1$
        tableGatewayRemoved( tableGateway );
    }

    /**
     * Template method invoked when a table gateway has been added to the table
     * network.
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
     * @param tableGateway
     *        The table gateway that was added; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableGateway} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    protected void tableGatewayAdded(
        /* @NonNull */
        final ITableGateway tableGateway )
    {
        assertArgumentNotNull( tableGateway, "tableGateway" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /**
     * Template method invoked when a table gateway has been removed from the
     * table network.
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
     * @param tableGateway
     *        The table gateway that was removed; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableGateway} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    protected void tableGatewayRemoved(
        /* @NonNull */
        final ITableGateway tableGateway )
    {
        assertArgumentNotNull( tableGateway, "tableGateway" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        // do nothing
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Superclass for implementations of the {@link ITransportLayerContext}
     * associated with a table network strategy.
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
