/*
 * SingleThreadedTableEnvironmentContextTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on May 26, 2013 at 10:23:43 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link SingleThreadedTableEnvironmentContext}
 * class.
 */
public final class SingleThreadedTableEnvironmentContextTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The single-threaded table environment context under test in the fixture. */
    private SingleThreadedTableEnvironmentContext tableEnvironmentContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code SingleThreadedTableEnvironmentContextTest} class.
     */
    public SingleThreadedTableEnvironmentContextTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        mocksControl_ = EasyMock.createControl();
        tableEnvironmentContext_ = new SingleThreadedTableEnvironmentContext();
    }

    /**
     * Ensures the {@link ITableEnvironmentContext#fireEventNotification} method
     * does not fire the event notification if the lock is held.
     */
    @Test
    public void testFireEventNotification_Locked_DoesNotFireEventNotification()
    {
        final Runnable eventNotification = mocksControl_.createMock( Runnable.class );
        mocksControl_.replay();

        tableEnvironmentContext_.getLock().lock();
        tableEnvironmentContext_.fireEventNotification( eventNotification );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code ITableEnvironmentLock#unlock} method does not fire
     * pending event notifications if the lock is held.
     */
    @Test
    public void testTableEnvironmentLock_Unlock_Locked_DoesNotFirePendingEventNotifications()
    {
        final Runnable eventNotification = mocksControl_.createMock( Runnable.class );
        mocksControl_.replay();

        tableEnvironmentContext_.getLock().lock();
        tableEnvironmentContext_.getLock().lock();
        tableEnvironmentContext_.fireEventNotification( eventNotification );
        tableEnvironmentContext_.getLock().unlock();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code ITableEnvironmentLock#unlock} method does not fire
     * pending event notifications if the lock is not held but another call to
     * {@code ITableEnvironmentLock#unlock} is active on the call stack.
     */
    @Test
    public void testTableEnvironmentLock_Unlock_Unlocked_DoesNotFirePendingEventNotifications_EventNotificationsInProgress()
    {
        final List<Integer> eventNotificationCallHistory = new ArrayList<>();
        final Runnable eventNotification1 = new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                tableEnvironmentContext_.getLock().lock();
                tableEnvironmentContext_.getLock().unlock(); // should not fire eventNotification2
                eventNotificationCallHistory.add( Integer.valueOf( 1 ) );
            }
        };
        final Runnable eventNotification2 = new Runnable()
        {
            @Override
            public void run()
            {
                eventNotificationCallHistory.add( Integer.valueOf( 2 ) );
            }
        };

        tableEnvironmentContext_.getLock().lock();
        tableEnvironmentContext_.fireEventNotification( eventNotification1 );
        tableEnvironmentContext_.fireEventNotification( eventNotification2 );
        tableEnvironmentContext_.getLock().unlock();

        assertEquals( 2, eventNotificationCallHistory.size() );
        assertEquals( Integer.valueOf( 1 ), eventNotificationCallHistory.get( 0 ) );
        assertEquals( Integer.valueOf( 2 ), eventNotificationCallHistory.get( 1 ) );
    }
}