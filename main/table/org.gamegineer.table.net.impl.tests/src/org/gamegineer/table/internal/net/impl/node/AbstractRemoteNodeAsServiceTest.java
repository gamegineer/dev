/*
 * AbstractRemoteNodeAsServiceTest.java
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
 * Created on Apr 23, 2011 at 9:40:52 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.impl.transport.AbstractServiceTestCase;
import org.gamegineer.table.internal.net.impl.transport.IService;

/**
 * A fixture for testing the {@link AbstractRemoteNode} class to ensure it does
 * not violate the contract of the {@link IService} interface.
 */
public final class AbstractRemoteNodeAsServiceTest
    extends AbstractServiceTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRemoteNodeAsServiceTest}
     * class.
     */
    public AbstractRemoteNodeAsServiceTest()
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
    private static INode<IRemoteNode> createMockLocalNode()
    {
        final IMocksControl mocksControl = EasyMock.createControl();
        final INode<IRemoteNode> localNode = mocksControl.createMock( INode.class );
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
     * @see org.gamegineer.table.internal.net.impl.transport.AbstractServiceTestCase#createService()
     */
    @Override
    protected IService createService()
    {
        return new AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode>( createMockNodeLayer(), createMockLocalNode() )
        {
            @Override
            protected IRemoteNode getThisAsRemoteNodeType()
            {
                return this;
            }
        };
    }
}
