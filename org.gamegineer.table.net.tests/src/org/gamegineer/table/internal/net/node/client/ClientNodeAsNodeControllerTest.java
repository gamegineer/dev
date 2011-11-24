/*
 * ClientNodeAsNodeControllerTest.java
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
 * Created on Apr 10, 2011 at 6:20:20 PM.
 */

package org.gamegineer.table.internal.net.node.client;

import org.gamegineer.table.internal.net.TableNetworkControllers;
import org.gamegineer.table.internal.net.node.AbstractAbstractNodeAsNodeControllerTestCase;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.client.ClientNode} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.node.INodeController} interface.
 */
public final class ClientNodeAsNodeControllerTest
    extends AbstractAbstractNodeAsNodeControllerTestCase<ClientNode, IRemoteServerNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientNodeAsNodeControllerTest}
     * class.
     */
    public ClientNodeAsNodeControllerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNodeControllerTestCase#createNodeController()
     */
    @Override
    protected ClientNode createNodeController()
        throws Exception
    {
        final ClientNode node = new ClientNode.Factory().createNode( TableNetworkControllers.createFakeTableNetworkController() );
        node.setHandshakeComplete( null );
        return node;
    }
}
