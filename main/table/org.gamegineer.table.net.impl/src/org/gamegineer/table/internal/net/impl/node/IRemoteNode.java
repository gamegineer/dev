/*
 * IRemoteNode.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Apr 8, 2011 at 9:19:53 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

/**
 * A proxy for a remote node connected to the table network.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IRemoteNode
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the name of the player associated with the remote node.
     * 
     * @return The name of the player associated with the remote node; never
     *         {@code null}.
     */
    /* @NonNull */
    public String getPlayerName();

    /**
     * Gets the table associated with the remote node.
     * 
     * @return The table associated with the remote node; never {@code null}.
     */
    /* @NonNull */
    public INetworkTable getTable();

    /**
     * Gracefully terminates the connection to the remote node.
     */
    public void goodbye();
}
