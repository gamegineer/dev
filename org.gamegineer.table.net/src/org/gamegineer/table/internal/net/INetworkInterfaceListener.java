/*
 * INetworkInterfaceListener.java
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
 * Created on Feb 12, 2011 at 10:06:15 PM.
 */

package org.gamegineer.table.internal.net;

import java.util.EventListener;

/**
 * The listener interface for use by clients to be notified of changes to the
 * network interface state.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface INetworkInterfaceListener
    extends EventListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked by the network interface when it has been disconnected.
     * 
     * <p>
     * Clients must still explicitly close the network interface to clean up
     * resources even after it has been disconnected.
     * </p>
     */
    public void networkInterfaceDisconnected();

    /**
     * Invoked by the network interface when a remote player has connected.
     * 
     * @param playerId
     *        The identifier of the remote player that has connected; must not
     *        be {@code null}.
     */
    public void playerConnected(
        /* @NonNull */
        String playerId );

    /**
     * Invoked by the network interface when a remote player has disconnected.
     * 
     * @param playerId
     *        The identifier of the remote player that has disconnected; must
     *        not be {@code null}.
     */
    public void playerDisconnected(
        /* @NonNull */
        String playerId );
}
