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
 * Created on Jul 10, 2008 at 10:47:52 PM.
 */

package org.gamegineer.game.core.config;

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
    private static final String BUNDLE_NAME = "org.gamegineer.game.core.config.Messages"; //$NON-NLS-1$

    // --- GameConfigurationBuilder -----------------------------------------

    /** The game identifier is not set. */
    public static String GameConfigurationBuilder_id_notSet;

    /** The game name is not set. */
    public static String GameConfigurationBuilder_name_notSet;

    /** The builder state is illegal. */
    public static String GameConfigurationBuilder_state_illegal;

    /** The game system is not set. */
    public static String GameConfigurationBuilder_system_notSet;

    // --- PlayerConfigurationBuilder ---------------------------------------

    /** The player role identifier is not set. */
    public static String PlayerConfigurationBuilder_roleId_notSet;

    /** The builder state is illegal. */
    public static String PlayerConfigurationBuilder_state_illegal;

    /** The player user identifier is not set. */
    public static String PlayerConfigurationBuilder_userId_notSet;


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
