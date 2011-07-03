/*
 * IRemoteNodeController.java
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
 * Created on Apr 22, 2011 at 4:54:20 PM.
 */

package org.gamegineer.table.internal.net.node;

import net.jcip.annotations.GuardedBy;
import org.gamegineer.table.internal.net.transport.IMessage;
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
public interface IRemoteNodeController<LocalNodeType extends INode<?>>
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
     * @throws java.lang.NullPointerException
     *         If {@code playerName} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    public void bind(
        /* @NonNull */
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
    @GuardedBy( "getLock()" )
    public void close(
        /* @Nullable */
        TableNetworkError error );

    /**
     * Gets the local table network node.
     * 
     * @return The local table network node; never {@code null}.
     */
    /* @NonNull */
    public LocalNodeType getLocalNode();

    /**
     * Gets the instance lock for the remote node.
     * 
     * @return The instance lock for the remote node; never {@code null}.
     */
    /* @NonNull */
    public Object getLock();

    /**
     * Gets the table associated with the remote node.
     * 
     * @return The table associated with the remote node; never {@code null}.
     */
    /* @NonNull */
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
     * @return {@code true} if the message was sent successfully; otherwise
     *         {@code false}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the remote node is closed.
     * @throws java.lang.NullPointerException
     *         If {@code message} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    public boolean sendMessage(
        /* @NonNull */
        IMessage message,
        /* @Nullable */
        IMessageHandler messageHandler );
}
