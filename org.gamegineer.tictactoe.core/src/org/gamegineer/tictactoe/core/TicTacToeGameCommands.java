/*
 * TicTacToeGameCommands.java
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
 * Created on Aug 25, 2009 at 9:55:05 PM.
 */

package org.gamegineer.tictactoe.core;

import org.gamegineer.engine.core.ICommand;
import org.gamegineer.game.core.GameCommands;
import org.gamegineer.tictactoe.internal.core.commands.GetBoardCommand;

/**
 * A collection of commands for exercising the functionality of a Tic-Tac-Toe
 * game.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
public class TicTacToeGameCommands
    extends GameCommands
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TicTacToeGameCommands} class.
     */
    protected TicTacToeGameCommands()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a command that retrieves the game board.
     * 
     * @return A command that retrieves the game board; never {@code null}.
     */
    /* @NonNull */
    public static ICommand<IBoard> createGetBoardCommand()
    {
        return new GetBoardCommand();
    }
}
