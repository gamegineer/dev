/*
 * AbstractNetworkTableStrategyTest.java
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
import org.gamegineer.table.internal.net.INetworkTableStrategyContext;
import org.gamegineer.table.internal.net.ITableGateway;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.common.AbstractNetworkTableStrategy}
 * class.
 */
public final class AbstractNetworkTableStrategyTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The network table strategy under test in the fixture. */
    private AbstractNetworkTableStrategy networkTableStrategy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractNetworkTableStrategyTest} class.
     */
    public AbstractNetworkTableStrategyTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code AbstractNetworkTableStrategy} class
     * with stubbed implementations of all abstract methods.
     * 
     * @param context
     *        The network table strategy context; must not be {@code null}.
     * 
     * @return A new instance of the {@code AbstractNetworkTableStrategy} class;
     *         never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    /* @NonNull */
    private static AbstractNetworkTableStrategy createNetworkTableStrategy(
        /* @NonNull */
        final INetworkTableStrategyContext context )
    {
        return new AbstractNetworkTableStrategy( context )
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
        networkTableStrategy_ = createNetworkTableStrategy( EasyMock.createMock( INetworkTableStrategyContext.class ) );
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
        networkTableStrategy_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * context.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Context_Null()
    {
        createNetworkTableStrategy( null );
    }

    /**
     * Ensures the {@code getLocalPlayerName} method throws an exception when
     * the network table strategy is disconnected.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetLocalPlayerName_Disconnected()
    {
        networkTableStrategy_.getLocalPlayerName();
    }

    /**
     * Ensures the {@code getPassword} method throws an exception when the
     * network table strategy is disconnected.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetPassword_Disconnected()
    {
        networkTableStrategy_.getPassword();
    }

    /**
     * Ensures the {@code getTableGateways} method returns a copy of the
     * registered table gateways collection.
     */
    @Test
    public void testGetTableGateways_ReturnValue_Copy()
    {
        final Collection<ITableGateway> tableGateways = networkTableStrategy_.getTableGateways();
        final int expectedTableGatewaysSize = tableGateways.size();
        tableGateways.add( EasyMock.createMock( ITableGateway.class ) );

        final int actualTableGatewaysSize = networkTableStrategy_.getTableGateways().size();

        assertEquals( expectedTableGatewaysSize, actualTableGatewaysSize );
    }
}
