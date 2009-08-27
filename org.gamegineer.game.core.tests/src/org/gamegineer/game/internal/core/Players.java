/*
 * Players.java
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
 * Created on Sep 12, 2008 at 10:58:15 PM.
 */

package org.gamegineer.game.internal.core;

import org.gamegineer.game.core.config.Configurations;
import org.gamegineer.game.core.config.IPlayerConfiguration;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IRole;

/**
 * A factory for creating various types of game player objects suitable for
 * testing.
 */
public final class Players
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Players} class.
     */
    private Players()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new player with a unique identifier.
     * 
     * @return A new player; never {@code null}.
     */
    /* @NonNull */
    public static Player createUniquePlayer()
    {
        final IRole role = GameSystems.createUniqueRole();
        final IPlayerConfiguration playerConfig = Configurations.createPlayerConfiguration( role.getId() );
        return new Player( role, playerConfig.getUserId() );
    }
}
