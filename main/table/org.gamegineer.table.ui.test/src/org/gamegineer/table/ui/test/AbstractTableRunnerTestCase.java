/*
 * AbstractTableRunnerTestCase.java
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
 * Created on Oct 3, 2009 at 8:07:16 PM.
 */

package org.gamegineer.table.ui.test;

import static org.junit.Assert.assertTrue;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.ui.ITableRunner;
import org.gamegineer.table.ui.TableResult;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITableRunner} interface.
 */
public abstract class AbstractTableRunnerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table runner under test in the fixture. */
    private Optional<ITableRunner> tableRunner_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableRunnerTestCase}
     * class.
     */
    protected AbstractTableRunnerTestCase()
    {
        tableRunner_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table runner to be tested.
     * 
     * @return The table runner to be tested.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract ITableRunner createTableRunner()
        throws Exception;

    /**
     * Gets the table runner under test in the fixture.
     * 
     * @return The table runner under test in the fixture.
     */
    protected final ITableRunner getTableRunner()
    {
        return tableRunner_.get();
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
        tableRunner_ = Optional.of( createTableRunner() );
    }

    /**
     * Ensures the {@link ITableRunner#call} method throws an exception if the
     * runner is no longer pristine.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class, timeout = 10000 )
    public void testCall_NotPristine()
        throws Exception
    {
        final ITableRunner tableRunner = getTableRunner();
        final ExecutorService executor = Executors.newCachedThreadPool();
        try
        {
            final Future<TableResult> firstTask = executor.submit( tableRunner );
            final Future<TableResult> secondTask = executor.submit( tableRunner );

            // One of the two tasks should throw an IllegalStateException
            // depending on the order in which they are run by the executor.
            try
            {
                while( true )
                {
                    try
                    {
                        firstTask.get( 500L, TimeUnit.MILLISECONDS );
                    }
                    catch( @SuppressWarnings( "unused" ) final TimeoutException e )
                    {
                        // ignored
                    }

                    try
                    {
                        secondTask.get( 500L, TimeUnit.MILLISECONDS );
                    }
                    catch( @SuppressWarnings( "unused" ) final TimeoutException e )
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
            assertTrue( "executor did not shut down cleanly", executor.awaitTermination( 10L, TimeUnit.SECONDS ) ); //$NON-NLS-1$
        }
    }
}
