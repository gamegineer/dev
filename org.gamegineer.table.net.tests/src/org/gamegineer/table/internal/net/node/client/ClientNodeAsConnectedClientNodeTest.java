/*
 * ClientNodeAsConnectedClientNodeTest.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Apr 14, 2011 at 11:29:02 PM.
 */

package org.gamegineer.table.internal.net.node.client;

import org.gamegineer.table.core.ITable;
import org.gamegineer.table.internal.net.TableNetworkConfigurations;
import org.gamegineer.table.internal.net.TableNetworkControllers;
import org.gamegineer.table.internal.net.node.AbstractNodeUtils;
import org.gamegineer.table.internal.net.node.NodeLayerRunner;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.client.ClientNode} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.node.client.IClientNode} interface
 * while in the connected state.
 */
public final class ClientNodeAsConnectedClientNodeTest
    extends AbstractConnectedClientNodeTestCase<ClientNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ClientNodeAsConnectedClientNodeTest} class.
     */
    public ClientNodeAsConnectedClientNodeTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractConnectedNodeTestCase#createConnectedNode(org.gamegineer.table.core.ITable)
     */
    @Override
    protected ClientNode createConnectedNode(
        final ITable localTable )
        throws Exception
    {
        final ClientNode node = new ClientNode.Factory().createNode( TableNetworkControllers.createFakeTableNetworkController() );
        final NodeLayerRunner nodeLayerRunner = new NodeLayerRunner( node );
        node.setHandshakeComplete( null );
        nodeLayerRunner.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration( localTable ) );
        return node;
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractConnectedNodeTestCase#createNodeLayerRunner(org.gamegineer.table.internal.net.node.INode)
     */
    @Override
    protected NodeLayerRunner createNodeLayerRunner(
        final ClientNode node )
    {
        return new NodeLayerRunner( node );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractConnectedNodeTestCase#isRemoteNodeBound(org.gamegineer.table.internal.net.node.INode, java.lang.String)
     */
    @Override
    protected boolean isRemoteNodeBound(
        final ClientNode node,
        final String playerName )
    {
        return AbstractNodeUtils.isRemoteNodeBound( node, playerName );
    }
}
