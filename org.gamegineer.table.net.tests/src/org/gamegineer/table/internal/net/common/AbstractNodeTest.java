/*
 * AbstractNodeTest.java
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
 * Created on Apr 10, 2011 at 5:44:57 PM.
 */

package org.gamegineer.table.internal.net.common;

import static org.junit.Assert.assertEquals;
import java.util.Collection;
import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.IRemoteNode;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.common.AbstractNode} class.
 */
public final class AbstractNodeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network node under test in the fixture. */
    private AbstractNode node_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNodeTest} class.
     */
    public AbstractNodeTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code AbstractNode} class with stubbed
     * implementations of all abstract methods.
     * 
     * @param tableNetworkController
     *        The table network controller; must not be {@code null}.
     * 
     * @return A new instance of the {@code AbstractNode} class; never {@code
     *         null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableNetworkController} is {@code null}.
     */
    /* @NonNull */
    private static AbstractNode createNode(
        /* @NonNull */
        final ITableNetworkController tableNetworkController )
    {
        return new AbstractNode( tableNetworkController )
        {
            @Override
            protected ITransportLayer createTransportLayer()
            {
                return null;
            }

            @Override
            public Collection<String> getPlayers()
            {
                return null;
            }

            @Override
            public boolean isPlayerConnected(
                @SuppressWarnings( "unused" )
                final String playerName )
            {
                return false;
            }

            @Override
            public void setPlayers(
                @SuppressWarnings( "unused" )
                final Collection<String> players )
            {
                // do nothing
            }
        };
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        node_ = createNode( EasyMock.createMock( ITableNetworkController.class ) );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        node_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table network controller.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_TableNetworkController_Null()
    {
        createNode( null );
    }

    /**
     * Ensures the {@code getPassword} method throws an exception when the table
     * network node is disconnected.
     */
    // TODO: Move to AbstractNodeTestCase once we modify test fixture to test both
    // connected and disconnected nodes.
    @Test( expected = IllegalStateException.class )
    public void testGetPassword_Disconnected()
    {
        synchronized( node_.getLock() )
        {
            node_.getPassword();
        }
    }

    /**
     * Ensures the {@code getPlayerName} method throws an exception when the
     * table network node is disconnected.
     */
    // TODO: Move to AbstractNodeTestCase once we modify test fixture to test both
    // connected and disconnected nodes.
    @Test( expected = IllegalStateException.class )
    public void testGetPlayerName_Disconnected()
    {
        synchronized( node_.getLock() )
        {
            node_.getPlayerName();
        }
    }

    /**
     * Ensures the {@code getRemoteNodes} method returns a copy of the bound
     * remote nodes collection.
     */
    @Test
    public void testGetRemoteNodes_ReturnValue_Copy()
    {
        final Collection<IRemoteNode> remoteNodes = node_.getRemoteNodes();
        final int expectedRemoteNodesSize = remoteNodes.size();
        remoteNodes.add( EasyMock.createMock( IRemoteNode.class ) );

        final int actualRemoteNodesSize = node_.getRemoteNodes().size();

        assertEquals( expectedRemoteNodesSize, actualRemoteNodesSize );
    }
}
