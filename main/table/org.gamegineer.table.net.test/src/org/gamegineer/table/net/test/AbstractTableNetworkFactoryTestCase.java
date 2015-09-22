/*
 * AbstractTableNetworkFactoryTestCase.java
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
 * Created on Nov 23, 2013 at 8:16:12 PM.
 */

package org.gamegineer.table.net.test;

import java.util.Optional;
import org.gamegineer.table.net.ITableNetworkFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITableNetworkFactory} interface.
 */
public abstract class AbstractTableNetworkFactoryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network factory under test in the fixture. */
    private Optional<ITableNetworkFactory> tableNetworkFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractTableNetworkFactoryTestCase} class.
     */
    protected AbstractTableNetworkFactoryTestCase()
    {
        tableNetworkFactory_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table network factory to be tested.
     * 
     * @return The table network factory to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract ITableNetworkFactory createTableNetworkFactory()
        throws Exception;

    /**
     * Gets the table network factory under test in the fixture.
     * 
     * @return The table network factory under test in the fixture; never
     *         {@code null}.
     */
    protected final ITableNetworkFactory getTableNetworkFactory()
    {
        return tableNetworkFactory_.get();
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
        tableNetworkFactory_ = Optional.of( createTableNetworkFactory() );
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
