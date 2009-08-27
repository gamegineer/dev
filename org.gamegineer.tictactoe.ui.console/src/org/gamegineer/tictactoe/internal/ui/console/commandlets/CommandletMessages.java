/*
 * CommandletMessages.java
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
 * Created on Aug 25, 2009 at 10:50:15 PM.
 */

package org.gamegineer.tictactoe.internal.ui.console.commandlets;

import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage localized messages for the built-in commandlets.
 */
final class CommandletMessages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the associated resource bundle. */
    private static final String BUNDLE_NAME = "org.gamegineer.tictactoe.internal.ui.console.commandlets.CommandletMessages"; //$NON-NLS-1$

    // --- Commandlet -------------------------------------------------------

    /** Too many arguments were specified. */
    public static String Commandlet_execute_tooManyArgs;

    /** Too many arguments were specified. */
    public static String Commandlet_output_tooManyArgs;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code CommandletMessages} class.
     */
    static
    {
        NLS.initializeMessages( BUNDLE_NAME, CommandletMessages.class );
    }

    /**
     * Initializes a new instance of the {@code CommandletMessages} class.
     */
    private CommandletMessages()
    {
        super();
    }
}
