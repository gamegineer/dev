/*
 * ServerNetworkTableStrategyAsNetworkTableStrategyTest.java
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
 * Created on Apr 10, 2011 at 6:20:52 PM.
 */

package org.gamegineer.table.internal.net.server;

import org.easymock.EasyMock;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.net.AbstractNetworkTableStrategyTestCase;
import org.gamegineer.table.internal.net.INetworkTableStrategy;
import org.gamegineer.table.internal.net.NetworkTable;
import org.gamegineer.table.internal.net.transport.ITransportLayerFactory;
import org.gamegineer.table.internal.net.transport.fake.FakeTransportLayerFactory;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.server.ServerNetworkTableStrategy}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.INetworkTableStrategy} interface.
 */
public final class ServerNetworkTableStrategyAsNetworkTableStrategyTest
    extends AbstractNetworkTableStrategyTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * ServerNetworkTableStrategyAsNetworkTableStrategyTest} class.
     */
    public ServerNetworkTableStrategyAsNetworkTableStrategyTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkTableStrategyTestCase#createNetworkTableStrategy()
     */
    @Override
    protected INetworkTableStrategy createNetworkTableStrategy()
    {
        final ITransportLayerFactory transportLayerFactory = new FakeTransportLayerFactory();
        final NetworkTable networkTable = new NetworkTable( EasyMock.createMock( ITable.class ), transportLayerFactory );
        return new ServerNetworkTableStrategy( networkTable, transportLayerFactory );
    }
}
