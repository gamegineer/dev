/*
 * IClientNode.java
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
 * Created on May 27, 2011 at 10:01:10 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client;

import java.util.Collection;
import org.gamegineer.table.internal.net.impl.node.INode;
import org.gamegineer.table.net.IPlayer;

/**
 * A local client node in a table network.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IClientNode
    extends INode<IRemoteServerNode>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets the collection of players connected to the table network.
     * 
     * @param players
     *        The collection of players connected to the table network; must not
     *        be {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the table network is not connected.
     * @throws java.lang.NullPointerException
     *         If {@code players} is {@code null}.
     */
    public void setPlayers(
        /* @NonNull */
        Collection<IPlayer> players );
}
