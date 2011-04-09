/*
 * INetworkServiceContext.java
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
 * Created on Mar 25, 2011 at 10:40:39 PM.
 */

package org.gamegineer.table.internal.net.transport;

/**
 * The execution context for a network service handler.
 * 
 * <p>
 * Provides operations that allow a network service handler to interact and
 * control its associated network interface.
 * </p>
 */
public interface INetworkServiceContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sends the specified message to the service handler peer.
     * 
     * @param message
     *        The message; must not be {@code null}.
     * 
     * @return {@code true} if the message was sent successfully; otherwise
     *         {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code message} is {@code null}.
     */
    public boolean sendMessage(
        /* @NonNull */
        IMessage message );

    /**
     * Stops the network service.
     * 
     * <p>
     * Any pending messages are guaranteed to be sent to the peer before the
     * network service is stopped.
     * </p>
     */
    public void stopService();
}
