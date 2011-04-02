/*
 * INetworkInterfaceContext.java
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
 * Created on Apr 1, 2011 at 10:45:38 PM.
 */

package org.gamegineer.table.internal.net;

/**
 * The execution context for a network interface.
 * 
 * <p>
 * Provides operations that allow a network interface to interact with its
 * associated network table.
 * </p>
 */
public interface INetworkInterfaceContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new client network service handler.
     * 
     * @return A new client network service handler; never {@code null}.
     */
    /* @NonNull */
    public INetworkServiceHandler createClientNetworkServiceHandler();

    /**
     * Creates a new server network service handler.
     * 
     * @return A new server network service handler; never {@code null}.
     */
    /* @NonNull */
    public INetworkServiceHandler createServerNetworkServiceHandler();

    /**
     * Invoked by the network interface when it has been disconnected.
     * 
     * <p>
     * Network tables must still explicitly close the network interface to clean
     * up resources even after it has been disconnected.
     * </p>
     */
    public void networkInterfaceDisconnected();
}
