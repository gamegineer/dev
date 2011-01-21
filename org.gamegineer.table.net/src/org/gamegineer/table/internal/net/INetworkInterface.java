/*
 * INetworkInterface.java
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
 * Created on Jan 16, 2011 at 5:05:40 PM.
 */

package org.gamegineer.table.internal.net;

import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A network interface for a specific role and transport implementation.
 */
public interface INetworkInterface
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the network interface.
     * 
     * <p>
     * This method does nothing if the network interface is already closed.
     * </p>
     * 
     * <p>
     * This method blocks until the network interface is closed or an error
     * occurs.
     * </p>
     */
    public void close();

    /**
     * Opens the network interface.
     * 
     * <p>
     * This method blocks until the network interface is connected or an error
     * occurs.
     * </p>
     * 
     * @param configuration
     *        The network table configuration; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the network interface has already been opened or is closed.
     * @throws java.lang.NullPointerException
     *         If {@code configuration} is {@code null}.
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    public void open(
        /* @NonNull */
        INetworkTableConfiguration configuration )
        throws NetworkTableException;
}
