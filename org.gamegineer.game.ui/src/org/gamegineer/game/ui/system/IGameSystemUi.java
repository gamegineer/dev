/*
 * IGameSystemUi.java
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
 * Created on Feb 15, 2009 at 9:34:43 PM.
 */

package org.gamegineer.game.ui.system;

import java.util.List;

/**
 * A game system user interface.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IGameSystemUi
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the game system identifier.
     * 
     * @return The game system identifier; never {@code null}.
     */
    /* @NonNull */
    public String getId();

    /**
     * Gets the game system name.
     * 
     * @return The game system name; never {@code null}.
     */
    /* @NonNull */
    public String getName();

    /**
     * Gets a list view of the game roles.
     * 
     * @return A list view of the game roles; never {@code null}.
     */
    /* @NonNull */
    public List<IRoleUi> getRoles();
}
