/*
 * ITableGatewayContext.java
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
 * Created on Apr 8, 2011 at 9:21:36 PM.
 */

package org.gamegineer.table.internal.net;

import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.net.NetworkTableException;

/**
 * The execution context for a table gateway.
 * 
 * <p>
 * Provides operations that allow a table gateway to interact with the network
 * table.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITableGatewayContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Disconnects the network table.
     */
    public void disconnectNetworkTable();

    // TODO: rename to disconnect(), but will require introducing inner classes
    // to avoid name collision with INetworkTableStrategy.disconnect()

    /**
     * Gets the local player name.
     * 
     * @return The local player name; never {@code null}.
     */
    /* @NonNull */
    public String getLocalPlayerName();

    /**
     * Gets the network password.
     * 
     * @return The network password; never {@code null}. The returned value is a
     *         copy and must be disposed when it is no longer needed.
     */
    /* @NonNull */
    public SecureString getPassword();

    // TODO: functionality needed for both client and server, but signature
    // doesn't need to be the same between both.  this is the server signature.

    /**
     * Invoked when a player has connected to the network table.
     * 
     * @param playerName
     *        The player name; must not be {@code null}.
     * @param tableGateway
     *        The table gateway associated with the player; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code playerName} or {@code tableGateway} is {@code null}.
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If a player with the same name has already connected to the
     *         network table.
     */
    public void playerConnected(
        /* @NonNull */
        String playerName,
        /* @NonNull */
        ITableGateway tableGateway )
        throws NetworkTableException;

    // TODO: behaves differently depending on strategy -- or do we refactor to a
    // more specialized interface, like IServerTableGatewayContext/IClientTableGatewayContext?

    /**
     * Invoked when a player has disconnected from the network table.
     * 
     * @param playerName
     *        The player name; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code playerName} is {@code null}.
     */
    public void playerDisconnected(
        /* @NonNull */
        String playerName );
}
