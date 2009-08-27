/*
 * BoardTest.java
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
 * Created on Aug 24, 2009 at 10:17:14 PM.
 */

package org.gamegineer.tictactoe.internal.core;

import java.util.ArrayList;
import java.util.List;
import org.gamegineer.tictactoe.core.ISquare;
import org.gamegineer.tictactoe.core.SquareId;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.tictactoe.internal.core.Board} class.
 */
public final class BoardTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BoardTest} class.
     */
    public BoardTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a legal square collection that is suitable to be passed to the
     * {@code Board} constructor.
     * 
     * @return A square collection; never {@code null}.
     */
    /* @NonNull */
    private static List<ISquare> createLegalSquareCollection()
    {
        final List<ISquare> squares = new ArrayList<ISquare>();
        for( final SquareId squareId : SquareId.values() )
        {
            squares.add( new Square( squareId, null ) );
        }
        return squares;
    }

    /**
     * Ensures the constructor throws an exception when passed a square
     * collection that contains a duplicate square.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Squares_Illegal_DuplicateSquare()
    {
        final List<ISquare> squares = createLegalSquareCollection();
        squares.set( 1, squares.get( 0 ) );

        new Board( squares );
    }

    /**
     * Ensures the constructor throws an exception when passed a square
     * collection that contains a {@code null} square.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Squares_Illegal_NullSquare()
    {
        final List<ISquare> squares = createLegalSquareCollection();
        squares.set( 1, null );

        new Board( squares );
    }

    /**
     * Ensures the constructor throws an exception when passed a square
     * collection that is missing one square.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Squares_Illegal_OneSquareAbsent()
    {
        final List<ISquare> squares = createLegalSquareCollection();
        squares.remove( 0 );

        new Board( squares );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * square collection.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Squares_Null()
    {
        new Board( null );
    }
}
