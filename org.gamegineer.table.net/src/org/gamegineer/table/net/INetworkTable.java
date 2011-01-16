/*
 * INetworkTable.java
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
 * Created on Nov 3, 2010 at 10:19:24 PM.
 */

package org.gamegineer.table.net;


/**
 * A network adapter for a virtual game table.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface INetworkTable
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified network table listener to this network table.
     * 
     * @param listener
     *        The network table listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered network table
     *         listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addNetworkTableListener(
        /* @NonNull */
        INetworkTableListener listener );

    /**
     * Disconnects from the network.
     * 
     * <p>
     * This method blocks until the network table is disconnected. This method
     * does nothing if the network is not connected.
     * </p>
     */
    public void disconnect();

    /**
     * Hosts the network table.
     * 
     * <p>
     * This method blocks until the network table is connected.
     * </p>
     * 
     * @param configuration
     *        The network table configuration; must not be {@code null}.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If the connection cannot be established or the network is already
     *         connected.
     */
    public void host(
        /* @NonNull */
        INetworkTableConfiguration configuration )
        throws NetworkTableException;

    /**
     * Indicates the network is connected.
     * 
     * @return {@code true} if the network is connected; otherwise {@code false}
     *         .
     */
    public boolean isConnected();

    /**
     * Joins another network table.
     * 
     * <p>
     * This method blocks until the network table is connected.
     * </p>
     * 
     * @param configuration
     *        The network table configuration; must not be {@code null}.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If the connection cannot be established or the network is already
     *         connected.
     */
    public void join(
        /* @NonNull */
        INetworkTableConfiguration configuration )
        throws NetworkTableException;

    /**
     * Removes the specified network table listener from this network table.
     * 
     * @param listener
     *        The network table listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered network table listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeNetworkTableListener(
        /* @NonNull */
        INetworkTableListener listener );
}
