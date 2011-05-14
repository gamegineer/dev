/*
 * AbstractTableNetworkControllerTestCase.java
 * Copyright 2008-2011 Gamegineer.org
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

package org.gamegineer.table.internal.net;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.ITableNetworkController} interface.
 */
public abstract class AbstractTableNetworkControllerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network controller under test in the fixture. */
    private ITableNetworkController controller_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractTableNetworkControllerTestCase} class.
     */
    protected AbstractTableNetworkControllerTestCase()
    {
        super();
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
    /* @NonNull */
    protected abstract ITableNetworkController createTableNetworkController()
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
        controller_ = createTableNetworkController();
        assertNotNull( controller_ );
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
        controller_ = null;
    }

    /**
     * Ensures the {@code getTransportLayerFactory} method does not return
     * {@code null}.
     */
    @Test
    public void testGetTransportLayerFactory_ReturnValue_NonNull()
    {
        assertNotNull( controller_.getTransportLayerFactory() );
    }
}
