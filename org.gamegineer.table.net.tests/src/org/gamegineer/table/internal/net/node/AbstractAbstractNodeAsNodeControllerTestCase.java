/*
 * AbstractAbstractNodeAsNodeControllerTestCase.java
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
 * Created on Apr 17, 2011 at 12:00:22 AM.
 */

package org.gamegineer.table.internal.net.node;

import static org.junit.Assert.assertEquals;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Future;
import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.easymock.EasyMock;
import org.gamegineer.common.core.util.concurrent.SynchronousFuture;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.TableNetworkConfigurations;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.internal.net.transport.TransportException;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkException;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.node.INodeController} interface via
 * extension of the {@link org.gamegineer.table.internal.net.node.AbstractNode}
 * class.
 * 
 * @param <T>
 *        The type of the table network node.
 * @param <RemoteNodeType>
 *        The type of the remote table network node.
 */
public abstract class AbstractAbstractNodeAsNodeControllerTestCase<T extends AbstractNode<RemoteNodeType>, RemoteNodeType extends IRemoteNode>
    extends AbstractNodeControllerTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractAbstractNodeAsNodeControllerTestCase} class.
     */
    public AbstractAbstractNodeAsNodeControllerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a transport layer that will fail when opened.
     * 
     * @return A transport layer that will fail when opened; never {@code null}.
     */
    /* @NonNull */
    private static ITransportLayer createFailingTransportLayer()
    {
        return new ITransportLayer()
        {
            @Override
            public Future<Void> beginClose()
            {
                return new SynchronousFuture<Void>();
            }

            @Override
            public Future<Void> beginOpen(
                @SuppressWarnings( "unused" )
                final String hostName,
                @SuppressWarnings( "unused" )
                final int port )
            {
                return new SynchronousFuture<Void>();
            }

            @Override
            public void endClose(
                @SuppressWarnings( "unused" )
                final Future<Void> future )
            {
                // do nothing
            }

            @Override
            public void endOpen(
                @SuppressWarnings( "unused" )
                final Future<Void> future )
                throws TransportException
            {
                throw new TransportException();
            }
        };
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractNodeControllerTestCase#createNodeLayerRunner(org.gamegineer.table.internal.net.node.INodeController)
     */
    @Override
    protected final NodeLayerRunner createNodeLayerRunner(
        final T nodeController )
    {
        return new NodeLayerRunner( nodeController );
    }

    /**
     * Creates a transport layer that will succeed when opened.
     * 
     * @return A transport layer that will succeed when opened; never {@code
     *         null}.
     */
    /* @NonNull */
    private static ITransportLayer createSuccessfulTransportLayer()
    {
        return new ITransportLayer()
        {
            @Override
            public Future<Void> beginClose()
            {
                return new SynchronousFuture<Void>();
            }

            @Override
            public Future<Void> beginOpen(
                @SuppressWarnings( "unused" )
                final String hostName,
                @SuppressWarnings( "unused" )
                final int port )
            {
                return new SynchronousFuture<Void>();
            }

            @Override
            public void endClose(
                @SuppressWarnings( "unused" )
                final Future<Void> future )
            {
                // do nothing
            }

            @Override
            public void endOpen(
                @SuppressWarnings( "unused" )
                final Future<Void> future )
            {
                // do nothing
            }
        };
    }

    /**
     * Ensures the connect operation does not invoke the {@code connected}
     * method when the transport layer fails to be opened.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TableNetworkException.class )
    public void testConnect_TransportLayerOpenFailure_DoesNotInvokeConnected()
        throws Exception
    {
        final MockNode node = new MockNode.Factory().createNode( EasyMock.createMock( ITableNetworkController.class ) );
        node.setTransportLayer( createFailingTransportLayer() );
        final NodeLayerRunner nodeLayerRunner = new NodeLayerRunner( node );

        try
        {
            nodeLayerRunner.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );
        }
        finally
        {
            assertEquals( 0, node.getConnectedCallCount() );
        }
    }

    /**
     * Ensures the connect operation invokes the {@code connecting} method when
     * the transport layer fails to be opened.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TableNetworkException.class )
    public void testConnect_TransportLayerOpenFailure_InvokesConnecting()
        throws Exception
    {
        final MockNode node = new MockNode.Factory().createNode( EasyMock.createMock( ITableNetworkController.class ) );
        node.setTransportLayer( createFailingTransportLayer() );
        final NodeLayerRunner nodeLayerRunner = new NodeLayerRunner( node );

        try
        {
            nodeLayerRunner.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );
        }
        finally
        {
            assertEquals( 1, node.getConnectingCallCount() );
        }
    }

    /**
     * Ensures the connect operation invokes the {@code dispose} method when the
     * transport layer fails to be opened.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TableNetworkException.class )
    public void testConnect_TransportLayerOpenFailure_InvokesDispose()
        throws Exception
    {
        final MockNode node = new MockNode.Factory().createNode( EasyMock.createMock( ITableNetworkController.class ) );
        node.setTransportLayer( createFailingTransportLayer() );
        final NodeLayerRunner nodeLayerRunner = new NodeLayerRunner( node );

        try
        {
            nodeLayerRunner.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );
        }
        finally
        {
            assertEquals( 1, node.getDisposeCallCount() );
        }
    }

    /**
     * Ensures the connect operation invokes the {@code connected} method when
     * the transport layer is opened successfully.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConnect_TransportLayerOpenSuccess_InvokesConnected()
        throws Exception
    {
        final MockNode node = new MockNode.Factory().createNode( EasyMock.createMock( ITableNetworkController.class ) );
        node.setTransportLayer( createSuccessfulTransportLayer() );
        final NodeLayerRunner nodeLayerRunner = new NodeLayerRunner( node );

        nodeLayerRunner.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );

        assertEquals( 1, node.getConnectedCallCount() );
    }

    /**
     * Ensures the connect operation invokes the {@code connecting} method when
     * the transport layer is opened successfully.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConnect_TransportLayerOpenSuccess_InvokesConnecting()
        throws Exception
    {
        final MockNode node = new MockNode.Factory().createNode( EasyMock.createMock( ITableNetworkController.class ) );
        node.setTransportLayer( createSuccessfulTransportLayer() );
        final NodeLayerRunner nodeLayerRunner = new NodeLayerRunner( node );

        nodeLayerRunner.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );

        assertEquals( 1, node.getConnectingCallCount() );
    }

    /**
     * Ensures the {@code connecting} method throws an exception when passed a
     * {@code null} table network configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testConnecting_Configuration_Null()
        throws Exception
    {
        getNodeController().connecting( null );
    }

    /**
     * Ensures the {@code createTableManagerDecoratorForLocalNetworkTable}
     * method throws an exception when passed a {@code null} table manager.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateTableManagerDecoratorForLocalNetworkTable_TableManager_Null()
    {
        getNodeController().createTableManagerDecoratorForLocalNetworkTable( null );
    }

    /**
     * Ensures the disconnect operation invokes the {@code disconnected} method
     * when the transport layer is open.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_TransportLayerOpen_InvokesDisconnected()
        throws Exception
    {
        final MockNode node = new MockNode.Factory().createNode( EasyMock.createMock( ITableNetworkController.class ) );
        node.setTransportLayer( createSuccessfulTransportLayer() );
        final NodeLayerRunner nodeLayerRunner = new NodeLayerRunner( node );
        nodeLayerRunner.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );

        nodeLayerRunner.disconnect();

        assertEquals( 1, node.getDisconnectedCallCount() );
    }

    /**
     * Ensures the disconnect operation invokes the {@code disconnecting} method
     * when the transport layer is open.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_TransportLayerOpen_InvokesDisconnecting()
        throws Exception
    {
        final MockNode node = new MockNode.Factory().createNode( EasyMock.createMock( ITableNetworkController.class ) );
        node.setTransportLayer( createSuccessfulTransportLayer() );
        final NodeLayerRunner nodeLayerRunner = new NodeLayerRunner( node );
        nodeLayerRunner.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );

        nodeLayerRunner.disconnect();

        assertEquals( 1, node.getDisconnectingCallCount() );
    }

    /**
     * Ensures the {@code remoteNodeBound} method throws an exception when
     * passed a {@code null} remote node.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoteNodeBound_RemoteNode_Null()
    {
        getNodeController().remoteNodeBound( null );
    }

    /**
     * Ensures the {@code remoteNodeUnbound} method throws an exception when
     * passed a {@code null} remote node.
     */
    @Test( expected = NullPointerException.class )
    public void testRemoteNodeUnbound_RemoteNode_Null()
    {
        getNodeController().remoteNodeUnbound( null );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Mock implementation of {@link AbstractNode}.
     */
    @ThreadSafe
    private static final class MockNode
        extends AbstractNode<IRemoteNode>
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The count of calls made to the {@link #connected()} method. */
        private int connectedCallCount_;

        /**
         * The count of calls made to the
         * {@link #connecting(ITableNetworkConfiguration)} method.
         */
        private int connectingCallCount_;

        /** The count of calls made to the {@link #disconnected()} method. */
        private int disconnectedCallCount_;

        /** The count of calls made to the {@link #disconnecting()} method. */
        private int disconnectingCallCount_;

        /** The count of calls made to the {@link #dispose()} method. */
        private int disposeCallCount_;

        /** The instance lock. */
        private final Object lock_;

        /** The transport layer used by the node. */
        private ITransportLayer transportLayer_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockNode} class.
         * 
         * @param nodeLayer
         *        The node layer; must not be {@code null}.
         * @param tableNetworkController
         *        The table network controller; must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code nodeLayer} or {@code tableNetworkController} is
         *         {@code null}.
         */
        private MockNode(
            /* @NonNull */
            final INodeLayer nodeLayer,
            /* @NonNull */
            final ITableNetworkController tableNetworkController )
        {
            super( nodeLayer, tableNetworkController );

            connectedCallCount_ = 0;
            connectingCallCount_ = 0;
            disconnectedCallCount_ = 0;
            disconnectingCallCount_ = 0;
            disposeCallCount_ = 0;
            lock_ = new Object();
            transportLayer_ = null;
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
         * @see org.gamegineer.table.internal.net.node.AbstractNode#connected()
         */
        @Override
        protected void connected()
            throws TableNetworkException
        {
            super.connected();

            synchronized( lock_ )
            {
                ++connectedCallCount_;
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.node.AbstractNode#connecting(org.gamegineer.table.net.ITableNetworkConfiguration)
         */
        @Override
        protected void connecting(
            final ITableNetworkConfiguration configuration )
            throws TableNetworkException
        {
            super.connecting( configuration );

            synchronized( lock_ )
            {
                ++connectingCallCount_;
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.node.AbstractNode#createTransportLayer()
         */
        @Override
        protected ITransportLayer createTransportLayer()
        {
            synchronized( lock_ )
            {
                assert transportLayer_ != null;
                return transportLayer_;
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.node.AbstractNode#disconnected()
         */
        @Override
        protected void disconnected()
        {
            super.disconnected();

            synchronized( lock_ )
            {
                ++disconnectedCallCount_;
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.node.AbstractNode#disconnecting()
         */
        @Override
        protected void disconnecting()
        {
            super.disconnecting();

            synchronized( lock_ )
            {
                ++disconnectingCallCount_;
            }
        }

        /*
         * @see org.gamegineer.table.internal.net.node.AbstractNode#dispose()
         */
        @Override
        protected void dispose()
        {
            super.dispose();

            synchronized( lock_ )
            {
                ++disposeCallCount_;
            }
        }

        /**
         * Gets the count of calls made to the {@link #connected()} method.
         * 
         * @return The count of calls made to the {@link #connected()} method.
         */
        int getConnectedCallCount()
        {
            synchronized( lock_ )
            {
                return connectedCallCount_;
            }
        }

        /**
         * Gets the count of calls made to the
         * {@link #connecting(ITableNetworkConfiguration)} method.
         * 
         * @return The count of calls made to the
         *         {@link #connecting(ITableNetworkConfiguration)} method.
         */
        int getConnectingCallCount()
        {
            synchronized( lock_ )
            {
                return connectingCallCount_;
            }
        }

        /**
         * Gets the count of calls made to the {@link #disconnected()} method.
         * 
         * @return The count of calls made to the {@link #disconnected()}
         *         method.
         */
        int getDisconnectedCallCount()
        {
            synchronized( lock_ )
            {
                return disconnectedCallCount_;
            }
        }

        /**
         * Gets the count of calls made to the {@link #disconnecting()} method.
         * 
         * @return The count of calls made to the {@link #disconnecting()}
         *         method.
         */
        int getDisconnectingCallCount()
        {
            synchronized( lock_ )
            {
                return disconnectingCallCount_;
            }
        }

        /**
         * Gets the count of calls made to the {@link #dispose()} method.
         * 
         * @return The count of calls made to the {@link #dispose()} method.
         */
        int getDisposeCallCount()
        {
            synchronized( lock_ )
            {
                return disposeCallCount_;
            }
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
         * @see org.gamegineer.table.internal.net.node.INodeController#giveControl(java.lang.String)
         */
        @Override
        public void giveControl(
            @SuppressWarnings( "unused" )
            final String playerName )
        {
            // do nothing
        }

        /*
         * @see org.gamegineer.table.internal.net.node.INodeController#requestControl()
         */
        @Override
        public void requestControl()
        {
            // do nothing
        }

        /**
         * Sets the transport layer used by the node.
         * 
         * @param transportLayer
         *        The transport layer; must not be {@code null}.
         */
        void setTransportLayer(
            /* @NonNull */
            final ITransportLayer transportLayer )
        {
            assert transportLayer != null;

            synchronized( lock_ )
            {
                transportLayer_ = transportLayer;
            }
        }


        // ==================================================================
        // Nested Types
        // ==================================================================

        /**
         * A factory for creating instances of {@link MockNode}.
         */
        @Immutable
        static final class Factory
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
                super();
            }


            // ==============================================================
            // Methods
            // ==============================================================

            /*
             * @see org.gamegineer.table.internal.net.node.AbstractNode.AbstractFactory#createNode(org.gamegineer.table.internal.net.node.INodeLayer, org.gamegineer.table.internal.net.ITableNetworkController)
             */
            @Override
            @SuppressWarnings( "synthetic-access" )
            protected MockNode createNode(
                final INodeLayer nodeLayer,
                final ITableNetworkController tableNetworkController )
            {
                return new MockNode( nodeLayer, tableNetworkController );
            }
        }
    }
}
