/*
 * TableNetworkListenerAsTableNetworkListenerTest.java
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
 * Created on Aug 3, 2011 at 8:55:13 PM.
 */

package org.gamegineer.table.net;

import org.gamegineer.table.net.test.AbstractTableNetworkListenerTestCase;

/**
 * A fixture for testing the {@link TableNetworkListener} class to ensure it
 * does not violate the contract of the {@link ITableNetworkListener} interface.
 */
public final class TableNetworkListenerAsTableNetworkListenerTest
    extends AbstractTableNetworkListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code TableNetworkListenerAsTableNetworkListenerTest} class.
     */
    public TableNetworkListenerAsTableNetworkListenerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.test.AbstractTableNetworkListenerTestCase#createTableNetworkListener()
     */
    @Override
    protected ITableNetworkListener createTableNetworkListener()
    {
        return new TableNetworkListener();
    }
}
