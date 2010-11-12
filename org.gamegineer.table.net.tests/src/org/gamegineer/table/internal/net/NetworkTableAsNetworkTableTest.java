/*
 * NetworkTableAsNetworkTableTest.java
 * Copyright 2008-2010 Gamegineer.org
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
import org.gamegineer.table.net.AbstractNetworkTableTestCase;
import org.gamegineer.table.net.INetworkTable;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.NetworkTable} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.table.net.INetworkTable} interface.
 */
public final class NetworkTableAsNetworkTableTest
    extends AbstractNetworkTableTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableAsNetworkTableTest}
     * class.
     */
    public NetworkTableAsNetworkTableTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.AbstractNetworkTableTestCase#createNetworkTable(org.gamegineer.table.core.ITable)
     */
    @Override
    protected INetworkTable createNetworkTable(
        final ITable table )
    {
        return new NetworkTable( table );
    }
}
