/*
 * IGameConfiguration.java
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
 * Created on Jul 10, 2008 at 9:27:59 PM.
 */

package org.gamegineer.game.core.config;

import java.util.List;
import org.gamegineer.game.core.system.IGameSystem;

/**
 * The configuration for a game.
 * 
 * <p>
 * A game configuration defines a unique instance of a game. The game
 * configuration specifies all variable and optional attributes of a game
 * system.
 * </p>
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IGameConfiguration
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the game identifier.
     * 
     * @return The game identifier; never {@code null}.
     */
    /* @NonNull */
    public String getId();

    // TODO: Consider making the game ID (and the system ID) a non-String
    // type such as java.util.UUID.

    /**
     * Gets the game name.
     * 
     * @return The game name; never {@code null}.
     */
    /* @NonNull */
    public String getName();

    /**
     * Gets a list view of the game players.
     * 
     * @return A list view of the game players; never {@code null}.
     */
    /* @NonNull */
    public List<IPlayerConfiguration> getPlayers();

    /**
     * Gets the game system.
     * 
     * @return The game system; never {@code null}.
     */
    /* @NonNull */
    public IGameSystem getSystem();
}
