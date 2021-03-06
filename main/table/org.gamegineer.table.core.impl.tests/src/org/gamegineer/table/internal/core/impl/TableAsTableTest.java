/*
 * TableAsTableTest.java
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
 * Created on Oct 6, 2009 at 11:11:27 PM.
 */

package org.gamegineer.table.internal.core.impl;

import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEnvironmentContext;
import org.gamegineer.table.core.test.AbstractTableTestCase;

/**
 * A fixture for testing the {@link Table} class to ensure it does not violate
 * the contract of the {@link ITable} interface.
 */
public final class TableAsTableTest
    extends AbstractTableTestCase<TableEnvironment, Table>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableAsTableTest} class.
     */
    public TableAsTableTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.test.AbstractTableTestCase#createTable(org.gamegineer.table.core.ITableEnvironment)
     */
    @Override
    protected Table createTable(
        final TableEnvironment tableEnvironment )
    {
        return new Table( tableEnvironment );
    }

    /*
     * @see org.gamegineer.table.core.test.AbstractTableTestCase#createTableEnvironment(org.gamegineer.table.core.ITableEnvironmentContext)
     */
    @Override
    protected TableEnvironment createTableEnvironment(
        final ITableEnvironmentContext context )
    {
        return new TableEnvironment( context );
    }
}
