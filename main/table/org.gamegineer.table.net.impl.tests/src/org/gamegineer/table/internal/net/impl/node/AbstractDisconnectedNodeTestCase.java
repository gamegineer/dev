/*
 * AbstractDisconnectedNodeTestCase.java
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
 * Created on May 29, 2011 at 5:22:58 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import java.util.Optional;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link INode} interface while in the disconnected state.
 * 
 * @param <T>
 *        The type of the node.
 * @param <RemoteNodeType>
 *        The type of the remote node.
 */
public abstract class AbstractDisconnectedNodeTestCase<T extends INode<RemoteNodeType>, @NonNull RemoteNodeType extends IRemoteNode>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The nice mocks control for use in the fixture. */
    private Optional<IMocksControl> niceMocksControl_;

    /** The table network node under test in the fixture. */
    private Optional<T> node_;

    /** The node layer runner for use in the fixture. */
    private Optional<NodeLayerRunner> nodeLayerRunner_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractDisconnectedNodeTestCase} class.
     */
    protected AbstractDisconnectedNodeTestCase()
    {
        niceMocksControl_ = Optional.empty();
        node_ = Optional.empty();
        nodeLayerRunner_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table network node to be tested in the disconnected state.
     * 
     * @return The table network node to be tested in the disconnected state;
     *         never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract T createDisconnectedNode()
        throws Exception;

    /**
     * Creates a mock remote table network node for use in the fixture.
     * 
     * @param mocksControl
     *        The mocks control for use in the fixture; must not be {@code null}
     *        .
     * 
     * @return A mock remote table network node for use in the fixture; never
     *         {@code null}.
     */
    protected abstract RemoteNodeType createMockRemoteNode(
        IMocksControl mocksControl );

    /**
     * Creates a node layer runner for the specified table network node.
     * 
     * @param node
     *        The table network node to associate with the node layer runner;
     *        must not be {@code null}.
     * 
     * @return The new node layer runner for the specified table network node;
     *         never {@code null}.
     */
    protected abstract NodeLayerRunner createNodeLayerRunner(
        T node );

    /**
     * Gets the fixture nice mocks control.
     * 
     * @return The fixture nice mocks control; never {@code null}.
     */
    private IMocksControl getNiceMocksControl()
    {
        return niceMocksControl_.get();
    }

    /**
     * Gets the table network node under test in the fixture.
     * 
     * @return The table network node under test in the fixture; never
     *         {@code null}.
     */
    protected final T getNode()
    {
        return node_.get();
    }

    /**
     * Gets the node layer runner for use in the fixture.
     * 
     * @return The node layer runner for use in the fixture; never {@code null}.
     */
    protected final NodeLayerRunner getNodeLayerRunner()
    {
        return nodeLayerRunner_.get();
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
        niceMocksControl_ = Optional.of( EasyMock.createNiceControl() );
        final T node = createDisconnectedNode();
        node_ = Optional.of( node );
        nodeLayerRunner_ = Optional.of( createNodeLayerRunner( node ) );
    }

    /**
     * Ensures the {@link INode#bindRemoteNode} method throws an exception when
     * passed a non-{@code null} remote node.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testBindRemoteNode_RemoteNode_NonNull()
        throws Exception
    {
        final T node = getNode();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        getNodeLayerRunner().run( new Runnable()
        {
            @Override
            public void run()
            {
                node.bindRemoteNode( createMockRemoteNode( niceMocksControl ) );
            }
        } );
    }

    /**
     * Ensures the {@link INode#getPassword} method throws an exception.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetPassword()
        throws Exception
    {
        final T node = getNode();
        getNodeLayerRunner().run( new Runnable()
        {
            @Override
            public void run()
            {
                node.getPassword();
            }
        } );
    }

    /**
     * Ensures the {@link INode#getPlayerName} method throws an exception.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testGetPlayerName()
        throws Exception
    {
        final T node = getNode();
        getNodeLayerRunner().run( new Runnable()
        {
            @Override
            public void run()
            {
                node.getPlayerName();
            }
        } );
    }

    /**
     * Ensures the {@link INode#unbindRemoteNode} method throws an exception
     * when passed a non-{@code null} remote node.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testUnbindRemoteNode_RemoteNode_NonNull()
        throws Exception
    {
        final T node = getNode();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        getNodeLayerRunner().run( new Runnable()
        {
            @Override
            public void run()
            {
                node.unbindRemoteNode( createMockRemoteNode( niceMocksControl ) );
            }
        } );
    }
}
