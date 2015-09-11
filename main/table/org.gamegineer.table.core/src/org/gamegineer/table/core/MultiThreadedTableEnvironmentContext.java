/*
 * MultiThreadedTableEnvironmentContext.java
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
 * Created on May 25, 2013 at 9:59:58 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.Activator;
import org.gamegineer.table.internal.core.Loggers;

/**
 * Implementation of {@link ITableEnvironmentContext} for use when the table
 * environment will be accessed by multiple threads.
 */
@ThreadSafe
public final class MultiThreadedTableEnvironmentContext
    implements ITableEnvironmentContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The asynchronous completion token for the task executing the event
     * notifications.
     */
    private final Future<?> eventNotificationTaskFuture_;

    /** The collection of pending event notifications to be executed. */
    private final BlockingQueue<Runnable> eventNotifications_;

    /** The table environment lock. */
    private final ITableEnvironmentLock lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code MultiThreadedTableEnvironmentContext} class.
     */
    public MultiThreadedTableEnvironmentContext()
    {
        eventNotifications_ = new LinkedBlockingQueue<>();
        lock_ = new TableEnvironmentLock();

        eventNotificationTaskFuture_ = nonNull( Activator.getDefault().getExecutorService().submit( createEventNotificationTask() ) );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the task used to execute the event notifications.
     * 
     * @return The task used to execute the event notifications; never
     *         {@code null}.
     */
    private Runnable createEventNotificationTask()
    {
        return new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                fireEventNotifications();
            }
        };
    }

    /**
     * Disposes of the resources managed by this object.
     */
    public void dispose()
    {
        if( !eventNotificationTaskFuture_.isDone() && !eventNotificationTaskFuture_.cancel( true ) )
        {
            Loggers.getDefaultLogger().warning( NonNlsMessages.MultiThreadedTableEnvironmentContext_dispose_cancelFailed );
        }
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironmentContext#fireEventNotification(java.lang.Runnable)
     */
    @Override
    public void fireEventNotification(
        final Runnable eventNotification )
    {
        assertStateLegal( lock_.isHeldByCurrentThread(), NonNlsMessages.MultiThreadedTableEnvironmentContext_fireEventNotification_tableEnvironmentLockNotHeld );

        eventNotifications_.add( eventNotification );
    }

    /**
     * Fires all pending event notifications until interrupted.
     */
    private void fireEventNotifications()
    {
        try
        {
            while( true )
            {
                final Runnable eventNotification = eventNotifications_.take();
                eventNotification.run();
            }
        }
        catch( @SuppressWarnings( "unused" ) final InterruptedException e )
        {
            Thread.currentThread().interrupt();
        }
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironmentContext#getLock()
     */
    @Override
    public ITableEnvironmentLock getLock()
    {
        return lock_;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Implementation of {@link ITableEnvironmentLock} for a multi-threaded
     * table environment.
     */
    @ThreadSafe
    private static final class TableEnvironmentLock
        extends ReentrantLock
        implements ITableEnvironmentLock
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** Serializable class version number. */
        private static final long serialVersionUID = 8594785012739399065L;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableEnvironmentLock} class.
         */
        TableEnvironmentLock()
        {
        }
    }
}
