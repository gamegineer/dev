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
 * Created on Dec 13, 2008 at 11:01:34 PM.
 */

package org.gamegineer.server.internal.core;

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
    private static final String BUNDLE_NAME = "org.gamegineer.server.internal.core.Messages"; //$NON-NLS-1$

    // --- GameServer -------------------------------------------------------

    /** The game identifier already exists on this server. */
    public static String GameServer_createGame_duplicateGameId;

    /** The game server configuration is illegal. */
    public static String GameServer_gameServerConfig_illegal;

    // --- GameServerFactory ------------------------------------------------

    /** The requested class name is unsupported. */
    public static String GameServerFactory_createComponent_unsupportedType;

    // --- NullGameServer ---------------------------------------------------

    /** The server does not support the creation of games. */
    public static String NullGameServer_createGame_unsupported;

    /** The server name. */
    public static String NullGameServer_name;

    // --- Services ---------------------------------------------------------

    /** The game system registry service tracker is not set. */
    public static String Services_gameSystemRegistryServiceTracker_notSet;


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

    // --- GameServer -------------------------------------------------------

    /**
     * Gets the formatted message indicating the game identifier already exists
     * on this server.
     * 
     * @param gameId
     *        The game identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the game identifier already
     *         exists on this server; never {@code null}.
     */
    /* @NonNull */
    static String GameServer_createGame_duplicateGameId(
        /* @NonNull */
        final String gameId )
    {
        return bind( GameServer_createGame_duplicateGameId, gameId );
    }

    // --- GameServerFactory ------------------------------------------------

    /**
     * Gets the formatted message indicating the requested class name is
     * unsupported.
     * 
     * @param className
     *        The class name; must not be {@code null}.
     * 
     * @return The formatted message indicating the requested class name is
     *         unsupported; never {@code null}.
     */
    /* @NonNull */
    static String GameServerFactory_createComponent_unsupportedType(
        /* @NonNull */
        final String className )
    {
        return bind( GameServerFactory_createComponent_unsupportedType, className );
    }
}
