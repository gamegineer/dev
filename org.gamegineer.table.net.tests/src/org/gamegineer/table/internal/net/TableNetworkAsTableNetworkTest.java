/*
 * TableNetworkAsTableNetworkTest.java
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
 * Created on Nov 9, 2010 at 10:58:36 PM.
 */

package org.gamegineer.table.internal.net;

import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.net.client.ClientTableNetworkStrategy;
import org.gamegineer.table.internal.net.transport.fake.FakeTransportLayerFactory;
import org.gamegineer.table.net.AbstractTableNetworkTestCase;
import org.gamegineer.table.net.ITableNetwork;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.TableNetwork} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.table.net.ITableNetwork} interface.
 */
public final class TableNetworkAsTableNetworkTest
    extends AbstractTableNetworkTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkAsTableNetworkTest}
     * class.
     */
    public TableNetworkAsTableNetworkTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.AbstractTableNetworkTestCase#createTableNetwork(org.gamegineer.table.core.ITable)
     */
    @Override
    protected ITableNetwork createTableNetwork(
        final ITable table )
    {
        return new TableNetwork( //
            table, //
            new DefaultTableNetworkStrategyFactory()
            {
                @Override
                public ITableNetworkStrategy createClientTableNetworkStrategy(
                    final ITableNetworkStrategyContext context )
                {
                    return new ClientTableNetworkStrategy( context, false );
                }
            }, //
            new FakeTransportLayerFactory() );
    }
}
