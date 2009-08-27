/*
 * IGameClient.java
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
 * Created on Dec 22, 2008 at 10:45:25 PM.
 */

package org.gamegineer.client.core;

import java.io.IOException;
import java.util.Collection;
import org.gamegineer.client.core.connection.IGameServerConnection;
import org.gamegineer.game.ui.system.IGameSystemUi;

/**
 * A game client.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IGameClient
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Connects this client to the server associated with the specified
     * connection.
     * 
     * <p>
     * The current connection, if any, will be disconnected before applying the
     * new connection.
     * </p>
     * 
     * <p>
     * If the new connection cannot be opened, this client will be left with no
     * connection.
     * </p>
     * 
     * @param connection
     *        The game server connection; must not be {@code null}.
     * 
     * @throws java.io.IOException
     *         If an error occurs while connecting to the server.
     * @throws java.lang.NullPointerException
     *         If {@code connection} is {@code null}.
     */
    public void connect(
        /* @NonNull */
        IGameServerConnection connection )
        throws IOException;

    /**
     * Disconnects this client from the connected server.
     * 
     * <p>
     * This method may be called even if this client is not connected.
     * </p>
     */
    public void disconnect();

    /**
     * Gets the game server connection.
     * 
     * <p>
     * This method always returns a valid connection even if the client is not
     * actually connected to a server. In such a case, the connection logically
     * represents no connection.
     * </p>
     * 
     * @return The game server connection; never {@code null}.
     */
    /* @NonNull */
    public IGameServerConnection getGameServerConnection();

    /**
     * Gets a game system user interface on this client.
     * 
     * @param gameSystemId
     *        The game system identifier; must not be {@code null}.
     * 
     * @return The game system user interface or {@code null} if a game system
     *         user interface with the specified identifier does not exist on
     *         this client.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemId} is {@code null}.
     */
    /* @Nullable */
    public IGameSystemUi getGameSystemUi(
        /* @NonNull */
        String gameSystemId );

    /**
     * Gets the game system user interfaces available to this client.
     * 
     * @return The game system user interfaces available to this client; never
     *         {@code null}.
     */
    /* @NonNull */
    public Collection<IGameSystemUi> getGameSystemUis();
}
