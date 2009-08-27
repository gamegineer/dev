/*
 * AbstractConsoleTestCase.java
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
 * Created on Oct 5, 2008 at 10:34:02 PM.
 */

package org.gamegineer.client.ui.console;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.client.ui.console.IConsole} interface.
 */
public abstract class AbstractConsoleTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The console under test in the fixture. */
    private IConsole m_console;

    /** The executor used to run the console. */
    private ExecutorService m_executor;

    /** The console task associated with the executor. */
    private Future<?> m_task;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractConsoleTestCase} class.
     */
    protected AbstractConsoleTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Blocks until the console enters the finished state.
     * 
     * @param console
     *        The console; must not be {@code null}.
     * 
     * @throws java.lang.InterruptedException
     *         If the current thread was interrupted while waiting.
     * @throws java.lang.NullPointerException
     *         If {@code console} is {@code null}.
     */
    protected abstract void awaitConsoleFinished(
        /* @NonNull */
        IConsole console )
        throws InterruptedException;

    /**
     * Blocks until the console enters the running state.
     * 
     * @param console
     *        The console; must not be {@code null}.
     * 
     * @throws java.lang.InterruptedException
     *         If the current thread was interrupted while waiting.
     * @throws java.lang.NullPointerException
     *         If {@code console} is {@code null}.
     */
    protected abstract void awaitConsoleRunning(
        /* @NonNull */
        IConsole console )
        throws InterruptedException;

    /**
     * Creates the console to be tested.
     * 
     * @param display
     *        The display to associate with the console; must not be
     *        {@code null}.
     * @param advisor
     *        The console advisor; must not be {@code null}.
     * 
     * @return The console to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code display} or {@code advisor} is {@code null}.
     */
    /* @NonNull */
    protected abstract IConsole createConsole(
        /* @NonNull */
        final IDisplay display,
        /* @NonNull */
        final IConsoleAdvisor advisor )
        throws Exception;

    /**
     * Creates a runner for the specified console.
     * 
     * @param console
     *        The console; must not be {@code null}.
     * 
     * @return A runner for the specified console; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code console} is {@code null}.
     */
    /* @NonNull */
    protected abstract Callable<ConsoleResult> createConsoleRunner(
        /* @NonNull */
        final IConsole console )
        throws Exception;

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
        m_console = createConsole( new FakeDisplay(), new ConsoleAdvisor() );
        assertNotNull( m_console );

        m_executor = Executors.newSingleThreadExecutor();
        m_task = null;
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
        m_executor.shutdown();
        if( m_task != null )
        {
            m_task.cancel( true );
            m_task = null;
        }
        assertTrue( m_executor.awaitTermination( 1, TimeUnit.SECONDS ) );
        m_executor = null;

        m_console = null;
    }

    /**
     * Ensures the {@code close} method does not throw an exception when it is
     * invoked after the console has finished running.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( timeout = 1000 )
    public void testClose_Finished()
        throws Exception
    {
        m_task = m_executor.submit( createConsoleRunner( m_console ) );
        awaitConsoleRunning( m_console );
        m_task.cancel( true );
        awaitConsoleFinished( m_console );

        m_console.close();
    }

    /**
     * Ensures the {@code close} method shuts down the console when it is
     * invoked before the console is running.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( timeout = 1000 )
    public void testClose_Pristine()
        throws Exception
    {
        m_console.close();
        m_task = m_executor.submit( createConsoleRunner( m_console ) );
        awaitConsoleFinished( m_console );
    }

    /**
     * Ensures the {@code close} method shuts down the console when it is
     * invoked while the console is running.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( timeout = 1000 )
    public void testClose_Running()
        throws Exception
    {
        m_task = m_executor.submit( createConsoleRunner( m_console ) );
        awaitConsoleRunning( m_console );

        m_console.close();

        awaitConsoleFinished( m_console );
    }

    /**
     * Ensures the {@code getDisplay} method does not return {@code null} when
     * it is invoked after the console has finished running.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( timeout = 1000 )
    public void testGetDisplay_Finished_ReturnValue_NonNull()
        throws Exception
    {
        m_task = m_executor.submit( createConsoleRunner( m_console ) );
        awaitConsoleRunning( m_console );
        m_task.cancel( true );
        awaitConsoleFinished( m_console );

        assertNotNull( m_console.getDisplay() );
    }

    /**
     * Ensures the {@code getDisplay} method does not return {@code null} when
     * it is invoked before the console is running.
     */
    @Test
    public void testGetDisplay_Pristine_ReturnValue_NonNull()
    {
        assertNotNull( m_console.getDisplay() );
    }

    /**
     * Ensures the {@code getDisplay} method does not return {@code null} when
     * it is invoked while the console is running.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( timeout = 1000 )
    public void testGetDisplay_Running_ReturnValue_NonNull()
        throws Exception
    {
        m_task = m_executor.submit( createConsoleRunner( m_console ) );
        awaitConsoleRunning( m_console );

        assertNotNull( m_console.getDisplay() );
    }
}
