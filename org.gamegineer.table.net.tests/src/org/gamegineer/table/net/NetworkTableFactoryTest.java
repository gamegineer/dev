/*
 * NetworkTableFactoryTest.java
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
 * Created on Nov 11, 2010 at 10:51:35 PM.
 */

package org.gamegineer.table.net;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.gamegineer.table.core.ITable;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.net.NetworkTableFactory} class.
 */
public final class NetworkTableFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableFactoryTest} class.
     */
    public NetworkTableFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createNetworkTable} method does not return {@code
     * null}.
     */
    @Test
    public void testCreateNetworkTable_ReturnValue_NonNull()
    {
        assertNotNull( NetworkTableFactory.createNetworkTable( EasyMock.createMock( ITable.class ) ) );
    }

    /**
     * Ensures the {@code createNetworkTable} method throws an exception when
     * passed a {@code null} table.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateNetworkTable_Table_Null()
    {
        NetworkTableFactory.createNetworkTable( null );
    }
}
