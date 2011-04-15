/*
 * AbstractTableGatewayContextTestCase.java
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
 * Created on Apr 14, 2011 at 11:24:04 PM.
 */

package org.gamegineer.table.internal.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.gamegineer.common.core.security.SecureString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.ITableGatewayContext} interface.
 */
public abstract class AbstractTableGatewayContextTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table gateway context under test in the fixture. */
    private ITableGatewayContext tableGatewayContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractTableGatewayContextTestCase} class.
     */
    protected AbstractTableGatewayContextTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table gateway context to be tested.
     * 
     * @return The table gateway context to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITableGatewayContext createTableGatewayContext()
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
        tableGatewayContext_ = createTableGatewayContext();
        assertNotNull( tableGatewayContext_ );
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
        tableGatewayContext_ = null;
    }

    /**
     * Ensures the {@code getPassword} method returns a copy of the password.
     */
    @Test
    public void testGetPassword_ReturnValue_Copy()
    {
        final SecureString password = tableGatewayContext_.getPassword();
        final SecureString expectedValue = new SecureString( password );
        password.dispose();

        final SecureString actualValue = tableGatewayContext_.getPassword();

        assertEquals( expectedValue, actualValue );
    }

    /**
     * Ensures the {@code playerConnected} method throws an exception when
     * passed a {@code null} player name.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testPlayerConnected_PlayerName_Null()
        throws Exception
    {
        tableGatewayContext_.playerConnected( null, EasyMock.createMock( ITableGateway.class ) );
    }

    /**
     * Ensures the {@code playerConnected} method throws an exception when
     * passed a {@code null} table gateway.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testPlayerConnected_TableGateway_Null()
        throws Exception
    {
        tableGatewayContext_.playerConnected( "playerName", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code playerDisconnected} method throws an exception when
     * passed a {@code null} player name.
     */
    @Test( expected = NullPointerException.class )
    public void testPlayerDisconnected_PlayerName_Null()
    {
        tableGatewayContext_.playerDisconnected( null );
    }
}
