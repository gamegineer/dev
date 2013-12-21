/*
 * TableRunnerFactoryAsTableRunnerFactoryTest.java
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
 * Created on Dec 20, 2013 at 9:23:59 PM.
 */

package org.gamegineer.table.internal.ui.impl;

import org.gamegineer.table.ui.ITableRunnerFactory;
import org.gamegineer.table.ui.test.AbstractTableRunnerFactoryTestCase;

/**
 * A fixture for testing the {@link TableRunnerFactory} class to ensure it does
 * not violate the contract of the {@link ITableRunnerFactory} interface.
 */
public final class TableRunnerFactoryAsTableRunnerFactoryTest
    extends AbstractTableRunnerFactoryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code TableRunnerFactoryAsTableRunnerFactoryTest} class.
     */
    public TableRunnerFactoryAsTableRunnerFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.ui.test.AbstractTableRunnerFactoryTestCase#createTableRunnerFactory()
     */
    @Override
    protected ITableRunnerFactory createTableRunnerFactory()
    {
        return new TableRunnerFactory();
    }
}
