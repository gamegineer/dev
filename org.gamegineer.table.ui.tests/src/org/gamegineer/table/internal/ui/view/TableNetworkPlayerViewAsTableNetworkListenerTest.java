/*
 * TableNetworkPlayerViewAsTableNetworkListenerTest.java
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
 * Created on May 21, 2011 at 10:27:26 PM.
 */

package org.gamegineer.table.internal.ui.view;

import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.net.AbstractTableNetworkListenerTestCase;
import org.gamegineer.table.net.ITableNetworkListener;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.TableNetworkPlayerView} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.net.ITableNetworkListener} interface.
 */
public final class TableNetworkPlayerViewAsTableNetworkListenerTest
    extends AbstractTableNetworkListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * TableNetworkPlayerViewAsTableNetworkListenerTest} class.
     */
    public TableNetworkPlayerViewAsTableNetworkListenerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.AbstractTableNetworkListenerTestCase#createTableNetworkListener()
     */
    @Override
    protected ITableNetworkListener createTableNetworkListener()
    {
        return new TableNetworkPlayerView( TableModel.createTableModel() );
    }
}
