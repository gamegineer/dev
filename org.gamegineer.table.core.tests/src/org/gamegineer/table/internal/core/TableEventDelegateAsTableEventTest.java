/*
 * TableEventDelegateAsTableEventTest.java
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
 * Created on Oct 16, 2009 at 10:57:03 PM.
 */

package org.gamegineer.table.internal.core;

import static org.easymock.EasyMock.createMock;
import org.gamegineer.table.core.AbstractTableEventTestCase;
import org.gamegineer.table.core.ITable;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.TableEventDelegate} class to ensure
 * it does not violate the contract of the
 * {@link org.gamegineer.table.core.ITableEvent} interface.
 */
public final class TableEventDelegateAsTableEventTest
    extends AbstractTableEventTestCase<TableEventDelegate>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * TableEventDelegateAsTableEventTest} class.
     */
    public TableEventDelegateAsTableEventTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractTableEventTestCase#createTableEvent()
     */
    @Override
    protected TableEventDelegate createTableEvent()
    {
        return new TableEventDelegate( createMock( ITable.class ) );
    }
}
