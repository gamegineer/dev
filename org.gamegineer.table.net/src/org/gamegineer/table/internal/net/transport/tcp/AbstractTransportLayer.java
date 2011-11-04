/*
 * AbstractTransportLayer.java
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
 * Created on Feb 12, 2011 at 10:54:54 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.common.core.util.concurrent.SynchronousFuture;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.internal.net.Activator;
import org.gamegineer.table.internal.net.transport.IService;
import org.gamegineer.table.internal.net.transport.ITransportLayerContext;
import org.gamegineer.table.internal.net.transport.TransportException;

/**
 * Superclass for all transport layer implementations that operate over a TCP
 * connection.
 * 
 * <p>
 * All client methods of this class are expected to be invoked on the associated
 * transport layer thread except where explicitly noted.
 * </p>
 */
@NotThreadSafe
abstract class AbstractTransportLayer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The transport layer context. */
    private final ITransportLayerContext context_;

    /**
     * The dispatcher associated with the transport layer or {@code null} if the
     * transport layer is not open.
     */
    private Dispatcher dispatcher_;

    /** The executor service associated with the transport layer. */
    private final ExecutorService executorService_;

    /** The transport layer state. */
    private State state_;

    /**
     * A reference to the transport layer thread or {@code null} if the
     * transport layer is not open.
     */
    private final AtomicReference<Thread> transportLayerThreadRef_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTransportLayer} class.
     * 
     * @param context
     *        The transport layer context; must not be {@code null}.
     */
    AbstractTransportLayer(
        /* @NonNull */
        final ITransportLayerContext context )
    {
        assert context != null;

        context_ = context;
        dispatcher_ = null;
        executorService_ = createExecutorService();
        state_ = State.PRISTINE;
        transportLayerThreadRef_ = new AtomicReference<Thread>( null );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Begins an asynchronous operation to close the transport layer.
     * 
     * @return An asynchronous completion token for the operation; never {@code
     *         null}.
     */
    /* @NonNull */
    final Future<Void> beginClose()
    {
        assert isTransportLayerThread();

        if( state_ != State.OPEN )
        {
            executorService_.shutdown();
            state_ = State.CLOSED;
            return new SynchronousFuture<Void>();
        }

        close();
        final Dispatcher dispatcher = dispatcher_;
        final Future<Void> dispatcherCloseTaskFuture = dispatcher.beginClose();

        return Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
            {
                try
                {
                    dispatcher.endClose( dispatcherCloseTaskFuture );

                    final Future<Void> closeTaskFuture = executorService_.submit( new Callable<Void>()
                    {
                        @Override
                        public Void call()
                        {
                            dispatcher_ = null;
                            executorService_.shutdown();
                            state_ = State.CLOSED;

                            return null;
                        }
                    } );

                    try
                    {
                        closeTaskFuture.get();
                    }
                    catch( final ExecutionException e )
                    {
                        throw TaskUtils.launderThrowable( e.getCause() );
                    }
                }
                catch( final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }

                return null;
            }
        } );
    }

    /**
     * Begins an asynchronous operation to open the transport layer.
     * 
     * @param hostName
     *        The host name; must not be {@code null}. For a passive transport
     *        layer, this value is the host name to which all services will be
     *        bound. For an active transport layer, this value is the host name
     *        of the remote service.
     * @param port
     *        The port. For a passive transport layer, this value is the port to
     *        which all services will be bound. For an active transport layer,
     *        this value is the port of the remote service.
     * 
     * @return An asynchronous completion token for the operation; never {@code
     *         null}.
     */
    /* @NonNull */
    final Future<Void> beginOpen(
        /* @NonNull */
        final String hostName,
        final int port )
    {
        assert hostName != null;
        assert isTransportLayerThread();

        if( state_ != State.PRISTINE )
        {
            return new SynchronousFuture<Void>( new IllegalStateException( NonNlsMessages.AbstractTransportLayer_state_notPristine ) );
        }

        state_ = State.OPEN;
        dispatcher_ = new Dispatcher( this );

        return Activator.getDefault().getExecutorService().submit( new Callable<Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Void call()
                throws TransportException
            {
                final Future<Void> openTaskFuture = getExecutorService().submit( new Callable<Void>()
                {
                    @Override
                    public Void call()
                        throws TransportException
                    {
                        try
                        {
                            dispatcher_.open();
                            open( hostName, port );
                        }
                        catch( final IOException e )
                        {
                            throw new TransportException( NonNlsMessages.AbstractTransportLayer_open_ioError, e );
                        }

                        return null;
                    }
                } );

                try
                {
                    openTaskFuture.get();
                }
                catch( final ExecutionException e )
                {
                    final Throwable cause = e.getCause();
                    if( cause instanceof TransportException )
                    {
                        synchronousClose();
                        throw (TransportException)cause;
                    }

                    throw TaskUtils.launderThrowable( cause );
                }
                catch( final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }

                return null;
            }
        } );
    }

    /**
     * Template method invoked to close the transport layer.
     */
    abstract void close();

    /**
     * Creates the transport layer executor service.
     * 
     * @return The transport layer executor service; never {@code null}.
     */
    /* @NonNull */
    private ExecutorService createExecutorService()
    {
        return Executors.newSingleThreadExecutor( new ThreadFactory()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public Thread newThread(
                final Runnable r )
            {
                final Thread transportLayerThread = new Thread( r, NonNlsMessages.AbstractTransportLayer_transportLayerThread_name )
                {
                    @Override
                    public void run()
                    {
                        transportLayerThreadRef_.set( this );
                        try
                        {
                            super.run();
                        }
                        finally
                        {
                            transportLayerThreadRef_.set( null );
                        }
                    }
                };

                return transportLayerThread;
            }
        } );
    }

    /**
     * Creates a new network service that can be associated with the transport
     * layer.
     * 
     * @return A new network service that can be associated with the transport
     *         layer; never {@code null}.
     */
    /* @NonNull */
    final IService createService()
    {
        assert isTransportLayerThread();

        return context_.createService();
    }

    /**
     * Invoked when the transport layer has been disconnected.
     * 
     * @param exception
     *        The exception that caused the transport layer to be disconnected
     *        or {@code null} if the transport layer was disconnected normally.
     */
    final void disconnected(
        /* @Nullable */
        final Exception exception )
    {
        assert isTransportLayerThread();

        context_.transportLayerDisconnected( exception );
    }

    /**
     * Ends an asynchronous operation to close the transport layer.
     * 
     * <p>
     * This method does nothing if the transport layer is already closed.
     * </p>
     * 
     * <p>
     * This method may be called from any thread. It must not be called on the
     * transport layer thread if the operation is not done.
     * </p>
     * 
     * @param future
     *        The asynchronous completion token associated with the operation;
     *        must not be {@code null}.
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the operation to
     *         complete.
     */
    final void endClose(
        /* @NonNull */
        final Future<Void> future )
        throws InterruptedException
    {
        assert future != null;
        assert !isTransportLayerThread() || future.isDone();

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
     * Ends an asynchronous operation to open the transport layer.
     * 
     * <p>
     * This method may be called from any thread. It must not be called on the
     * transport layer thread if the operation is not done.
     * </p>
     * 
     * @param future
     *        The asynchronous completion token associated with the operation;
     *        must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the transport layer has already been opened or is closed.
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the operation to
     *         complete.
     * @throws org.gamegineer.table.internal.net.transport.TransportException
     *         If an error occurs.
     */
    final void endOpen(
        /* @NonNull */
        final Future<Void> future )
        throws TransportException, InterruptedException
    {
        assert future != null;
        assert !isTransportLayerThread() || future.isDone();

        try
        {
            future.get();
        }
        catch( final ExecutionException e )
        {
            final Throwable cause = e.getCause();
            if( cause instanceof TransportException )
            {
                throw (TransportException)cause;
            }

            throw TaskUtils.launderThrowable( e );
        }
    }

    /**
     * Gets the dispatcher associated with the transport layer.
     * 
     * @return The dispatcher associated with the transport layer; never {@code
     *         null}.
     */
    /* @NonNull */
    final Dispatcher getDispatcher()
    {
        assert isTransportLayerThread();
        assert dispatcher_ != null;

        return dispatcher_;
    }

    /**
     * Gets the executor service associated with the transport layer.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @return The executor service associated with the transport layer; never
     *         {@code null}.
     */
    /* @NonNull */
    final ExecutorService getExecutorService()
    {
        return executorService_;
    }

    /**
     * Indicates the current thread is the transport layer thread.
     * 
     * <p>
     * This method may be called from any thread.
     * </p>
     * 
     * @return {@code true} if the current thread is the transport layer thread;
     *         otherwise {@code false}.
     */
    final boolean isTransportLayerThread()
    {
        return Thread.currentThread() == transportLayerThreadRef_.get();
    }

    /**
     * Template method invoked to open the transport layer.
     * 
     * @param hostName
     *        The host name; must not be {@code null}.
     * @param port
     *        The port.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    abstract void open(
        /* @NonNull */
        String hostName,
        int port )
        throws IOException;

    /**
     * Synchronously closes the transport layer.
     * 
     * <p>
     * This method must not be called on the transport layer thread.
     * </p>
     */
    private void synchronousClose()
    {
        assert !isTransportLayerThread();

        final Future<Future<Void>> beginCloseTaskFuture = getExecutorService().submit( new Callable<Future<Void>>()
        {
            @Override
            public Future<Void> call()
            {
                return beginClose();
            }
        } );

        try
        {
            endClose( beginCloseTaskFuture.get() );
        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
        catch( final InterruptedException e )
        {
            Thread.currentThread().interrupt();
        }
    }
}
