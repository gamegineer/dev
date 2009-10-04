/*
 * TableUI.java
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
 * Created on Sep 17, 2009 at 11:26:49 PM.
 */

package org.gamegineer.table.ui;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.core.util.concurrent.TaskUtils;
import org.gamegineer.table.internal.ui.TableFrameRunner;

/**
 * Provides access to the table user interface.
 */
@ThreadSafe
public final class TableUI
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableUI} class.
     */
    private TableUI()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table user interface and runs it.
     * 
     * <p>
     * This method is intended to be called by the Equinox application's {@code
     * start} method.
     * </p>
     * 
     * @param advisor
     *        The table advisor; must not be {@code null}.
     * 
     * @return The table result; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code advisor} is {@code null}.
     */
    /* @NonNull */
    public static TableResult createAndRunTable(
        /* @NonNull */
        final ITableAdvisor advisor )
        throws Exception
    {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        try
        {
            return executor.submit( createTableRunner( advisor ) ).get();
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
        finally
        {
            executor.shutdown();
        }
    }

    /**
     * Creates the table user interface and returns an object capable of running
     * it.
     * 
     * @param advisor
     *        The table advisor; must not be {@code null}.
     * 
     * @return An object capable of running the table user interface; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code advisor} is {@code null}.
     */
    /* @NonNull */
    public static ITableRunner createTableRunner(
        /* @NonNull */
        final ITableAdvisor advisor )
    {
        assertArgumentNotNull( advisor, "advisor" ); //$NON-NLS-1$

        return new TableFrameRunner( advisor );
    }
}
