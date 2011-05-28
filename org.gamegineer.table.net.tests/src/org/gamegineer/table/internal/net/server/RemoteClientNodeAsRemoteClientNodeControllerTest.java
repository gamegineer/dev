/*
 * RemoteClientNodeAsRemoteClientNodeControllerTest.java
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
 * Created on Apr 23, 2011 at 9:26:14 PM.
 */

package org.gamegineer.table.internal.net.server;

import org.gamegineer.table.internal.net.transport.FakeServiceContext;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.server.RemoteClientNode} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.server.IRemoteClientNodeController}
 * interface.
 */
public final class RemoteClientNodeAsRemoteClientNodeControllerTest
    extends AbstractRemoteClientNodeControllerTestCase<RemoteClientNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * RemoteClientNodeAsRemoteClientNodeControllerTest} class.
     */
    public RemoteClientNodeAsRemoteClientNodeControllerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteNodeControllerTestCase#createRemoteNodeController(org.gamegineer.table.internal.net.INode)
     */
    @Override
    protected RemoteClientNode createRemoteNodeController(
        final IServerNode node )
    {
        return new RemoteClientNode( node );
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteNodeControllerTestCase#openRemoteNode(org.gamegineer.table.internal.net.common.IRemoteNodeController)
     */
    @Override
    protected void openRemoteNode(
        final RemoteClientNode controller )
    {
        controller.started( new FakeServiceContext() );
    }
}
