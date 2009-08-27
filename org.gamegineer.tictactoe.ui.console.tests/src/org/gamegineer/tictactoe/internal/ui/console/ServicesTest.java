/*
 * ServicesTest.java
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
 * Created on Jun 12, 2009 at 10:23:34 PM.
 */

package org.gamegineer.tictactoe.internal.ui.console;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.tictactoe.internal.ui.console.Services} class.
 */
public final class ServicesTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServicesTest} class.
     */
    public ServicesTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code open} method throws an exception when passed a
     * {@code null} bundle context.
     */
    @Test( expected = NullPointerException.class )
    public void testOpen_Context_Null()
    {
        Services.getDefault().open( null );
    }
}
