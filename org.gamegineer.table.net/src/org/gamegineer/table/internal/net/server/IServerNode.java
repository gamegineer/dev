/*
 * IServerNode.java
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
 * Created on May 27, 2011 at 10:02:02 PM.
 */

package org.gamegineer.table.internal.net.server;

import net.jcip.annotations.GuardedBy;
import org.gamegineer.table.internal.net.INode;

/**
 * A local server node in a table network.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IServerNode
    extends INode<IRemoteClientNode>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates the player with the specified name is connected to the table
     * network.
     * 
     * @param playerName
     *        The player name; must not be {@code null}.
     * 
     * @return {@code true} if a player with the specified name is connected to
     *         the table network; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code playerName} is {@code null}.
     */
    @GuardedBy( "getLock()" )
    public boolean isPlayerConnected(
        /* @NonNull */
        String playerName );
}
