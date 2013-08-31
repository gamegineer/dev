/*
 * ExecutorService.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Oct 29, 2011 at 9:39:43 PM.
 */

package org.gamegineer.common.internal.core.impl.util.concurrent;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.internal.core.impl.Loggers;

/**
 * Implementation of {@link ExecutorService} that provides an executor service
 * in an OSGi environment.
 */
@ThreadSafe
public final class ExecutorService
    implements java.util.concurrent.ExecutorService
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** A reference to the actual executor service. */
    private final AtomicReference<java.util.concurrent.ExecutorService> actualExecutorServiceRef_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ExecutorService} class.
     */
    public ExecutorService()
    {
        actualExecutorServiceRef_ = new AtomicReference<>( null );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Activates this component.
     */
    public void activate()
    {
        final java.util.concurrent.ExecutorService actualExecutorService = actualExecutorServiceRef_.getAndSet( Executors.newCachedThreadPool() );
        assert actualExecutorService == null;
    }

    /*
     * @see java.util.concurrent.ExecutorService#awaitTermination(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public boolean awaitTermination(
        final long timeout,
        final TimeUnit unit )
        throws InterruptedException
    {
        return getActualExecutorService().awaitTermination( timeout, unit );
    }

    /**
     * Creates a decorator for the specified {@link Callable} task that logs any
     * uncaught exceptions on the task thread.
     * 
     * @param <T>
     *        The result type of the task.
     * 
     * @param task
     *        The task; must not be {@code null}.
     * 
     * @return A decorator for the specified task; never {@code null}.
     */
    /* @NonNull */
    private static <T> Callable<T> createCallableDecorator(
        /* @NonNull */
        final Callable<T> task )
    {
        assert task != null;

        return new Callable<T>()
        {
            @Override
            public T call()
                throws Exception
            {
                try
                {
                    return task.call();
                }
                catch( final RuntimeException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ExecutorService_uncaughtException(), e );
                    throw e;
                }
                catch( final Error e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ExecutorService_uncaughtException(), e );
                    throw e;
                }
            }
        };
    }

    /**
     * Creates a decorator for the specified {@link Runnable} task that logs any
     * uncaught exceptions on the task thread.
     * 
     * @param task
     *        The task; must not be {@code null}.
     * 
     * @return A decorator for the specified task; never {@code null}.
     */
    /* @NonNull */
    private static Runnable createRunnableDecorator(
        /* @NonNull */
        final Runnable task )
    {
        assert task != null;

        return new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    task.run();
                }
                catch( final RuntimeException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ExecutorService_uncaughtException(), e );
                    throw e;
                }
                catch( final Error e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.ExecutorService_uncaughtException(), e );
                    throw e;
                }
            }
        };
    }

    /**
     * Deactivates this component.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    public void deactivate()
        throws Exception
    {
        final java.util.concurrent.ExecutorService actualExecutorService = actualExecutorServiceRef_.getAndSet( null );
        assert actualExecutorService != null;

        actualExecutorService.shutdown();

        if( !actualExecutorService.awaitTermination( 10, TimeUnit.SECONDS ) )
        {
            actualExecutorService.shutdownNow();
        }
    }

    /*
     * @see java.util.concurrent.Executor#execute(java.lang.Runnable)
     */
    @Override
    public void execute(
        final Runnable command )
    {
        getActualExecutorService().execute( command );
    }

    /**
     * Gets the actual executor service.
     * 
     * @return The actual executor service; never {@code null}.
     */
    /* @NonNull */
    private java.util.concurrent.ExecutorService getActualExecutorService()
    {
        final java.util.concurrent.ExecutorService actualExecutorService = actualExecutorServiceRef_.get();
        assert actualExecutorService != null;
        return actualExecutorService;
    }

    /*
     * @see java.util.concurrent.ExecutorService#invokeAll(java.util.Collection)
     */
    @Override
    public <T> List<Future<T>> invokeAll(
        final Collection<? extends Callable<T>> tasks )
        throws InterruptedException
    {
        return getActualExecutorService().invokeAll( tasks );
    }

    /*
     * @see java.util.concurrent.ExecutorService#invokeAll(java.util.Collection, long, java.util.concurrent.TimeUnit)
     */
    @Override
    public <T> List<Future<T>> invokeAll(
        final Collection<? extends Callable<T>> tasks,
        final long timeout,
        final TimeUnit unit )
        throws InterruptedException
    {
        return getActualExecutorService().invokeAll( tasks, timeout, unit );
    }

    /*
     * @see java.util.concurrent.ExecutorService#invokeAny(java.util.Collection)
     */
    @Override
    public <T> T invokeAny(
        final Collection<? extends Callable<T>> tasks )
        throws InterruptedException, ExecutionException
    {
        return getActualExecutorService().invokeAny( tasks );
    }

    /*
     * @see java.util.concurrent.ExecutorService#invokeAny(java.util.Collection, long, java.util.concurrent.TimeUnit)
     */
    @Override
    public <T> T invokeAny(
        final Collection<? extends Callable<T>> tasks,
        final long timeout,
        final TimeUnit unit )
        throws InterruptedException, ExecutionException, TimeoutException
    {
        return getActualExecutorService().invokeAny( tasks, timeout, unit );
    }

    /*
     * @see java.util.concurrent.ExecutorService#isShutdown()
     */
    @Override
    public boolean isShutdown()
    {
        return getActualExecutorService().isShutdown();
    }

    /*
     * @see java.util.concurrent.ExecutorService#isTerminated()
     */
    @Override
    public boolean isTerminated()
    {
        return getActualExecutorService().isTerminated();
    }

    /*
     * @see java.util.concurrent.ExecutorService#shutdown()
     */
    @Override
    public void shutdown()
    {
        getActualExecutorService().shutdown();
    }

    /*
     * @see java.util.concurrent.ExecutorService#shutdownNow()
     */
    @Override
    public List<Runnable> shutdownNow()
    {
        return getActualExecutorService().shutdownNow();
    }

    /*
     * @see java.util.concurrent.ExecutorService#submit(java.util.concurrent.Callable)
     */
    @Override
    public <T> Future<T> submit(
        final Callable<T> task )
    {
        assertArgumentNotNull( task, "task" ); //$NON-NLS-1$

        return getActualExecutorService().submit( createCallableDecorator( task ) );
    }

    /*
     * @see java.util.concurrent.ExecutorService#submit(java.lang.Runnable)
     */
    @Override
    public Future<?> submit(
        final Runnable task )
    {
        assertArgumentNotNull( task, "task" ); //$NON-NLS-1$

        return getActualExecutorService().submit( createRunnableDecorator( task ) );
    }

    /*
     * @see java.util.concurrent.ExecutorService#submit(java.lang.Runnable, java.lang.Object)
     */
    @Override
    public <T> Future<T> submit(
        final Runnable task,
        final T result )
    {
        assertArgumentNotNull( task, "task" ); //$NON-NLS-1$

        return getActualExecutorService().submit( createRunnableDecorator( task ), result );
    }
}
