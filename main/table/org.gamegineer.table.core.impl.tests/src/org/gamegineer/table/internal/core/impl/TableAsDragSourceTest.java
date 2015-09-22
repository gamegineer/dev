/*
 * TableAsDragSourceTest.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Mar 16, 2013 at 8:53:42 PM.
 */

package org.gamegineer.table.internal.core.impl;

import java.util.Optional;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.SingleThreadedTableEnvironmentContext;
import org.gamegineer.table.core.dnd.IDragSource;
import org.gamegineer.table.core.dnd.test.AbstractDragSourceTestCase;
import org.junit.Before;

/**
 * A fixture for testing the {@link Table} class to ensure it does not violate
 * the contract of the {@link IDragSource} interface.
 */
public final class TableAsDragSourceTest
    extends AbstractDragSourceTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table associated with the fixture. */
    private Optional<Table> table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableAsDragSourceTest} class.
     */
    public TableAsDragSourceTest()
    {
        table_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.dnd.test.AbstractDragSourceTestCase#getTable()
     */
    @Override
    protected ITable getTable()
    {
        return table_.get();
    }

    /*
     * @see org.gamegineer.table.core.dnd.test.AbstractDragSourceTestCase#setUp()
     */
    @Before
    @Override
    public void setUp()
        throws Exception
    {
        table_ = Optional.of( new Table( new TableEnvironment( new SingleThreadedTableEnvironmentContext() ) ) );

        super.setUp();
    }
}
