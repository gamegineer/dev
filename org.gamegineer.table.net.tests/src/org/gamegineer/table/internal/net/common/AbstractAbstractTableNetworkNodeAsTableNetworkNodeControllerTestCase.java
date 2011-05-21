/*
 * AbstractAbstractTableNetworkNodeAsTableNetworkNodeControllerTestCase.java
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

package org.gamegineer.table.internal.net.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import net.jcip.annotations.NotThreadSafe;
import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.AbstractTableNetworkNodeControllerTestCase;
import org.gamegineer.table.internal.net.ITableNetworkController;
import org.gamegineer.table.internal.net.ITableProxy;
import org.gamegineer.table.internal.net.TableNetworkConfigurations;
import org.gamegineer.table.internal.net.transport.ITransportLayer;
import org.gamegineer.table.internal.net.transport.TransportException;
import org.gamegineer.table.net.ITableNetworkConfiguration;
import org.gamegineer.table.net.TableNetworkException;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.ITableNetworkNodeController}
 * interface via extension of the
 * {@link org.gamegineer.table.internal.net.common.AbstractTableNetworkNode}
 * class.
 * 
 * @param <T>
 *        The type of the table network node.
 */
public abstract class AbstractAbstractTableNetworkNodeAsTableNetworkNodeControllerTestCase<T extends AbstractTableNetworkNode>
    extends AbstractTableNetworkNodeControllerTestCase<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractAbstractTableNetworkNodeAsTableNetworkNodeControllerTestCase}
     * class.
     */
    public AbstractAbstractTableNetworkNodeAsTableNetworkNodeControllerTestCase()
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
            public void close()
            {
                // do nothing
            }

            @Override
            public void open(
                @SuppressWarnings( "unused" )
                final String hostName,
                @SuppressWarnings( "unused" )
                final int port )
                throws TransportException
            {
                throw new TransportException();
            }
        };
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
            public void close()
            {
                // do nothing
            }

            @Override
            public void open(
                @SuppressWarnings( "unused" )
                final String hostName,
                @SuppressWarnings( "unused" )
                final int port )
            {
                // do nothing
            }
        };
    }

    /**
     * Ensures the {@code connect} method adds a table proxy for the local
     * player.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConnect_AddsLocalTableProxy()
        throws Exception
    {
        final ITableNetworkConfiguration configuration = TableNetworkConfigurations.createDefaultTableNetworkConfiguration();
        getTableNetworkNodeController().connect( configuration );

        boolean localTableProxyFound = false;
        for( final ITableProxy tableProxy : getTableNetworkNodeController().getTableProxies() )
        {
            if( tableProxy.getPlayerName().equals( configuration.getLocalPlayerName() ) )
            {
                localTableProxyFound = true;
                break;
            }
        }

        assertTrue( localTableProxyFound );
    }

    /**
     * Ensures the {@code connect} method does not invoke the {@code connected}
     * method when the transport layer fails to be opened.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TableNetworkException.class )
    public void testConnect_TransportLayerOpenFailure_DoesNotInvokeConnected()
        throws Exception
    {
        final MockTableNetworkNode node = new MockTableNetworkNode( createFailingTransportLayer() );

        try
        {
            node.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );
        }
        finally
        {
            assertEquals( 0, node.getConnectedCallCount() );
        }
    }

    /**
     * Ensures the {@code connect} method invokes the {@code connecting} method
     * when the transport layer fails to be opened.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TableNetworkException.class )
    public void testConnect_TransportLayerOpenFailure_InvokesConnecting()
        throws Exception
    {
        final MockTableNetworkNode node = new MockTableNetworkNode( createFailingTransportLayer() );

        try
        {
            node.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );
        }
        finally
        {
            assertEquals( 1, node.getConnectingCallCount() );
        }
    }

    /**
     * Ensures the {@code connect} method invokes the {@code dispose} method
     * when the transport layer fails to be opened.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = TableNetworkException.class )
    public void testConnect_TransportLayerOpenFailure_InvokesDispose()
        throws Exception
    {
        final MockTableNetworkNode node = new MockTableNetworkNode( createFailingTransportLayer() );

        try
        {
            node.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );
        }
        finally
        {
            assertEquals( 1, node.getDisposeCallCount() );
        }
    }

    /**
     * Ensures the {@code connect} method invokes the {@code connected} method
     * when the transport layer is opened successfully.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConnect_TransportLayerOpenSuccess_InvokesConnected()
        throws Exception
    {
        final MockTableNetworkNode node = new MockTableNetworkNode( createSuccessfulTransportLayer() );

        node.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );

        assertEquals( 1, node.getConnectedCallCount() );
    }

    /**
     * Ensures the {@code connect} method invokes the {@code connecting} method
     * when the transport layer is opened successfully.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConnect_TransportLayerOpenSuccess_InvokesConnecting()
        throws Exception
    {
        final MockTableNetworkNode node = new MockTableNetworkNode( createSuccessfulTransportLayer() );

        node.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );

        assertEquals( 1, node.getConnectingCallCount() );
    }

    /**
     * Ensures the {@code disconnect} method invokes the {@code disconnected}
     * method when the transport layer is open.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_TransportLayerOpen_InvokesDisconnected()
        throws Exception
    {
        final MockTableNetworkNode node = new MockTableNetworkNode( createSuccessfulTransportLayer() );
        node.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );

        node.disconnect();

        assertEquals( 1, node.getDisconnectedCallCount() );
    }

    /**
     * Ensures the {@code disconnect} method invokes the {@code disconnecting}
     * method when the transport layer is open.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_TransportLayerOpen_InvokesDisconnecting()
        throws Exception
    {
        final MockTableNetworkNode node = new MockTableNetworkNode( createSuccessfulTransportLayer() );
        node.connect( TableNetworkConfigurations.createDefaultTableNetworkConfiguration() );

        node.disconnect();

        assertEquals( 1, node.getDisconnectingCallCount() );
    }

    /**
     * Ensures the {@code tableProxyAdded} method throws an exception when
     * passed a {@code null} table proxy.
     */
    @Test( expected = NullPointerException.class )
    public void testTableProxyAdded_TableProxy_Null()
    {
        getTableNetworkNodeController().tableProxyAdded( null );
    }

    /**
     * Ensures the {@code tableProxyRemoved} method throws an exception when
     * passed a {@code null} table proxy.
     */
    @Test( expected = NullPointerException.class )
    public void testTableProxyRemoved_TableProxy_Null()
    {
        getTableNetworkNodeController().tableProxyRemoved( null );
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Mock implementation of
     * {@link org.gamegineer.table.internal.net.common.AbstractTableNetworkNode}
     * .
     */
    @NotThreadSafe
    private static final class MockTableNetworkNode
        extends AbstractTableNetworkNode
    {
        // ==================================================================
        // Fields
        // ==================================================================

        /** The count of calls made to the {@link #connected()} method. */
        private int connectedCallCount_;

        /** The count of calls made to the {@link #connecting()} method. */
        private int connectingCallCount_;

        /** The count of calls made to the {@link #disconnected()} method. */
        private int disconnectedCallCount_;

        /** The count of calls made to the {@link #disconnecting()} method. */
        private int disconnectingCallCount_;

        /** The count of calls made to the {@link #dispose()} method. */
        private int disposeCallCount_;

        /** The transport layer used by the node. */
        private final ITransportLayer transportLayer_;


        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code MockTableNetworkNode} class.
         * 
         * @param transportLayer
         *        The transport layer used by the node; must not be {@code null}
         *        .
         */
        MockTableNetworkNode(
            /* @NonNull */
            final ITransportLayer transportLayer )
        {
            super( EasyMock.createMock( ITableNetworkController.class ) );

            assert transportLayer != null;

            connectedCallCount_ = 0;
            connectingCallCount_ = 0;
            disconnectedCallCount_ = 0;
            disconnectingCallCount_ = 0;
            disposeCallCount_ = 0;
            transportLayer_ = transportLayer;
        }


        // ==================================================================
        // Methods
        // ==================================================================

        /*
         * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkNode#connected()
         */
        @Override
        protected void connected()
            throws TableNetworkException
        {
            super.connected();

            ++connectedCallCount_;
        }

        /*
         * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkNode#connecting()
         */
        @Override
        protected void connecting()
            throws TableNetworkException
        {
            super.connecting();

            ++connectingCallCount_;
        }

        /*
         * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkNode#createTransportLayer()
         */
        @Override
        protected ITransportLayer createTransportLayer()
        {
            return transportLayer_;
        }

        /*
         * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkNode#disconnected()
         */
        @Override
        protected void disconnected()
        {
            super.disconnected();

            ++disconnectedCallCount_;
        }

        /*
         * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkNode#disconnecting()
         */
        @Override
        protected void disconnecting()
        {
            super.disconnecting();

            ++disconnectingCallCount_;
        }

        /*
         * @see org.gamegineer.table.internal.net.common.AbstractTableNetworkNode#dispose()
         */
        @Override
        protected void dispose()
        {
            super.dispose();

            ++disposeCallCount_;
        }

        /**
         * Gets the count of calls made to the {@link #connected()} method.
         * 
         * @return The count of calls made to the {@link #connected()} method.
         */
        int getConnectedCallCount()
        {
            return connectedCallCount_;
        }

        /**
         * Gets the count of calls made to the {@link #connecting()} method.
         * 
         * @return The count of calls made to the {@link #connecting()} method.
         */
        int getConnectingCallCount()
        {
            return connectingCallCount_;
        }

        /**
         * Gets the count of calls made to the {@link #disconnected()} method.
         * 
         * @return The count of calls made to the {@link #disconnected()}
         *         method.
         */
        int getDisconnectedCallCount()
        {
            return disconnectedCallCount_;
        }

        /**
         * Gets the count of calls made to the {@link #disconnecting()} method.
         * 
         * @return The count of calls made to the {@link #disconnecting()}
         *         method.
         */
        int getDisconnectingCallCount()
        {
            return disconnectingCallCount_;
        }

        /**
         * Gets the count of calls made to the {@link #dispose()} method.
         * 
         * @return The count of calls made to the {@link #dispose()} method.
         */
        int getDisposeCallCount()
        {
            return disposeCallCount_;
        }

        /*
         * @see org.gamegineer.table.internal.net.ITableNetworkNodeController#getPlayers()
         */
        @Override
        public Collection<String> getPlayers()
        {
            throw new AssertionError( "not implemented" ); //$NON-NLS-1$
        }

        /*
         * @see org.gamegineer.table.internal.net.ITableNetworkNode#setPlayers(java.util.Collection)
         */
        @Override
        public void setPlayers(
            @SuppressWarnings( "unused" )
            final Collection<String> players )
        {
            throw new AssertionError( "not implemented" ); //$NON-NLS-1$
        }
    }
}
