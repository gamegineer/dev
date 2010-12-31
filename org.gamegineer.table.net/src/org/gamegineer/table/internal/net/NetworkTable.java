/*
 * NetworkTable.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Nov 6, 2010 at 2:01:23 PM.
 */

package org.gamegineer.table.internal.net;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.net.INetworkTable;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.INetworkTableListener;
import org.gamegineer.table.net.NetworkTableEvent;
import org.gamegineer.table.net.NetworkTableException;

/**
 * Implementation of {@link org.gamegineer.table.net.INetworkTable}.
 */
@ThreadSafe
public final class NetworkTable
    implements INetworkTable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The connection state. */
    @GuardedBy( "lock_" )
    private ConnectionState connectionState_;

    /** The collection of network table listeners. */
    private final CopyOnWriteArrayList<INetworkTableListener> listeners_;

    /** The instance lock. */
    private final Object lock_;

    /**
     * The active network table strategy or {@code null} if the network is not
     * connected.
     */
    @GuardedBy( "lock_" )
    private AbstractNetworkTableStrategy strategy_;

    /** The network table strategy factory. */
    private final AbstractNetworkTableStrategyFactory strategyFactory_;

    /** The table to be attached to the network. */
    @SuppressWarnings( "unused" )
    private final ITable table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTable} class using the
     * default strategy factory.
     * 
     * @param table
     *        The table to be attached to the network; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} is {@code null}.
     */
    public NetworkTable(
        /* @NonNull */
        final ITable table )
    {
        this( table, new DefaultNetworkTableStrategyFactory() );
    }

    /**
     * Initializes a new instance of the {@code NetworkTable} class using the
     * specified strategy factory.
     * 
     * @param table
     *        The table to be attached to the network; must not be {@code null}.
     * @param strategyFactory
     *        The network table strategy factory; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} is {@code null}.
     */
    NetworkTable(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final AbstractNetworkTableStrategyFactory strategyFactory )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$
        assert strategyFactory != null;

        connectionState_ = ConnectionState.DISCONNECTED;
        listeners_ = new CopyOnWriteArrayList<INetworkTableListener>();
        lock_ = new Object();
        strategy_ = null;
        strategyFactory_ = strategyFactory;
        table_ = table;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.INetworkTable#addNetworkTableListener(org.gamegineer.table.net.INetworkTableListener)
     */
    @Override
    public void addNetworkTableListener(
        final INetworkTableListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.addIfAbsent( listener ), "listener", Messages.NetworkTable_addNetworkTableListener_listener_registered ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#beginDisconnect()
     */
    @Override
    public Future<Void> beginDisconnect()
    {
        return new Token<Void>( Operation.DISCONNECT, Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
                throws Exception
            {
                disconnect();
                return null;
            }
        } ) );
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#beginHost(org.gamegineer.table.net.INetworkTableConfiguration)
     */
    @Override
    public Future<Void> beginHost(
        final INetworkTableConfiguration configuration )
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        return new Token<Void>( Operation.HOST, Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
                throws Exception
            {
                connect( configuration, strategyFactory_.createServerNetworkTableStrategy( NetworkTable.this ) );
                return null;
            }
        } ) );
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#beginJoin(org.gamegineer.table.net.INetworkTableConfiguration)
     */
    @Override
    public Future<Void> beginJoin(
        final INetworkTableConfiguration configuration )
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        return new Token<Void>( Operation.JOIN, Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
                throws Exception
            {
                connect( configuration, strategyFactory_.createClientNetworkTableStrategy( NetworkTable.this ) );
                return null;
            }
        } ) );
    }

    /**
     * Connects to the network.
     * 
     * @param configuration
     *        The network table configuration; must not be {@code null}.
     * @param strategy
     *        The strategy to use for the connection; must not be {@code null}.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If the connection cannot be established or the network is already
     *         connected.
     */
    private void connect(
        /* @NonNull */
        final INetworkTableConfiguration configuration,
        /* @NonNull */
        final AbstractNetworkTableStrategy strategy )
        throws NetworkTableException
    {
        assert configuration != null;
        assert strategy != null;

        synchronized( lock_ )
        {
            if( connectionState_ != ConnectionState.DISCONNECTED )
            {
                throw new NetworkTableException( Messages.NetworkTable_connect_networkConnected );
            }

            connectionState_ = ConnectionState.CONNECTING;
        }

        try
        {
            strategy.connect( configuration );

            synchronized( lock_ )
            {
                connectionState_ = ConnectionState.CONNECTED;
                strategy_ = strategy;
            }

            fireNetworkConnectionStateChanged();
        }
        catch( final NetworkTableException e )
        {
            synchronized( lock_ )
            {
                connectionState_ = ConnectionState.DISCONNECTED;
            }

            throw e;
        }
    }

    /**
     * Disconnects from the network.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    private void disconnect()
        throws NetworkTableException
    {
        final AbstractNetworkTableStrategy strategy;
        synchronized( lock_ )
        {
            if( connectionState_ != ConnectionState.CONNECTED )
            {
                return;
            }

            connectionState_ = ConnectionState.DISCONNECTING;
            strategy = strategy_;
            assert strategy != null;
        }

        try
        {
            strategy.disconnect();
        }
        finally
        {
            disconnected();
        }
    }

    /**
     * Invoked when the network has been either passively or actively
     * disconnected.
     */
    void disconnected()
    {
        synchronized( lock_ )
        {
            connectionState_ = ConnectionState.DISCONNECTED;
            strategy_ = null;
        }

        fireNetworkConnectionStateChanged();
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#endDisconnect(java.util.concurrent.Future)
     */
    @Override
    public void endDisconnect(
        final Future<Void> token )
        throws NetworkTableException, InterruptedException
    {
        assertArgumentNotNull( token, "token" ); //$NON-NLS-1$
        assertArgumentLegal( token instanceof Token<?>, Messages.NetworkTable_endAsyncOperation_illegalToken, "token" ); //$NON-NLS-1$
        assertArgumentLegal( ((Token<?>)token).getOperation() == Operation.DISCONNECT, Messages.NetworkTable_endDisconnect_illegalOperation, "token" ); //$NON-NLS-1$

        try
        {
            token.get();
        }
        catch( final ExecutionException e )
        {
            final Throwable cause = e.getCause();
            if( cause instanceof NetworkTableException )
            {
                throw (NetworkTableException)cause;
            }

            throw TaskUtils.launderThrowable( cause );
        }
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#endHost(java.util.concurrent.Future)
     */
    @Override
    public void endHost(
        final Future<Void> token )
        throws NetworkTableException, InterruptedException
    {
        assertArgumentNotNull( token, "token" ); //$NON-NLS-1$
        assertArgumentLegal( token instanceof Token<?>, Messages.NetworkTable_endAsyncOperation_illegalToken, "token" ); //$NON-NLS-1$
        assertArgumentLegal( ((Token<?>)token).getOperation() == Operation.HOST, Messages.NetworkTable_endHost_illegalOperation, "token" ); //$NON-NLS-1$

        try
        {
            token.get();
        }
        catch( final ExecutionException e )
        {
            final Throwable cause = e.getCause();
            if( cause instanceof NetworkTableException )
            {
                throw (NetworkTableException)cause;
            }

            throw TaskUtils.launderThrowable( cause );
        }
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#endJoin(java.util.concurrent.Future)
     */
    @Override
    public void endJoin(
        final Future<Void> token )
        throws NetworkTableException, InterruptedException
    {
        assertArgumentNotNull( token, "token" ); //$NON-NLS-1$
        assertArgumentLegal( token instanceof Token<?>, Messages.NetworkTable_endAsyncOperation_illegalToken, "token" ); //$NON-NLS-1$
        assertArgumentLegal( ((Token<?>)token).getOperation() == Operation.JOIN, Messages.NetworkTable_endJoin_illegalOperation, "token" ); //$NON-NLS-1$

        try
        {
            token.get();
        }
        catch( final ExecutionException e )
        {
            final Throwable cause = e.getCause();
            if( cause instanceof NetworkTableException )
            {
                throw (NetworkTableException)cause;
            }

            throw TaskUtils.launderThrowable( cause );
        }
    }

    /**
     * Fires a network connection state changed event.
     */
    private void fireNetworkConnectionStateChanged()
    {
        final NetworkTableEvent event = InternalNetworkTableEvent.createNetworkTableEvent( this );
        for( final INetworkTableListener listener : listeners_ )
        {
            try
            {
                listener.networkConnectionStateChanged( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.NetworkTable_networkConnectionStateChanged_unexpectedException, e );
            }
        }
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#isConnected()
     */
    @Override
    public boolean isConnected()
    {
        synchronized( lock_ )
        {
            return connectionState_ == ConnectionState.CONNECTED;
        }
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#removeNetworkTableListener(org.gamegineer.table.net.INetworkTableListener)
     */
    @Override
    public void removeNetworkTableListener(
        final INetworkTableListener listener )
    {
        assertArgumentNotNull( listener, "listener" ); //$NON-NLS-1$
        assertArgumentLegal( listeners_.remove( listener ), "listener", Messages.NetworkTable_removeNetworkTableListener_listener_notRegistered ); //$NON-NLS-1$
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * The network table connection state.
     */
    private enum ConnectionState
    {
        /** The network is disconnected. */
        DISCONNECTED,

        /** The network is connecting. */
        CONNECTING,

        /** The network is connected. */
        CONNECTED,

        /** The network is being disconnected. */
        DISCONNECTING;
    }

    /**
     * A network table operation.
     */
    private enum Operation
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The operation to disconnect the network table. */
        DISCONNECT,

        /** The operation to host the network table. */
        HOST,

        /** The operation to join another network table. */
        JOIN;
    }

    /**
     * An asynchronous completion token.
     * 
     * @param <V>
     *        The type of the asynchronous operation result.
     */
    @Immutable
    private static final class Token<V>
        implements Future<V>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The operation associated with the token. */
        private final Operation operation_;

        /** The asynchronous operation. */
        private final Future<V> task_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code Token} class.
         * 
         * @param operation
         *        The operation associated with the token; must not be {@code
         *        null}.
         * @param task
         *        The asynchronous operation; must not be {@code null}.
         */
        Token(
            /* @NonNull */
            final Operation operation,
            /* @NonNull */
            final Future<V> task )
        {
            assert operation != null;
            assert task != null;

            operation_ = operation;
            task_ = task;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.util.concurrent.Future#cancel(boolean)
         */
        @Override
        public boolean cancel(
            final boolean mayInterruptIfRunning )
        {
            return task_.cancel( mayInterruptIfRunning );
        }

        /*
         * @see java.util.concurrent.Future#get()
         */
        @Override
        public V get()
            throws InterruptedException, ExecutionException
        {
            return task_.get();
        }

        /*
         * @see java.util.concurrent.Future#get(long, java.util.concurrent.TimeUnit)
         */
        @Override
        public V get(
            final long timeout,
            final TimeUnit unit )
            throws InterruptedException, ExecutionException, TimeoutException
        {
            return task_.get( timeout, unit );
        }

        /**
         * Gets the operation associated with the token.
         * 
         * @return The operation associated with the token; never {@code null}.
         */
        /* @NonNull */
        Operation getOperation()
        {
            return operation_;
        }

        /*
         * @see java.util.concurrent.Future#isCancelled()
         */
        @Override
        public boolean isCancelled()
        {
            return task_.isCancelled();
        }

        /*
         * @see java.util.concurrent.Future#isDone()
         */
        @Override
        public boolean isDone()
        {
            return task_.isDone();
        }
    }
}
