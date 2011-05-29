/*
 * AbstractConnectedNodeTestCase.java
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
 * Created on Apr 14, 2011 at 11:24:04 PM.
 */

package org.gamegineer.table.internal.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.core.security.SecureString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.INode} interface while in the
 * connected state.
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
    private IMocksControl niceMocksControl_;

    /** The table network node under test in the fixture. */
    private T node_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractConnectedNodeTestCase}
     * class.
     */
    protected AbstractConnectedNodeTestCase()
    {
        super();
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
     * Gets the table network node under test in the fixture.
     * 
     * @return The table network node under test in the fixture; never {@code
     *         null}.
     */
    /* @NonNull */
    protected final T getNode()
    {
        assertNotNull( node_ );
        return node_;
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
        niceMocksControl_ = null;
    }

    /**
     * Ensures the {@code bindRemoteNode} method adds the remote node when the
     * remote node is absent from the bound remote nodes collection.
     */
    @Test
    public void testBindRemoteNode_RemoteNode_Absent()
    {
        synchronized( node_.getLock() )
        {
            final RemoteNodeType remoteNode = createMockRemoteNode( niceMocksControl_ );
            niceMocksControl_.replay();
            assertFalse( isRemoteNodeBound( node_, remoteNode.getPlayerName() ) );

            node_.bindRemoteNode( remoteNode );

            assertTrue( isRemoteNodeBound( node_, remoteNode.getPlayerName() ) );
        }
    }

    /**
     * Ensures the {@code bindRemoteNode} method throws an exception when passed
     * a {@code null} remote node.
     */
    @Test( expected = NullPointerException.class )
    public void testBindRemoteNode_RemoteNode_Null()
    {
        synchronized( node_.getLock() )
        {
            node_.bindRemoteNode( null );
        }
    }

    /**
     * Ensures the {@code bindRemoteNode} method throws an exception when the
     * remote node is present in the bound remote nodes collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testBindRemoteNode_RemoteNode_Present()
    {
        synchronized( node_.getLock() )
        {
            final RemoteNodeType remoteNode = createMockRemoteNode( niceMocksControl_ );
            niceMocksControl_.replay();
            node_.bindRemoteNode( remoteNode );

            node_.bindRemoteNode( remoteNode );
        }
    }

    /**
     * Ensures the {@code getPassword} method returns a copy of the password.
     */
    @Test
    public void testGetPassword_ReturnValue_Copy()
    {
        synchronized( node_.getLock() )
        {
            final SecureString password = node_.getPassword();
            final SecureString expectedValue = new SecureString( password );
            password.dispose();

            final SecureString actualValue = node_.getPassword();

            assertEquals( expectedValue, actualValue );
        }
    }

    /**
     * Ensures the {@code getPassword} method does not return {@code null}.
     */
    @Test
    public void testGetPassword_ReturnValue_NonNull()
    {
        synchronized( node_.getLock() )
        {
            assertNotNull( node_.getPassword() );
        }
    }

    /**
     * Ensures the {@code getPlayerName} method does not return {@code null}.
     */
    @Test
    public void testGetPlayerName_ReturnValue_NonNull()
    {
        synchronized( node_.getLock() )
        {
            assertNotNull( node_.getPlayerName() );
        }
    }

    /**
     * Ensures the {@code unbindRemoteNode} method throws an exception when the
     * remote node is absent from the bound remote nodes collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testUnbindRemoteNode_RemoteNode_Absent()
        throws Exception
    {
        synchronized( node_.getLock() )
        {
            final RemoteNodeType remoteNode = createMockRemoteNode( niceMocksControl_ );
            niceMocksControl_.replay();

            node_.unbindRemoteNode( remoteNode );
        }
    }

    /**
     * Ensures the {@code unbindRemoteNode} method throws an exception when
     * passed a {@code null} remote node.
     */
    @Test( expected = NullPointerException.class )
    public void testUnbindRemoteNode_RemoteNode_Null()
    {
        synchronized( node_.getLock() )
        {
            node_.unbindRemoteNode( null );
        }
    }

    /**
     * Ensures the {@code unbindRemoteNode} method removes the remote node when
     * the remote node is present in the bound remote nodes collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testUnbindRemoteNode_RemoteNode_Present()
        throws Exception
    {
        synchronized( node_.getLock() )
        {
            final RemoteNodeType remoteNode = createMockRemoteNode( niceMocksControl_ );
            niceMocksControl_.replay();
            node_.bindRemoteNode( remoteNode );
            assertTrue( isRemoteNodeBound( node_, remoteNode.getPlayerName() ) );

            node_.unbindRemoteNode( remoteNode );

            assertFalse( isRemoteNodeBound( node_, remoteNode.getPlayerName() ) );
        }
    }
}
