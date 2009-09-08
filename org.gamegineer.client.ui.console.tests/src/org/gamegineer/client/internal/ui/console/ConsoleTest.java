/*
 * ConsoleTest.java
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
 * Created on Oct 5, 2008 at 10:12:49 PM.
 */

package org.gamegineer.client.internal.ui.console;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.gamegineer.client.ui.console.ConsoleAdvisor;
import org.gamegineer.client.ui.console.FakeDisplay;
import org.gamegineer.client.ui.console.IConsoleAdvisor;
import org.gamegineer.client.ui.console.IDisplay;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.ui.console.Console} class.
 */
public final class ConsoleTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The console under test in the fixture. */
    private Console console_;

    /** The executor used to run the console. */
    private ExecutorService executor_;

    /** The collection of console tasks associated with the executor. */
    private Collection<Future<?>> tasks_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConsoleTest} class.
     */
    public ConsoleTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Blocks until the console enters the specified state.
     * 
     * @param console
     *        The console; must not be {@code null}.
     * @param state
     *        The desired console state; must not be {@code null}.
     * 
     * @throws java.lang.InterruptedException
     *         If the current thread was interrupted while waiting.
     */
    static void awaitConsoleState(
        /* @NonNull */
        final Console console,
        /* @NonNull */
        final Console.State state )
        throws InterruptedException
    {
        assert console != null;
        assert state != null;

        while( console.getState() != state )
        {
            Thread.sleep( 1 );
        }
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
        console_ = new Console( new FakeDisplay(), new ConsoleAdvisor() );
        executor_ = Executors.newCachedThreadPool();
        tasks_ = new ArrayList<Future<?>>();
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
        for( final Future<?> task : tasks_ )
        {
            task.cancel( true );
        }
        tasks_.clear();
        tasks_ = null;
        assertTrue( executor_.awaitTermination( 1, TimeUnit.SECONDS ) );
        executor_ = null;
        console_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * console advisor.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Advisor_Null()
    {
        new Console( createDummy( IDisplay.class ), null );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * display.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Display_Null()
    {
        new Console( null, createDummy( IConsoleAdvisor.class ) );
    }

    /**
     * Ensures the {@code createRunner} method throws an exception when the
     * runner is invoked after the console has finished running.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class, timeout = 1000 )
    public void testCreateRunner_Finished()
        throws Exception
    {
        final Future<?> task1 = executor_.submit( console_.createRunner() );
        tasks_.add( task1 );
        awaitConsoleState( console_, Console.State.RUNNING );
        task1.cancel( true );
        awaitConsoleState( console_, Console.State.FINISHED );
        final Future<?> task2 = executor_.submit( console_.createRunner() );
        tasks_.add( task2 );

        try
        {
            task2.get();
        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
    }

    /**
     * Ensures the {@code createRunner} method throws an exception when the
     * runner is invoked while the console is running.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class, timeout = 1000 )
    public void testCreateRunner_Running()
        throws Exception
    {
        final Future<?> task1 = executor_.submit( console_.createRunner() );
        tasks_.add( task1 );
        awaitConsoleState( console_, Console.State.RUNNING );
        final Future<?> task2 = executor_.submit( console_.createRunner() );
        tasks_.add( task2 );

        try
        {
            task2.get();
        }
        catch( final ExecutionException e )
        {
            throw TaskUtils.launderThrowable( e.getCause() );
        }
    }

    /**
     * Ensures the {@code isRunning} method returns {@code false} when it is
     * invoked after the console has finished running.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testIsRunning_Finished()
        throws Exception
    {
        final Future<?> task = executor_.submit( console_.createRunner() );
        tasks_.add( task );
        awaitConsoleState( console_, Console.State.RUNNING );
        task.cancel( true );
        awaitConsoleState( console_, Console.State.FINISHED );

        assertFalse( console_.isRunning() );
    }

    /**
     * Ensures the {@code isRunning} method returns {@code false} when it is
     * invoked before the console is running.
     */
    @Test
    public void testIsRunning_Pristine()
    {
        assertFalse( console_.isRunning() );
    }

    /**
     * Ensures the {@code isRunning} method returns {@code true} when it is
     * invoked while the console is running.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testIsRunning_Running()
        throws Exception
    {
        final Future<?> task = executor_.submit( console_.createRunner() );
        tasks_.add( task );
        awaitConsoleState( console_, Console.State.RUNNING );

        assertTrue( console_.isRunning() );
    }
}
