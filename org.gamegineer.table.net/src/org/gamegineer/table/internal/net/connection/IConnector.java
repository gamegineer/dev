/*
 * IConnector.java
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
 * Created on Jan 6, 2011 at 10:59:38 PM.
 */

package org.gamegineer.table.internal.net.connection;

import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A connector in the network table Acceptor-Connector pattern implementation.
 * 
 * <p>
 * A connector is responsible for actively connecting and initializing a network
 * table client-side service handler.
 * </p>
 * 
 * @param <H>
 *        The type of the transport handle.
 * @param <E>
 *        The type of the event.
 */
public interface IConnector<H, E>
    extends IEventHandler<H, E>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Opens the connector and connects to the peer host.
     * 
     * <p>
     * This method blocks until the connection is established or an error
     * occurs.
     * </p>
     * 
     * @param configuration
     *        The network table configuration; must not be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If an attempt has already been made to connect to the peer host.
     * @throws java.lang.NullPointerException
     *         If {@code configuration} is {@code null}.
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs
     */
    public void connect(
        /* @NonNull */
        INetworkTableConfiguration configuration )
        throws NetworkTableException;
}
