/*
 * PlatformUiTest.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Oct 7, 2008 at 11:05:01 PM.
 */

package org.gamegineer.client.ui.console;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.client.ui.console.PlatformUi}
 * class.
 */
public final class PlatformUiTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The executor used to run the console. */
    private ExecutorService executor_;

    /** The console task associated with the executor. */
    private Future<?> task_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlatformUiTest} class.
     */
    public PlatformUiTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Blocks until the platform console enters the running state.
     * 
     * @throws java.lang.InterruptedException
     *         If the current thread was interrupted while waiting.
     */
    private static void awaitConsoleRunning()
        throws InterruptedException
    {
        while( !PlatformUi.isConsoleRunning() )
        {
            Thread.sleep( 1 );
        }
    }

    /**
     * Resets the platform console.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    private static void resetPlatformConsole()
        throws Exception
    {
        final Field platformConsoleField = PlatformUi.class.getDeclaredField( "console_" ); //$NON-NLS-1$
        platformConsoleField.setAccessible( true );
        final AtomicReference<?> platformConsole = (AtomicReference<?>)platformConsoleField.get( null );
        platformConsole.set( null );
    }

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
        executor_ = Executors.newSingleThreadExecutor();
        task_ = null;
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
        executor_.shutdown();
        if( task_ != null )
        {
            task_.cancel( true );
        }
        task_ = null;
        assertTrue( executor_.awaitTermination( 1, TimeUnit.SECONDS ) );
        executor_ = null;

        resetPlatformConsole();
    }

    /**
     * Ensures the {@code createAndRunConsole} method throws an exception when
     * it is invoked after the platform console has been created.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class, timeout = 1000 )
    public void testCreateAndRunConsole_InvokedTwice()
        throws Exception
    {
        final Callable<Object> runner = new Callable<Object>()
        {
            public Object call()
                throws Exception
            {
                return PlatformUi.createAndRunConsole( new FakeDisplay(), new ConsoleAdvisor() );
            }
        };
        task_ = executor_.submit( runner );
        awaitConsoleRunning();

        PlatformUi.createAndRunConsole( new FakeDisplay(), new ConsoleAdvisor() );
    }

    /**
     * Ensures the {@code createAndRunConsole} method throws an exception when
     * passed a {@code null} advisor.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateAndRunConsole_Advisor_Null()
        throws Exception
    {
        PlatformUi.createAndRunConsole( createDummy( IDisplay.class ), null );
    }

    /**
     * Ensures the {@code createAndRunConsole} method throws an exception when
     * passed a {@code null} display.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateAndRunConsole_Display_Null()
        throws Exception
    {
        PlatformUi.createAndRunConsole( null, createDummy( IConsoleAdvisor.class ) );
    }

    /**
     * Ensures the {@code createConsoleRunner} method throws an exception when
     * passed a {@code null} advisor.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateConsoleRunner_Advisor_Null()
    {
        PlatformUi.createConsoleRunner( createDummy( IDisplay.class ), null );
    }

    /**
     * Ensures the {@code createConsoleRunner} method throws an exception when
     * it is invoked after the platform console has been created.
     */
    @Test( expected = IllegalStateException.class )
    public void testCreateConsoleRunner_ConsoleCreated()
    {
        PlatformUi.createConsoleRunner( new FakeDisplay(), new ConsoleAdvisor() );
        PlatformUi.createConsoleRunner( new FakeDisplay(), new ConsoleAdvisor() );
    }

    /**
     * Ensures the {@code createConsoleRunner} method throws an exception when
     * passed a {@code null} display.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateConsoleRunner_Display_Null()
    {
        PlatformUi.createConsoleRunner( null, createDummy( IConsoleAdvisor.class ) );
    }

    /**
     * Ensures the {@code createDisplay} method does not return {@code null}.
     */
    @Test
    public void testCreateDisplay_ReturnValue_NonNull()
    {
        assertNotNull( PlatformUi.createDisplay() );
    }

    /**
     * Ensures the {@code getConsole} method does not return {@code null} when
     * it is invoked after the platform console has been created.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetConsole_ConsoleCreated_ReturnValue_NonNull()
        throws Exception
    {
        task_ = executor_.submit( PlatformUi.createConsoleRunner( new FakeDisplay(), new ConsoleAdvisor() ) );
        awaitConsoleRunning();

        assertNotNull( PlatformUi.getConsole() );
    }

    /**
     * Ensures the {@code getConsole} method throws an exception when it is
     * invoked before the platform console has been created.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetConsole_ConsoleNotCreated()
    {
        PlatformUi.getConsole();
    }
}
