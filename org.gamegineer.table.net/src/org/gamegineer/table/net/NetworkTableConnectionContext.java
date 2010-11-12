/*
 * NetworkTableConnectionContext.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Nov 4, 2010 at 11:47:54 PM.
 */

package org.gamegineer.table.net;

import java.net.InetAddress;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.security.SecureString;

/**
 * A context for establishing a network table connection.
 */
@Immutable
public final class NetworkTableConnectionContext
{
    // ======================================================================
    // Fields
    // ======================================================================

    // TODO: add fields


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableConnectionContext}
     * class.
     */
    public NetworkTableConnectionContext()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the address of the network table.
     * 
     * <p>
     * When hosting a network table, the return value is the address to which
     * the network table will be bound. When joining a network table, the return
     * value is the address of the remote network table.
     * </p>
     * 
     * @return The address of the network table; never {@code null}.
     */
    /* @NonNull */
    public InetAddress getAddress()
    {
        // TODO
        return null;
    }

    /**
     * Gets the name of the local player.
     * 
     * <p>
     * When hosting a network table, the return value is the name of the player
     * hosting the network table. When joining a network table, the return value
     * is the name of the player joining the network table.
     * </p>
     * 
     * @return The name of the local player; never {@code null}.
     */
    /* @NonNull */
    public String getLocalPlayerName()
    {
        // TODO
        return null;
    }

    /**
     * Gets the password used to authenticate the connection.
     * 
     * <p>
     * When hosting a network table, the return value is the password each
     * player must provide to connect to the network table. When joining a
     * network table, the return value is the password provided by the player
     * joining the network table.
     * </p>
     * 
     * @return The password used to authenticate the connection; never {@code
     *         null}.
     */
    /* @NonNull */
    public SecureString getPassword()
    {
        // TODO
        return null;
    }

    /**
     * Gets the port of the network table.
     * 
     * <p>
     * When hosting a network table, the return value is the port to which the
     * network table will be bound. When joining a network table, the return
     * value is the port of the remote network table.
     * </p>
     * 
     * @return The port of the network table; never {@code null}.
     */
    public int getPort()
    {
        // TODO
        return 0;
    }
}