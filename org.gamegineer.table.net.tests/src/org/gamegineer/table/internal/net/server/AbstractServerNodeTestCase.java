/*
 * AbstractServerNodeTestCase.java
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
 * Created on May 27, 2011 at 10:17:56 PM.
 */

package org.gamegineer.table.internal.net.server;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.AbstractNodeTestCase;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.server.IServerNode} interface.
 * 
 * @param <T>
 *        The type of the server node.
 */
public abstract class AbstractServerNodeTestCase<T extends IServerNode>
    extends AbstractNodeTestCase<T, IRemoteClientNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractServerNodeTestCase}
     * class.
     */
    protected AbstractServerNodeTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.AbstractNodeTestCase#createMockRemoteNode(org.easymock.IMocksControl)
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
