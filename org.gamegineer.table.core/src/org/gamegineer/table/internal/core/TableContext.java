/*
 * TableContext.java
 * Copyright 2008-2012 Gamegineer.org
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

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableContext;

/**
 * The execution context for a virtual game table.
 */
@ThreadSafe
public final class TableContext
    implements ITableContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Indicates an event notification is in progress on the current thread. */
    private final ThreadLocal<Boolean> isEventNotificationInProgress_;

    /** The table context lock. */
    private final TableContextLock lock_;

    /**
     * The collection of pending event notifications queued on the current
     * thread to be executed the next time the table context lock is released on
     * this thread.
     */
    private final ThreadLocal<Queue<Runnable>> pendingEventNotifications_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableContext} class.
     */
    public TableContext()
    {
        isEventNotificationInProgress_ = new ThreadLocal<Boolean>()
        {
            @Override
            protected Boolean initialValue()
            {
                return Boolean.FALSE;
            }
        };
        lock_ = new TableContextLock();
        pendingEventNotifications_ = new ThreadLocal<Queue<Runnable>>()
        {
            @Override
            protected Queue<Runnable> initialValue()
            {
                return new ArrayDeque<Runnable>();
            }
        };
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

        if( canFireEventNotifications() )
        {
            notification.run();
        }
        else
        {
            if( !pendingEventNotifications_.get().offer( notification ) )
            {
                Loggers.getDefaultLogger().warning( NonNlsMessages.TableContext_addEventNotification_queueFailed );
            }
        }
    }

    /**
     * Indicates event notifications can be fired on the current thread.
     * 
     * @return {@code true} if event notifications can be fired on the current
     *         thread; otherwise {@code false}.
     */
    private boolean canFireEventNotifications()
    {
        return !lock_.isHeldByCurrentThread() && !isEventNotificationInProgress_.get().booleanValue();
    }

    /*
     * @see org.gamegineer.table.core.ITableContext#createCard()
     */
    @Override
    public ICard createCard()
    {
        return new Card( this );
    }

    /*
     * @see org.gamegineer.table.core.ITableContext#createCardPile()
     */
    @Override
    public ICardPile createCardPile()
    {
        return new CardPile( this );
    }

    /*
     * @see org.gamegineer.table.core.ITableContext#createTable()
     */
    @Override
    public ITable createTable()
    {
        return new Table( this );
    }

    /**
     * Fires all pending event notifications queued for the current thread.
     */
    private void firePendingEventNotifications()
    {
        assert canFireEventNotifications();

        isEventNotificationInProgress_.set( Boolean.TRUE );
        try
        {
            final Queue<Runnable> pendingEventNotifications = pendingEventNotifications_.get();
            Runnable notification = null;
            while( (notification = pendingEventNotifications.poll()) != null )
            {
                notification.run();
            }
        }
        finally
        {
            isEventNotificationInProgress_.set( Boolean.FALSE );
        }
    }

    /*
     * @see org.gamegineer.table.core.ITableContext#getLock()
     */
    @Override
    public ReentrantLock getLock()
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

            if( canFireEventNotifications() )
            {
                firePendingEventNotifications();
            }
        }
    }
}
