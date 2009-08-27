/*
 * IGame.java
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
 * Created on Jul 9, 2008 at 9:20:21 PM.
 */

package org.gamegineer.game.core;

import org.gamegineer.engine.core.IEngine;

/**
 * A game.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IGame
    extends IEngine
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

    /**
     * Gets the game name.
     * 
     * @return The game name; never {@code null}.
     */
    /* @NonNull */
    public String getName();

    /**
     * Gets the game system identifier.
     * 
     * @return The game system identifier; never {@code null}.
     */
    /* @NonNull */
    public String getSystemId();

    /**
     * Indicates the game is complete.
     * 
     * @return {@code true} if the game is complete; otherwise {@code false}.
     */
    public boolean isComplete();
}
