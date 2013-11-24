/*
 * TableNetworkFactoryAsTableNetworkFactoryTest.java
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
 * Created on Nov 23, 2013 at 8:21:36 PM.
 */

package org.gamegineer.table.internal.net.impl;

import org.gamegineer.table.net.ITableNetworkFactory;
import org.gamegineer.table.net.test.AbstractTableNetworkFactoryTestCase;

/**
 * A fixture for testing the {@link TableNetworkFactory} class to ensure it does
 * not violate the contract of the {@link ITableNetworkFactory} interface.
 */
public final class TableNetworkFactoryAsTableNetworkFactoryTest
    extends AbstractTableNetworkFactoryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code TableNetworkFactoryAsTableNetworkFactoryTest} class.
     */
    public TableNetworkFactoryAsTableNetworkFactoryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.test.AbstractTableNetworkFactoryTestCase#createTableNetworkFactory()
     */
    @Override
    protected ITableNetworkFactory createTableNetworkFactory()
    {
        return new TableNetworkFactory();
    }
}
