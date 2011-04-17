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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.net.NetworkTableException;
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

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

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
     * Indicates the specified table gateway context contains the specified
     * table gateway.
     * 
     * @param tableGatewayContext
     *        The table gateway context under test in the fixture; must not be
     *        {@code null}.
     * @param tableGateway
     *        The table gateway; must not be {@code null}.
     * 
     * @return {@code true} if the specified table gateway context contains the
     *         specified table gateway; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableGatewayContext} or {@code tableGateway} is {@code
     *         null}.
     */
    protected abstract boolean containsTableGateway(
        /* @NonNull */
        ITableGatewayContext tableGatewayContext,
        /* @NonNull */
        ITableGateway tableGateway );

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
        mocksControl_ = EasyMock.createControl();
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
        mocksControl_ = null;
    }

    /**
     * Ensures the {@code addTableGateway} method adds the table gateway when
     * the table gateway is absent from the registered table gateways
     * collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testAddTableGateway_TableGateway_Absent()
        throws Exception
    {
        final ITableGateway tableGateway = mocksControl_.createMock( ITableGateway.class );
        EasyMock.expect( tableGateway.getPlayerName() ).andReturn( "newPlayerName" ).anyTimes(); //$NON-NLS-1$
        mocksControl_.replay();
        assertFalse( containsTableGateway( tableGatewayContext_, tableGateway ) );

        tableGatewayContext_.addTableGateway( tableGateway );

        assertTrue( containsTableGateway( tableGatewayContext_, tableGateway ) );
    }

    /**
     * Ensures the {@code addTableGateway} method throws an exception when
     * passed a {@code null} table gateway.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testAddTableGateway_TableGateway_Null()
        throws Exception
    {
        tableGatewayContext_.addTableGateway( null );
    }

    /**
     * Ensures the {@code addTableGateway} method throws an exception when the
     * table gateway is present in the registered table gateways collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NetworkTableException.class )
    public void testAddTableGateway_TableGateway_Present()
        throws Exception
    {
        final ITableGateway tableGateway = mocksControl_.createMock( ITableGateway.class );
        EasyMock.expect( tableGateway.getPlayerName() ).andReturn( "newPlayerName" ).anyTimes(); //$NON-NLS-1$
        mocksControl_.replay();
        tableGatewayContext_.addTableGateway( tableGateway );

        tableGatewayContext_.addTableGateway( tableGateway );
    }

    /**
     * Ensures the {@code getLocalPlayerName} method does not return {@code
     * null}.
     */
    @Test
    public void testGetLocalPlayerName_ReturnValue_NonNull()
    {
        assertNotNull( tableGatewayContext_.getLocalPlayerName() );
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
     * Ensures the {@code getPassword} method does not return {@code null}.
     */
    @Test
    public void testGetPassword_ReturnValue_NonNull()
    {
        assertNotNull( tableGatewayContext_.getPassword() );
    }

    /**
     * Ensures the {@code removeTableGateway} method throws an exception when
     * the table gateway is absent from the registered table gateways
     * collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveTableGateway_TableGateway_Absent()
        throws Exception
    {
        final ITableGateway tableGateway = mocksControl_.createMock( ITableGateway.class );
        EasyMock.expect( tableGateway.getPlayerName() ).andReturn( "newPlayerName" ).anyTimes(); //$NON-NLS-1$
        mocksControl_.replay();

        tableGatewayContext_.removeTableGateway( tableGateway );
    }

    /**
     * Ensures the {@code removeTableGateway} method throws an exception when
     * passed a {@code null} table gateway.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveTableGateway_TableGateway_Null()
    {
        tableGatewayContext_.removeTableGateway( null );
    }

    /**
     * Ensures the {@code removeTableGateway} method removes the table gateway
     * when the table gateway is present in the registered table gateways
     * collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveTableGateway_TableGateway_Present()
        throws Exception
    {
        final ITableGateway tableGateway = mocksControl_.createMock( ITableGateway.class );
        EasyMock.expect( tableGateway.getPlayerName() ).andReturn( "newPlayerName" ).anyTimes(); //$NON-NLS-1$
        mocksControl_.replay();
        tableGatewayContext_.addTableGateway( tableGateway );
        assertTrue( containsTableGateway( tableGatewayContext_, tableGateway ) );

        tableGatewayContext_.removeTableGateway( tableGateway );

        assertFalse( containsTableGateway( tableGatewayContext_, tableGateway ) );
    }
}
