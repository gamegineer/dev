/*
 * RemoteServerNodeAsRemoteServerNodeTest.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on May 15, 2011 at 7:01:53 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.impl.node.INodeLayer;
import org.gamegineer.table.internal.net.impl.transport.FakeServiceContext;

/**
 * A fixture for testing the {@link RemoteServerNode} class to ensure it does
 * not violate the contract of the {@link IRemoteServerNode} interface.
 */
public final class RemoteServerNodeAsRemoteServerNodeTest
    extends AbstractRemoteServerNodeTestCase<RemoteServerNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code RemoteServerNodeAsRemoteServerNodeTest} class.
     */
    public RemoteServerNodeAsRemoteServerNodeTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a mock local node for use in the fixture.
     * 
     * @return A mock local node for use in the fixture.
     */
    private static IClientNode createMockLocalNode()
    {
        final IMocksControl mocksControl = EasyMock.createNiceControl();
        final IClientNode localNode = mocksControl.createMock( IClientNode.class );
        mocksControl.replay();
        return localNode;
    }

    /**
     * Creates a mock node layer for use in the fixture.
     * 
     * @return A mock node layer for use in the fixture.
     */
    @SuppressWarnings( "boxing" )
    private static INodeLayer createMockNodeLayer()
    {
        final IMocksControl mocksControl = EasyMock.createControl();
        final INodeLayer nodeLayer = mocksControl.createMock( INodeLayer.class );
        EasyMock.expect( nodeLayer.isNodeLayerThread() ).andReturn( true ).anyTimes();
        mocksControl.replay();
        return nodeLayer;
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractRemoteNodeTestCase#createRemoteNode()
     */
    @Override
    protected RemoteServerNode createRemoteNode()
    {
        final RemoteServerNode remoteNode = new RemoteServerNode( createMockNodeLayer(), createMockLocalNode() );
        remoteNode.started( new FakeServiceContext() );
        remoteNode.bind( "playerName" ); //$NON-NLS-1$
        return remoteNode;
    }
}
