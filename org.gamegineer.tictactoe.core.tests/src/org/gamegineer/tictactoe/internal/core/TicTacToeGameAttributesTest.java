/*
 * TicTacToeGameAttributesTest.java
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
 * Created on Jul 11, 2009 at 9:52:25 PM.
 */

package org.gamegineer.tictactoe.internal.core;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.tictactoe.internal.core.TicTacToeGameAttributes} class.
 */
public final class TicTacToeGameAttributesTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TicTacToeGameAttributesTest}
     * class.
     */
    public TicTacToeGameAttributesTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code squareState} method throws an exception when passed a
     * {@code null} square identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testSquareState_SquareId_Null()
    {
        TicTacToeGameAttributes.squareState( null );
    }
}
