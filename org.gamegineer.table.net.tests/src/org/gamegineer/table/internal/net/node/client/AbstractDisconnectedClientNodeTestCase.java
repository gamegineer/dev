/*
 * AbstractDisconnectedClientNodeTestCase.java
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
 * Created on May 29, 2011 at 5:30:53 PM.
 */

package org.gamegineer.table.internal.net.node.client;

import java.util.Collections;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.node.AbstractDisconnectedNodeTestCase;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.node.client.IClientNode} interface
 * while in the disconnected state.
 * 
 * @param <T>
 *        The type of the client node.
 */
public abstract class AbstractDisconnectedClientNodeTestCase<T extends IClientNode>
    extends AbstractDisconnectedNodeTestCase<T, IRemoteServerNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractDisconnectedClientNodeTestCase} class.
     */
    protected AbstractDisconnectedClientNodeTestCase()
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
    protected IRemoteServerNode createMockRemoteNode(
        final IMocksControl mocksControl )
    {
        final IRemoteServerNode remoteNode = mocksControl.createMock( IRemoteServerNode.class );
        EasyMock.expect( remoteNode.getPlayerName() ).andReturn( "newPlayerName" ).anyTimes(); //$NON-NLS-1$
        return remoteNode;
    }

    /**
     * Ensures the {@code setPlayers} method throws an exception when passed a
     * non-{@code null} players collection.
     */
    @Test( expected = IllegalStateException.class )
    public void testSetPlayers_Players_NonNull()
    {
        synchronized( getNode().getLock() )
        {
            getNode().setPlayers( Collections.<String>emptyList() );
        }
    }

    /**
     * Ensures the {@code setPlayers} method throws an exception when passed a
     * {@code null} players collection.
     */
    @Test( expected = NullPointerException.class )
    public void testSetPlayers_Players_Null()
    {
        synchronized( getNode().getLock() )
        {
            getNode().setPlayers( null );
        }
    }
}
