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
 * Created on Mar 27, 2009 at 11:41:31 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.connection;

import org.eclipse.osgi.util.NLS;
import org.gamegineer.client.core.connection.IGameServerConnection;

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
    private static final String BUNDLE_NAME = "org.gamegineer.client.internal.ui.console.commandlets.connection.Messages"; //$NON-NLS-1$

    // --- ConnectServerCommandlet ------------------------------------------

    /** The game server identifier is not registered. */
    public static String ConnectServerCommandlet_createLocalGameServerConnection_gameServerIdUnregistered;

    /** The game server identifier was not specified. */
    public static String ConnectServerCommandlet_createLocalGameServerConnection_noGameServerId;

    /** An error occurred while connecting to the game server. */
    public static String ConnectServerCommandlet_execute_connectGameServerError;

    /** The game server argument was not specified. */
    public static String ConnectServerCommandlet_execute_noGameServerArg;

    /** The user identifier argument was not specified. */
    public static String ConnectServerCommandlet_execute_noUserIdArg;

    /** The connection type is unknown. */
    public static String ConnectServerCommandlet_execute_unknownConnectionType;

    /** The help detailed description. */
    public static String ConnectServerCommandlet_help_detailedDescription;

    /** The help synopsis. */
    public static String ConnectServerCommandlet_help_synopsis;

    /** An error occurred while connecting to the game server. */
    public static String ConnectServerCommandlet_output_connectGameServerError;

    /** The game server has been connected. */
    public static String ConnectServerCommandlet_output_connected;

    /** The game server identifier is not registered. */
    public static String ConnectServerCommandlet_output_gameServerIdUnregistered;

    /** The game server argument was not specified. */
    public static String ConnectServerCommandlet_output_noGameServerArg;

    /** The game server identifier was not specified. */
    public static String ConnectServerCommandlet_output_noGameServerId;

    /** The user identifier argument was not specified. */
    public static String ConnectServerCommandlet_output_noUserIdArg;

    /** An error occurred while parsing the connection properties. */
    public static String ConnectServerCommandlet_output_parseConnectionPropertiesError;

    /** The connection type is unknown. */
    public static String ConnectServerCommandlet_output_unknownConnectionType;

    /** An error occurred while parsing the connection properties. */
    public static String ConnectServerCommandlet_parseConnectionString_parseConnectionPropertiesError;

    // --- DisconnectServerCommandlet ---------------------------------------

    /** The help detailed description. */
    public static String DisconnectServerCommandlet_help_detailedDescription;

    /** The help synopsis. */
    public static String DisconnectServerCommandlet_help_synopsis;

    /** The connected game server has been disconnected. */
    public static String DisconnectServerCommandlet_output_disconnected;

    // --- GetServerCommandlet ----------------------------------------------

    /** The help detailed description. */
    public static String GetServerCommandlet_help_detailedDescription;

    /** The help synopsis. */
    public static String GetServerCommandlet_help_synopsis;

    /** Information about the connected game server. */
    public static String GetServerCommandlet_output_gameServerInfo;


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

    // --- ConnectServerCommandlet ------------------------------------------

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
    static String ConnectServerCommandlet_createLocalGameServerConnection_gameServerIdUnregistered(
        /* @NonNull */
        final String gameServerId )
    {
        return bind( ConnectServerCommandlet_createLocalGameServerConnection_gameServerIdUnregistered, gameServerId );
    }

    /**
     * Gets the formatted message indicating the connection type is unknown.
     * 
     * @param connectionType
     *        The connection type; must not be {@code null}.
     * 
     * @return The formatted message indicating the connection type is unknown;
     *         never {@code null}.
     */
    /* @NonNull */
    static String ConnectServerCommandlet_execute_unknownConnectionType(
        /* @NonNull */
        final String connectionType )
    {
        return bind( ConnectServerCommandlet_execute_unknownConnectionType, connectionType );
    }

    /**
     * Gets the formatted message indicating the game server has been connected.
     * 
     * @param connection
     *        The connection; must not be {@code null}.
     * 
     * @return The formatted message indicating the game server has been
     *         connected; never {@code null}.
     */
    /* @NonNull */
    static String ConnectServerCommandlet_output_connected(
        /* @NonNull */
        final IGameServerConnection connection )
    {
        return bind( ConnectServerCommandlet_output_connected, new Object[] {
            connection.getName(), connection.getGameServer().getName(), connection.getUserPrincipal().getName()
        } );
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
    static String ConnectServerCommandlet_output_gameServerIdUnregistered(
        /* @NonNull */
        final String gameServerId )
    {
        return bind( ConnectServerCommandlet_output_gameServerIdUnregistered, gameServerId );
    }

    /**
     * Gets the formatted message indicating the connection type is unknown.
     * 
     * @param connectionType
     *        The connection type; must not be {@code null}.
     * 
     * @return The formatted message indicating the connection type is unknown;
     *         never {@code null}.
     */
    /* @NonNull */
    static String ConnectServerCommandlet_output_unknownConnectionType(
        /* @NonNull */
        final String connectionType )
    {
        return bind( ConnectServerCommandlet_output_unknownConnectionType, connectionType );
    }

    // --- DisconnectServerCommandlet ---------------------------------------

    /**
     * Gets the formatted message indicating the connected game server has been
     * disconnected.
     * 
     * @param connection
     *        The connection; must not be {@code null}.
     * 
     * @return The formatted message indicating the connected game server has
     *         been disconnected; never {@code null}.
     */
    /* @NonNull */
    static String DisconnectServerCommandlet_output_disconnected(
        /* @NonNull */
        final IGameServerConnection connection )
    {
        return bind( DisconnectServerCommandlet_output_disconnected, new Object[] {
            connection.getName(), connection.getGameServer().getName(), connection.getUserPrincipal().getName()
        } );
    }

    // --- GetServerCommandlet ----------------------------------------------

    /**
     * Gets the formatted message containing information about the connected
     * game server.
     * 
     * @param connection
     *        The connection; must not be {@code null}.
     * 
     * @return The formatted message containing information about the connected
     *         game server; never {@code null}.
     */
    /* @NonNull */
    static String GetServerCommandlet_output_gameServerInfo(
        /* @NonNull */
        final IGameServerConnection connection )
    {
        return bind( GetServerCommandlet_output_gameServerInfo, new Object[] {
            connection.getName(), connection.getGameServer().getName(), connection.getUserPrincipal().getName()
        } );
    }
}
