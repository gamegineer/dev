/*
 * TableNetworkConfigurationAsTableNetworkConfigurationTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Nov 16, 2013 at 10:29:37 PM.
 */

package org.gamegineer.table.internal.net;

import org.easymock.EasyMock;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.test.AbstractTableNetworkConfigurationTestCase;

/**
 * A fixture for testing the {@link TableNetworkConfiguration} class to ensure
 * it does not violate the contract of the {@link ITableNetworkConfiguration}
 * interface.
 */
public final class TableNetworkConfigurationAsTableNetworkConfigurationTest
    extends AbstractTableNetworkConfigurationTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code TableNetworkConfigurationAsTableNetworkConfigurationTest} class.
     */
    public TableNetworkConfigurationAsTableNetworkConfigurationTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.test.AbstractTableNetworkConfigurationTestCase#createTableNetworkConfiguration()
     */
    @Override
    protected ITableNetworkConfiguration createTableNetworkConfiguration()
    {
        return new TableNetworkConfiguration( "hostName", 0, new SecureString( "password".toCharArray() ), "localPlayerName", EasyMock.createMock( ITable.class ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
}
