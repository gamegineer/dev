/*
 * SingleThreadedTableEnvironmentContext.java
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
 * Created on May 25, 2013 at 9:58:21 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Implementation of {@link ITableEnvironmentContext} for use when the table
 * environment will be accessed by a single thread.
 */
@NotThreadSafe
public final class SingleThreadedTableEnvironmentContext
    implements ITableEnvironmentContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The collection of pending event notifications to be executed. */
    private final Queue<Runnable> eventNotifications_;

    /** Indicates an event notification is in progress. */
    private boolean isEventNotificationInProgress_;

    /** The table environment lock. */
    private final ITableEnvironmentLock lock_;

    /** The table environment thread. */
    private final Thread thread_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code SingleThreadedTableEnvironmentContext} class.
     */
    public SingleThreadedTableEnvironmentContext()
    {
        eventNotifications_ = new ArrayDeque<>();
        isEventNotificationInProgress_ = false;
        lock_ = new TableEnvironmentLock();
        thread_ = Thread.currentThread();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates event notifications can be fired.
     * 
     * @return {@code true} if event notifications can be fired; otherwise
     *         {@code false}.
     */
    private boolean canFireEventNotifications()
    {
        return !lock_.isHeldByCurrentThread() && !isEventNotificationInProgress_;
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironmentContext#fireEventNotification(java.lang.Runnable)
     */
    @Override
    public void fireEventNotification(
        final Runnable eventNotification )
    {
        assertStateLegal( lock_.isHeldByCurrentThread(), NonNlsMessages.SingleThreadedTableEnvironmentContext_fireEventNotification_tableEnvironmentLockNotHeld );
        assert thread_ == Thread.currentThread();

        eventNotifications_.add( eventNotification );
    }

    /**
     * Fires all pending event notifications if it is legal to do so.
     */
    private void fireEventNotifications()
    {
        if( canFireEventNotifications() )
        {
            isEventNotificationInProgress_ = true;
            try
            {
                Runnable eventNotification = null;
                while( (eventNotification = eventNotifications_.poll()) != null )
                {
                    eventNotification.run();
                }
            }
            finally
            {
                isEventNotificationInProgress_ = false;
            }
        }
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironmentContext#getLock()
     */
    @Override
    public ITableEnvironmentLock getLock()
    {
        assert thread_ == Thread.currentThread();

        return lock_;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Implementation of {@link ITableEnvironmentLock} for a single-threaded
     * table environment.
     */
    @NotThreadSafe
    @SuppressWarnings( "synthetic-access" )
    private final class TableEnvironmentLock
        implements ITableEnvironmentLock
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The lock count. */
        private int lockCount_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableEnvironmentLock} class.
         */
        TableEnvironmentLock()
        {
            lockCount_ = 0;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.core.ITableEnvironmentLock#isHeldByCurrentThread()
         */
        @Override
        public boolean isHeldByCurrentThread()
        {
            assert thread_ == Thread.currentThread();

            return lockCount_ != 0;
        }

        /*
         * @see java.util.concurrent.locks.Lock#lock()
         */
        @Override
        public void lock()
        {
            assert thread_ == Thread.currentThread();

            ++lockCount_;
        }

        /*
         * @see java.util.concurrent.locks.Lock#lockInterruptibly()
         */
        @Override
        public void lockInterruptibly()
        {
            lock();
        }

        /*
         * @see java.util.concurrent.locks.Lock#newCondition()
         */
        @Override
        public Condition newCondition()
        {
            assert thread_ == Thread.currentThread();

            throw new UnsupportedOperationException();
        }

        /*
         * @see java.util.concurrent.locks.Lock#tryLock()
         */
        @Override
        public boolean tryLock()
        {
            lock();
            return true;
        }

        /*
         * @see java.util.concurrent.locks.Lock#tryLock(long, java.util.concurrent.TimeUnit)
         */
        @Override
        public boolean tryLock(
            @SuppressWarnings( "unused" )
            final long time,
            @Nullable
            @SuppressWarnings( "unused" )
            final TimeUnit unit )
        {
            return tryLock();
        }

        /*
         * @see java.util.concurrent.locks.Lock#unlock()
         */
        @Override
        public void unlock()
        {
            assert thread_ == Thread.currentThread();

            --lockCount_;
            fireEventNotifications();
        }
    }
}
