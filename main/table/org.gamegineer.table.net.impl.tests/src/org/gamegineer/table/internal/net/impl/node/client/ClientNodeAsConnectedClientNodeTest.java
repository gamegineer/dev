/*
 * ClientNodeAsConnectedClientNodeTest.java
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
 * Created on Apr 14, 2011 at 11:29:02 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client;

import org.gamegineer.table.internal.net.impl.TableNetworkConfigurations;
import org.gamegineer.table.internal.net.impl.TableNetworkControllers;
import org.gamegineer.table.internal.net.impl.node.AbstractNodeUtils;
import org.gamegineer.table.internal.net.impl.node.NodeLayerRunner;

/**
 * A fixture for testing the {@link ClientNode} class to ensure it does not
 * violate the contract of the {@link IClientNode} interface while in the
 * connected state.
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
     * @see org.gamegineer.table.internal.net.impl.node.AbstractConnectedNodeTestCase#createConnectedNode()
     */
    @Override
    protected ClientNode createConnectedNode()
        throws Exception
    {
        final ClientNode node = new ClientNode.Factory().createNode( TableNetworkControllers.createFakeTableNetworkController() );
        final NodeLayerRunner nodeLayerRunner = new NodeLayerRunner( node );
        node.setHandshakeComplete( null );
        nodeLayerRunner.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );
        return node;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractConnectedNodeTestCase#createNodeLayerRunner(org.gamegineer.table.internal.net.impl.node.INode)
     */
    @Override
    protected NodeLayerRunner createNodeLayerRunner(
        final ClientNode node )
    {
        return new NodeLayerRunner( node );
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractConnectedNodeTestCase#isRemoteNodeBound(org.gamegineer.table.internal.net.impl.node.INode, java.lang.String)
     */
    @Override
    protected boolean isRemoteNodeBound(
        final ClientNode node,
        final String playerName )
    {
        return AbstractNodeUtils.isRemoteNodeBound( node, playerName );
    }
}
