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
 * Created on Feb 28, 2009 at 9:08:58 PM.
 */

package org.gamegineer.game.internal.ui.system;

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
    private static final String BUNDLE_NAME = "org.gamegineer.game.internal.ui.system.Messages"; //$NON-NLS-1$

    // --- GameSystemUiUtils ------------------------------------------------

    /** The role user interface list is empty. */
    public static String GameSystemUiUtils_assertGameSystemUiLegal_roleUis_empty;

    /** The role user interface list contains a non-unique role identifier. */
    public static String GameSystemUiUtils_assertGameSystemUiLegal_roleUis_notUnique;

    // --- NullGameSystemUi -------------------------------------------------

    /** The game system name. */
    public static String NullGameSystemUi_name;

    // --- NullRoleUi -------------------------------------------------------

    /** The role name. */
    public static String NullRoleUi_name;


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

    // --- NullGameSystemUi -------------------------------------------------

    /**
     * Gets the formatted message which provides the name for a null game system
     * user interface.
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * 
     * @return The formatted message which provides the name for a null game
     *         system user interface; never {@code null}.
     */
    /* @NonNull */
    static String NullGameSystemUi_name(
        /* @NonNull */
        final String id )
    {
        return bind( NullGameSystemUi_name, id );
    }

    // --- NullRoleUi -------------------------------------------------------

    /**
     * Gets the formatted message which provides the name for a null role user
     * interface.
     * 
     * @param id
     *        The role identifier; must not be {@code null}.
     * 
     * @return The formatted message which provides the name for a null role
     *         user interface; never {@code null}.
     */
    /* @NonNull */
    static String NullRoleUi_name(
        /* @NonNull */
        final String id )
    {
        return bind( NullRoleUi_name, id );
    }
}
