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
 * Created on Mar 3, 2009 at 11:22:26 PM.
 */

package org.gamegineer.game.internal.ui.services.systemuiregistry;

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
    private static final String BUNDLE_NAME = "org.gamegineer.game.internal.ui.services.systemuiregistry.Messages"; //$NON-NLS-1$

    // --- GameSystemUiRegistry ---------------------------------------------

    /** An error occurred while creating the game system user interface. */
    public static String GameSystemUiRegistry_getForeignGameSystemUisFromExtensionRegistry_creationError;

    /** A duplicate game system user interface identifier was detected. */
    public static String GameSystemUiRegistry_getGameSystemUiMap_duplicateId;


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

    // --- GameSystemUiRegistry ---------------------------------------------

    /**
     * Gets the formatted message indicating an error occurred while creating
     * the game system user interface.
     * 
     * @param gameSystemId
     *        The game system identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while creating
     *         the game system user interface; never {@code null}.
     */
    /* @NonNull */
    static String GameSystemUiRegistry_getForeignGameSystemUisFromExtensionRegistry_creationError(
        /* @NonNull */
        final String gameSystemId )
    {
        return bind( GameSystemUiRegistry_getForeignGameSystemUisFromExtensionRegistry_creationError, gameSystemId );
    }

    /**
     * Gets the formatted message indicating a duplicate game system user
     * interface identifier was detected.
     * 
     * @param gameSystemId
     *        The game system identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a duplicate game system user
     *         interface identifier was detected; never {@code null}.
     */
    /* @NonNull */
    static String GameSystemUiRegistry_getGameSystemUiMap_duplicateId(
        /* @NonNull */
        final String gameSystemId )
    {
        return bind( GameSystemUiRegistry_getGameSystemUiMap_duplicateId, gameSystemId );
    }
}
