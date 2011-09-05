/*
 * INodeController.java
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

package org.gamegineer.table.internal.net.node;

import java.util.Collection;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkException;

/**
 * The control interface for a local table network node.
 * 
 * <p>
 * This interface provides operations that allow a table network controller to
 * control the local table network node. It is only intended for use by an
 * implementation of {@link ITableNetworkController}.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface INodeController
{
    // ======================================================================
    // Methods
    // ======================================================================

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
     * Connects the table network node to the table network using the specified
     * configuration.
     * 
     * <p>
     * This method blocks until the table network node is connected.
     * </p>
     * 
     * @param configuration
     *        The table network configuration; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code configuration} is {@code null}.
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the connection cannot be established or the table network node
     *         is already connected.
     */
    public void connect(
        /* @NonNull */
        ITableNetworkConfiguration configuration )
        throws TableNetworkException;

    /**
     * Disconnects the table network node from the table network.
     * 
     * <p>
     * This method blocks until the table network node is disconnected. This
     * method does nothing if the table network node is not connected.
     * </p>
     */
    public void disconnect();

    /**
     * Gets the player associated with the table network node.
     * 
     * @return The player associated with the table network node or {@code null}
     *         if the table network is not connected.
     */
    /* @Nullable */
    public IPlayer getPlayer();

    /**
     * Gets the collection of players connected to the table network.
     * 
     * @return The collection of players connected to the table network; never
     *         {@code null}.
     */
    /* @NonNull */
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
     * 
     * @throws java.lang.NullPointerException
     *         If {@code player} is {@code null}.
     */
    public void giveControl(
        /* @NonNull */
        IPlayer player );

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
