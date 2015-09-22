/*
 * AbstractTableNetworkControllerTestCase.java
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
 * Created on May 14, 2011 at 2:41:45 PM.
 */

package org.gamegineer.table.internal.net.impl;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITableNetworkController} interface.
 */
public abstract class AbstractTableNetworkControllerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network controller under test in the fixture. */
    private Optional<ITableNetworkController> tableNetworkController_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractTableNetworkControllerTestCase} class.
     */
    protected AbstractTableNetworkControllerTestCase()
    {
        tableNetworkController_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table network controller to be tested.
     * 
     * @return The table network controller to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract ITableNetworkController createTableNetworkController()
        throws Exception;

    /**
     * Gets the table network controller under test in the fixture.
     * 
     * @return The table network controller under test in the fixture; never
     *         {@code null}.
     */
    protected final ITableNetworkController getTableNetworkController()
    {
        return tableNetworkController_.get();
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
        tableNetworkController_ = Optional.of( createTableNetworkController() );
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
