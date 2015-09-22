/*
 * AbstractTableEnvironmentFactoryTestCase.java
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
 * Created on Oct 1, 2013 at 8:19:26 PM.
 */

package org.gamegineer.table.core.test;

import java.util.Optional;
import org.gamegineer.table.core.ITableEnvironmentFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITableEnvironmentFactory} interface.
 */
public abstract class AbstractTableEnvironmentFactoryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table environment factory under test in the fixture. */
    private Optional<ITableEnvironmentFactory> tableEnvironmentFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractTableEnvironmentFactoryTestCase} class.
     */
    protected AbstractTableEnvironmentFactoryTestCase()
    {
        tableEnvironmentFactory_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table environment factory to be tested.
     * 
     * @return The table environment factory to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract ITableEnvironmentFactory createTableEnvironmentFactory()
        throws Exception;

    /**
     * Gets the table environment factory under test in the fixture.
     * 
     * @return The table environment factory under test in the fixture; never
     *         {@code null}.
     */
    protected final ITableEnvironmentFactory getTableEnvironmentFactory()
    {
        return tableEnvironmentFactory_.get();
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
        tableEnvironmentFactory_ = Optional.of( createTableEnvironmentFactory() );
    }

    /**
     * Placeholder for future interface tests.
     */
    @Test
    public void testDummy()
    {
        // do nothing
    }
}
