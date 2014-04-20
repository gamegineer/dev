/*
 * IServiceContext.java
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
 * Created on Mar 25, 2011 at 10:40:39 PM.
 */

package org.gamegineer.table.internal.net.impl.transport;

/**
 * The execution context for a network service.
 * 
 * <p>
 * Provides operations that allow a service to interact with the transport
 * layer.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IServiceContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sends the specified message to the service peer.
     * 
     * @param message
     *        The message; must not be {@code null}.
     */
    public void sendMessage(
        IMessage message );

    /**
     * Stops the service.
     * 
     * <p>
     * Any pending messages are guaranteed to be sent to the peer before the
     * service is stopped.
     * </p>
     */
    public void stopService();
}
