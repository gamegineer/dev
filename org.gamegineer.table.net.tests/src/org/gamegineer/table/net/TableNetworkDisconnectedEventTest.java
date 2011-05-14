/*
 * TableNetworkDisconnectedEventTest.java
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
 * Created on Apr 30, 2011 at 10:15:35 PM.
 */

package org.gamegineer.table.net;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.net.TableNetworkDisconnectedEvent} class.
 */
public final class TableNetworkDisconnectedEventTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * TableNetworkDisconnectedEventTest} class.
     */
    public TableNetworkDisconnectedEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table network.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_TableNetwork_Null()
    {
        new TableNetworkDisconnectedEvent( null, TableNetworkError.UNSPECIFIED_ERROR );
    }
}
