/*
 * Messages.java
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
 * Created on Aug 19, 2009 at 9:51:01 PM.
 */

package org.gamegineer.tictactoe.core.system;

import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage localized messages for the package.
 */
final class Messages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the associated resource bundle. */
    private static final String BUNDLE_NAME = "org.gamegineer.tictactoe.core.system.Messages"; //$NON-NLS-1$

    // --- TicTacToeGameSystemExtensionFactory ------------------------------

    /** An error occurred while creating the game system. */
    public static String TicTacToeGameSystemExtensionFactory_create_creationError;

    /** The game system file was not found. */
    public static String TicTacToeGameSystemExtensionFactory_create_fileNotFound;

    /** An error occurred opening the game system file. */
    public static String TicTacToeGameSystemExtensionFactory_create_openFileError;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Messages} class.
     */
    static
    {
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    /**
     * Initializes a new instance of the {@code Messages} class.
     */
    private Messages()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- TicTacToeGameSystemExtensionFactory ------------------------------

    /**
     * Gets the formatted message indicating the game system file was not found.
     * 
     * @param path
     *        The bundle path of the game system file; must not be {@code null}.
     * 
     * @return The formatted message indicating the game system file was not
     *         found; never {@code null}.
     */
    /* @NonNull */
    static String TicTacToeGameSystemExtensionFactory_create_fileNotFound(
        /* @NonNull */
        final String path )
    {
        return bind( TicTacToeGameSystemExtensionFactory_create_fileNotFound, path );
    }
}
