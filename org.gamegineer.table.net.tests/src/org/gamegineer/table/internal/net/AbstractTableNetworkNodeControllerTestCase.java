/*
 * AbstractTableNetworkNodeControllerTestCase.java
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
 * Created on Apr 10, 2011 at 6:14:52 PM.
 */

package org.gamegineer.table.internal.net;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.ITableNetworkNodeController}
 * interface.
 * 
 * @param <T>
 *        The type of the table network node controller.
 */
public abstract class AbstractTableNetworkNodeControllerTestCase<T extends ITableNetworkNodeController>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network node controller under test in the fixture. */
    private T controller_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractTableNetworkNodeControllerTestCase} class.
     */
    protected AbstractTableNetworkNodeControllerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table network node controller to be tested.
     * 
     * @return The table network node controller to be tested; never {@code
     *         null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createTableNetworkNodeController()
        throws Exception;

    /**
     * Gets the table network node controller under test in the fixture.
     * 
     * @return The table network node controller under test in the fixture;
     *         never {@code null}.
     */
    /* @NonNull */
    protected final T getTableNetworkNodeController()
    {
        assertNotNull( controller_ );
        return controller_;
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
        controller_ = createTableNetworkNodeController();
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
     * Ensures the {@code connect} method throws an exception when passed a
     * {@code null} table network configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testConnect_Configuration_Null()
        throws Exception
    {
        controller_.connect( null );
    }

    /**
     * Ensures the {@code connect} method throws an exception when the table
     * network node is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TableNetworkException.class )
    public void testConnect_Connected_ThrowsException()
        throws Exception
    {
        final ITableNetworkConfiguration configuration = TableNetworkConfigurations.createDefaultTableNetworkConfiguration();
        controller_.connect( configuration );

        controller_.connect( configuration );
    }

    /**
     * Ensures the {@code disconnect} method does nothing when the table network
     * node is disconnected.
     */
    @Test
    public void testDisconnect_Disconnected_DoesNothing()
    {
        controller_.disconnect();
    }
}
