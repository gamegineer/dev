/*
 * ISquare.java
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
 * Created on Jun 21, 2009 at 9:30:27 PM.
 */

package org.gamegineer.tictactoe.core;

/**
 * A square on the game board.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface ISquare
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the square identifier.
     * 
     * @return The square identifier; never {@code null}.
     */
    /* @NonNull */
    public SquareId getId();

    /**
     * Gets the role identifier of the square owner.
     * 
     * @return The role identifier of the square owner or {@code null} if the
     *         square is not owned.
     */
    /* @Nullable */
    public String getOwnerRoleId();
}
