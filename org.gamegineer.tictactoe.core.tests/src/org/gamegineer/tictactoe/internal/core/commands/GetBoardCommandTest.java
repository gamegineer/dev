/*
 * GetBoardCommandTest.java
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
 * Created on Aug 21, 2009 at 11:30:31 PM.
 */

package org.gamegineer.tictactoe.internal.core.commands;

import static org.junit.Assert.assertEquals;
import org.gamegineer.engine.core.FakeEngineContext;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.tictactoe.core.IBoard;
import org.gamegineer.tictactoe.core.SquareId;
import org.gamegineer.tictactoe.internal.core.SquareState;
import org.gamegineer.tictactoe.internal.core.TicTacToeGameAttributes;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.tictactoe.internal.core.commands.GetBoardCommand}
 * class.
 */
public final class GetBoardCommandTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GetBoardCommandTest} class.
     */
    public GetBoardCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code execute} method returns the expected board.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testExecute_ReturnValue()
        throws Exception
    {
        final GetBoardCommand command = new GetBoardCommand();
        final IEngineContext context = new FakeEngineContext();
        final String expectedOwnerRoleId = "owner-role-id"; //$NON-NLS-1$
        for( final SquareId squareId : SquareId.values() )
        {
            final SquareState initialSquareState = new SquareState( squareId );
            TicTacToeGameAttributes.squareState( squareId ).add( context.getState(), initialSquareState.changeOwner( expectedOwnerRoleId ) );
        }

        final IBoard board = command.execute( context );

        for( final SquareId squareId : SquareId.values() )
        {
            final String actualOwnerRoleId = board.getSquare( squareId ).getOwnerRoleId();
            assertEquals( expectedOwnerRoleId, actualOwnerRoleId );
        }
    }
}
