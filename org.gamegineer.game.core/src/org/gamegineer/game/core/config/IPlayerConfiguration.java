/*
 * IPlayerConfiguration.java
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
 * Created on Jan 17, 2009 at 10:29:01 PM.
 */

package org.gamegineer.game.core.config;

/**
 * The configuration for a game player.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IPlayerConfiguration
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the player role identifier.
     * 
     * @return The player role identifier; never {@code null}.
     */
    /* @NonNull */
    public String getRoleId();

    /**
     * Gets the player user identifier.
     * 
     * @return The player user identifier; never {@code null}.
     */
    /* @NonNull */
    public String getUserId();
}
