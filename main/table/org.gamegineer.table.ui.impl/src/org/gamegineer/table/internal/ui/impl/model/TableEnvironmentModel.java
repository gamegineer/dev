/*
 * TableEnvironmentModel.java
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
 * Created on May 31, 2013 at 10:30:56 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEnvironment;
import org.gamegineer.table.net.ITableNetwork;

/**
 * The table environment model.
 */
@ThreadSafe
public final class TableEnvironmentModel
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The collection of pending event notifications queued on the current
     * thread to be executed the next time the table environment model lock is
     * released on this thread.
     */
    private final ThreadLocal<Queue<Runnable>> eventNotifications_;

    /** Indicates an event notification is in progress on the current thread. */
    private final ThreadLocal<Boolean> isEventNotificationInProgress_;

    /** The table environment model lock. */
    private final ITableEnvironmentModelLock lock_;

    /** The table environment associated with this model. */
    private final ITableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableEnvironmentModel} class.
     * 
     * @param tableEnvironment
     *        The table environment associated with this model.
     */
    public TableEnvironmentModel(
        final ITableEnvironment tableEnvironment )
    {
        eventNotifications_ = new ThreadLocal<Queue<Runnable>>()
        {
            @Override
            protected Queue<Runnable> initialValue()
            {
                return new ArrayDeque<>();
            }
        };
        isEventNotificationInProgress_ = new ThreadLocal<Boolean>()
        {
            @Override
            protected Boolean initialValue()
            {
                return Boolean.FALSE;
            }
        };
        lock_ = new TableEnvironmentModelLock();
        tableEnvironment_ = tableEnvironment;
    }


    // ======================================================================
    // Methods
    // ======================================================================

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

    /**
     * Creates a new component model for the specified component.
     * 
     * @param component
     *        The component.
     * 
     * @return A new component model for the specified component.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code component} was created by a table environment other
     *         than the table environment associated with this model.
     */
    public ComponentModel createComponentModel(
        final IComponent component )
    {
        assertArgumentLegal( tableEnvironment_.equals( component.getTableEnvironment() ), "component", NonNlsMessages.TableEnvironmentModel_createComponentModel_componentCreatedByDifferentTableEnvironment ); //$NON-NLS-1$

        if( component instanceof IContainer )
        {
            return createContainerModel( (IContainer)component );
        }

        return new ComponentModel( this, component );
    }

    /**
     * Creates a new container model for the specified container.
     * 
     * @param container
     *        The container.
     * 
     * @return A new container model for the specified container.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code container} was created by a table environment other
     *         than the table environment associated with this model.
     */
    public ContainerModel createContainerModel(
        final IContainer container )
    {
        assertArgumentLegal( tableEnvironment_.equals( container.getTableEnvironment() ), "container", NonNlsMessages.TableEnvironmentModel_createContainerModel_containerCreatedByDifferentTableEnvironment ); //$NON-NLS-1$

        return new ContainerModel( this, container );
    }

    /**
     * Creates a new table model for the specified table and table network.
     * 
     * @param table
     *        The table.
     * @param tableNetwork
     *        The table network.
     * 
     * @return A new table model for the specified table.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code table} was created by a table environment other than
     *         the table environment associated with this model.
     */
    public TableModel createTableModel(
        final ITable table,
        final ITableNetwork tableNetwork )
    {
        assertArgumentLegal( tableEnvironment_.equals( table.getTableEnvironment() ), "table", NonNlsMessages.TableEnvironmentModel_createTableModel_tableCreatedByDifferentTableEnvironment ); //$NON-NLS-1$

        return new TableModel( this, table, tableNetwork );
    }

    /**
     * Fires the specified event notification as the table environment model
     * lock is not held by the current thread.
     * 
     * <p>
     * If the current thread does not hold the table environment model lock, the
     * event notification will be fired immediately. Otherwise, it will be
     * queued and fired as soon as this thread releases the table environment
     * model lock.
     * </p>
     * 
     * @param eventNotification
     *        The event notification.
     */
    void fireEventNotification(
        final Runnable eventNotification )
    {
        if( canFireEventNotifications() )
        {
            eventNotification.run();
        }
        else
        {
            eventNotifications_.get().add( eventNotification );
        }
    }

    /**
     * Fires all pending event notifications queued for the current thread.
     */
    private void fireEventNotifications()
    {
        assert canFireEventNotifications();

        isEventNotificationInProgress_.set( Boolean.TRUE );
        try
        {
            final Queue<Runnable> eventNotifications = eventNotifications_.get();
            Runnable eventNotification = null;
            while( (eventNotification = eventNotifications.poll()) != null )
            {
                eventNotification.run();
            }
        }
        finally
        {
            isEventNotificationInProgress_.set( Boolean.FALSE );
        }
    }

    /**
     * Gets the table environment model lock.
     * 
     * @return The table environment model lock.
     */
    public ITableEnvironmentModelLock getLock()
    {
        return lock_;
    }

    /**
     * Gets the table environment associated with this model.
     * 
     * @return The table environment associated with this model.
     */
    public ITableEnvironment getTableEnvironment()
    {
        return tableEnvironment_;
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A reentrant mutual exclusion lock for a table environment model.
     */
    @SuppressWarnings( "synthetic-access" )
    @ThreadSafe
    private final class TableEnvironmentModelLock
        implements ITableEnvironmentModelLock
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code TableEnvironmentModelLock}
         * class.
         */
        TableEnvironmentModelLock()
        {
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.ui.impl.model.ITableEnvironmentModelLock#isHeldByCurrentThread()
         */
        @Override
        public boolean isHeldByCurrentThread()
        {
            return tableEnvironment_.getLock().isHeldByCurrentThread();
        }

        /*
         * @see java.util.concurrent.locks.ReentrantLock#lock()
         */
        @Override
        public void lock()
        {
            tableEnvironment_.getLock().lock();
        }

        /*
         * @see java.util.concurrent.locks.Lock#lockInterruptibly()
         */
        @Override
        public void lockInterruptibly()
            throws InterruptedException
        {
            tableEnvironment_.getLock().lockInterruptibly();
        }

        /*
         * @see java.util.concurrent.locks.Lock#newCondition()
         */
        @Override
        public Condition newCondition()
        {
            return tableEnvironment_.getLock().newCondition();
        }

        /*
         * @see java.util.concurrent.locks.Lock#tryLock()
         */
        @Override
        public boolean tryLock()
        {
            return tableEnvironment_.getLock().tryLock();
        }

        /*
         * @see java.util.concurrent.locks.Lock#tryLock(long, java.util.concurrent.TimeUnit)
         */
        @Override
        public boolean tryLock(
            final long time,
            final @Nullable TimeUnit unit )
            throws InterruptedException
        {
            return tableEnvironment_.getLock().tryLock( time, unit );
        }

        /*
         * @see java.util.concurrent.locks.ReentrantLock#unlock()
         */
        @Override
        public void unlock()
        {
            tableEnvironment_.getLock().unlock();

            if( canFireEventNotifications() )
            {
                fireEventNotifications();
            }
        }
    }
}
