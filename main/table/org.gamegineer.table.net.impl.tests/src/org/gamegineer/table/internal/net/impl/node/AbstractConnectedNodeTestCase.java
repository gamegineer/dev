/*
 * AbstractConnectedNodeTestCase.java
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
 * Created on Apr 14, 2011 at 11:24:04 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Optional;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.MultiThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestTableEnvironments;
import org.gamegineer.table.internal.net.impl.TableNetworkConfigurations;
import org.gamegineer.table.net.TableNetworkConfiguration;
import org.junit.After;
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
public abstract class AbstractConnectedNodeTestCase<T extends INode<RemoteNodeType>, @NonNull RemoteNodeType extends IRemoteNode>
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

    /** The table for use in the fixture. */
    private Optional<ITable> table_;

    /** The table environment context for use in the fixture. */
    private Optional<MultiThreadedTableEnvironmentContext> tableEnvironmentContext_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractConnectedNodeTestCase}
     * class.
     */
    protected AbstractConnectedNodeTestCase()
    {
        niceMocksControl_ = Optional.empty();
        node_ = Optional.empty();
        nodeLayerRunner_ = Optional.empty();
        table_ = Optional.empty();
        tableEnvironmentContext_ = Optional.empty();
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
     */
    protected abstract RemoteNodeType createMockRemoteNode(
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
     */
    protected abstract NodeLayerRunner createNodeLayerRunner(
        T node );

    /**
     * Creates a new table network configuration.
     * 
     * @return A new table network configuration; never {@code null}.
     */
    protected final TableNetworkConfiguration createTableNetworkConfiguration()
    {
        return TableNetworkConfigurations.createDefaultTableNetworkConfiguration( getTable() );
    }

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
     * Gets the fixture node layer runner.
     * 
     * @return The fixture node layer runner; never {@code null}.
     */
    protected final NodeLayerRunner getNodeLayerRunner()
    {
        return nodeLayerRunner_.get();
    }

    /**
     * Gets the fixture table.
     * 
     * @return The fixture table; never {@code null}.
     */
    private ITable getTable()
    {
        return table_.get();
    }

    /**
     * Gets the fixture table environment context.
     * 
     * @return The fixture table environment context; never {@code null}.
     */
    private MultiThreadedTableEnvironmentContext getTableEnvironmentContext()
    {
        return tableEnvironmentContext_.get();
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
     */
    protected abstract boolean isRemoteNodeBound(
        final T node,
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
        final MultiThreadedTableEnvironmentContext tableEnvironmentContext = new MultiThreadedTableEnvironmentContext();
        tableEnvironmentContext_ = Optional.of( tableEnvironmentContext );
        table_ = Optional.of( TestTableEnvironments.createTableEnvironment( tableEnvironmentContext ).createTable() );

        niceMocksControl_ = Optional.of( EasyMock.createNiceControl() );
        final T node = createConnectedNode();
        node_ = Optional.of( node );
        nodeLayerRunner_ = Optional.of( createNodeLayerRunner( node ) );
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
        getTableEnvironmentContext().dispose();
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
        final T node = getNode();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        getNodeLayerRunner().run( new Runnable()
        {
            @Override
            public void run()
            {
                final RemoteNodeType remoteNode = createMockRemoteNode( niceMocksControl );
                niceMocksControl.replay();
                assertFalse( isRemoteNodeBound( node, remoteNode.getPlayerName() ) );

                node.bindRemoteNode( remoteNode );

                assertTrue( isRemoteNodeBound( node, remoteNode.getPlayerName() ) );
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
        final T node = getNode();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        getNodeLayerRunner().run( new Runnable()
        {
            @Override
            public void run()
            {
                final RemoteNodeType remoteNode = createMockRemoteNode( niceMocksControl );
                niceMocksControl.replay();
                node.bindRemoteNode( remoteNode );

                node.bindRemoteNode( remoteNode );
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
        final T node = getNode();
        getNodeLayerRunner().run( new Runnable()
        {
            @Override
            public void run()
            {
                final SecureString password = node.getPassword();
                final SecureString expectedValue = new SecureString( password );
                password.dispose();

                final SecureString actualValue = node.getPassword();

                assertEquals( expectedValue, actualValue );
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
        final T node = getNode();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        getNodeLayerRunner().run( new Runnable()
        {
            @Override
            public void run()
            {
                final RemoteNodeType remoteNode = createMockRemoteNode( niceMocksControl );
                niceMocksControl.replay();

                node.unbindRemoteNode( remoteNode );
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
        final T node = getNode();
        final IMocksControl niceMocksControl = getNiceMocksControl();
        getNodeLayerRunner().run( new Runnable()
        {
            @Override
            public void run()
            {
                final RemoteNodeType remoteNode = createMockRemoteNode( niceMocksControl );
                niceMocksControl.replay();
                node.bindRemoteNode( remoteNode );
                assertTrue( isRemoteNodeBound( node, remoteNode.getPlayerName() ) );

                node.unbindRemoteNode( remoteNode );

                assertFalse( isRemoteNodeBound( node, remoteNode.getPlayerName() ) );
            }
        } );
    }
}
