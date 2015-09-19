/*
 * INode.java
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
 * Created on Apr 8, 2011 at 9:21:36 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.net.TableNetworkError;

/**
 * A local node in a table network.
 * 
 * <p>
 * A node is responsible for managing the local table and its interaction with
 * the other remote nodes on the network and their associated tables.
 * </p>
 * 
 * @param <RemoteNodeType>
 *        The type of the remote node managed by the local table network node.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface INode<RemoteNodeType extends IRemoteNode>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Binds the specified remote node to the local table network node.
     * 
     * @param remoteNode
     *        The remote node; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the table network is connected and {@code remoteNode} is
     *         already bound to the local table network node.
     * @throws java.lang.IllegalStateException
     *         If the table network is not connected.
     */
    public void bindRemoteNode(
        RemoteNodeType remoteNode );

    /**
     * Disconnects the local table network node for the specified cause.
     * 
     * @param error
     *        The error that caused the local table network node to be
     *        disconnected or {@code null} if the local table network node was
     *        disconnected normally.
     */
    public void disconnect(
        @Nullable TableNetworkError error );

    /**
     * Gets the table network password.
     * 
     * @return The table network password; never {@code null}. The returned
     *         value is a copy and must be disposed when it is no longer needed.
     * 
     * @throws java.lang.IllegalStateException
     *         If the table network is not connected.
     */
    public SecureString getPassword();

    /**
     * Gets the name of the player associated with the local table network node.
     * 
     * @return The name of the player associated with the local table network
     *         node; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the table network is not connected.
     */
    public String getPlayerName();

    /**
     * Gets the table manager for the local table network node.
     * 
     * @return The table manager for the local table network node; never
     *         {@code null}.
     */
    public ITableManager getTableManager();

    /**
     * Unbinds the specified remote node from the local table network node.
     * 
     * @param remoteNode
     *        The remote node; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If the table network is connected and {@code remoteNode} is not
     *         bound to the local table network node.
     * @throws java.lang.IllegalStateException
     *         If the table network is not connected.
     */
    public void unbindRemoteNode(
        RemoteNodeType remoteNode );
}
