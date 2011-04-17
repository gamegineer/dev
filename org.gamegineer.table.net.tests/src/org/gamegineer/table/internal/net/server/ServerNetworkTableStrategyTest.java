/*
 * ServerNetworkTableStrategyTest.java
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
 * Created on Apr 10, 2011 at 6:11:21 PM.
 */

package org.gamegineer.table.internal.net.server;

import org.easymock.EasyMock;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.net.NetworkTable;
import org.gamegineer.table.internal.net.transport.ITransportLayerFactory;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.server.ServerNetworkTableStrategy}
 * class.
 */
public final class ServerNetworkTableStrategyTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServerNetworkTableStrategyTest}
     * class.
     */
    public ServerNetworkTableStrategyTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * network table.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_NetworkTable_Null()
    {
        new ServerNetworkTableStrategy( null, EasyMock.createMock( ITransportLayerFactory.class ) );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * transport layer factory.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_TransportLayerFactory_Null()
    {
        new ServerNetworkTableStrategy( new NetworkTable( EasyMock.createMock( ITable.class ) ), null );
    }
}
