/*
 * ITicTacToeGame.java
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
 * Created on Jun 21, 2009 at 9:25:45 PM.
 */

package org.gamegineer.tictactoe.core;

import org.gamegineer.game.core.IGame;

/**
 * A Tic-Tac-Toe game.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface ITicTacToeGame
    extends IGame
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the game board.
     * 
     * @return The game board; never {@code null}.
     */
    /* @NonNull */
    public IBoard getBoard();
}
