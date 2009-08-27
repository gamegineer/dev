/*
 * AbstractBoardTestCase.java
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
 * Created on Jun 25, 2009 at 11:35:11 PM.
 */

package org.gamegineer.tictactoe.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.tictactoe.core.IBoard} interface.
 */
public abstract class AbstractBoardTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The board under test in the fixture. */
    private IBoard m_board;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractBoardTestCase} class.
     */
    protected AbstractBoardTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the board to be tested.
     * 
     * @return The board to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IBoard createBoard()
        throws Exception;

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        m_board = createBoard();
        assertNotNull( m_board );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        m_board = null;
    }

    /**
     * Ensures the {@code getSquare} method throws an exception when passed a
     * {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetSquare_Id_Null()
    {
        m_board.getSquare( null );
    }

    /**
     * Ensures the {@code getSquare} method returns the expected square for all
     * possible identifiers.
     */
    @Test
    public void testGetSquare()
    {
        for( final SquareId id : SquareId.values() )
        {
            final ISquare square = m_board.getSquare( id );
            assertNotNull( square );
            assertEquals( id, square.getId() );
        }
    }
}
