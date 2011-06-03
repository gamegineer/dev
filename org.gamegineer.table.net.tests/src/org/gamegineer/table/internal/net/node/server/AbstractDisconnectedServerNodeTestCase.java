/*
 * AbstractDisconnectedServerNodeTestCase.java
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
 * Created on May 29, 2011 at 5:35:29 PM.
 */

package org.gamegineer.table.internal.net.node.server;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.node.AbstractDisconnectedNodeTestCase;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.node.server.IServerNode} interface
 * while in the disconnected state.
 * 
 * @param <T>
 *        The type of the server node.
 */
public abstract class AbstractDisconnectedServerNodeTestCase<T extends IServerNode>
    extends AbstractDisconnectedNodeTestCase<T, IRemoteClientNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractDisconnectedServerNodeTestCase} class.
     */
    protected AbstractDisconnectedServerNodeTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractDisconnectedNodeTestCase#createMockRemoteNode(org.easymock.IMocksControl)
     */
    @Override
    protected IRemoteClientNode createMockRemoteNode(
        final IMocksControl mocksControl )
    {
        final IRemoteClientNode remoteNode = mocksControl.createMock( IRemoteClientNode.class );
        EasyMock.expect( remoteNode.getPlayerName() ).andReturn( "newPlayerName" ).anyTimes(); //$NON-NLS-1$
        return remoteNode;
    }

    /**
     * Ensures the {@code isPlayerConnected} method throws an exception when
     * passed a non-{@code null} player name.
     */
    @Test( expected = IllegalStateException.class )
    public void testIsPlayerConnected_PlayerName_NonNull()
    {
        synchronized( getNode().getLock() )
        {
            getNode().isPlayerConnected( "playerName" ); //$NON-NLS-1$
        }
    }

    /**
     * Ensures the {@code isPlayerConnected} method throws an exception when
     * passed a {@code null} player name.
     */
    @Test( expected = NullPointerException.class )
    public void testIsPlayerConnected_PlayerName_Null()
    {
        synchronized( getNode().getLock() )
        {
            getNode().isPlayerConnected( null );
        }
    }
}