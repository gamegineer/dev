/*
 * AbstractTableNetworkConfigurationTestCase.java
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
 * Created on Nov 12, 2010 at 9:51:05 PM.
 */

package org.gamegineer.table.net;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITableNetworkConfiguration} interface.
 */
public abstract class AbstractTableNetworkConfigurationTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network configuration under test in the fixture. */
    private ITableNetworkConfiguration configuration_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractTableNetworkConfigurationTestCase} class.
     */
    protected AbstractTableNetworkConfigurationTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table network configuration to be tested.
     * 
     * @return The table network configuration to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITableNetworkConfiguration createTableNetworkConfiguration()
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
        configuration_ = createTableNetworkConfiguration();
        assertNotNull( configuration_ );
    }

    /**
     * Ensures the {@link ITableNetworkConfiguration#getHostName} method does
     * not return {@code null}.
     */
    @Test
    public void testGetHostName_ReturnValue_NonNull()
    {
        assertNotNull( configuration_.getHostName() );
    }

    /**
     * Ensures the {@link ITableNetworkConfiguration#getLocalPlayerName} method
     * does not return {@code null}.
     */
    @Test
    public void testGetLocalPlayerName_ReturnValue_NonNull()
    {
        assertNotNull( configuration_.getLocalPlayerName() );
    }

    /**
     * Ensures the {@link ITableNetworkConfiguration#getLocalTable} method does
     * not return {@code null}.
     */
    @Test
    public void testGetLocalTable_ReturnValue_NonNull()
    {
        assertNotNull( configuration_.getLocalTable() );
    }

    /**
     * Ensures the {@link ITableNetworkConfiguration#getPassword} method does
     * not return {@code null}.
     */
    @Test
    public void testGetPassword_ReturnValue_NonNull()
    {
        assertNotNull( configuration_.getPassword() );
    }
}