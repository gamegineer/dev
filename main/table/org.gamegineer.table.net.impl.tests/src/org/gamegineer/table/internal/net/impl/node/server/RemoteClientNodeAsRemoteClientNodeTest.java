/*
 * RemoteClientNodeAsRemoteClientNodeTest.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on May 15, 2011 at 6:59:02 PM.
 */

package org.gamegineer.table.internal.net.impl.node.server;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.impl.node.INodeLayer;
import org.gamegineer.table.internal.net.impl.transport.FakeServiceContext;

/**
 * A fixture for testing the {@link RemoteClientNode} class to ensure it does
 * not violate the contract of the {@link IRemoteClientNode} interface.
 */
public final class RemoteClientNodeAsRemoteClientNodeTest
    extends AbstractRemoteClientNodeTestCase<RemoteClientNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code RemoteClientNodeAsRemoteClientNodeTest} class.
     */
    public RemoteClientNodeAsRemoteClientNodeTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a mock local node for use in the fixture.
     * 
     * @return A mock local node for use in the fixture; never {@code null}.
     */
    private static IServerNode createMockLocalNode()
    {
        final IMocksControl mocksControl = EasyMock.createNiceControl();
        final IServerNode localNode = nonNull( mocksControl.createMock( IServerNode.class ) );
        mocksControl.replay();
        return localNode;
    }

    /**
     * Creates a mock node layer for use in the fixture.
     * 
     * @return A mock node layer for use in the fixture; never {@code null}.
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
    protected RemoteClientNode createRemoteNode()
    {
        final RemoteClientNode remoteNode = new RemoteClientNode( createMockNodeLayer(), createMockLocalNode() );
        remoteNode.started( new FakeServiceContext() );
        remoteNode.bind( "playerName" ); //$NON-NLS-1$
        return remoteNode;
    }
}
