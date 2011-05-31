/*
 * RemoteServerNodeAsRemoteServerNodeControllerTest.java
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
 * Created on Apr 23, 2011 at 9:16:57 PM.
 */

package org.gamegineer.table.internal.net.node.client;

import org.gamegineer.table.internal.net.transport.FakeServiceContext;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.client.RemoteServerNode} class
 * to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.node.client.IRemoteServerNodeController}
 * interface.
 */
public final class RemoteServerNodeAsRemoteServerNodeControllerTest
    extends AbstractRemoteServerNodeControllerTestCase<RemoteServerNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * RemoteServerNodeAsRemoteServerNodeControllerTest} class.
     */
    public RemoteServerNodeAsRemoteServerNodeControllerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractRemoteNodeControllerTestCase#createRemoteNodeController(org.gamegineer.table.internal.net.node.INode)
     */
    @Override
    protected RemoteServerNode createRemoteNodeController(
        final IClientNode node )
    {
        return new RemoteServerNode( node );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractRemoteNodeControllerTestCase#openRemoteNode(org.gamegineer.table.internal.net.node.IRemoteNodeController)
     */
    @Override
    protected void openRemoteNode(
        final RemoteServerNode controller )
    {
        controller.started( new FakeServiceContext() );
    }
}
