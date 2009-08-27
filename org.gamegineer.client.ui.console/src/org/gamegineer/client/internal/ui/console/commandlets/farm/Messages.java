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
 * Created on May 16, 2009 at 10:55:22 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.farm;

import org.eclipse.osgi.util.NLS;
import org.gamegineer.server.core.IGameServer;

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
    private static final String BUNDLE_NAME = "org.gamegineer.client.internal.ui.console.commandlets.farm.Messages"; //$NON-NLS-1$

    // --- GetLocalServersCommandlet ----------------------------------------

    /** The help detailed description. */
    public static String GetLocalServersCommandlet_help_detailedDescription;

    /** The help synopsis. */
    public static String GetLocalServersCommandlet_help_synopsis;

    /** Information about a game server. */
    public static String GetLocalServersCommandlet_output_gameServerInfo;

    /** No game servers are available. */
    public static String GetLocalServersCommandlet_output_noGameServers;

    // --- StartLocalServerCommandlet ---------------------------------------

    /** An error occurred while creating the game server. */
    public static String StartLocalServerCommandlet_createLocalGameServer_createGameServerError;

    /** The game server configuration is illegal. */
    public static String StartLocalServerCommandlet_createLocalGameServer_illegalGameServerConfig;

    /** The game server identifier is already registered. */
    public static String StartLocalServerCommandlet_execute_gameServerIdRegistered;

    /** The game server identifier argument was not specified. */
    public static String StartLocalServerCommandlet_execute_noGameServerIdArg;

    /** The game server name argument was not specified. */
    public static String StartLocalServerCommandlet_execute_noGameServerNameArg;

    /** The help detailed description. */
    public static String StartLocalServerCommandlet_help_detailedDescription;

    /** The help synopsis. */
    public static String StartLocalServerCommandlet_help_synopsis;

    /** An error occurred while creating the game server. */
    public static String StartLocalServerCommandlet_output_createGameServerError;

    /** The game server identifier is already registered. */
    public static String StartLocalServerCommandlet_output_gameServerIdRegistered;

    /** The game server configuration is illegal. */
    public static String StartLocalServerCommandlet_output_illegalGameServerConfig;

    /** The game server identifier argument was not specified. */
    public static String StartLocalServerCommandlet_output_noGameServerIdArg;

    /** The game server name argument was not specified. */
    public static String StartLocalServerCommandlet_output_noGameServerNameArg;

    /** The game server has been started. */
    public static String StartLocalServerCommandlet_output_started;

    // --- StopLocalServerCommandlet ----------------------------------------

    /** The game server identifier is not registered. */
    public static String StopLocalServerCommandlet_execute_gameServerIdUnregistered;

    /** The game server identifier argument was not specified. */
    public static String StopLocalServerCommandlet_execute_noGameServerIdArg;

    /** The help detailed description. */
    public static String StopLocalServerCommandlet_help_detailedDescription;

    /** The help synopsis. */
    public static String StopLocalServerCommandlet_help_synopsis;

    /** The game server identifier is not registered. */
    public static String StopLocalServerCommandlet_output_gameServerIdUnregistered;

    /** The game server identifier argument was not specified. */
    public static String StopLocalServerCommandlet_output_noGameServerIdArg;

    /** The game server has been stopped. */
    public static String StopLocalServerCommandlet_output_stopped;


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

    // --- GetLocalServersCommandlet ----------------------------------------

    /**
     * Gets the formatted message containing information about the game server.
     * 
     * @param gameServerId
     *        The game server identifier; must not be {@code null}.
     * @param gameServer
     *        The game server; must not be {@code null}.
     * 
     * @return The formatted message containing information about the game
     *         server; never {@code null}.
     */
    /* @NonNull */
    static String GetLocalServersCommandlet_output_gameServerInfo(
        /* @NonNull */
        final String gameServerId,
        /* @NonNull */
        final IGameServer gameServer )
    {
        return bind( GetLocalServersCommandlet_output_gameServerInfo, gameServerId, gameServer.getName() );
    }

    // --- StartLocalServerCommandlet ---------------------------------------

    /**
     * Gets the formatted message indicating the game server identifier is
     * already registered.
     * 
     * @param gameServerId
     *        The game server identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the game server identifier is
     *         already registered; never {@code null}.
     */
    /* @NonNull */
    static String StartLocalServerCommandlet_execute_gameServerIdRegistered(
        /* @NonNull */
        final String gameServerId )
    {
        return bind( StartLocalServerCommandlet_execute_gameServerIdRegistered, gameServerId );
    }

    /**
     * Gets the formatted message indicating the game server identifier is
     * already registered.
     * 
     * @param gameServerId
     *        The game server identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the game server identifier is
     *         already registered; never {@code null}.
     */
    /* @NonNull */
    static String StartLocalServerCommandlet_output_gameServerIdRegistered(
        /* @NonNull */
        final String gameServerId )
    {
        return bind( StartLocalServerCommandlet_output_gameServerIdRegistered, gameServerId );
    }

    /**
     * Gets the formatted message indicating the game server has been started.
     * 
     * @param gameServerId
     *        The game server identifier; must not be {@code null}.
     * @param gameServer
     *        The game server; must not be {@code null}.
     * 
     * @return The formatted message indicating the game server has been
     *         started; never {@code null}.
     */
    /* @NonNull */
    static String StartLocalServerCommandlet_output_started(
        /* @NonNull */
        final String gameServerId,
        /* @NonNull */
        final IGameServer gameServer )
    {
        return bind( StartLocalServerCommandlet_output_started, gameServerId, gameServer.getName() );
    }

    // --- StopLocalServerCommandlet ----------------------------------------

    /**
     * Gets the formatted message indicating the game server identifier is not
     * registered.
     * 
     * @param gameServerId
     *        The game server identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the game server identifier is
     *         not registered; never {@code null}.
     */
    /* @NonNull */
    static String StopLocalServerCommandlet_execute_gameServerIdUnregistered(
        /* @NonNull */
        final String gameServerId )
    {
        return bind( StopLocalServerCommandlet_execute_gameServerIdUnregistered, gameServerId );
    }

    /**
     * Gets the formatted message indicating the game server identifier is not
     * registered.
     * 
     * @param gameServerId
     *        The game server identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the game server identifier is
     *         not registered; never {@code null}.
     */
    /* @NonNull */
    static String StopLocalServerCommandlet_output_gameServerIdUnregistered(
        /* @NonNull */
        final String gameServerId )
    {
        return bind( StopLocalServerCommandlet_output_gameServerIdUnregistered, gameServerId );
    }

    /**
     * Gets the formatted message indicating the game server has been stopped.
     * 
     * @param gameServerId
     *        The game server identifier; must not be {@code null}.
     * @param gameServer
     *        The game server; must not be {@code null}.
     * 
     * @return The formatted message indicating the game server has been
     *         stopped; never {@code null}.
     */
    /* @NonNull */
    static String StopLocalServerCommandlet_output_stopped(
        /* @NonNull */
        final String gameServerId,
        /* @NonNull */
        final IGameServer gameServer )
    {
        return bind( StopLocalServerCommandlet_output_stopped, gameServerId, gameServer.getName() );
    }
}
