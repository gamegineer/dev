/*
 * ClientNetworkTableStrategyAsTableGatewayContextTest.java
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
 * Created on Apr 14, 2011 at 11:29:02 PM.
 */

package org.gamegineer.table.internal.net.client;

import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.NetworkTableConfigurations;
import org.gamegineer.table.internal.net.NetworkTableStrategyContexts;
import org.gamegineer.table.internal.net.common.AbstractAbstractNetworkTableStrategyAsTableGatewayContextTestCase;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.client.ClientNetworkTableStrategy}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.ITableGatewayContext} interface.
 */
public final class ClientNetworkTableStrategyAsTableGatewayContextTest
    extends AbstractAbstractNetworkTableStrategyAsTableGatewayContextTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * ClientNetworkTableStrategyAsTableGatewayContextTest} class.
     */
    public ClientNetworkTableStrategyAsTableGatewayContextTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.AbstractTableGatewayContextTestCase#createTableGatewayContext()
     */
    @Override
    protected ITableGatewayContext createTableGatewayContext()
        throws Exception
    {
        final ClientNetworkTableStrategy strategy = new ClientNetworkTableStrategy( NetworkTableStrategyContexts.createFakeNetworkTableStrategyContext(), false );
        strategy.connect( NetworkTableConfigurations.createDefaultNetworkTableConfiguration() );
        return strategy;
    }
}
