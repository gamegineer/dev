/*
 * AbstractTableRunnerTestCase.java
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
 * Created on Oct 3, 2009 at 8:07:16 PM.
 */

package org.gamegineer.table.ui;

import static org.junit.Assert.assertNotNull;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.ui.ITableRunner} interface.
 */
public abstract class AbstractTableRunnerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table runner under test in the fixture. */
    private ITableRunner runner_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableRunnerTestCase}
     * class.
     */
    protected AbstractTableRunnerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table runner to be tested.
     * 
     * @return The table runner to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITableRunner createTableRunner()
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
        runner_ = createTableRunner();
        assertNotNull( runner_ );
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
        runner_ = null;
    }

    /**
     * Ensures the {@code call} method throws an exception if the runner is no
     * longer pristine.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class, timeout = 1000 )
    public void testCall_NotPristine()
        throws Exception
    {
        final ExecutorService executor = Executors.newCachedThreadPool();
        try
        {
            final Future<TableResult> firstTask = executor.submit( runner_ );
            final Future<TableResult> secondTask = executor.submit( runner_ );

            // One of the two tasks should throw an IllegalStateException
            // depending on the order in which they are run by the executor.
            try
            {
                while( true )
                {
                    try
                    {
                        firstTask.get( 1, TimeUnit.MILLISECONDS );
                    }
                    catch( final TimeoutException e )
                    {
                        // ignored
                    }

                    try
                    {
                        secondTask.get( 1, TimeUnit.MILLISECONDS );
                    }
                    catch( final TimeoutException e )
                    {
                        // ignored
                    }
                }
            }
            catch( final ExecutionException e )
            {
                final Throwable cause = e.getCause();
                if( cause instanceof Exception )
                {
                    throw (Exception)cause;
                }

                throw TaskUtils.launderThrowable( cause );
            }
        }
        finally
        {
            executor.shutdownNow();
        }
    }
}
