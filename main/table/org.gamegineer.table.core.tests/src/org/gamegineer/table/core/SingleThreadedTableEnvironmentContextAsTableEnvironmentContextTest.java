/*
 * SingleThreadedTableEnvironmentContextAsTableEnvironmentContextTest.java
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
 * Created on May 28, 2013 at 8:29:13 PM.
 */

package org.gamegineer.table.core;

import org.gamegineer.table.core.test.AbstractTableEnvironmentContextTestCase;

/**
 * A fixture for testing the {@link SingleThreadedTableEnvironmentContext} class
 * to ensure it does not violate the contract of the
 * {@link ITableEnvironmentContext} interface.
 */
public final class SingleThreadedTableEnvironmentContextAsTableEnvironmentContextTest
    extends AbstractTableEnvironmentContextTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code SingleThreadedTableEnvironmentContextAsTableEnvironmentContextTest}
     * class.
     */
    public SingleThreadedTableEnvironmentContextAsTableEnvironmentContextTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.test.AbstractTableEnvironmentContextTestCase#createTableEnvironmentContext()
     */
    @Override
    protected ITableEnvironmentContext createTableEnvironmentContext()
    {
        return new SingleThreadedTableEnvironmentContext();
    }
}
