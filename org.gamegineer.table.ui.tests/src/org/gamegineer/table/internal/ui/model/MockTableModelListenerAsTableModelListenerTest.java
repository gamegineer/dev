/*
 * MockTableModelListenerAsTableModelListenerTest.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Dec 28, 2009 at 9:17:55 PM.
 */

package org.gamegineer.table.internal.ui.model;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.MockTableModelListener} class
 * to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.ui.model.ITableModelListener} interface.
 */
public final class MockTableModelListenerAsTableModelListenerTest
    extends AbstractTableModelListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * MockTableModelListenerAsTableModelListenerTest} class.
     */
    public MockTableModelListenerAsTableModelListenerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.ui.model.AbstractTableModelListenerTestCase#createTableModelListener()
     */
    @Override
    protected ITableModelListener createTableModelListener()
    {
        return new MockTableModelListener();
    }
}
