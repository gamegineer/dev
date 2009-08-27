/*
 * SquareAsSquareTest.java
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
 * Created on Jun 30, 2009 at 11:55:50 PM.
 */

package org.gamegineer.tictactoe.internal.core;

import org.gamegineer.tictactoe.core.AbstractSquareTestCase;
import org.gamegineer.tictactoe.core.ISquare;
import org.gamegineer.tictactoe.core.SquareId;

/**
 * A fixture for testing the
 * {@link org.gamegineer.tictactoe.internal.core.Square} class to ensure it does
 * not violate the contract of the {@link org.gamegineer.tictactoe.core.ISquare}
 * interface.
 */
public final class SquareAsSquareTest
    extends AbstractSquareTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SquareAsSquareTest} class.
     */
    public SquareAsSquareTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.tictactoe.core.AbstractSquareTestCase#createSquare()
     */
    @Override
    protected ISquare createSquare()
    {
        return new Square( SquareId.MIDDLE_CENTER, null );
    }
}
