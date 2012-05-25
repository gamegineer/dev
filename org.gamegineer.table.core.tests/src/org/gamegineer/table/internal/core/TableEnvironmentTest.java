/*
 * TableEnvironmentTest.java
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
 * Created on Jul 21, 2011 at 10:07:33 PM.
 */

package org.gamegineer.table.internal.core;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.TableEnvironment} class.
 */
public final class TableEnvironmentTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The table environment under test in the fixture. */
    private TableEnvironment tableEnvironment_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableEnvironmentTest} class.
     */
    public TableEnvironmentTest()
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
        tableEnvironment_ = new TableEnvironment();
    }

    /**
     * Ensures the {@code addEventNotification} method does not fire the event
     * notification if the lock is held by the current thread.
     */
    @Test
    public void testAddEventNotification_Locked_DoesNotFireEventNotification()
    {
        final Runnable notification = mocksControl_.createMock( Runnable.class );
        mocksControl_.replay();

        tableEnvironment_.getLock().lock();
        tableEnvironment_.addEventNotification( notification );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code addEventNotification} method does not fire the event
     * notification if the lock is not held by the current thread but a call to
     * {@code TableEnvironmentLock#unlock} is active on the call stack.
     */
    @Test
    public void testAddEventNotification_Unlocked_DoesNotFireEventNotification_EventNotificationsInProgress()
    {
        final List<Integer> notificationCallHistory = new ArrayList<Integer>();
        final Runnable notification2 = new Runnable()
        {
            @Override
            public void run()
            {
                notificationCallHistory.add( Integer.valueOf( 2 ) );
            }
        };
        final Runnable notification1 = new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                tableEnvironment_.addEventNotification( notification2 ); // should not fire immediately
                notificationCallHistory.add( Integer.valueOf( 1 ) );
            }
        };

        tableEnvironment_.getLock().lock();
        tableEnvironment_.addEventNotification( notification1 );
        tableEnvironment_.getLock().unlock();

        assertEquals( 2, notificationCallHistory.size() );
        assertEquals( Integer.valueOf( 1 ), notificationCallHistory.get( 0 ) );
        assertEquals( Integer.valueOf( 2 ), notificationCallHistory.get( 1 ) );
    }

    /**
     * Ensures the {@code addEventNotification} method fires the event
     * notification if the lock is not held by the current thread.
     */
    @Test
    public void testAddEventNotification_Unlocked_FiresEventNotification()
    {
        final Runnable notification = mocksControl_.createMock( Runnable.class );
        notification.run();
        mocksControl_.replay();

        tableEnvironment_.addEventNotification( notification );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code TableEnvironmentLock#unlock} method does not fire
     * pending event notifications if the lock is held by the current thread.
     */
    @Test
    public void testUnlock_Locked_DoesNotFirePendingEventNotifications()
    {
        final Runnable notification = mocksControl_.createMock( Runnable.class );
        mocksControl_.replay();

        tableEnvironment_.getLock().lock();
        tableEnvironment_.getLock().lock();
        tableEnvironment_.addEventNotification( notification );
        tableEnvironment_.getLock().unlock();

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code TableEnvironmentLock#unlock} method does not fire
     * pending event notifications if the lock is not held by the current thread
     * but another call to {@code TableEnvironmentLock#unlock} is active on the
     * call stack.
     */
    @Test
    public void testUnlock_Unlocked_DoesNotFirePendingEventNotifications_EventNotificationsInProgress()
    {
        final List<Integer> notificationCallHistory = new ArrayList<Integer>();
        final Runnable notification1 = new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                tableEnvironment_.getLock().lock();
                tableEnvironment_.getLock().unlock(); // should not fire notification2
                notificationCallHistory.add( Integer.valueOf( 1 ) );
            }
        };
        final Runnable notification2 = new Runnable()
        {
            @Override
            public void run()
            {
                notificationCallHistory.add( Integer.valueOf( 2 ) );
            }
        };

        tableEnvironment_.getLock().lock();
        tableEnvironment_.addEventNotification( notification1 );
        tableEnvironment_.addEventNotification( notification2 );
        tableEnvironment_.getLock().unlock();

        assertEquals( 2, notificationCallHistory.size() );
        assertEquals( Integer.valueOf( 1 ), notificationCallHistory.get( 0 ) );
        assertEquals( Integer.valueOf( 2 ), notificationCallHistory.get( 1 ) );
    }
}
