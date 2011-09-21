/*
 * PlayerRole.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Aug 9, 2011 at 8:50:14 PM.
 */

package org.gamegineer.table.net;

/**
 * The possible roles of a table network player.
 */
public enum PlayerRole
{
    // ======================================================================
    // Enum Constants
    // ======================================================================

    /** The player is the table network editor. */
    EDITOR,

    /** The player has requested control to be the table network editor. */
    EDITOR_REQUESTER,

    /** The player is the table network host. */
    HOST,

    /** The player is associated with the local network node. */
    LOCAL;
}
