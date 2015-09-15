/*
 * IRemoteNodeController.java
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
 * Created on Apr 22, 2011 at 4:54:20 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.net.impl.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;

/**
 * The control interface for a remote node.
 * 
 * <p>
 * This interface provides operations that allow a message handler to control
 * its associated remote node. It is only intended for use by an implementation
 * of {@link IMessageHandler}.
 * </p>
 * 
 * @param <LocalNodeType>
 *        The type of the local table network node.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IRemoteNodeController<@NonNull LocalNodeType extends @NonNull INode<@NonNull ?>>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Binds the remote node to the local node for the specified player name.
     * 
     * @param playerName
     *        The name of the player associated with the remote node; must not
     *        be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If a remote node with the same player name has already been bound
     *         to the local node.
     * @throws java.lang.IllegalStateException
     *         If the remote node is closed or is already bound.
     */
    public void bind(
        String playerName );

    /**
     * Closes the remote node.
     * 
     * @param error
     *        The error that caused the remote node to be closed or {@code null}
     *        if the remote node was closed normally.
     * 
     * @throws java.lang.IllegalStateException
     *         If the remote node is closed.
     */
    public void close(
        @Nullable
        TableNetworkError error );

    /**
     * Gets the local table network node.
     * 
     * @return The local table network node; never {@code null}.
     */
    public LocalNodeType getLocalNode();

    /**
     * Gets the table associated with the remote node.
     * 
     * @return The table associated with the remote node; never {@code null}.
     */
    public INetworkTable getTable();

    /**
     * Sends the specified message to the remote node peer.
     * 
     * @param message
     *        The message; must not be {@code null}.
     * @param messageHandler
     *        The message handler to be invoked for any response to the
     *        specified message or {@code null} if no response is expected.
     * 
     * @throws java.lang.IllegalStateException
     *         If the remote node is closed.
     */
    public void sendMessage(
        IMessage message,
        @Nullable
        IMessageHandler messageHandler );
}
