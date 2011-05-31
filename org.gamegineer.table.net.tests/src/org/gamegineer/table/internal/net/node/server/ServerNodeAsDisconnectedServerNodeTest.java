/*
 * ServerNodeAsDisconnectedServerNodeTest.java
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
 * Created on May 29, 2011 at 5:37:42 PM.
 */

package org.gamegineer.table.internal.net.node.server;

import org.gamegineer.table.internal.net.TableNetworkControllers;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.server.ServerNode} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.node.server.IServerNode} interface
 * while in the disconnected state.
 */
public final class ServerNodeAsDisconnectedServerNodeTest
    extends AbstractDisconnectedServerNodeTestCase<ServerNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * ServerNodeAsDisconnectedServerNodeTest} class.
     */
    public ServerNodeAsDisconnectedServerNodeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractDisconnectedNodeTestCase#createDisconnectedNode()
     */
    @Override
    protected ServerNode createDisconnectedNode()
    {
        return new ServerNode( TableNetworkControllers.createFakeTableNetworkController() );
    }
}
