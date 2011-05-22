/*
 * TableNetworkAsTableNetworkControllerTest.java
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
 * Created on May 14, 2011 at 2:43:52 PM.
 */

package org.gamegineer.table.internal.net;

import org.easymock.EasyMock;
import org.gamegineer.table.core.ITable;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.TableNetwork} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.ITableNetworkController} interface.
 */
public final class TableNetworkAsTableNetworkControllerTest
    extends AbstractTableNetworkControllerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * TableNetworkAsTableNetworkControllerTest} class.
     */
    public TableNetworkAsTableNetworkControllerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.AbstractTableNetworkControllerTestCase#createTableNetworkController()
     */
    @Override
    protected ITableNetworkController createTableNetworkController()
    {
        return new TableNetwork( EasyMock.createMock( ITable.class ) );
    }
}