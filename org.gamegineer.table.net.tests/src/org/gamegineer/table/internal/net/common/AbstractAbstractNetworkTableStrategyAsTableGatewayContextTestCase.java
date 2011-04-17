/*
 * AbstractAbstractNetworkTableStrategyAsTableGatewayContextTestCase.java
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
 * Created on Apr 16, 2011 at 10:51:35 PM.
 */

package org.gamegineer.table.internal.net.common;

import org.gamegineer.table.internal.net.AbstractTableGatewayContextTestCase;
import org.gamegineer.table.internal.net.ITableGateway;
import org.gamegineer.table.internal.net.ITableGatewayContext;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.table.internal.net.common.AbstractNetworkTableStrategy}
 * class.
 */
public abstract class AbstractAbstractNetworkTableStrategyAsTableGatewayContextTestCase
    extends AbstractTableGatewayContextTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractAbstractNetworkTableStrategyAsTableGatewayContextTestCase} class.
     */
    protected AbstractAbstractNetworkTableStrategyAsTableGatewayContextTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.AbstractTableGatewayContextTestCase#containsTableGateway(org.gamegineer.table.internal.net.ITableGatewayContext, org.gamegineer.table.internal.net.ITableGateway)
     */
    @Override
    protected final boolean containsTableGateway(
        final ITableGatewayContext tableGatewayContext,
        final ITableGateway tableGateway )
    {
        return ((AbstractNetworkTableStrategy)tableGatewayContext).getTableGateways().contains( tableGateway );
    }
}
