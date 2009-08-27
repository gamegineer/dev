/*
 * TicTacToeGameAttributes.java
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
 * Created on Jul 11, 2009 at 9:37:25 PM.
 */

package org.gamegineer.tictactoe.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.IState.Scope;
import org.gamegineer.engine.core.util.attribute.Attribute;
import org.gamegineer.tictactoe.core.SquareId;

/**
 * A collection of engine attributes defined by the Tic-Tac-Toe game.
 */
public final class TicTacToeGameAttributes
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game complete attribute. */
    public static final Attribute<Boolean> GAME_COMPLETE = new Attribute<Boolean>( Scope.APPLICATION, "org.gamegineer.tictactoe.gameComplete" ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TicTacToeGameAttributes} class.
     */
    private TicTacToeGameAttributes()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the square state attribute for the specified square.
     * 
     * @param squareId
     *        The square identifier; must not be {@code null}.
     * 
     * @return The square state attribute for the specified stage; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code squareId} is {@code null}.
     */
    /* @NonNull */
    public static Attribute<SquareState> squareState(
        /* @NonNull */
        final SquareId squareId )
    {
        assertArgumentNotNull( squareId, "squareId" ); //$NON-NLS-1$

        return new Attribute<SquareState>( Scope.APPLICATION, String.format( "org.gamegineer.tictactoe.square-%1$s.state", squareId.name() ) ); //$NON-NLS-1$
    }
}
