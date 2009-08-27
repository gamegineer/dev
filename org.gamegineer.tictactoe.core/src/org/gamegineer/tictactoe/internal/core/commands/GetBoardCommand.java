/*
 * GetBoardCommand.java
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
 * Created on Aug 21, 2009 at 11:24:22 PM.
 */

package org.gamegineer.tictactoe.internal.core.commands;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.AbstractCommand;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.tictactoe.core.IBoard;
import org.gamegineer.tictactoe.core.ISquare;
import org.gamegineer.tictactoe.core.SquareId;
import org.gamegineer.tictactoe.internal.core.Board;
import org.gamegineer.tictactoe.internal.core.Square;
import org.gamegineer.tictactoe.internal.core.SquareState;
import org.gamegineer.tictactoe.internal.core.TicTacToeGameAttributes;

/**
 * A command to retrieve the game board.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class GetBoardCommand
    extends AbstractCommand<IBoard>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetBoardCommand} class.
     */
    public GetBoardCommand()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.ICommand#execute(org.gamegineer.engine.core.IEngineContext)
     */
    public IBoard execute(
        final IEngineContext context )
    {
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        final ArrayList<ISquare> squares = new ArrayList<ISquare>();
        for( final SquareId squareId : SquareId.values() )
        {
            final SquareState squareState = TicTacToeGameAttributes.squareState( squareId ).getValue( context.getState() );
            squares.add( new Square( squareId, squareState.getOwnerRoleId() ) );
        }

        return new Board( squares );
    }
}
