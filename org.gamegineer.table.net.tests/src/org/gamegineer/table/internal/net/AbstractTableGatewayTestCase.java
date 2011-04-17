/*
 * AbstractTableGatewayTestCase.java
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
 * Created on Apr 16, 2011 at 11:04:26 PM.
 */

package org.gamegineer.table.internal.net;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.ITableGateway} interface.
 * 
 * @param <T>
 *        The type of the table gateway.
 */
public abstract class AbstractTableGatewayTestCase<T extends ITableGateway>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table gateway under test in the fixture. */
    private T tableGateway_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableGatewayTestCase}
     * class.
     */
    protected AbstractTableGatewayTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table gateway to be tested.
     * 
     * @return The table gateway to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createTableGateway()
        throws Exception;

    /**
     * Gets the table gateway under test in the fixture.
     * 
     * @return The table gateway under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final T getTableGateway()
    {
        assertNotNull( tableGateway_ );
        return tableGateway_;
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
        tableGateway_ = createTableGateway();
        assertNotNull( tableGateway_ );
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
        tableGateway_ = null;
    }

    /**
     * Ensures the {@code getPlayerName} method does not return {@code null}.
     */
    @Test
    public void testGetPlayerName_ReturnValue_NonNull()
    {
        assertNotNull( tableGateway_.getPlayerName() );
    }
}
