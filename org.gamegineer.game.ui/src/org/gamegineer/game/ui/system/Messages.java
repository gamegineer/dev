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
 * Created on Feb 27, 2009 at 10:56:51 PM.
 */

package org.gamegineer.game.ui.system;

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
    private static final String BUNDLE_NAME = "org.gamegineer.game.ui.system.Messages"; //$NON-NLS-1$

    // --- GameSystemUiBuilder ----------------------------------------------

    /** The game system identifier is not set. */
    public static String GameSystemUiBuilder_id_notSet;

    /** The game system name is not set. */
    public static String GameSystemUiBuilder_name_notSet;

    /** The builder state is illegal. */
    public static String GameSystemUiBuilder_state_illegal;

    // --- RoleUiBuilder ----------------------------------------------------

    /** The role identifier is not set. */
    public static String RoleUiBuilder_id_notSet;

    /** The role name is not set. */
    public static String RoleUiBuilder_name_notSet;

    /** The builder state is illegal. */
    public static String RoleUiBuilder_state_illegal;


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
}