/*
 * IRemoteServerNode.java
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
 * Created on May 27, 2011 at 11:11:47 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client;

import org.gamegineer.table.internal.net.impl.node.IRemoteNode;

/**
 * A proxy for a remote server node connected to the table network.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IRemoteServerNode
    extends IRemoteNode
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Cancels the current network table control request made by the local
     * player.
     */
    public void cancelControlRequest();

    /**
     * Gives control of the network table to the player with the specified name.
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
     * Requests that the local player be given control of the network table.
     */
    public void requestControl();
}
