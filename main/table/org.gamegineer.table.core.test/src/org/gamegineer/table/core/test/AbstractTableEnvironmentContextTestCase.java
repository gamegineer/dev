/*
 * AbstractTableEnvironmentContextTestCase.java
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
 * Created on May 28, 2013 at 8:22:43 PM.
 */

package org.gamegineer.table.core.test;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.core.ITableEnvironmentContext;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITableEnvironmentContext} interface.
 * 
 * @param <TableEnvironmentContextType>
 *        The type of the table environment context.
 */
@NonNullByDefault( false )
public abstract class AbstractTableEnvironmentContextTestCase<TableEnvironmentContextType extends ITableEnvironmentContext>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table environment context under test in the fixture. */
    private TableEnvironmentContextType tableEnvironmentContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractTableEnvironmentContextTestCase} class.
     */
    protected AbstractTableEnvironmentContextTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table environment context to be tested.
     * 
     * @return The table environment context to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @NonNull
    protected abstract TableEnvironmentContextType createTableEnvironmentContext()
        throws Exception;

    /**
     * Gets the table environment context under test in the fixture.
     * 
     * @return The table environment context under test in the fixture; never
     *         {@code null}.
     */
    @NonNull
    protected final TableEnvironmentContextType getTableEnvironmentContext()
    {
        assertNotNull( tableEnvironmentContext_ );
        return tableEnvironmentContext_;
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
        tableEnvironmentContext_ = createTableEnvironmentContext();
        assertNotNull( tableEnvironmentContext_ );
    }

    /**
     * Ensures the {@link ITableEnvironmentContext#fireEventNotification} method
     * throws an exception when invoked while the table environment lock is not
     * held.
     */
    @Test( expected = IllegalStateException.class )
    public void testFireEventNotification_ThrowsExceptionWhenLockNotHeld()
    {
        tableEnvironmentContext_.fireEventNotification( nonNull( EasyMock.createMock( Runnable.class ) ) );
    }
}
