/*
 * Messages.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Apr 15, 2011 at 1:32:38 PM.
 */

package org.gamegineer.table.internal.net.server;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage localized messages for the package.
 */
@ThreadSafe
final class Messages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    // --- RemoteClientTableGateway -----------------------------------------

    /** The player has not been authenticated. */
    public static String RemoteClientTableGateway_playerNotAuthenticated;

    // --- ServerNetworkTableStrategy ---------------------------------------

    /** The player has connected. */
    public static String ServerNetworkTableStrategy_playerConnected_playerConnected;

    /** The player has disconnected. */
    public static String ServerNetworkTableStrategy_playerDisconnected_playerDisconnected;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Messages} class.
     */
    static
    {
        NLS.initializeMessages( Messages.class.getName(), Messages.class );
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

    // --- ServerNetworkTableStrategy ---------------------------------------

    /**
     * Gets the formatted message indicating the player has connected.
     * 
     * @param playerName
     *        The name of the player that has connected; must not be {@code
     *        null}.
     * 
     * @return The formatted message indicating the player has connected; never
     *         {@code null}.
     */
    /* @NonNull */
    static String ServerNetworkTableStrategy_playerConnected_playerConnected(
        /* @NonNull */
        final String playerName )
    {
        return bind( ServerNetworkTableStrategy_playerConnected_playerConnected, playerName );
    }

    /**
     * Gets the formatted message indicating the player has disconnected.
     * 
     * @param playerName
     *        The name of the player that has disconnected; must not be {@code
     *        null}.
     * 
     * @return The formatted message indicating the player has disconnected;
     *         never {@code null}.
     */
    /* @NonNull */
    static String ServerNetworkTableStrategy_playerDisconnected_playerDisconnected(
        /* @NonNull */
        final String playerName )
    {
        return bind( ServerNetworkTableStrategy_playerDisconnected_playerDisconnected, playerName );
    }
}
