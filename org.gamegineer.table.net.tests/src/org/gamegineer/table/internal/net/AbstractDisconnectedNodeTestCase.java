/*
 * AbstractDisconnectedNodeTestCase.java
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
 * Created on May 29, 2011 at 5:22:58 PM.
 */

package org.gamegineer.table.internal.net;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.INode} interface while in the
 * disconnected state.
 * 
 * @param <T>
 *        The type of the node.
 * @param <RemoteNodeType>
 *        The type of the remote node.
 */
public abstract class AbstractDisconnectedNodeTestCase<T extends INode<RemoteNodeType>, RemoteNodeType extends IRemoteNode>
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
     * Initializes a new instance of the {@code
     * AbstractDisconnectedNodeTestCase} class.
     */
    protected AbstractDisconnectedNodeTestCase()
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
     * Creates the table network node to be tested in the disconnected state.
     * 
     * @return The table network node to be tested in the disconnected state;
     *         never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createDisconnectedNode()
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
        node_ = createDisconnectedNode();
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
     * Ensures the {@code bindRemoteNode} method throws an exception when passed
     * a non-{@code null} remote node.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindRemoteNode_RemoteNode_NonNull()
    {
        synchronized( node_.getLock() )
        {
            node_.bindRemoteNode( createMockRemoteNode( niceMocksControl_ ) );
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
     * Ensures the {@code getPassword} method throws an exception.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetPassword()
    {
        synchronized( node_.getLock() )
        {
            node_.getPassword();
        }
    }

    /**
     * Ensures the {@code getPlayerName} method throws an exception.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetPlayerName()
    {
        synchronized( node_.getLock() )
        {
            node_.getPlayerName();
        }
    }

    /**
     * Ensures the {@code unbindRemoteNode} method throws an exception when
     * passed a non-{@code null} remote node.
     */
    @Test( expected = IllegalStateException.class )
    public void testUnbindRemoteNode_RemoteNode_NonNull()
    {
        synchronized( node_.getLock() )
        {
            node_.unbindRemoteNode( createMockRemoteNode( niceMocksControl_ ) );
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
}
