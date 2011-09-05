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

package org.gamegineer.table.internal.net.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import net.jcip.annotations.Immutable;
import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.TableNetworkConfigurations;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkException;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.AbstractNode} class.
 */
public final class AbstractNodeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network node under test in the fixture. */
    private AbstractNode<IRemoteNode> node_;


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
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        node_ = new MockNode();
    }

    /**
     * Ensures the {@code connect} method adds a table proxy for the local
     * player before either the {@code connecting} or {@code connected} methods
     * are invoked.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConnect_AddsLocalTableProxy()
        throws Exception
    {
        final ITableNetworkConfiguration configuration = TableNetworkConfigurations.createDefaultTableNetworkConfiguration();
        final MockNode node = new MockNode()
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
                final ITableNetworkConfiguration configuration )
                throws TableNetworkException
            {
                super.connecting( configuration );

                assertTrue( isTableBound( configuration.getLocalPlayerName() ) );
            }
        };

        node.connect( configuration );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table network controller.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_TableNetworkController_Null()
    {
        new AbstractNode<IRemoteNode>( null )
        {
            @Override
            public void cancelControlRequest()
            {
                // do nothing
            }

            @Override
            protected ITransportLayer createTransportLayer()
            {
                return null;
            }

            @Override
            public IPlayer getPlayer()
            {
                return null;
            }

            @Override
            public Collection<IPlayer> getPlayers()
            {
                return null;
            }

            @Override
            public ITableManager getTableManager()
            {
                return null;
            }

            @Override
            public void giveControl(
                @SuppressWarnings( "unused" )
                final IPlayer player )
            {
                // do nothing
            }

            @Override
            public void requestControl()
            {
                // do nothing
            }
        };
    }

    /**
     * Ensures the {@code disconnect} method removes the table proxy for the
     * local player after the {@code disconnecting} method is invoked but before
     * the {@code disconnected} method is invoked.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_RemovesLocalTableProxy()
        throws Exception
    {
        final ITableNetworkConfiguration configuration = TableNetworkConfigurations.createDefaultTableNetworkConfiguration();
        final MockNode node = new MockNode()
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
        node.connect( configuration );

        node.disconnect();
    }

    /**
     * Ensures the {@code getRemoteNode} method throws an exception when passed
     * a {@code null} player name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetRemoteNode_PlayerName_Null()
    {
        node_.getRemoteNode( null );
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
         */
        MockNode()
        {
            super( EasyMock.createNiceMock( ITableNetworkController.class ) );
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.net.node.INodeController#cancelControlRequest()
         */
        @Override
        public void cancelControlRequest()
        {
            // do nothing
        }

        /*
         * @see org.gamegineer.table.internal.net.node.AbstractNode#createTransportLayer()
         */
        @Override
        protected ITransportLayer createTransportLayer()
        {
            return EasyMock.createNiceMock( ITransportLayer.class );
        }

        /*
         * @see org.gamegineer.table.internal.net.node.INodeController#getPlayer()
         */
        @Override
        public IPlayer getPlayer()
        {
            return null;
        }

        /*
         * @see org.gamegineer.table.internal.net.node.INodeController#getPlayers()
         */
        @Override
        public Collection<IPlayer> getPlayers()
        {
            return Collections.emptyList();
        }

        /*
         * @see org.gamegineer.table.internal.net.node.INode#getTableManager()
         */
        @Override
        public ITableManager getTableManager()
        {
            return EasyMock.createMock( ITableManager.class );
        }

        /*
         * @see org.gamegineer.table.internal.net.node.INodeController#giveControl(org.gamegineer.table.net.IPlayer)
         */
        @Override
        public void giveControl(
            @SuppressWarnings( "unused" )
            final IPlayer player )
        {
            // do nothing
        }

        /**
         * Indicates a table is bound to this node for the specified player
         * name.
         * 
         * @param playerName
         *        The player name; must not be {@code null}.
         * 
         * @return {@code true} if a table is bound to this node for the
         *         specified player name; otherwise {@code false}.
         */
        final boolean isTableBound(
            /* @NonNull */
            final String playerName )
        {
            assert playerName != null;

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
         * @see org.gamegineer.table.internal.net.node.INodeController#requestControl()
         */
        @Override
        public void requestControl()
        {
            // do nothing
        }
    }
}
