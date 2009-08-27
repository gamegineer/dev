/*
 * TicTacToeGameFactoryTest.java
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
 * Created on Aug 25, 2009 at 10:01:26 PM.
 */

package org.gamegineer.tictactoe.core;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.tictactoe.core.TicTacToeGameFactory} class.
 */
public final class TicTacToeGameFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TicTacToeGameFactoryTest} class.
     */
    public TicTacToeGameFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code toTicTacToeGame} method throws an exception when
     * passed a {@code null} game.
     */
    @Test( expected = NullPointerException.class )
    public void testToTicTacToeGame_Game_Null()
    {
        TicTacToeGameFactory.toTicTacToeGame( null );
    }
}
