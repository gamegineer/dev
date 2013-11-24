/*
 * AbstractTableNetworkFactoryTestCase.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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

import static org.junit.Assert.assertNotNull;
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
    private ITableNetworkFactory tableNetworkFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractTableNetworkFactoryTestCase} class.
     */
    protected AbstractTableNetworkFactoryTestCase()
    {
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
    /* @NonNull */
    protected abstract ITableNetworkFactory createTableNetworkFactory()
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
        tableNetworkFactory_ = createTableNetworkFactory();
        assertNotNull( tableNetworkFactory_ );
    }

    /**
     * Ensures the {@link ITableNetworkFactory#createTableNetwork} method does
     * not return {@code null}.
     */
    @Test
    public void testCreateTableNetwork_ReturnValue_NonNull()
    {
        assertNotNull( tableNetworkFactory_.createTableNetwork() );
    }
}
