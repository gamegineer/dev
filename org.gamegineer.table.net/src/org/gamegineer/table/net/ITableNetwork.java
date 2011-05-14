/*
 * ITableNetwork.java
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
 * Created on Nov 3, 2010 at 10:19:24 PM.
 */

package org.gamegineer.table.net;

/**
 * A network of virtual game tables.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ITableNetwork
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified table network listener to this table network.
     * 
     * @param listener
     *        The table network listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered table network
     *         listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addTableNetworkListener(
        /* @NonNull */
        ITableNetworkListener listener );

    /**
     * Disconnects the table network.
     * 
     * <p>
     * This method blocks until the table network is disconnected. This method
     * does nothing if the table network is not connected.
     * </p>
     */
    public void disconnect();

    /**
     * Hosts a new table network.
     * 
     * <p>
     * This method blocks until the table network is connected.
     * </p>
     * 
     * @param configuration
     *        The table network configuration; must not be {@code null}.
     * 
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the connection cannot be established or the table network is
     *         already connected.
     */
    public void host(
        /* @NonNull */
        ITableNetworkConfiguration configuration )
        throws TableNetworkException;

    /**
     * Indicates the table network is connected.
     * 
     * @return {@code true} if the table network is connected; otherwise {@code
     *         false}.
     */
    public boolean isConnected();

    /**
     * Joins an existing table network.
     * 
     * <p>
     * This method blocks until the table network is connected.
     * </p>
     * 
     * @param configuration
     *        The table network configuration; must not be {@code null}.
     * 
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the connection cannot be established or the table network is
     *         already connected.
     */
    public void join(
        /* @NonNull */
        ITableNetworkConfiguration configuration )
        throws TableNetworkException;

    /**
     * Removes the specified table network listener from this table network.
     * 
     * @param listener
     *        The table network listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered table network listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeTableNetworkListener(
        /* @NonNull */
        ITableNetworkListener listener );
}
