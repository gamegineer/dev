/*
 * DialogMessageType.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Sep 18, 2010 at 9:56:52 PM.
 */

package org.gamegineer.common.ui.dialog;

/**
 * A dialog message type.
 */
public enum DialogMessageType
{
    // ======================================================================
    // Enum Constants
    // ======================================================================

    /**
     * The normal message type.
     * 
     * <p>
     * Typically indicates a message should be displayed with no additional
     * decoration.
     * </p>
     */
    NORMAL,

    /** The information message type. */
    INFORMATION,

    /** The warning message type. */
    WARNING,

    /** The error message type. */
    ERROR;
}
