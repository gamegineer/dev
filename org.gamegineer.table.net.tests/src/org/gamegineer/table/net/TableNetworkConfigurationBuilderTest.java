/*
 * TableNetworkConfigurationBuilderTest.java
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
 * Created on Nov 12, 2010 at 10:13:50 PM.
 */

package org.gamegineer.table.net;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.net.TableNetworkConfigurationBuilder} class.
 */
public final class TableNetworkConfigurationBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network configuration builder under test in the fixture. */
    private TableNetworkConfigurationBuilder builder_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * TableNetworkConfigurationBuilderTest} class.
     */
    public TableNetworkConfigurationBuilderTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        builder_ = new TableNetworkConfigurationBuilder();
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
        builder_ = null;
    }

    /**
     * Ensures the {@code setHostName} method throws an exception when passed a
     * {@code null} host name.
     */
    @Test( expected = NullPointerException.class )
    public void testSetHostName_HostName_Null()
    {
        builder_.setHostName( null );
    }

    /**
     * Ensures the {@code setLocalPlayerName} method throws an exception when
     * passed a {@code null} local player name.
     */
    @Test( expected = NullPointerException.class )
    public void testSetLocalPlayerName_LocalPlayerName_Null()
    {
        builder_.setLocalPlayerName( null );
    }

    /**
     * Ensures the {@code setLocalTable} method throws an exception when passed
     * a {@code null} local table.
     */
    @Test( expected = NullPointerException.class )
    public void testSetLocalTable_LocalTable_Null()
    {
        builder_.setLocalTable( null );
    }

    /**
     * Ensures the {@code setPassword} method throws an exception when passed a
     * {@code null} password.
     */
    @Test( expected = NullPointerException.class )
    public void testPassword_Password_Null()
    {
        builder_.setPassword( null );
    }
}
