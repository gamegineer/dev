/*
 * IRemoteTableProxyController.java
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

package org.gamegineer.table.internal.net.common;

import net.jcip.annotations.GuardedBy;
import org.gamegineer.table.internal.net.ITableNetworkNode;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;

/**
 * The control interface for a proxy for a remote table connected to the table
 * network.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IRemoteTableProxyController
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Binds the table represented by the remote table proxy to the local node
     * for the specified player name.
     * 
     * @param playerName
     *        The name of the player associated with the table; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If a table with the same player name has already been bound to
     *         the local node.
     * @throws java.lang.IllegalStateException
     *         If the remote table proxy is closed or is already bound.
     * @throws java.lang.NullPointerException
     *         If {@code playerName} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    public void bind(
        /* @NonNull */
        String playerName );

    /**
     * Closes the remote table proxy.
     * 
     * @param error
     *        The error that caused the remote table proxy to be closed or
     *        {@code null} if the remote table proxy was closed normally.
     * 
     * @throws java.lang.IllegalStateException
     *         If the remote table proxy is closed.
     */
    @GuardedBy( "getLock()" )
    public void close(
        /* @Nullable */
        TableNetworkError error );

    /**
     * Gets the instance lock for the remote table proxy.
     * 
     * @return The instance lock for the remote table proxy; never {@code null}.
     */
    /* @NonNull */
    public Object getLock();

    /**
     * Gets the local table network node.
     * 
     * @return The local table network node; never {@code null}.
     */
    /* @NonNull */
    public ITableNetworkNode getNode();

    /**
     * Sends the specified message to the remote table proxy peer.
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
     *         If the remote table proxy is closed.
     * @throws java.lang.NullPointerException
     *         If {@code message} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    public boolean sendMessage(
        /* @NonNull */
        IMessage message,
        /* @Nullable */
        IMessageHandler messageHandler );


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * A remote table proxy message handler.
     * 
     * @noextend This interface is not intended to be extended by clients.
     */
    public interface IMessageHandler
    {
        // ==================================================================
        // Methods
        // ==================================================================

        /**
         * Handles the specified message for the associated remote table proxy.
         * 
         * <p>
         * This method will be invoked by the remote table proxy while its
         * instance lock is held. Thus, message handlers may assume any methods
         * they invoke on the remote table proxy will be thread-safe and atomic.
         * </p>
         * 
         * @param message
         *        The message; must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code message} is {@code null}.
         */
        public void handleMessage(
            /* @NonNull */
            IMessage message );
    }
}
