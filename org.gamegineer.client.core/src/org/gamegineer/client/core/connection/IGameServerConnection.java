/*
 * IGameServerConnection.java
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
 * Created on Dec 22, 2008 at 10:42:51 PM.
 */

package org.gamegineer.client.core.connection;

import java.io.Closeable;
import java.io.IOException;
import java.security.Principal;
import org.gamegineer.server.core.IGameServer;

/**
 * A connection to a game server.
 * 
 * <p>
 * A newly-created object of this type is not connected until its {@link #open}
 * method has been called. Once a connection has been closed, it cannot be
 * re-opened.
 * </p>
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IGameServerConnection
    extends Closeable
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes this connection to the server.
     * 
     * <p>
     * This method does nothing if this connection is already closed. If this
     * connection has never been opened, it will be moved to the closed state
     * and no exception will be thrown.
     * </p>
     * 
     * @throws java.io.IOException
     *         If this connection could not be closed.
     */
    public void close()
        throws IOException;

    /**
     * Gets the game server associated with this connection.
     * 
     * <p>
     * The returned game server will throw a {@code RemoteException} if any of
     * its methods are invoked after this connection has been closed.
     * </p>
     * 
     * @return The game server associated with this connection; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If this connection has not yet been opened or it has been closed.
     */
    /* @NonNull */
    public IGameServer getGameServer();

    /**
     * Gets the name of this connection.
     * 
     * @return The name of this connection; never {@code null}.
     */
    /* @NonNull */
    public String getName();

    /**
     * Gets the user principal associated with this connection.
     * 
     * @return The user principal associated with this connection; never
     *         {@code null}.
     */
    /* @NonNull */
    public Principal getUserPrincipal();

    /**
     * Opens this connection to the game server.
     * 
     * <p>
     * This method does nothing if this connection is already opened. If this
     * connection has been closed, calling this method will throw an exception.
     * </p>
     * 
     * @throws java.io.IOException
     *         If this connection could not be opened.
     */
    public void open()
        throws IOException;
}
