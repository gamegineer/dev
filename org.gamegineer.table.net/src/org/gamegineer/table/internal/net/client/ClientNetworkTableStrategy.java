/*
 * ClientNetworkTableStrategy.java
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

package org.gamegineer.table.internal.net.client;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.INetworkTableStrategyContext;
import org.gamegineer.table.internal.net.ITableGateway;
import org.gamegineer.table.internal.net.common.AbstractNetworkTableStrategy;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.net.NetworkTableError;
import org.gamegineer.table.net.NetworkTableException;

/**
 * Implementation of
 * {@link org.gamegineer.table.internal.net.INetworkTableStrategy} for client
 * network tables.
 */
@ThreadSafe
public final class ClientNetworkTableStrategy
    extends AbstractNetworkTableStrategy
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
    private NetworkTableError handshakeError_;

    /** The handshake lock. */
    private final Lock handshakeLock_;

    /** Indicates the handshake is complete. */
    @GuardedBy( "handshakeLock_" )
    private boolean isHandshakeComplete_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientNetworkTableStrategy}
     * class.
     * 
     * @param context
     *        The network table strategy context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    public ClientNetworkTableStrategy(
        /* @NonNull */
        final INetworkTableStrategyContext context )
    {
        this( context, true );
    }

    /**
     * Initializes a new instance of the {@code ClientNetworkTableStrategy}
     * class and indicates whether or not the strategy should wait for handshake
     * completion.
     * 
     * <p>
     * This constructor is only intended to support testing.
     * </p>
     * 
     * @param context
     *        The network table strategy context; must not be {@code null}.
     * @param waitForHandshakeCompletion
     *        {@code true} if the strategy should wait for the handshake to
     *        complete before indicating the connection has been established;
     *        otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    public ClientNetworkTableStrategy(
        /* @NonNull */
        final INetworkTableStrategyContext context,
        final boolean waitForHandshakeCompletion )
    {
        super( context );

        handshakeLock_ = new ReentrantLock();
        handshakeCondition_ = handshakeLock_.newCondition();
        handshakeError_ = null;
        isHandshakeComplete_ = waitForHandshakeCompletion ? false : true;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractNetworkTableStrategy#connected()
     */
    @Override
    protected void connected()
        throws NetworkTableException
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
                        throw new NetworkTableException( NetworkTableError.TIME_OUT, Messages.ClientNetworkTableStrategy_handshake_timedOut );
                    }
                }
                catch( final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                    throw new NetworkTableException( NetworkTableError.INTERRUPTED, Messages.ClientNetworkTableStrategy_handshake_interrupted, e );
                }
            }

            if( handshakeError_ != null )
            {
                throw new NetworkTableException( handshakeError_ );
            }
        }
        finally
        {
            handshakeLock_.unlock();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractNetworkTableStrategy#createTransportLayer()
     */
    @Override
    protected ITransportLayer createTransportLayer()
    {
        assert Thread.holdsLock( getLock() );

        return getContext().getTransportLayerFactory().createActiveTransportLayer( new AbstractTransportLayerContext()
        {
            @Override
            public IService createService()
            {
                return new RemoteServerTableGateway( ClientNetworkTableStrategy.this );
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractNetworkTableStrategy#networkTableDisconnecting(org.gamegineer.table.net.NetworkTableError)
     */
    @Override
    protected void networkTableDisconnecting(
        final NetworkTableError error )
    {
        super.networkTableDisconnecting( error );

        setHandshakeComplete( error );
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
        final NetworkTableError error )
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
     * @see org.gamegineer.table.internal.net.common.AbstractNetworkTableStrategy#tableGatewayAdded(org.gamegineer.table.internal.net.ITableGateway)
     */
    @Override
    protected void tableGatewayAdded(
        final ITableGateway tableGateway )
    {
        assertArgumentNotNull( tableGateway, "tableGateway" ); //$NON-NLS-1$
        assert Thread.holdsLock( getLock() );

        super.tableGatewayAdded( tableGateway );

        if( tableGateway instanceof RemoteServerTableGateway )
        {
            setHandshakeComplete( null );
        }
    }
}
