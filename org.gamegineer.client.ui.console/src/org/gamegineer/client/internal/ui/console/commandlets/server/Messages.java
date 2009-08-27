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
 * Created on Mar 27, 2009 at 11:53:20 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.server;

import org.eclipse.osgi.util.NLS;
import org.gamegineer.game.core.IGame;
import org.gamegineer.game.ui.system.IGameSystemUi;

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
    private static final String BUNDLE_NAME = "org.gamegineer.client.internal.ui.console.commandlets.server.Messages"; //$NON-NLS-1$

    // --- CreateGameCommandlet ---------------------------------------------

    /** The game configuration is illegal. */
    public static String CreateGameCommandlet_execute_illegalGameConfiguration;

    /** The help detailed description. */
    public static String CreateGameCommandlet_help_detailedDescription;

    /** The help synopsis. */
    public static String CreateGameCommandlet_help_synopsis;

    /** A new game was created. */
    public static String CreateGameCommandlet_output_gameCreated;

    /** The game configuration is illegal. */
    public static String CreateGameCommandlet_output_illegalGameConfiguration;

    /** An error occurred while parsing the player role bindings. */
    public static String CreateGameCommandlet_output_parsePlayerRoleBindingsError;

    /** The game system identifier is unknown. */
    public static String CreateGameCommandlet_output_unknownGameSystemId;

    /** An error occurred while parsing the player role bindings. */
    public static String CreateGameCommandlet_parseGameConfiguration_parsePlayerRoleBindingsError;

    /** The game system identifier is unknown. */
    public static String CreateGameCommandlet_parseGameConfiguration_unknownGameSystemId;

    // --- GetGameCommandlet ------------------------------------------------

    /** The game identifier argument was not specified. */
    public static String GetGameCommandlet_execute_noGameIdArg;

    /** The help detailed description. */
    public static String GetGameCommandlet_help_detailedDescription;

    /** The help synopsis. */
    public static String GetGameCommandlet_help_synopsis;

    /** Information about the specified game. */
    public static String GetGameCommandlet_output_gameInfo;

    /** The game is not available. */
    public static String GetGameCommandlet_output_noGame;

    /** The game identifier argument was not specified. */
    public static String GetGameCommandlet_output_noGameIdArg;

    // --- GetGameSystemsCommandlet -----------------------------------------

    /** The help detailed description. */
    public static String GetGameSystemsCommandlet_help_detailedDescription;

    /** The help synopsis. */
    public static String GetGameSystemsCommandlet_help_synopsis;

    /** Information about a game system. */
    public static String GetGameSystemsCommandlet_output_gameSystemInfo;

    /** No game systems are available. */
    public static String GetGameSystemsCommandlet_output_noGameSystems;

    // --- GetGamesCommandlet -----------------------------------------------

    /** The help detailed description. */
    public static String GetGamesCommandlet_help_detailedDescription;

    /** The help synopsis. */
    public static String GetGamesCommandlet_help_synopsis;

    /** Information about a game. */
    public static String GetGamesCommandlet_output_gameInfo;

    /** No games are available. */
    public static String GetGamesCommandlet_output_noGames;


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

    // --- CreateGameCommandlet ---------------------------------------------

    /**
     * Gets the formatted message indicating a new game was created.
     * 
     * @param gameId
     *        The game identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a new game was created; never
     *         {@code null}.
     */
    /* @NonNull */
    static String CreateGameCommandlet_output_gameCreated(
        /* @NonNull */
        final String gameId )
    {
        return bind( CreateGameCommandlet_output_gameCreated, gameId );
    }

    /**
     * Gets the formatted message indicating the game system identifier is
     * unknown.
     * 
     * @param gameSystemId
     *        The game system identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the game system identifier is
     *         unknown; never {@code null}.
     */
    /* @NonNull */
    static String CreateGameCommandlet_ouput_unknownGameSystemId(
        /* @NonNull */
        final String gameSystemId )
    {
        return bind( CreateGameCommandlet_output_unknownGameSystemId, gameSystemId );
    }

    /**
     * Gets the formatted message indicating the game system identifier is
     * unknown.
     * 
     * @param gameSystemId
     *        The game system identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the game system identifier is
     *         unknown; never {@code null}.
     */
    /* @NonNull */
    static String CreateGameCommandlet_parseGameConfiguration_unknownGameSystemId(
        /* @NonNull */
        final String gameSystemId )
    {
        return bind( CreateGameCommandlet_parseGameConfiguration_unknownGameSystemId, gameSystemId );
    }

    // --- GetGameCommandlet ------------------------------------------------

    /**
     * Gets the formatted message containing information about the specified
     * game.
     * 
     * @param game
     *        The game; must not be {@code null}.
     * @param gameSystemUi
     *        The game system user interface; must not be {@code null}.
     * 
     * @return The formatted message containing information about the specified
     *         game; never {@code null}.
     */
    /* @NonNull */
    static String GetGameCommandlet_output_gameInfo(
        /* @NonNull */
        final IGame game,
        /* @NonNull */
        final IGameSystemUi gameSystemUi )
    {
        return bind( GetGameCommandlet_output_gameInfo, gameSystemUi.getName(), game.getName() );
    }

    // --- GetGameSystemsCommandlet -----------------------------------------

    /**
     * Gets the formatted message containing information about the game system.
     * 
     * @param gameSystemUi
     *        The game system user interface; must not be {@code null}.
     * 
     * @return The formatted message containing information about the game
     *         system; never {@code null}.
     */
    /* @NonNull */
    static String GetGameSystemsCommandlet_output_gameSystemInfo(
        /* @NonNull */
        final IGameSystemUi gameSystemUi )
    {
        return bind( GetGameSystemsCommandlet_output_gameSystemInfo, gameSystemUi.getId(), gameSystemUi.getName() );
    }

    // --- GetGamesCommandlet -----------------------------------------------

    /**
     * Gets the formatted message containing information about the game.
     * 
     * @param game
     *        The game; must not be {@code null}.
     * @param gameSystemUi
     *        The game system user interface; must not be {@code null}.
     * 
     * @return The formatted message containing information about the game;
     *         never {@code null}.
     */
    /* @NonNull */
    static String GetGamesCommandlet_output_gameInfo(
        /* @NonNull */
        final IGame game,
        /* @NonNull */
        final IGameSystemUi gameSystemUi )
    {
        return bind( GetGamesCommandlet_output_gameInfo, new Object[] {
            game.getId(), gameSystemUi.getName(), game.getName()
        } );
    }
}
