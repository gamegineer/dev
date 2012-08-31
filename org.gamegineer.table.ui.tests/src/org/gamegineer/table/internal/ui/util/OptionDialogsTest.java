/*
 * OptionDialogsTest.java
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
 * Created on Nov 24, 2010 at 8:28:51 PM.
 */

package org.gamegineer.table.internal.ui.util;

import javax.swing.JPanel;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.util.OptionDialogs} class.
 */
public final class OptionDialogsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code OptionDialogsTest} class.
     */
    public OptionDialogsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code showConfirmDialog} method throws an exception when
     * passed a {@code null} message.
     */
    @Test( expected = NullPointerException.class )
    public void testShowConfirmDialog_Message_Null()
    {
        OptionDialogs.showConfirmDialog( new JPanel(), null );
    }

    /**
     * Ensures the {@code showErrorMessageDialog} method throws an exception
     * when passed a {@code null} message.
     */
    @Test( expected = NullPointerException.class )
    public void testShowErrorMessageDialog_Message_Null()
    {
        OptionDialogs.showErrorMessageDialog( new JPanel(), null );
    }
}
