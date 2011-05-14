/*
 * AbstractTableNetworkNodeTest.java
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
 * Created on Apr 10, 2011 at 5:44:57 PM.
 */

package org.gamegineer.table.internal.net.common;

import static org.junit.Assert.assertEquals;
import java.util.Collection;
import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.ITableGateway;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.common.AbstractTableNetworkNode}
 * class.
 */
public final class AbstractTableNetworkNodeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network node under test in the fixture. */
    private AbstractTableNetworkNode node_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableNetworkNodeTest}
     * class.
     */
    public AbstractTableNetworkNodeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code AbstractTableNetworkNode} class with
     * stubbed implementations of all abstract methods.
     * 
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * 
     * @return A new instance of the {@code AbstractTableNetworkNode} class;
     *         never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetworkController} is {@code null}.
     */
    /* @NonNull */
    private static AbstractTableNetworkNode createTableNetworkNode(
        /* @NonNull */
        final ITableNetworkController tableNetworkController )
    {
        return new AbstractTableNetworkNode( tableNetworkController )
        {
            @Override
            protected ITransportLayer createTransportLayer()
            {
                return null;
            }
        };
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
        node_ = createTableNetworkNode( EasyMock.createMock( ITableNetworkController.class ) );
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
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table network controller.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_TableNetworkController_Null()
    {
        createTableNetworkNode( null );
    }

    /**
     * Ensures the {@code getLocalPlayerName} method throws an exception when
     * the table network node is disconnected.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetLocalPlayerName_Disconnected()
    {
        synchronized( node_.getLock() )
        {
            node_.getLocalPlayerName();
        }
    }

    /**
     * Ensures the {@code getPassword} method throws an exception when the table
     * network node is disconnected.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetPassword_Disconnected()
    {
        synchronized( node_.getLock() )
        {
            node_.getPassword();
        }
    }

    /**
     * Ensures the {@code getTableGateways} method returns a copy of the
     * registered table gateways collection.
     */
    @Test
    public void testGetTableGateways_ReturnValue_Copy()
    {
        final Collection<ITableGateway> tableGateways = node_.getTableGateways();
        final int expectedTableGatewaysSize = tableGateways.size();
        tableGateways.add( EasyMock.createMock( ITableGateway.class ) );

        final int actualTableGatewaysSize = node_.getTableGateways().size();

        assertEquals( expectedTableGatewaysSize, actualTableGatewaysSize );
    }
}
