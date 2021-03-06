/*
 * AbstractTransportLayer.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl.transport.tcp;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.util.concurrent.SynchronousFuture;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.internal.net.impl.Activator;
import org.gamegineer.table.internal.net.impl.transport.IService;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayer;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayerContext;
import org.gamegineer.table.internal.net.impl.transport.TransportException;

/**
 * Superclass for all implementations of {@link ITransportLayer} that operate
 * over a TCP connection.
 * 
 * <p>
 * All client methods of this class are expected to be invoked on the associated
 * transport layer thread except where explicitly noted.
 * </p>
 */
@NotThreadSafe
abstract class AbstractTransportLayer
    implements ITransportLayer
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
    private @Nullable Dispatcher dispatcher_;

    /** The transport layer executor service. */
    private final ExecutorService executorService_;

    /** The transport layer state. */
    private State state_;

    /** The transport layer thread. */
    private final Thread transportLayerThread_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTransportLayer} class.
     * 
     * <p>
     * It is assumed that the thread that invokes this constructor is the
     * transport layer thread and is managed by the specified executor service.
     * </p>
     * 
     * @param executorService
     *        The transport layer executor service.
     * @param context
     *        The transport layer context.
     */
    AbstractTransportLayer(
        final ExecutorService executorService,
        final ITransportLayerContext context )
    {
        context_ = context;
        dispatcher_ = null;
        executorService_ = executorService;
        state_ = State.PRISTINE;
        transportLayerThread_ = Thread.currentThread();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asynchronously executes the specified task on the transport layer thread.
     * 
     * @param <T>
     *        The return type of the task.
     * 
     * @param task
     *        The task to execute.
     * 
     * @return An asynchronous completion token for the task.
     * 
     * @throws java.util.concurrent.RejectedExecutionException
     *         If the task cannot be scheduled for execution.
     */
    final <T> Future<T> asyncExec(
        final Callable<T> task )
    {
        return executorService_.submit( task );
    }

    /**
     * Asynchronously executes the specified task on the transport layer thread.
     * 
     * @param task
     *        The task to execute.
     * 
     * @return An asynchronous completion token for the task.
     * 
     * @throws java.util.concurrent.RejectedExecutionException
     *         If the task cannot be scheduled for execution.
     */
    Future<?> asyncExec(
        final Runnable task )
    {
        return executorService_.submit( task );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#beginClose()
     */
    @Override
    public final Future<@Nullable Void> beginClose()
    {
        assert isTransportLayerThread();

        if( state_ != State.OPEN )
        {
            executorService_.shutdown();
            state_ = State.CLOSED;
            return new SynchronousFuture<>();
        }

        close();
        final Dispatcher dispatcher = dispatcher_;
        assert dispatcher != null;
        final Future<@Nullable Void> dispatcherCloseTaskFuture = dispatcher.beginClose();

        return Activator.getDefault().getExecutorService().submit( new Callable<@Nullable Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public @Nullable Void call()
            {
                try
                {
                    dispatcher.endClose( dispatcherCloseTaskFuture );

                    final Future<@Nullable Void> closeTaskFuture = executorService_.submit( new Callable<@Nullable Void>()
                    {
                        @Override
                        public @Nullable Void call()
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
                catch( @SuppressWarnings( "unused" ) final InterruptedException e )
                {
                    Thread.currentThread().interrupt();
                }

                return null;
            }
        } );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#beginOpen(java.lang.String, int)
     */
    @Override
    public final Future<@Nullable Void> beginOpen(
        final String hostName,
        final int port )
    {
        assert isTransportLayerThread();

        if( state_ != State.PRISTINE )
        {
            return new SynchronousFuture<>( new IllegalStateException( NonNlsMessages.AbstractTransportLayer_state_notPristine ) );
        }

        state_ = State.OPEN;
        dispatcher_ = new Dispatcher( this );

        return Activator.getDefault().getExecutorService().submit( new Callable<@Nullable Void>()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public @Nullable Void call()
                throws TransportException
            {
                try
                {
                    syncExec( new Callable<@Nullable Void>()
                    {
                        @Override
                        public @Nullable Void call()
                            throws TransportException
                        {
                            try
                            {
                                final Dispatcher dispatcher = dispatcher_;
                                assert dispatcher != null;
                                dispatcher.open();
                                open( hostName, port );
                            }
                            catch( final IOException e )
                            {
                                throw new TransportException( NonNlsMessages.AbstractTransportLayer_open_ioError, e );
                            }

                            return null;
                        }
                    } );
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
                catch( @SuppressWarnings( "unused" ) final InterruptedException e )
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
     * Creates a new network service that can be associated with the transport
     * layer.
     * 
     * @return A new network service that can be associated with the transport
     *         layer.
     */
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
        final @Nullable Exception exception )
    {
        assert isTransportLayerThread();

        context_.transportLayerDisconnected( exception );
    }

    /**
     * This method may be called from any thread. It must not be called on the
     * transport layer thread if the operation is not done.
     * 
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#endClose(java.util.concurrent.Future)
     */
    @Override
    public final void endClose(
        final Future<@Nullable Void> future )
        throws InterruptedException
    {
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
     * This method may be called from any thread. It must not be called on the
     * transport layer thread if the operation is not done.
     * 
     * @see org.gamegineer.table.internal.net.impl.transport.ITransportLayer#endOpen(java.util.concurrent.Future)
     */
    @Override
    public final void endOpen(
        final Future<@Nullable Void> future )
        throws TransportException, InterruptedException
    {
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
     * @return The dispatcher associated with the transport layer.
     */
    final Dispatcher getDispatcher()
    {
        assert isTransportLayerThread();
        assert dispatcher_ != null;

        return dispatcher_;
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
        return Thread.currentThread() == transportLayerThread_;
    }

    /**
     * Template method invoked to open the transport layer.
     * 
     * @param hostName
     *        The host name.
     * @param port
     *        The port.
     * 
     * @throws java.io.IOException
     *         If an I/O error occurs.
     */
    abstract void open(
        String hostName,
        int port )
        throws IOException;

    /**
     * Synchronously executes the specified task on the transport layer thread.
     * 
     * @param <T>
     *        The return type of the task.
     * 
     * @param task
     *        The task to execute.
     * 
     * @return The return value of the task.
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the task to
     *         complete.
     * @throws java.util.concurrent.ExecutionException
     *         If an error occurs while executing the task.
     * @throws java.util.concurrent.RejectedExecutionException
     *         If the task cannot be scheduled for execution.
     */
    <T> T syncExec(
        final Callable<T> task )
        throws ExecutionException, InterruptedException
    {
        if( isTransportLayerThread() )
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
     * Synchronously executes the specified task on the transport layer thread.
     * 
     * @param task
     *        The task to execute.
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the task to
     *         complete.
     * @throws java.util.concurrent.ExecutionException
     *         If an error occurs while executing the task.
     * @throws java.util.concurrent.RejectedExecutionException
     *         If the task cannot be scheduled for execution.
     */
    void syncExec(
        final Runnable task )
        throws ExecutionException, InterruptedException
    {
        if( isTransportLayerThread() )
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

        final Future<Future<@Nullable Void>> beginCloseTaskFuture = asyncExec( new Callable<Future<@Nullable Void>>()
        {
            @Override
            public Future<@Nullable Void> call()
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
        catch( @SuppressWarnings( "unused" ) final InterruptedException e )
        {
            Thread.currentThread().interrupt();
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Superclass for factories that create instances of
     * {@link AbstractTransportLayer}.
     * 
     * <p>
     * The purpose of this class is to ensure an instance of
     * {@link AbstractTransportLayer} is constructed on its associated transport
     * layer thread to guarantee thread confinement.
     * </p>
     */
    @Immutable
    static abstract class AbstractFactory
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractFactory} class.
         */
        AbstractFactory()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Creates the transport layer executor service.
         * 
         * @return The transport layer executor service.
         */
        private static ExecutorService createExecutorService()
        {
            return Executors.newSingleThreadExecutor( new ThreadFactory()
            {
                @Override
                public Thread newThread(
                    final @Nullable Runnable r )
                {
                    return new Thread( r, NonNlsMessages.AbstractTransportLayer_transportLayerThread_name );
                }
            } );
        }

        /**
         * Creates a new transport layer.
         * 
         * @param context
         *        The transport layer context.
         * 
         * @return A new transport layer.
         * 
         * @throws org.gamegineer.table.internal.net.impl.transport.
         *         TransportException
         *         If the transport layer cannot be created.
         */
        final AbstractTransportLayer createTransportLayer(
            final ITransportLayerContext context )
            throws TransportException
        {
            final ExecutorService executorService = createExecutorService();
            final Future<AbstractTransportLayer> future = executorService.submit( new Callable<AbstractTransportLayer>()
            {
                @Override
                public AbstractTransportLayer call()
                {
                    return createTransportLayer( executorService, context );
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
            catch( @SuppressWarnings( "unused" ) final InterruptedException e )
            {
                Thread.currentThread().interrupt();
                throw new TransportException( NonNlsMessages.AbstractTransportLayer_createTransportLayer_interrupted );
            }
        }

        /**
         * Template method invoked to create a new transport layer.
         * 
         * <p>
         * This method will be invoked on the transport layer thread.
         * </p>
         * 
         * @param executorService
         *        The transport layer executor service.
         * @param context
         *        The transport layer context.
         * 
         * @return A new transport layer.
         */
        abstract AbstractTransportLayer createTransportLayer(
            ExecutorService executorService,
            ITransportLayerContext context );
    }
}
