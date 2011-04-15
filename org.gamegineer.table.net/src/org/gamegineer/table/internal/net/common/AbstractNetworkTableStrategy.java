/*
 * AbstractNetworkTableStrategy.java
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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.INetworkTableStrategy;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.NetworkTable;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.internal.net.transport.ITransportLayerFactory;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableException;

/**
 * Superclass for all implementations of
 * {@link org.gamegineer.table.internal.net.INetworkTableStrategy}.
 * 
 * <p>
 * Implementations of this class should not be reused for multiple connections.
 * </p>
 */
@ThreadSafe
public abstract class AbstractNetworkTableStrategy
    implements INetworkTableStrategy, ITableGatewayContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The local player name or {@code null} if the network is not connected. */
    @GuardedBy( "getLock()" )
    private String localPlayerName_;

    /** The instance lock. */
    private final Object lock_;

    /** The network table that hosts the strategy. */
    private final NetworkTable networkTable_;

    /** The network password or {@code null} if the network is not connected. */
    @GuardedBy( "getLock()" )
    private SecureString password_;

    /** The transport layer or {@code null} if the network is not connected. */
    @GuardedBy( "getLock()" )
    private ITransportLayer transportLayer_;

    /** The transport layer factory used by the strategy. */
    private final ITransportLayerFactory transportLayerFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNetworkTableStrategy}
     * class.
     * 
     * @param networkTable
     *        The network table that hosts the strategy; must not be {@code
     *        null}.
     * @param transportLayerFactory
     *        The transport layer factory used by the strategy; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code networkTable} or {@code transportLayerFactory} is
     *         {@code null}.
     */
    protected AbstractNetworkTableStrategy(
        /* @NonNull */
        final NetworkTable networkTable,
        /* @NonNull */
        final ITransportLayerFactory transportLayerFactory )
    {
        assertArgumentNotNull( networkTable, "networkTable" ); //$NON-NLS-1$
        assertArgumentNotNull( transportLayerFactory, "transportLayerFactory" ); //$NON-NLS-1$

        localPlayerName_ = null;
        lock_ = new Object();
        networkTable_ = networkTable;
        password_ = null;
        transportLayer_ = null;
        transportLayerFactory_ = transportLayerFactory;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.INetworkTableStrategy#connect(org.gamegineer.table.net.INetworkTableConfiguration)
     */
    @Override
    public final void connect(
        final INetworkTableConfiguration configuration )
        throws NetworkTableException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        synchronized( getLock() )
        {
            if( transportLayer_ != null )
            {
                throw new NetworkTableException( Messages.AbstractNetworkTableStrategy_connect_networkConnected );
            }

            localPlayerName_ = configuration.getLocalPlayerName();
            password_ = configuration.getPassword();
            connecting();

            final ITransportLayer transportLayer = createTransportLayer( transportLayerFactory_ );
            try
            {
                transportLayer.open( configuration.getHostName(), configuration.getPort() );
            }
            catch( final NetworkTableException e )
            {
                dispose();
                throw e;
            }

            transportLayer_ = transportLayer;
            connected();
        }
    }

    /**
     * Template method invoked when the network table has connected to the
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
     */
    @GuardedBy( "getLock()" )
    protected void connected()
    {
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /**
     * Template method invoked when the network table is connecting to the
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
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    @GuardedBy( "getLock()" )
    protected void connecting()
        throws NetworkTableException
    {
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /**
     * Creates the transport layer for this strategy using the specified
     * transport layer factory.
     * 
     * <p>
     * This method is invoked while the instance lock is held.
     * </p>
     * 
     * @param transportLayerFactory
     *        The transport layer factory; must not be {@code null}.
     * 
     * @return The transport layer for this strategy; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code transportLayerFactory} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    /* @NonNull */
    protected abstract ITransportLayer createTransportLayer(
        /* @NonNull */
        ITransportLayerFactory transportLayerFactory );

    /*
     * @see org.gamegineer.table.internal.net.INetworkTableStrategy#disconnect()
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

                disconnected();
                dispose();
            }
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.ITableGatewayContext#disconnectNetworkTable()
     */
    @Override
    public void disconnectNetworkTable()
    {
        getNetworkTable().disconnect();
    }

    /**
     * Template method invoked when the network table has disconnected from the
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
     */
    @GuardedBy( "getLock()" )
    protected void disconnected()
    {
        assert Thread.holdsLock( getLock() );

        // do nothing
    }

    /**
     * Template method invoked when the network table is disconnecting from the
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
        synchronized( getLock() )
        {
            assertStateLegal( localPlayerName_ != null, Messages.AbstractNetworkTableStrategy_networkDisconnected );
            return localPlayerName_;
        }
    }

    /**
     * Gets the instance lock.
     * 
     * @return The instance lock; never {@code null}.
     */
    /* @NonNull */
    protected final Object getLock()
    {
        return lock_;
    }

    /**
     * Gets the network table that hosts the strategy.
     * 
     * @return The network table that hosts the strategy; never {@code null}.
     */
    /* @NonNull */
    protected final NetworkTable getNetworkTable()
    {
        return networkTable_;
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
        synchronized( getLock() )
        {
            assertStateLegal( password_ != null, Messages.AbstractNetworkTableStrategy_networkDisconnected );
            return new SecureString( password_ );
        }
    }
}
