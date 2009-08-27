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
 * Created on Mar 27, 2009 at 11:32:16 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets;

import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage localized messages for the built-in commandlets.
 */
public final class CommandletMessages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the associated resource bundle. */
    private static final String BUNDLE_NAME = "org.gamegineer.client.internal.ui.console.commandlets.CommandletMessages"; //$NON-NLS-1$

    // --- Commandlet -------------------------------------------------------

    /** Too few arguments were specified. */
    public static String Commandlet_execute_tooFewArgs;

    /** Too many arguments were specified. */
    public static String Commandlet_execute_tooManyArgs;

    /** Too few arguments were specified. */
    public static String Commandlet_output_tooFewArgs;

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
