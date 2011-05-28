/*
 * RemoteServerNodeAsRemoteNodeTest.java
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
 * Created on May 15, 2011 at 7:01:53 PM.
 */

package org.gamegineer.table.internal.net.client;

import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.AbstractRemoteNodeTestCase;
import org.gamegineer.table.internal.net.INode;
import org.gamegineer.table.internal.net.IRemoteNode;
import org.gamegineer.table.internal.net.transport.FakeServiceContext;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.client.RemoteServerNode} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.IRemoteNode} interface.
 */
public final class RemoteServerNodeAsRemoteNodeTest
    extends AbstractRemoteNodeTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * RemoteServerNodeAsRemoteNodeTest} class.
     */
    public RemoteServerNodeAsRemoteNodeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.AbstractRemoteNodeTestCase#createRemoteNode()
     */
    @Override
    protected IRemoteNode createRemoteNode()
    {
        final RemoteServerNode remoteNode = new RemoteServerNode( EasyMock.createMock( INode.class ) );
        synchronized( remoteNode.getLock() )
        {
            remoteNode.started( new FakeServiceContext() );
            remoteNode.bind( "playerName" ); //$NON-NLS-1$
        }
        return remoteNode;
    }
}
