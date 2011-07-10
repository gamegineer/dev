/*
 * TableContext.java
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
 * Created on Jul 6, 2011 at 8:11:30 PM.
 */

package org.gamegineer.table.internal.core;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

/**
 * The execution context for a virtual game table.
 */
@Immutable
final class TableContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table context lock. */
    private final TableContextLock lock_;

    /**
     * The collection of pending event notifications to be executed the next
     * time the table context lock is released.
     */
    private final Queue<Runnable> pendingEventNotifications_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableContext} class.
     */
    TableContext()
    {
        lock_ = new TableContextLock();
        pendingEventNotifications_ = new ConcurrentLinkedQueue<Runnable>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds an event notification to be fired as soon as the table context lock
     * is not held by the current thread.
     * 
     * <p>
     * If the current thread does not hold the table context lock, the event
     * notification will be fired immediately. Otherwise, it will be queued and
     * fired as soon as this thread releases the table context lock.
     * </p>
     * 
     * @param notification
     *        The event notification; must not be {@code null}.
     */
    void addEventNotification(
        /* @NonNull */
        final Runnable notification )
    {
        assert notification != null;

        if( lock_.isHeldByCurrentThread() )
        {
            if( !pendingEventNotifications_.offer( notification ) )
            {
                Loggers.getDefaultLogger().warning( Messages.TableContext_addEventNotification_queueFailed );
            }
        }
        else
        {
            notification.run();
        }
    }

    /**
     * Gets the table context lock.
     * 
     * @return The table context lock; never {@code null}.
     */
    /* @NonNull */
    ReentrantLock getLock()
    {
        return lock_;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A reentrant mutual exclusion lock for a table context.
     */
    @ThreadSafe
    private final class TableContextLock
        extends ReentrantLock
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** Serializable class version number. */
        private static final long serialVersionUID = 7505597870826416138L;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableContextLock} class.
         */
        TableContextLock()
        {
            super();
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see java.util.concurrent.locks.ReentrantLock#unlock()
         */
        @Override
        @SuppressWarnings( "synthetic-access" )
        public void unlock()
        {
            super.unlock();

            if( !isHeldByCurrentThread() )
            {
                Runnable notification = null;
                while( (notification = pendingEventNotifications_.poll()) != null )
                {
                    notification.run();
                }
            }
        }
    }
}
