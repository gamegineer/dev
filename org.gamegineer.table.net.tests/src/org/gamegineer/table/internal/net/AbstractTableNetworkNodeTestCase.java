/*
 * AbstractTableNetworkNodeTestCase.java
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.ITableNetworkNode} interface.
 */
public abstract class AbstractTableNetworkNodeTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The table network node under test in the fixture. */
    private ITableNetworkNode node_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractTableNetworkNodeTestCase} class.
     */
    protected AbstractTableNetworkNodeTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table network node to be tested.
     * 
     * @return The table network node to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITableNetworkNode createTableNetworkNode()
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
        node_ = createTableNetworkNode();
        assertNotNull( node_ );
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
        node_ = null;
        mocksControl_ = null;
    }

    /**
     * Ensures the {@code addTableProxy} method adds the table proxy when the
     * table proxy is absent from the registered table proxies collection.
     */
    @Test
    public void testAddTableProxy_TableProxy_Absent()
    {
        synchronized( node_.getLock() )
        {
            final ITableProxy tableProxy = mocksControl_.createMock( ITableProxy.class );
            EasyMock.expect( tableProxy.getPlayerName() ).andReturn( "newPlayerName" ).anyTimes(); //$NON-NLS-1$
            mocksControl_.replay();
            assertFalse( node_.isTableProxyPresent( tableProxy.getPlayerName() ) );

            node_.addTableProxy( tableProxy );

            assertTrue( node_.isTableProxyPresent( tableProxy.getPlayerName() ) );
        }
    }

    /**
     * Ensures the {@code addTableProxy} method throws an exception when passed
     * a {@code null} table proxy.
     */
    @Test( expected = NullPointerException.class )
    public void testAddTableProxy_TableProxy_Null()
    {
        synchronized( node_.getLock() )
        {
            node_.addTableProxy( null );
        }
    }

    /**
     * Ensures the {@code addTableProxy} method throws an exception when the
     * table proxy is present in the registered table proxies collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAddTableProxy_TableProxy_Present()
    {
        synchronized( node_.getLock() )
        {
            final ITableProxy tableProxy = mocksControl_.createMock( ITableProxy.class );
            EasyMock.expect( tableProxy.getPlayerName() ).andReturn( "newPlayerName" ).anyTimes(); //$NON-NLS-1$
            mocksControl_.replay();
            node_.addTableProxy( tableProxy );

            node_.addTableProxy( tableProxy );
        }
    }

    /**
     * Ensures the {@code getLocalPlayerName} method does not return {@code
     * null}.
     */
    @Test
    public void testGetLocalPlayerName_ReturnValue_NonNull()
    {
        synchronized( node_.getLock() )
        {
            assertNotNull( node_.getLocalPlayerName() );
        }
    }

    /**
     * Ensures the {@code getPassword} method returns a copy of the password.
     */
    @Test
    public void testGetPassword_ReturnValue_Copy()
    {
        synchronized( node_.getLock() )
        {
            final SecureString password = node_.getPassword();
            final SecureString expectedValue = new SecureString( password );
            password.dispose();

            final SecureString actualValue = node_.getPassword();

            assertEquals( expectedValue, actualValue );
        }
    }

    /**
     * Ensures the {@code getPassword} method does not return {@code null}.
     */
    @Test
    public void testGetPassword_ReturnValue_NonNull()
    {
        synchronized( node_.getLock() )
        {
            assertNotNull( node_.getPassword() );
        }
    }

    /**
     * Ensures the {@code isTableProxyPresent} method throws an exception when
     * passed a {@code null} player name.
     */
    @Test( expected = NullPointerException.class )
    public void testIsTableProxyPresent_PlayerName_Null()
    {
        synchronized( node_.getLock() )
        {
            node_.isTableProxyPresent( null );
        }
    }

    /**
     * Ensures the {@code removeTableProxy} method throws an exception when the
     * table proxy is absent from the registered table proxies collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRemoveTableProxy_TableProxy_Absent()
        throws Exception
    {
        synchronized( node_.getLock() )
        {
            final ITableProxy tableProxy = mocksControl_.createMock( ITableProxy.class );
            EasyMock.expect( tableProxy.getPlayerName() ).andReturn( "newPlayerName" ).anyTimes(); //$NON-NLS-1$
            mocksControl_.replay();

            node_.removeTableProxy( tableProxy );
        }
    }

    /**
     * Ensures the {@code removeTableProxy} method throws an exception when
     * passed a {@code null} table proxy.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoveTableProxy_TableProxy_Null()
    {
        synchronized( node_.getLock() )
        {
            node_.removeTableProxy( null );
        }
    }

    /**
     * Ensures the {@code removeTableProxy} method removes the table proxy when
     * the table proxy is present in the registered table proxies collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testRemoveTableProxy_TableProxy_Present()
        throws Exception
    {
        synchronized( node_.getLock() )
        {
            final ITableProxy tableProxy = mocksControl_.createMock( ITableProxy.class );
            EasyMock.expect( tableProxy.getPlayerName() ).andReturn( "newPlayerName" ).anyTimes(); //$NON-NLS-1$
            mocksControl_.replay();
            node_.addTableProxy( tableProxy );
            assertTrue( node_.isTableProxyPresent( tableProxy.getPlayerName() ) );

            node_.removeTableProxy( tableProxy );

            assertFalse( node_.isTableProxyPresent( tableProxy.getPlayerName() ) );
        }
    }
}
