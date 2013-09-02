/*
 * AbstractDialogPageAsDialogPageTest.java
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
 * Created on Sep 23, 2010 at 10:36:52 PM.
 */

package org.gamegineer.common.ui.dialog;

/**
 * A fixture for testing the {@link AbstractDialogPage} class to ensure it does
 * not violate the contract of the {@link IDialogPage} interface.
 */
public final class AbstractDialogPageAsDialogPageTest
    extends AbstractDialogPageTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractDialogPageAsDialogPageTest} class.
     */
    public AbstractDialogPageAsDialogPageTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.ui.dialog.AbstractDialogPageTestCase#createDialogPage()
     */
    @Override
    protected IDialogPage createDialogPage()
    {
        return new AbstractDialogPage()
        {
            // no overrides
        };
    }
}
