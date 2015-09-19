/*
 * ITableNetwork.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

import java.util.Collection;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A network of virtual game tables.
 * 
 * @noextend This interface is not intended to be extended by clients.
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
     */
    public void addTableNetworkListener(
        ITableNetworkListener listener );

    /**
     * Cancels the current network table control request made by the local
     * player.
     * 
     * <p>
     * This method does nothing if the local player has not made a network table
     * control request or if the table network is not connected.
     * </p>
     */
    public void cancelControlRequest();

    /**
     * Disconnects the table network.
     * 
     * <p>
     * This method blocks until the table network is disconnected. This method
     * does nothing if the table network is not connected.
     * </p>
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the table network
     *         to disconnect.
     */
    public void disconnect()
        throws InterruptedException;

    /**
     * Gets the local player connected to the table network.
     * 
     * @return The local player connected to the table network or {@code null}
     *         if the table network is not connected.
     */
    public @Nullable IPlayer getLocalPlayer();

    /**
     * Gets the collection of players connected to the table network.
     * 
     * @return The collection of players connected to the table network; never
     *         {@code null}.
     */
    public Collection<IPlayer> getPlayers();

    /**
     * Gives control of the network table to the specified player.
     * 
     * <p>
     * This method does nothing if the local player is not the table network
     * editor or if the table network is not connected.
     * </p>
     * 
     * @param player
     *        The player to receive control; must not be {@code null}.
     */
    public void giveControl(
        IPlayer player );

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
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the table network
     *         to connect.
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the connection cannot be established or the table network is
     *         already connected.
     */
    public void host(
        TableNetworkConfiguration configuration )
        throws TableNetworkException, InterruptedException;

    /**
     * Indicates the table network is connected.
     * 
     * @return {@code true} if the table network is connected; otherwise
     *         {@code false}.
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
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the table network
     *         to connect.
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the connection cannot be established or the table network is
     *         already connected.
     */
    public void join(
        TableNetworkConfiguration configuration )
        throws TableNetworkException, InterruptedException;

    /**
     * Removes the specified table network listener from this table network.
     * 
     * @param listener
     *        The table network listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered table network listener.
     */
    public void removeTableNetworkListener(
        ITableNetworkListener listener );

    /**
     * Requests that the local player be given control of the network table.
     * 
     * <p>
     * This method does nothing if the local player already has control of the
     * network table or if the table network is not connected.
     * </p>
     */
    public void requestControl();
}
