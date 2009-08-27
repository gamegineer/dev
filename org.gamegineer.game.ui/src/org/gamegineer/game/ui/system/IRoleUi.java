/*
 * IRoleUi.java
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
 * Created on Feb 26, 2009 at 10:35:04 PM.
 */

package org.gamegineer.game.ui.system;

/**
 * A game role user interface.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IRoleUi
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the role identifier.
     * 
     * @return The role identifier; never {@code null}.
     */
    /* @NonNull */
    public String getId();

    /**
     * Gets the role name.
     * 
     * @return The role name; never {@code null}.
     */
    /* @NonNull */
    public String getName();
}
