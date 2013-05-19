/*
 * TableEnvironment.java
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
 * Created on Jul 6, 2011 at 8:11:30 PM.
 */

package org.gamegineer.table.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.memento.MementoException;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IComponentStrategy;
import org.gamegineer.table.core.IContainer;
import org.gamegineer.table.core.IContainerStrategy;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEnvironment;

/**
 * Implementation of {@link org.gamegineer.table.core.ITableEnvironment}.
 */
@ThreadSafe
public final class TableEnvironment
    implements ITableEnvironment
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The event notification lock. */
    private final Lock eventNotificationLock_;

    /** The event notification task. */
    private final Future<?> eventNotificationTask_;

    /** The table environment lock. */
    private final ReentrantLock lock_;

    /** The collection of pending event notifications. */
    @GuardedBy( "eventNotificationLock_" )
    private final Queue<Runnable> pendingEventNotifications_;

    /**
     * The condition that signals the pending event notifications collection has
     * transitioned from empty to not empty.
     */
    @GuardedBy( "eventNotificationLock_" )
    private final Condition pendingEventNotificationsNotEmptyCondition_;

    /**
     * The condition that signals the pending event notifications collection has
     * transitioned from not empty to empty.
     */
    @GuardedBy( "eventNotificationLock_" )
    private final Condition pendingEventNotificationsEmptyCondition_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableEnvironment} class.
     */
    public TableEnvironment()
    {
        eventNotificationLock_ = new ReentrantLock();
        lock_ = new ReentrantLock();
        pendingEventNotifications_ = new ArrayDeque<Runnable>();
        pendingEventNotificationsNotEmptyCondition_ = eventNotificationLock_.newCondition();
        pendingEventNotificationsEmptyCondition_ = eventNotificationLock_.newCondition();

        eventNotificationTask_ = Activator.getDefault().getExecutorService().submit( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                processEventNotifications();
            }
        } );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds an event notification to be fired asynchronously.
     * 
     * @param notification
     *        The event notification; must not be {@code null}.
     */
    void addEventNotification(
        /* @NonNull */
        final Runnable notification )
    {
        assert notification != null;

        eventNotificationLock_.lock();
        try
        {
            pendingEventNotifications_.add( notification );
            pendingEventNotificationsNotEmptyCondition_.signal();
        }
        finally
        {
            eventNotificationLock_.unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironment#awaitPendingEvents()
     */
    @Override
    public void awaitPendingEvents()
        throws InterruptedException
    {
        eventNotificationLock_.lock();
        try
        {
            while( !pendingEventNotifications_.isEmpty() )
            {
                pendingEventNotificationsEmptyCondition_.await();
            }
        }
        finally
        {
            eventNotificationLock_.unlock();
        }
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironment#createComponent(java.lang.Object)
     */
    @Override
    public IComponent createComponent(
        final Object memento )
        throws MementoException
    {
        assertArgumentNotNull( memento, "memento" ); //$NON-NLS-1$

        return ComponentFactory.createComponent( this, memento );
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironment#createComponent(org.gamegineer.table.core.IComponentStrategy)
     */
    @Override
    public IComponent createComponent(
        final IComponentStrategy strategy )
    {
        assertArgumentNotNull( strategy, "strategy" ); //$NON-NLS-1$

        return new Component( this, strategy );
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironment#createContainer(org.gamegineer.table.core.IContainerStrategy)
     */
    @Override
    public IContainer createContainer(
        final IContainerStrategy strategy )
    {
        assertArgumentNotNull( strategy, "strategy" ); //$NON-NLS-1$

        return new Container( this, strategy );
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironment#createTable()
     */
    @Override
    public ITable createTable()
    {
        return new Table( this );
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironment#dispose()
     */
    @Override
    public void dispose()
    {
        if( !eventNotificationTask_.isDone() && !eventNotificationTask_.cancel( true ) )
        {
            Loggers.getDefaultLogger().warning( NonNlsMessages.TableEnvironment_dispose_eventNotificationTask_cancelFailed );
        }
    }

    /*
     * @see org.gamegineer.table.core.ITableEnvironment#getLock()
     */
    @Override
    public ReentrantLock getLock()
    {
        return lock_;
    }

    /**
     * Processes all pending event notifications until interrupted.
     * 
     * <p>
     * This method is intended to be called from a dedicated thread.
     * </p>
     */
    private void processEventNotifications()
    {
        try
        {
            while( true )
            {
                Runnable runnable = null;
                eventNotificationLock_.lock();
                try
                {
                    while( (runnable = pendingEventNotifications_.peek()) == null )
                    {
                        pendingEventNotificationsNotEmptyCondition_.await();
                    }
                }
                finally
                {
                    eventNotificationLock_.unlock();
                }

                try
                {
                    runnable.run();
                }
                catch( final RuntimeException e )
                {
                    Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.TableEnvironment_processEventNotifications_unexpectedException, e );
                }

                eventNotificationLock_.lock();
                try
                {
                    pendingEventNotifications_.remove();
                    if( pendingEventNotifications_.isEmpty() )
                    {
                        pendingEventNotificationsEmptyCondition_.signal();
                    }
                }
                finally
                {
                    eventNotificationLock_.unlock();
                }
            }
        }
        catch( final InterruptedException e )
        {
            Thread.currentThread().interrupt();
        }
    }
}
