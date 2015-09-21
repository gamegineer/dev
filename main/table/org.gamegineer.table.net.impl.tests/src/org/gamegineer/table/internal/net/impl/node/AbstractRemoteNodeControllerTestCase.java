/*
 * AbstractRemoteNodeControllerTestCase.java
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
 * Created on Apr 23, 2011 at 9:12:20 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.internal.net.impl.transport.IMessage;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link IRemoteNodeController} interface.
 * 
 * @param <T>
 *        The type of the remote node controller.
 * @param <LocalNodeType>
 *        The type of the local table network node.
 * @param <RemoteNodeType>
 *        The type of the remote table network node.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
public abstract class AbstractRemoteNodeControllerTestCase<T extends IRemoteNodeController<LocalNodeType>, LocalNodeType extends INode<RemoteNodeType>, RemoteNodeType extends IRemoteNode>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /** The mocks control used to create the mock local node in the fixture. */
    private IMocksControl nodeMocksControl_;

    /** The remote node controller under test in the fixture. */
    private T remoteNodeController_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractRemoteNodeControllerTestCase} class.
     */
    protected AbstractRemoteNodeControllerTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a mock local table network node for use in the fixture.
     * 
     * @param mocksControl
     *        The mocks control for use in the fixture; must not be {@code null}
     *        .
     * 
     * @return A mock local table network node for use in the fixture; never
     *         {@code null}.
     */
    protected abstract LocalNodeType createMockLocalNode(
        IMocksControl mocksControl );

    /**
     * Creates a mock node layer for use in the fixture.
     * 
     * @return A mock node layer for use in the fixture; never {@code null}.
     */
    @SuppressWarnings( "boxing" )
    private static INodeLayer createMockNodeLayer()
    {
        final IMocksControl mocksControl = EasyMock.createControl();
        final INodeLayer nodeLayer = mocksControl.createMock( INodeLayer.class );
        EasyMock.expect( nodeLayer.isNodeLayerThread() ).andReturn( true ).anyTimes();
        mocksControl.replay();
        return nodeLayer;
    }

    /**
     * Creates the remote node controller to be tested.
     * 
     * @param nodeLayer
     *        The node layer; must not be {@code null}.
     * @param node
     *        The local table network node; must not be {@code null}.
     * 
     * @return The remote node controller to be tested; never {@code null}. The
     *         remote node associated with the returned controller should be in
     *         the closed state.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    protected abstract T createRemoteNodeController(
        INodeLayer nodeLayer,
        LocalNodeType node )
        throws Exception;

    /**
     * Gets the remote node controller under test in the fixture.
     * 
     * @return The remote node controller under test in the fixture; never
     *         {@code null}.
     */
    protected final T getRemoteNodeController()
    {
        assertNotNull( remoteNodeController_ );
        return remoteNodeController_;
    }

    /**
     * Opens the remote node associated with the specified controller.
     * 
     * @param remoteNodeController
     *        The remote node controller; must not be {@code null}.
     */
    protected abstract void openRemoteNode(
        T remoteNodeController );

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
        final IMocksControl nodeMocksControl = nodeMocksControl_ = EasyMock.createNiceControl();
        final LocalNodeType node = createMockLocalNode( nodeMocksControl );
        nodeMocksControl_.replay();

        remoteNodeController_ = createRemoteNodeController( createMockNodeLayer(), node );
        assertNotNull( remoteNodeController_ );
    }

    /**
     * Ensures the {@link IRemoteNodeController#bind} method throws an exception
     * when the remote node is already bound.
     */
    @Test( expected = IllegalStateException.class )
    public void testBind_Bound()
    {
        openRemoteNode( getRemoteNodeController() );
        getRemoteNodeController().bind( "playerName" ); //$NON-NLS-1$

        getRemoteNodeController().bind( "playerName" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link IRemoteNodeController#bind} method throws an exception
     * when the remote node is closed.
     */
    @Test( expected = IllegalStateException.class )
    public void testBind_Closed()
    {
        getRemoteNodeController().bind( "playerName" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link IRemoteNodeController#bind} method throws an exception
     * when passed a player name that is associated with a table that is already
     * bound to the local node.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testBind_PlayerName_Bound()
        throws Exception
    {
        final IMocksControl nodeMocksControl = EasyMock.createControl();
        final LocalNodeType node = createMockLocalNode( nodeMocksControl );
        node.bindRemoteNode( EasyMock.<RemoteNodeType>notNull() );
        EasyMock.expectLastCall().andThrow( new IllegalArgumentException() );
        nodeMocksControl.replay();
        final T remoteNodeController = createRemoteNodeController( createMockNodeLayer(), node );
        openRemoteNode( remoteNodeController );

        remoteNodeController.bind( "playerName" ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link IRemoteNodeController#close} method throws an
     * exception when the remote node is closed.
     */
    @Test( expected = IllegalStateException.class )
    public void testClose_Closed()
    {
        getRemoteNodeController().close( null );
    }

    /**
     * Ensures the {@link IRemoteNodeController#sendMessage} method throws an
     * exception when the remote node is closed.
     */
    @Test( expected = IllegalStateException.class )
    public void testSendMessage_Closed()
    {
        final IMessageHandler messageHandler = EasyMock.createMock( IMessageHandler.class );

        getRemoteNodeController().sendMessage( EasyMock.createMock( IMessage.class ), messageHandler );
    }
}
