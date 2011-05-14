/*
 * AbstractTableNetworkStrategyTest.java
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
 * {@link org.gamegineer.table.internal.net.common.AbstractTableNetworkStrategy}
 * class.
 */
public final class AbstractTableNetworkStrategyTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network strategy under test in the fixture. */
    private AbstractTableNetworkStrategy strategy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractTableNetworkStrategyTest} class.
     */
    public AbstractTableNetworkStrategyTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code AbstractTableNetworkStrategy} class
     * with stubbed implementations of all abstract methods.
     * 
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * 
     * @return A new instance of the {@code AbstractTableNetworkStrategy} class;
     *         never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetworkController} is {@code null}.
     */
    /* @NonNull */
    private static AbstractTableNetworkStrategy createTableNetworkStrategy(
        /* @NonNull */
        final ITableNetworkController tableNetworkController )
    {
        return new AbstractTableNetworkStrategy( tableNetworkController )
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
        strategy_ = createTableNetworkStrategy( EasyMock.createMock( ITableNetworkController.class ) );
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
        strategy_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table network controller.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_TableNetworkController_Null()
    {
        createTableNetworkStrategy( null );
    }

    /**
     * Ensures the {@code getLocalPlayerName} method throws an exception when
     * the table network strategy is disconnected.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetLocalPlayerName_Disconnected()
    {
        synchronized( strategy_.getLock() )
        {
            strategy_.getLocalPlayerName();
        }
    }

    /**
     * Ensures the {@code getPassword} method throws an exception when the table
     * network strategy is disconnected.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetPassword_Disconnected()
    {
        synchronized( strategy_.getLock() )
        {
            strategy_.getPassword();
        }
    }

    /**
     * Ensures the {@code getTableGateways} method returns a copy of the
     * registered table gateways collection.
     */
    @Test
    public void testGetTableGateways_ReturnValue_Copy()
    {
        final Collection<ITableGateway> tableGateways = strategy_.getTableGateways();
        final int expectedTableGatewaysSize = tableGateways.size();
        tableGateways.add( EasyMock.createMock( ITableGateway.class ) );

        final int actualTableGatewaysSize = strategy_.getTableGateways().size();

        assertEquals( expectedTableGatewaysSize, actualTableGatewaysSize );
    }
}
