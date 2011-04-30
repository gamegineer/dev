/*
 * ITransportLayer.java
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

package org.gamegineer.table.internal.net.transport;

/**
 * A network transport layer.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITransportLayer
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Closes the transport layer.
     * 
     * <p>
     * This method does nothing if the transport layer is already closed.
     * </p>
     * 
     * <p>
     * This method blocks until the transport layer is closed or an error
     * occurs.
     * </p>
     */
    public void close();

    /**
     * Opens the transport layer.
     * 
     * <p>
     * This method blocks until the transport layer is connected or an error
     * occurs.
     * </p>
     * 
     * @param hostName
     *        The host name; must not be {@code null}. For a passive transport
     *        layer, this value is the host name to which all services will be
     *        bound. For an active transport layer, this value is the host name
     *        of the remote service.
     * @param port
     *        The port. For a passive transport layer, this value is the port to
     *        which all services will be bound. For an active transport layer,
     *        this value is the port of the remote service.
     * 
     * @throws java.lang.IllegalStateException
     *         If the transport layer has already been opened or is closed.
     * @throws java.lang.NullPointerException
     *         If {@code hostName} is {@code null}.
     * @throws org.gamegineer.table.internal.net.transport.TransportException
     *         If an error occurs.
     */
    public void open(
        /* @NonNull */
        String hostName,
        int port )
        throws TransportException;
}
