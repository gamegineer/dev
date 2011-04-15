/*
 * INetworkTableStrategy.java
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
 * Created on Apr 8, 2011 at 9:16:16 PM.
 */

package org.gamegineer.table.internal.net;

import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A strategy for the behavior of a network table.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface INetworkTableStrategy
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Connects to the network using the specified configuration.
     * 
     * <p>
     * This method blocks until the network is connected.
     * </p>
     * 
     * @param configuration
     *        The network table configuration; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code configuration} is {@code null}.
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If the connection cannot be established or the network is already
     *         connected.
     */
    public void connect(
        /* @NonNull */
        INetworkTableConfiguration configuration )
        throws NetworkTableException;

    /**
     * Disconnects from the network.
     * 
     * <p>
     * This method blocks until the network is disconnected. This method does
     * nothing if the network is not connected.
     * </p>
     */
    public void disconnect();
}
