/*
 * MultiThreadedTableEnvironmentContextTest.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on May 26, 2013 at 10:23:57 PM.
 */

package org.gamegineer.table.core;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link MultiThreadedTableEnvironmentContext} class.
 */
@NonNullByDefault( false )
public final class MultiThreadedTableEnvironmentContextTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The multi-threaded table environment context under test in the fixture. */
    private MultiThreadedTableEnvironmentContext tableEnvironmentContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code MultiThreadedTableEnvironmentContextTest} class.
     */
    public MultiThreadedTableEnvironmentContextTest()
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
        tableEnvironmentContext_ = new MultiThreadedTableEnvironmentContext();
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        tableEnvironmentContext_.dispose();
    }

    /**
     * Ensures the
     * {@link MultiThreadedTableEnvironmentContext#fireEventNotification} method
     * fires the event notification on a different thread.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testFireEventNotification_EventNotificationFiredOnDifferentThread()
        throws Exception
    {
        final Thread testThread = Thread.currentThread();
        final AtomicReference<Thread> eventNotificationThreadReference = new AtomicReference<>();
        final CountDownLatch countDownLatch = new CountDownLatch( 1 );
        final Runnable eventNotification = mocksControl_.createMock( Runnable.class );
        eventNotification.run();
        EasyMock.expectLastCall().andAnswer( new IAnswer<Void>()
        {
            @Override
            public Void answer()
            {
                eventNotificationThreadReference.set( Thread.currentThread() );
                countDownLatch.countDown();
                return null;
            }
        } );
        mocksControl_.replay();

        tableEnvironmentContext_.getLock().lock();
        try
        {
            tableEnvironmentContext_.fireEventNotification( eventNotification );
        }
        finally
        {
            tableEnvironmentContext_.getLock().unlock();
        }

        countDownLatch.await();
        mocksControl_.verify();
        assertThat( eventNotificationThreadReference.get(), not( testThread ) );
    }
}
