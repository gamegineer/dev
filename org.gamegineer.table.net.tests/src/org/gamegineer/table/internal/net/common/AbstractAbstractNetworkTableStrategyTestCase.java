/*
 * AbstractAbstractNetworkTableStrategyTestCase.java
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
 * Created on Apr 17, 2011 at 12:00:22 AM.
 */

package org.gamegineer.table.internal.net.common;

import static org.junit.Assert.assertTrue;
import org.gamegineer.table.internal.net.AbstractNetworkTableStrategyTestCase;
import org.gamegineer.table.internal.net.ITableGateway;
import org.gamegineer.table.internal.net.NetworkTableConfigurations;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.table.internal.net.common.AbstractNetworkTableStrategy}
 * class.
 * 
 * @param <T>
 *        The type of the network table strategy.
 */
public abstract class AbstractAbstractNetworkTableStrategyTestCase<T extends AbstractNetworkTableStrategy>
    extends AbstractNetworkTableStrategyTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractAbstractNetworkTableStrategyTestCase} class.
     */
    public AbstractAbstractNetworkTableStrategyTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code connect} method adds a table gateway for the local
     * player.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConnect_AddsLocalTableGateway()
        throws Exception
    {
        final INetworkTableConfiguration configuration = NetworkTableConfigurations.createDefaultNetworkTableConfiguration();
        getNetworkTableStrategy().connect( configuration );

        boolean localTableGatewayFound = false;
        for( final ITableGateway tableGateway : getNetworkTableStrategy().getTableGateways() )
        {
            if( tableGateway.getPlayerName().equals( configuration.getLocalPlayerName() ) )
            {
                localTableGatewayFound = true;
                break;
            }
        }

        assertTrue( localTableGatewayFound );
    }

    /**
     * Ensures the {@code tableGatewayAdded} method throws an exception when
     * passed a {@code null} table gateway.
     */
    @Test( expected = NullPointerException.class )
    public void testTableGatewayAdded_TableGateway_Null()
    {
        getNetworkTableStrategy().tableGatewayAdded( null );
    }

    /**
     * Ensures the {@code tableGatewayRemoved} method throws an exception when
     * passed a {@code null} table gateway.
     */
    @Test( expected = NullPointerException.class )
    public void testTableGatewayRemoved_TableGateway_Null()
    {
        getNetworkTableStrategy().tableGatewayRemoved( null );
    }
}
