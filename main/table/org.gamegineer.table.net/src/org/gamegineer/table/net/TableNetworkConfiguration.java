/*
 * TableNetworkConfiguration.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Nov 12, 2010 at 9:47:16 PM.
 */

package org.gamegineer.table.net;

import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.core.ITable;

/**
 * The configuration for a table network.
 */
@Immutable
public final class TableNetworkConfiguration
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the table network host. */
    private final String hostName_;

    /** The name of the local player. */
    private final String localPlayerName_;

    /** The local table to attach to the table network. */
    private final ITable localTable_;

    /** The password used to authenticate connections to the table network. */
    private final SecureString password_;

    /** The port of the table network host. */
    private final int port_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkConfiguration}
     * class.
     * 
     * @param hostName
     *        The name of the table network host; must not be {@code null}.
     * @param port
     *        The port of the table network host.
     * @param password
     *        The password used to authenticate connections to the table
     *        network; must not be {@code null}.
     * @param localPlayerName
     *        The name of the local player; must not be {@code null}.
     * @param localTable
     *        The local table to attach to the table network; must not be
     *        {@code null}.
     */
    public TableNetworkConfiguration(
        final String hostName,
        final int port,
        final SecureString password,
        final String localPlayerName,
        final ITable localTable )
    {
        hostName_ = hostName;
        localPlayerName_ = localPlayerName;
        localTable_ = localTable;
        password_ = new SecureString( password );
        port_ = port;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the name of the table network host.
     * 
     * <p>
     * When hosting a table network, the return value is the local name to which
     * the table network will be bound. When joining a table network, the return
     * value is the remote name of the table network host.
     * </p>
     * 
     * @return The name of the table network host; never {@code null}.
     */
    public String getHostName()
    {
        return hostName_;
    }

    /**
     * Gets the name of the local player.
     * 
     * <p>
     * When hosting a table network, the return value is the name of the player
     * hosting the table network. When joining a table network, the return value
     * is the name of the player joining the table network.
     * </p>
     * 
     * @return The name of the local player; never {@code null}.
     */
    public String getLocalPlayerName()
    {
        return localPlayerName_;
    }

    /**
     * Gets the local table to attach to the table network.
     * 
     * <p>
     * When hosting a table network, the return value is the table to be hosted
     * on the table network. When joining a table network, the return value is
     * the table to be joined to the table network.
     * </p>
     * 
     * @return The local table to attach to the table network; never
     *         {@code null}.
     */
    public ITable getLocalTable()
    {
        return localTable_;
    }

    /**
     * Gets the password used to authenticate connections to the table network.
     * 
     * <p>
     * When hosting a table network, the return value is the password each
     * player must provide to connect to the table network. When joining a table
     * network, the return value is the password provided by the player joining
     * the table network.
     * </p>
     * 
     * @return The password used to authenticate connections to the table
     *         network; never {@code null}.
     */
    public SecureString getPassword()
    {
        return new SecureString( password_ );
    }

    /**
     * Gets the port of the table network host.
     * 
     * <p>
     * When hosting a table network, the return value is the local port to which
     * the table network will be bound. When joining a table network, the return
     * value is the remote port of the table network host.
     * </p>
     * 
     * @return The port of the table network; never {@code null}.
     */
    public int getPort()
    {
        return port_;
    }
}
