/*
 * IMessageHandler.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on May 30, 2011 at 8:40:18 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import org.gamegineer.table.internal.net.impl.transport.IMessage;

/**
 * A remote node message handler.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IMessageHandler
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles the specified message for the remote node associated with the
     * specified control interface.
     * 
     * <p>
     * This method will be invoked by the remote node while its instance lock is
     * held, as well as the instance lock for the local node.. Thus, message
     * handlers may assume any methods they invoke on the remote and local nodes
     * will be thread-safe and atomic.
     * </p>
     * 
     * @param remoteNodeController
     *        The control interface for the remote node that received the
     *        message; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    public void handleMessage(
        IRemoteNodeController<?> remoteNodeController,
        IMessage message );
}
