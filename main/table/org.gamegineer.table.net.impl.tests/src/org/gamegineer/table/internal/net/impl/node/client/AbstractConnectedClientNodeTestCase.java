/*
 * AbstractConnectedClientNodeTestCase.java
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
 * Created on May 27, 2011 at 10:06:08 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.impl.node.AbstractConnectedNodeTestCase;
import org.gamegineer.table.internal.net.impl.node.INetworkTable;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IClientNode} interface while in the connected state.
 * 
 * @param <T>
 *        The type of the client node.
 */
public abstract class AbstractConnectedClientNodeTestCase<T extends IClientNode>
    extends AbstractConnectedNodeTestCase<T, IRemoteServerNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractConnectedClientNodeTestCase} class.
     */
    protected AbstractConnectedClientNodeTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractConnectedNodeTestCase#createMockRemoteNode(org.easymock.IMocksControl)
     */
    @Override
    protected IRemoteServerNode createMockRemoteNode(
        final IMocksControl mocksControl )
    {
        final IRemoteServerNode remoteNode = mocksControl.createMock( IRemoteServerNode.class );
        EasyMock.expect( remoteNode.getPlayerName() ).andReturn( "newPlayerName" ).anyTimes(); //$NON-NLS-1$
        EasyMock.expect( remoteNode.getTable() ).andReturn( mocksControl.createMock( INetworkTable.class ) ).anyTimes();
        return remoteNode;
    }
}
