/*
 * IMessageHandler.java
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
 * Created on May 30, 2011 at 8:40:18 PM.
 */

package org.gamegineer.table.internal.net.node;

import org.gamegineer.table.internal.net.transport.IMessage;

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
     * Handles the specified message for the associated remote node.
     * 
     * <p>
     * This method will be invoked by the remote node while its instance lock is
     * held. Thus, message handlers may assume any methods they invoke on the
     * remote node will be thread-safe and atomic.
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
