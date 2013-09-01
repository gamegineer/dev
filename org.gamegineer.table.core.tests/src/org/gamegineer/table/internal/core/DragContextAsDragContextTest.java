/*
 * DragContextAsDragContextTest.java
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
 * Created on Feb 22, 2013 at 10:09:20 PM.
 */

package org.gamegineer.table.internal.core;

import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.dnd.AbstractDragContextTestCase;
import org.gamegineer.table.core.dnd.IDragContext;

/**
 * A fixture for testing the {@link DragContext} class to ensure it does not
 * violate the contract of the {@link IDragContext} interface.
 */
public final class DragContextAsDragContextTest
    extends AbstractDragContextTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table associated with the fixture. */
    private Table table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DragContextAsDragContextTest}
     * class.
     */
    public DragContextAsDragContextTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.AbstractDragContextTestCase#getTable()
     */
    @Override
    protected ITable getTable()
    {
        return table_;
    }

    /*
     * @see org.gamegineer.table.core.AbstractDragContextTestCase#setUp()
     */
    @Override
    public void setUp()
        throws Exception
    {
        table_ = new Table( new TableEnvironment( new SingleThreadedTableEnvironmentContext() ) );

        super.setUp();
    }
}
