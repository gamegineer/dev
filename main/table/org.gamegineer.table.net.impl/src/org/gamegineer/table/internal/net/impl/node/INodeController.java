/*
 * INodeController.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl.node;

import java.util.Collection;
import java.util.concurrent.Future;
import org.gamegineer.table.internal.net.impl.ITableNetworkController;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.TableNetworkConfiguration;
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
     * Begins an asynchronous operation to connect the table network node to the
     * table network using the specified configuration.
     * 
     * @param configuration
     *        The table network configuration; must not be {@code null}.
     * 
     * @return An asynchronous completion token for the operation; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code configuration} is {@code null}.
     */
    /* @NonNull */
    public Future<Void> beginConnect(
        /* @NonNull */
        TableNetworkConfiguration configuration );

    /**
     * Begins an asynchronous operation to disconnect the table network node
     * from the table network.
     * 
     * <p>
     * This method does nothing if the table network node is not connected.
     * </p>
     * 
     * @return An asynchronous completion token for the operation; never
     *         {@code null}.
     */
    /* @NonNull */
    public Future<Void> beginDisconnect();

    /**
     * Cancels the current network table control request made by the active
     * player.
     * 
     * <p>
     * This method does nothing if the active player has not made a network
     * table control request.
     * </p>
     */
    public void cancelControlRequest();

    /**
     * Ends the asynchronous operation to connect the table network node to the
     * table network associated with the specified asynchronous token.
     * 
     * <p>
     * This method blocks until the table network node is connected or an error
     * occurs.
     * </p>
     * 
     * @param future
     *        The asynchronous completion token associated with the operation;
     *        must not be {@code null}.
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the table network
     *         node to be connected.
     * @throws java.lang.NullPointerException
     *         If {@code future} is {@code null}.
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the connection cannot be established or the table network node
     *         is already connected.
     */
    public void endConnect(
        /* @NonNull  */
        Future<Void> future )
        throws TableNetworkException, InterruptedException;

    /**
     * Ends the asynchronous operation to disconnect the table network node from
     * the table network associated with the specified asynchronous token.
     * 
     * <p>
     * This method blocks until the table network node is disconnected.
     * </p>
     * 
     * @param future
     *        The asynchronous completion token associated with the operation;
     *        must not be {@code null}.
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the table network
     *         node to be disconnected.
     * @throws java.lang.NullPointerException
     *         If {@code future} is {@code null}.
     */
    public void endDisconnect(
        /* @NonNull */
        Future<Void> future )
        throws InterruptedException;

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
     * This method does nothing if the active player is not the table network
     * editor.
     * </p>
     * 
     * @param playerName
     *        The name of the player to receive control; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code playerName} is {@code null}.
     */
    public void giveControl(
        /* @NonNull */
        String playerName );

    /**
     * Requests that the active player be given control of the network table.
     * 
     * <p>
     * This method does nothing if the active player already has control of the
     * network table.
     * </p>
     */
    public void requestControl();
}
