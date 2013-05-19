/*
 * DragContextAsDragContextTest.java
 * Copyright 2008-2013 Gamegineer.org
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
import org.gamegineer.table.core.dnd.AbstractDragContextTestCase;
import org.junit.After;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.core.DragContext} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.table.core.dnd.IDragContext} interface.
 */
public final class DragContextAsDragContextTest
    extends AbstractDragContextTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table associated with the fixture. */
    private Table table_;

    /** The table environment associated with the fixture. */
    private TableEnvironment tableEnvironment_;


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
        tableEnvironment_ = new TableEnvironment();
        table_ = new Table( tableEnvironment_ );

        super.setUp();
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        tableEnvironment_.dispose();
    }
}
