/*
 * IPlayer.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Aug 9, 2011 at 8:49:50 PM.
 */

package org.gamegineer.table.net;

import java.util.Set;

/**
 * A player connected to the table network.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IPlayer
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the player name.
     * 
     * @return The player name.
     */
    public String getName();

    /**
     * Gets the collection of player roles.
     * 
     * @return The collection of player roles.
     */
    public Set<PlayerRole> getRoles();

    /**
     * Indicates the player has the specified role.
     * 
     * @param role
     *        The player role to test.
     * 
     * @return {@code true} if the player has the specified role; otherwise
     *         {@code false}.
     */
    public boolean hasRole(
        PlayerRole role );
}
