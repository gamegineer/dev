/*
 * RemoteClientTableGatewayTest.java
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
 * Created on Apr 14, 2011 at 11:10:47 PM.
 */

package org.gamegineer.table.internal.net.server;

import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.server.RemoteClientTableGateway}
 * class.
 */
public final class RemoteClientTableGatewayTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The remote client table gateway under test in the fixture. */
    private RemoteClientTableGateway remoteClientTableGateway_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RemoteClientTableGatewayTest}
     * class.
     */
    public RemoteClientTableGatewayTest()
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
        remoteClientTableGateway_ = new RemoteClientTableGateway( EasyMock.createMock( ITableGatewayContext.class ) );
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
        remoteClientTableGateway_ = null;
    }

    /**
     * Ensures the {@code getPlayerName} method throws an exception when the
     * client has not yet authenticated.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetPlayerName_NotAuthenticated()
    {
        remoteClientTableGateway_.getPlayerName();
    }
}
