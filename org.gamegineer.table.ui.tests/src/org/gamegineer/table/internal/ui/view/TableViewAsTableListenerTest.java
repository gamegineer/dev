/*
 * TableViewAsTableListenerTest.java
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
 * Created on Oct 17, 2009 at 11:18:09 PM.
 */

package org.gamegineer.table.internal.ui.view;

import org.gamegineer.table.core.AbstractTableListenerTestCase;
import org.gamegineer.table.core.ITableListener;
import org.gamegineer.table.internal.ui.model.TableModel;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.TableView} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.table.core.ITableListener} interface.
 */
public final class TableViewAsTableListenerTest
    extends AbstractTableListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableViewAsTableListenerTest}
     * class.
     */
    public TableViewAsTableListenerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractTableListenerTestCase#createTableListener()
     */
    @Override
    protected ITableListener createTableListener()
    {
        return new TableView( TableModel.createTableModel() );
    }
}
