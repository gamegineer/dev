/*
 * NetworkTable.java
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
 * Created on Nov 6, 2010 at 2:01:23 PM.
 */

package org.gamegineer.table.internal.net;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.net.connection.IAcceptor;
import org.gamegineer.table.internal.net.connection.IConnector;
import org.gamegineer.table.internal.net.connection.IDispatcher;
import org.gamegineer.table.internal.net.connection.INetworkInterface;
import org.gamegineer.table.internal.net.connection.INetworkInterfaceFactory;
import org.gamegineer.table.internal.net.tcp.TcpNetworkInterfaceFactory;
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

    /**
     * The task executing the dispatcher or {@code null} if the network is not
     * connected.
     */
    @GuardedBy( "lock_" )
    private Future<?> dispatcherTask_;

    /** The collection of network table listeners. */
    private final CopyOnWriteArrayList<INetworkTableListener> listeners_;

    /** The instance lock. */
    private final Object lock_;

    /** The network table network interface factory. */
    private final INetworkInterfaceFactory networkInterfaceFactory_;

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
        this( table, new TcpNetworkInterfaceFactory() );
    }

    /**
     * Initializes a new instance of the {@code NetworkTable} class using the
     * specified network interface factory.
     * 
     * @param table
     *        The table to be attached to the network; must not be {@code null}.
     * @param networkInterfaceFactory
     *        The network table network interface factory; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code table} is {@code null}.
     */
    NetworkTable(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final INetworkInterfaceFactory networkInterfaceFactory )
    {
        assertArgumentNotNull( table, "table" ); //$NON-NLS-1$
        assert networkInterfaceFactory != null;

        dispatcherTask_ = null;
        listeners_ = new CopyOnWriteArrayList<INetworkTableListener>();
        lock_ = new Object();
        networkInterfaceFactory_ = networkInterfaceFactory;
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
     * @see org.gamegineer.table.net.INetworkTable#disconnect()
     */
    @Override
    public void disconnect()
    {
        final boolean fireNetworkDisconnected;
        synchronized( lock_ )
        {
            if( dispatcherTask_ != null )
            {
                fireNetworkDisconnected = true;

                final Future<?> task = dispatcherTask_;
                dispatcherTask_ = null;

                task.cancel( true );
                try
                {
                    task.get( 10, TimeUnit.SECONDS );
                }
                catch( final CancellationException e )
                {
                    // do nothing
                }
                catch( final Exception e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, Messages.NetworkTable_disconnect_error, e );
                }
            }
            else
            {
                fireNetworkDisconnected = false;
            }
        }

        if( fireNetworkDisconnected )
        {
            fireNetworkDisconnected();
        }
    }

    /**
     * Fires a network connected event.
     */
    private void fireNetworkConnected()
    {
        final NetworkTableEvent event = new NetworkTableEvent( this );
        for( final INetworkTableListener listener : listeners_ )
        {
            try
            {
                listener.networkConnected( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.NetworkTable_networkConnected_unexpectedException, e );
            }
        }
    }

    /**
     * Fires a network disconnected event.
     */
    private void fireNetworkDisconnected()
    {
        final NetworkTableEvent event = new NetworkTableEvent( this );
        for( final INetworkTableListener listener : listeners_ )
        {
            try
            {
                listener.networkDisconnected( event );
            }
            catch( final RuntimeException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.NetworkTable_networkDisconnected_unexpectedException, e );
            }
        }
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#host(org.gamegineer.table.net.INetworkTableConfiguration)
     */
    @Override
    public void host(
        final INetworkTableConfiguration configuration )
        throws NetworkTableException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            if( dispatcherTask_ != null )
            {
                throw new NetworkTableException( Messages.NetworkTable_host_networkConnected );
            }

            final INetworkInterface networkInterface = networkInterfaceFactory_.createNetworkInterface( this );
            final IDispatcher<?, ?> dispatcher = networkInterface.getDispatcher();
            final Future<?> dispatcherTask = Activator.getDefault().getExecutorService().submit( new Runnable()
            {
                @Override
                public void run()
                {
                    dispatcher.open();
                }
            } );

            try
            {
                final IAcceptor<?, ?> acceptor = networkInterface.createAcceptor();
                acceptor.bind( configuration );
            }
            catch( final NetworkTableException e )
            {
                dispatcherTask.cancel( true );
                throw e;
            }

            dispatcherTask_ = dispatcherTask;
        }

        fireNetworkConnected();
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#isConnected()
     */
    @Override
    public boolean isConnected()
    {
        synchronized( lock_ )
        {
            //return connectionState_ == ConnectionState.CONNECTED;
            return dispatcherTask_ != null;
        }
    }

    /*
     * @see org.gamegineer.table.net.INetworkTable#join(org.gamegineer.table.net.INetworkTableConfiguration)
     */
    @Override
    public void join(
        final INetworkTableConfiguration configuration )
        throws NetworkTableException
    {
        assertArgumentNotNull( configuration, "configuration" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            if( dispatcherTask_ != null )
            {
                throw new NetworkTableException( Messages.NetworkTable_join_networkConnected );
            }

            final INetworkInterface networkInterface = networkInterfaceFactory_.createNetworkInterface( this );
            final IDispatcher<?, ?> dispatcher = networkInterface.getDispatcher();
            final Future<?> dispatcherTask = Activator.getDefault().getExecutorService().submit( new Runnable()
            {
                @Override
                public void run()
                {
                    dispatcher.open();
                }
            } );

            final IConnector<?, ?> connector = networkInterface.createConnector();
            try
            {
                connector.connect( configuration );
            }
            catch( final NetworkTableException e )
            {
                dispatcherTask.cancel( true );
                throw e;
            }
            finally
            {
                connector.close();
            }

            dispatcherTask_ = dispatcherTask;
        }

        fireNetworkConnected();
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
}
