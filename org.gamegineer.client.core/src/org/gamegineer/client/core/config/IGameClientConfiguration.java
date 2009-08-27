/*
 * IGameClientConfiguration.java
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
 * Created on Mar 6, 2009 at 11:12:11 PM.
 */

package org.gamegineer.client.core.config;

import org.gamegineer.client.core.system.IGameSystemUiSource;

/**
 * The configuration for a game client.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IGameClientConfiguration
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the game system user interface source for the server.
     * 
     * @return The game system user interface source for the server; never
     *         {@code null}.
     */
    /* @NonNull */
    public IGameSystemUiSource getGameSystemUiSource();
}
