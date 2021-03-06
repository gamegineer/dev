/*
 * AbstractNodeTest.java
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
 * Created on Apr 10, 2011 at 5:44:57 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import net.jcip.annotations.Immutable;
import org.easymock.EasyMock;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.MultiThreadedTableEnvironmentContext;
import org.gamegineer.table.core.test.TestTableEnvironments;
import org.gamegineer.table.internal.net.impl.ITableNetworkController;
import org.gamegineer.table.internal.net.impl.TableNetworkConfigurations;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayer;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayerContext;
import org.gamegineer.table.internal.net.impl.transport.fake.FakeTransportLayerFactory;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.TableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link AbstractNode} class.
 */
public final class AbstractNodeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network node under test in the fixture. */
    private Optional<AbstractNode<@NonNull IRemoteNode>> node_;

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
     * Initializes a new instance of the {@code AbstractNodeTest} class.
     */
    public AbstractNodeTest()
    {
        node_ = Optional.empty();
        nodeLayerRunner_ = Optional.empty();
        table_ = Optional.empty();
        tableEnvironmentContext_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new table network configuration.
     * 
     * @return A new table network configuration.
     */
    private TableNetworkConfiguration createTableNetworkConfiguration()
    {
        return TableNetworkConfigurations.createDefaultTableNetworkConfiguration( getTable() );
    }

    /**
     * Gets the node under test in the fixture.
     * 
     * @return The node under test in the fixture.
     */
    private AbstractNode<@NonNull IRemoteNode> getNode()
    {
        return node_.get();
    }

    /**
     * Gets the fixture node layer runner.
     * 
     * @return The fixture node layer runner.
     */
    private NodeLayerRunner getNodeLayerRunner()
    {
        return nodeLayerRunner_.get();
    }

    /**
     * Gets the fixture table.
     * 
     * @return The fixture table.
     */
    private ITable getTable()
    {
        return table_.get();
    }

    /**
     * Gets the fixture table environment context.
     * 
     * @return The fixture table environment context.
     */
    private MultiThreadedTableEnvironmentContext getTableEnvironmentContext()
    {
        return tableEnvironmentContext_.get();
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
        final MultiThreadedTableEnvironmentContext tableEnvironmentContext = new MultiThreadedTableEnvironmentContext();
        tableEnvironmentContext_ = Optional.of( tableEnvironmentContext );
        table_ = Optional.of( TestTableEnvironments.createTableEnvironment( tableEnvironmentContext ).createTable() );

        final AbstractNode<IRemoteNode> node = new MockNode.Factory().createNode( EasyMock.createMock( ITableNetworkController.class ) );
        node_ = Optional.of( node );
        nodeLayerRunner_ = Optional.of( new NodeLayerRunner( node ) );
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
     * Ensures the connect operation adds a table proxy for the local player
     * before either the {@code connecting} or {@code connected} methods are
     * invoked.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConnect_AddsLocalTableProxy()
        throws Exception
    {
        final TableNetworkConfiguration configuration = createTableNetworkConfiguration();
        final MockNode.Factory nodeFactory = new MockNode.Factory()
        {
            @Override
            protected MockNode createNode(
                final INodeLayer nodeLayer,
                final ITableNetworkController tableNetworkController )
            {
                return new MockNode( nodeLayer, tableNetworkController )
                {
                    @Override
                    protected void connected()
                        throws TableNetworkException
                    {
                        super.connected();

                        assertTrue( isTableBound( configuration.getLocalPlayerName() ) );
                    }

                    @Override
                    protected void connecting(
                        @SuppressWarnings( "hiding" )
                        final TableNetworkConfiguration configuration )
                        throws TableNetworkException
                    {
                        super.connecting( configuration );

                        assertTrue( isTableBound( configuration.getLocalPlayerName() ) );
                    }
                };
            }
        };
        final MockNode node = nodeFactory.createNode( EasyMock.createMock( ITableNetworkController.class ) );
        final NodeLayerRunner nodeLayerRunner = new NodeLayerRunner( node );

        nodeLayerRunner.connect( configuration );
    }

    /**
     * Ensures the disconnect operation removes the table proxy for the local
     * player after the {@code disconnecting} method is invoked but before the
     * {@code disconnected} method is invoked.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_RemovesLocalTableProxy()
        throws Exception
    {
        final TableNetworkConfiguration configuration = createTableNetworkConfiguration();
        final MockNode.Factory nodeFactory = new MockNode.Factory()
        {
            @Override
            protected MockNode createNode(
                final INodeLayer nodeLayer,
                final ITableNetworkController tableNetworkController )
            {
                return new MockNode( nodeLayer, tableNetworkController )
                {
                    @Override
                    protected void disconnected()
                    {
                        super.disconnected();

                        assertFalse( isTableBound( configuration.getLocalPlayerName() ) );
                    }

                    @Override
                    protected void disconnecting()
                    {
                        super.disconnecting();

                        assertTrue( isTableBound( configuration.getLocalPlayerName() ) );
                    }
                };
            }
        };
        final MockNode node = nodeFactory.createNode( EasyMock.createMock( ITableNetworkController.class ) );
        final NodeLayerRunner nodeLayerRunner = new NodeLayerRunner( node );
        nodeLayerRunner.connect( configuration );

        nodeLayerRunner.disconnect();
    }

    /**
     * Ensures the {@link AbstractNode#getRemoteNodes} method returns a copy of
     * the bound remote nodes collection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetRemoteNodes_ReturnValue_Copy()
        throws Exception
    {
        final AbstractNode<@NonNull IRemoteNode> node = getNode();
        getNodeLayerRunner().run( new Runnable()
        {
            @Override
            public void run()
            {
                final Collection<IRemoteNode> remoteNodes = node.getRemoteNodes();
                final int expectedRemoteNodesSize = remoteNodes.size();
                remoteNodes.add( EasyMock.createMock( IRemoteNode.class ) );

                final int actualRemoteNodesSize = node.getRemoteNodes().size();

                assertEquals( expectedRemoteNodesSize, actualRemoteNodesSize );
            }
        } );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Mock implementation of {@link AbstractNode}.
     */
    @Immutable
    private static class MockNode
        extends AbstractNode<IRemoteNode>
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockNode} class.
         * 
         * @param nodeLayer
         *        The node layer.
         * @param tableNetworkController
         *        The table network controller.
         */
        MockNode(
            final INodeLayer nodeLayer,
            final ITableNetworkController tableNetworkController )
        {
            super( nodeLayer, tableNetworkController );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.net.impl.node.INodeController#cancelControlRequest()
         */
        @Override
        public void cancelControlRequest()
        {
            // do nothing
        }

        /*
         * @see org.gamegineer.table.internal.net.impl.node.AbstractNode#createTransportLayer()
         */
        @Override
        protected ITransportLayer createTransportLayer()
        {
            return new FakeTransportLayerFactory().createActiveTransportLayer( EasyMock.createNiceMock( ITransportLayerContext.class ) );
        }

        /*
         * @see org.gamegineer.table.internal.net.impl.node.INodeController#getPlayer()
         */
        @Override
        public @Nullable IPlayer getPlayer()
        {
            return null;
        }

        /*
         * @see org.gamegineer.table.internal.net.impl.node.INodeController#getPlayers()
         */
        @Override
        public Collection<IPlayer> getPlayers()
        {
            return Collections.<@NonNull IPlayer>emptyList();
        }

        /*
         * @see org.gamegineer.table.internal.net.impl.node.INode#getTableManager()
         */
        @Override
        public ITableManager getTableManager()
        {
            return EasyMock.createMock( ITableManager.class );
        }

        /*
         * @see org.gamegineer.table.internal.net.impl.node.INodeController#giveControl(java.lang.String)
         */
        @Override
        public void giveControl(
            final String playerName )
        {
            // do nothing
        }

        /**
         * Indicates a table is bound to this node for the specified player
         * name.
         * 
         * @param playerName
         *        The player name.
         * 
         * @return {@code true} if a table is bound to this node for the
         *         specified player name; otherwise {@code false}.
         */
        final boolean isTableBound(
            final String playerName )
        {
            try
            {
                final Field field = AbstractNode.class.getDeclaredField( "tables_" ); //$NON-NLS-1$
                field.setAccessible( true );
                final Map<?, ?> tables = (Map<?, ?>)field.get( this );
                return tables.containsKey( playerName );
            }
            catch( final Exception e )
            {
                throw new AssertionError( e );
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.impl.node.INodeController#requestControl()
         */
        @Override
        public void requestControl()
        {
            // do nothing
        }


        // ==================================================================
        // Nested Types
        // ==================================================================

        /**
         * A factory for creating instances of {@link MockNode}.
         */
        @Immutable
        static class Factory
            extends AbstractFactory<MockNode>
        {
            // ==============================================================
            // Constructors
            // ==============================================================

            /**
             * Initializes a new instance of the {@code Factory} class.
             */
            Factory()
            {
            }


            // ==============================================================
            // Methods
            // ==============================================================

            /*
             * @see org.gamegineer.table.internal.net.impl.node.AbstractNode.AbstractFactory#createNode(org.gamegineer.table.internal.net.impl.node.INodeLayer, org.gamegineer.table.internal.net.impl.ITableNetworkController)
             */
            @Override
            protected MockNode createNode(
                final INodeLayer nodeLayer,
                final ITableNetworkController tableNetworkController )
            {
                return new MockNode( nodeLayer, tableNetworkController );
            }
        }
    }
}
