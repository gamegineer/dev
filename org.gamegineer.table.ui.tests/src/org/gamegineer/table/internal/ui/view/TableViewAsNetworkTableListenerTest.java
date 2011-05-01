/*
 * TableViewAsNetworkTableListenerTest.java
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
 * Created on May 1, 2011 at 4:46:41 PM.
 */

package org.gamegineer.table.internal.ui.view;

import org.gamegineer.table.internal.ui.model.TableModel;
import org.gamegineer.table.net.AbstractNetworkTableListenerTestCase;
import org.gamegineer.table.net.INetworkTableListener;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.TableView} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.table.net.INetworkTableListener} interface.
 */
public final class TableViewAsNetworkTableListenerTest
    extends AbstractNetworkTableListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * TableViewAsNetworkTableListenerTest} class.
     */
    public TableViewAsNetworkTableListenerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.AbstractNetworkTableListenerTestCase#createNetworkTableListener()
     */
    @Override
    protected INetworkTableListener createNetworkTableListener()
    {
        return new TableView( TableModel.createTableModel() );
    }
}
