/*
 * AbstractConnectedNodeTestCase.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Apr 14, 2011 at 11:24:04 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.core.security.SecureString;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link INode} interface while in the connected state.
 * 
 * @param <T>
 *        The type of the node.
 * @param <RemoteNodeType>
 *        The type of the remote node.
 */
public abstract class AbstractConnectedNodeTestCase<T extends INode<RemoteNodeType>, RemoteNodeType extends IRemoteNode>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The nice mocks control for use in the fixture. */
    private volatile IMocksControl niceMocksControl_;

    /** The table network node under test in the fixture. */
    private volatile T node_;

    /** The node layer runner for use in the fixture. */
    private NodeLayerRunner nodeLayerRunner_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractConnectedNodeTestCase}
     * class.
     */
    protected AbstractConnectedNodeTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a mock remote table network node for use in the fixture.
     * 
     * @param mocksControl
     *        The mocks control for use in the fixture; must not be {@code null}
     *        .
     * 
     * @return A mock remote table network node for use in the fixture; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code mocksControl} is {@code null}.
     */
    /* @NonNull */
    protected abstract RemoteNodeType createMockRemoteNode(
        /* @NonNull */
        IMocksControl mocksControl );

    /**
     * Creates the table network node to be tested in the connected state.
     * 
     * @return The table network node to be tested in the connected state; never
     *         {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createConnectedNode()
        throws Exception;

    /**
     * Creates a node layer runner for the specified table network node.
     * 
     * @param node
     *        The table network node to associate with the node layer runner;
     *        must not be {@code null}.
     * 
     * @return The new node layer runner for the specified table network node;
     *         never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code node} is {@code null}.
     */
    /* @NonNull */
    protected abstract NodeLayerRunner createNodeLayerRunner(
        /* @NonNull */
        T node );

    /**
     * Gets the table network node under test in the fixture.
     * 
     * @return The table network node under test in the fixture; never
     *         {@code null}.
     */
    /* @NonNull */
    protected final T getNode()
    {
        assertNotNull( node_ );
        return node_;
    }

    /**
     * Gets the node layer runner for use in the fixture.
     * 
     * @return The node layer runner for use in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final NodeLayerRunner getNodeLayerRunner()
    {
        assertNotNull( nodeLayerRunner_ );
        return nodeLayerRunner_;
    }

    /**
     * Indicates a remote node for the specified player is bound to the
     * specified table network node.
     * 
     * @param node
     *        The table network node under test in the fixture; must not be
     *        {@code null}.
     * @param playerName
     *        The name of the player associated with the remote node; must not
     *        be {@code null}.
     * 
     * @return {@code true} if a remote node for the specified player is bound
     *         to the specified table network node; otherwise {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code node} or {@code playerName} is {@code null}.
     */
    protected abstract boolean isRemoteNodeBound(
        /* @NonNull */
        final T node,
        /* @NonNull */
        final String playerName );

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
        niceMocksControl_ = EasyMock.createNiceControl();
        node_ = createConnectedNode();
        assertNotNull( node_ );
        nodeLayerRunner_ = createNodeLayerRunner( node_ );
    }

    /**
     * Ensures the {@link INode#bindRemoteNode} method adds the remote node when
     * the remote node is absent from the bound remote nodes collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testBindRemoteNode_RemoteNode_Absent()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                final RemoteNodeType remoteNode = createMockRemoteNode( niceMocksControl_ );
                niceMocksControl_.replay();
                assertFalse( isRemoteNodeBound( node_, remoteNode.getPlayerName() ) );

                node_.bindRemoteNode( remoteNode );

                assertTrue( isRemoteNodeBound( node_, remoteNode.getPlayerName() ) );
            }
        } );
    }

    /**
     * Ensures the {@link INode#bindRemoteNode} method throws an exception when
     * passed a {@code null} remote node.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testBindRemoteNode_RemoteNode_Null()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                node_.bindRemoteNode( null );
            }
        } );
    }

    /**
     * Ensures the {@link INode#bindRemoteNode} method throws an exception when
     * the remote node is present in the bound remote nodes collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testBindRemoteNode_RemoteNode_Present()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                final RemoteNodeType remoteNode = createMockRemoteNode( niceMocksControl_ );
                niceMocksControl_.replay();
                node_.bindRemoteNode( remoteNode );

                node_.bindRemoteNode( remoteNode );
            }
        } );
    }

    /**
     * Ensures the {@link INode#getPassword} method returns a copy of the
     * password.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetPassword_ReturnValue_Copy()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                final SecureString password = node_.getPassword();
                final SecureString expectedValue = new SecureString( password );
                password.dispose();

                final SecureString actualValue = node_.getPassword();

                assertEquals( expectedValue, actualValue );
            }
        } );
    }

    /**
     * Ensures the {@link INode#getPassword} method does not return {@code null}
     * .
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetPassword_ReturnValue_NonNull()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertNotNull( node_.getPassword() );
            }
        } );
    }

    /**
     * Ensures the {@link INode#getPlayerName} method does not return
     * {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetPlayerName_ReturnValue_NonNull()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                assertNotNull( node_.getPlayerName() );
            }
        } );
    }

    /**
     * Ensures the {@link INode#unbindRemoteNode} method throws an exception
     * when the remote node is absent from the bound remote nodes collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindRemoteNode_RemoteNode_Absent()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                final RemoteNodeType remoteNode = createMockRemoteNode( niceMocksControl_ );
                niceMocksControl_.replay();

                node_.unbindRemoteNode( remoteNode );
            }
        } );
    }

    /**
     * Ensures the {@link INode#unbindRemoteNode} method throws an exception
     * when passed a {@code null} remote node.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindRemoteNode_RemoteNode_Null()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                node_.unbindRemoteNode( null );
            }
        } );
    }

    /**
     * Ensures the {@link INode#unbindRemoteNode} method removes the remote node
     * when the remote node is present in the bound remote nodes collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUnbindRemoteNode_RemoteNode_Present()
        throws Exception
    {
        nodeLayerRunner_.run( new Runnable()
        {
            @Override
            @SuppressWarnings( "synthetic-access" )
            public void run()
            {
                final RemoteNodeType remoteNode = createMockRemoteNode( niceMocksControl_ );
                niceMocksControl_.replay();
                node_.bindRemoteNode( remoteNode );
                assertTrue( isRemoteNodeBound( node_, remoteNode.getPlayerName() ) );

                node_.unbindRemoteNode( remoteNode );

                assertFalse( isRemoteNodeBound( node_, remoteNode.getPlayerName() ) );
            }
        } );
    }
}