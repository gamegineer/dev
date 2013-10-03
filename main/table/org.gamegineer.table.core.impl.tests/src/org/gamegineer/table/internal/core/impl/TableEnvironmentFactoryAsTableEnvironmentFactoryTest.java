/*
 * TableEnvironmentFactoryAsTableEnvironmentFactoryTest.java
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
 * Created on Oct 1, 2013 at 8:23:55 PM.
 */

package org.gamegineer.table.internal.core.impl;

import org.gamegineer.table.core.ITableEnvironmentFactory;
import org.gamegineer.table.core.test.AbstractTableEnvironmentFactoryTestCase;

/**
 * A fixture for testing the {@link TableEnvironmentFactory} class to ensure it
 * does not violate the contract of the {@link ITableEnvironmentFactory}
 * interface.
 */
public final class TableEnvironmentFactoryAsTableEnvironmentFactoryTest
    extends AbstractTableEnvironmentFactoryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code TableEnvironmentFactoryAsTableEnvironmentFactoryTest} class.
     */
    public TableEnvironmentFactoryAsTableEnvironmentFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.test.AbstractTableEnvironmentFactoryTestCase#createTableEnvironmentFactory()
     */
    @Override
    protected ITableEnvironmentFactory createTableEnvironmentFactory()
    {
        return new TableEnvironmentFactory();
    }
}
