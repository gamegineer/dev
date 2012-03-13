/*
 * MainModelListenerAsMainModelListenerTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Aug 3, 2011 at 9:10:13 PM.
 */

package org.gamegineer.table.internal.ui.model;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.MainModelListener} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.ui.model.IMainModelListener} interface.
 */
public final class MainModelListenerAsMainModelListenerTest
    extends AbstractMainModelListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code MainModelListenerAsMainModelListenerTest} class.
     */
    public MainModelListenerAsMainModelListenerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.ui.model.AbstractMainModelListenerTestCase#createMainModelListener()
     */
    @Override
    protected IMainModelListener createMainModelListener()
    {
        return new MainModelListener();
    }
}
