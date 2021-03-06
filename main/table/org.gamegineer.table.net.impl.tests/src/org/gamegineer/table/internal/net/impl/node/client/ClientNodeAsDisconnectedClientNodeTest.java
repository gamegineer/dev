/*
 * ClientNodeAsDisconnectedClientNodeTest.java
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
 * Created on May 29, 2011 at 5:34:04 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client;

import org.gamegineer.table.internal.net.impl.TableNetworkControllers;
import org.gamegineer.table.internal.net.impl.node.NodeLayerRunner;

/**
 * A fixture for testing the {@link ClientNode} class to ensure it does not
 * violate the contract of the {@link IClientNode} interface while in the
 * disconnected state.
 */
public final class ClientNodeAsDisconnectedClientNodeTest
    extends AbstractDisconnectedClientNodeTestCase<ClientNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ClientNodeAsDisconnectedClientNodeTest} class.
     */
    public ClientNodeAsDisconnectedClientNodeTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractDisconnectedNodeTestCase#createDisconnectedNode()
     */
    @Override
    protected ClientNode createDisconnectedNode()
        throws Exception
    {
        final ClientNode node = new ClientNode.Factory().createNode( TableNetworkControllers.createFakeTableNetworkController() );
        node.setHandshakeComplete( null );
        return node;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractDisconnectedNodeTestCase#createNodeLayerRunner(org.gamegineer.table.internal.net.impl.node.INode)
     */
    @Override
    protected NodeLayerRunner createNodeLayerRunner(
        final ClientNode node )
    {
        return new NodeLayerRunner( node );
    }
}
