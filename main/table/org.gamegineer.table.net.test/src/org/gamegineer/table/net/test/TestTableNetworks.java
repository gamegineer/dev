/*
 * TestTableNetworks.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Nov 23, 2013 at 8:33:33 PM.
 */

package org.gamegineer.table.net.test;

import net.jcip.annotations.ThreadSafe;
import org.easymock.EasyMock;
import org.gamegineer.table.net.ITableNetwork;

/**
 * A factory for creating various types of table networks suitable for testing.
 */
@ThreadSafe
public final class TestTableNetworks
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TestTableNetworks} class.
     */
    private TestTableNetworks()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new table network.
     * 
     * @return A new table network.
     */
    public static ITableNetwork createTableNetwork()
    {
        return EasyMock.createMock( ITableNetwork.class );
    }
}
