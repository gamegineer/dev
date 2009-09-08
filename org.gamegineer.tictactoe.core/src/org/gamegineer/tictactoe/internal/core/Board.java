/*
 * Board.java
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
 * Created on Aug 24, 2009 at 10:09:48 PM.
 */

package org.gamegineer.tictactoe.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import net.jcip.annotations.Immutable;
import org.gamegineer.tictactoe.core.IBoard;
import org.gamegineer.tictactoe.core.ISquare;
import org.gamegineer.tictactoe.core.SquareId;

/**
 * Implementation of {@link org.gamegineer.tictactoe.core.IBoard}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class Board
    implements IBoard
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The count of squares on the board. */
    private static final int SQUARE_COUNT = SquareId.values().length;

    /** The collection of board squares. */
    private final EnumMap<SquareId, ISquare> squares_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Board} class.
     * 
     * @param squares
     *        The collection of squares on the board; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code squares} does not contain exactly one entry for each
     *         square on the board.
     * @throws java.lang.NullPointerException
     *         If {@code squares} is {@code null}.
     */
    public Board(
        /* @NonNull */
        final Collection<ISquare> squares )
    {
        assertArgumentNotNull( squares, "squares" ); //$NON-NLS-1$
        assertArgumentLegal( isSquareCollectionLegal( squares ), "squares", Messages.Board_squares_illegal ); //$NON-NLS-1$

        squares_ = new EnumMap<SquareId, ISquare>( SquareId.class );
        for( final ISquare square : squares )
        {
            squares_.put( square.getId(), square );
        }
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.tictactoe.core.IBoard#getSquare(org.gamegineer.tictactoe.core.SquareId)
     */
    public ISquare getSquare(
        final SquareId id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return squares_.get( id );
    }

    /**
     * Indicates the specified square collection is legal, that is, it contains
     * exactly one entry for each square on the board.
     * 
     * @param squares
     *        The square collection; must not be {@code null}.
     * 
     * @return {@code true} if the specified square collection is legal;
     *         otherwise {@code false}.
     */
    private static boolean isSquareCollectionLegal(
        /* @NonNull */
        final Collection<ISquare> squares )
    {
        assert squares != null;

        if( squares.size() != SQUARE_COUNT )
        {
            return false;
        }

        final EnumSet<SquareId> presentSquareIds = EnumSet.noneOf( SquareId.class );
        for( final ISquare square : squares )
        {
            if( square == null )
            {
                return false;
            }
            presentSquareIds.add( square.getId() );
        }
        return presentSquareIds.size() == SQUARE_COUNT;
    }
}
