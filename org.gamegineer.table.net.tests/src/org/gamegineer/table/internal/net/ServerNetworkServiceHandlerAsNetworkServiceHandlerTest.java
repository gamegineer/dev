/*
 * ServerNetworkServiceHandlerAsNetworkServiceHandlerTest.java
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
 * Created on Apr 1, 2011 at 9:34:49 PM.
 */

package org.gamegineer.table.internal.net;

import org.easymock.EasyMock;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.net.transport.AbstractNetworkServiceHandlerTestCase;
import org.gamegineer.table.internal.net.transport.INetworkInterfaceFactory;
import org.gamegineer.table.internal.net.transport.INetworkServiceHandler;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.ServerNetworkServiceHandler} class
 * to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.transport.INetworkServiceHandler}
 * interface.
 */
public final class ServerNetworkServiceHandlerAsNetworkServiceHandlerTest
    extends AbstractNetworkServiceHandlerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * ServerNetworkServiceHandlerAsNetworkServiceHandlerTest} class.
     */
    public ServerNetworkServiceHandlerAsNetworkServiceHandlerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkServiceHandlerTestCase#createNetworkServiceHandler()
     */
    @Override
    protected INetworkServiceHandler createNetworkServiceHandler()
    {
        return new ServerNetworkServiceHandler( new NetworkTable( EasyMock.createMock( ITable.class ), EasyMock.createMock( INetworkInterfaceFactory.class ) ) );
    }
}
