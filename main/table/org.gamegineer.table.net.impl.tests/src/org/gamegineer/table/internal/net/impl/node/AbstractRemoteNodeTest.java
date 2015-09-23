/*
 * AbstractRemoteNodeTest.java
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
 * Created on Apr 14, 2011 at 11:03:54 PM.
 */

package org.gamegineer.table.internal.net.impl.node;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import static org.junit.Assert.assertEquals;
import java.util.Optional;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.impl.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.impl.transport.FakeMessage;
import org.gamegineer.table.internal.net.impl.transport.IMessage;
import org.gamegineer.table.internal.net.impl.transport.IServiceContext;
import org.gamegineer.table.internal.net.impl.transport.MessageEnvelope;
import org.gamegineer.table.net.TableNetworkError;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link AbstractRemoteNode} class.
 */
public final class AbstractRemoteNodeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private Optional<IMocksControl> mocksControl_;

    /** The remote node under test in the fixture. */
    private Optional<AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode>> remoteNode_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRemoteNodeTest} class.
     */
    public AbstractRemoteNodeTest()
    {
        mocksControl_ = Optional.empty();
        remoteNode_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a mock local node for use in the fixture.
     * 
     * @return A mock local node for use in the fixture; never {@code null}.
     */
    private static INode<IRemoteNode> createMockLocalNode()
    {
        final IMocksControl mocksControl = EasyMock.createControl();
        final INode<IRemoteNode> localNode = mocksControl.createMock( INode.class );
        mocksControl.replay();
        return localNode;
    }

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
     * Creates a new instance of the {@code AbstractRemoteNode} class.
     * 
     * @param nodeLayer
     *        The node layer; must not be {@code null}.
     * @param node
     *        The local table network node; must not be {@code null}.
     * 
     * @return A new instance of the {@code AbstractRemoteNode} class; never
     *         {@code null}.
     */
    private static AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode> createRemoteNode(
        final INodeLayer nodeLayer,
        final INode<IRemoteNode> node )
    {
        return new AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode>( nodeLayer, node )
        {
            @Override
            protected IRemoteNode getThisAsRemoteNodeType()
            {
                return this;
            }
        };
    }

    /**
     * Gets the fixture mocks control.
     * 
     * @return The fixture mocks control; never {@code null}.
     */
    private IMocksControl getMocksControl()
    {
        return mocksControl_.get();
    }

    /**
     * Gets the remote node under test in the fixture.
     * 
     * @return The remote node under test in the fixture; never {@code null}.
     */
    private AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode> getRemoteNode()
    {
        return remoteNode_.get();
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
        mocksControl_ = Optional.of( EasyMock.createControl() );
        remoteNode_ = Optional.of( createRemoteNode( createMockNodeLayer(), createMockLocalNode() ) );
    }

    /**
     * Ensures the {@link AbstractRemoteNode#messageReceived} method does
     * nothing in response to a message envelope that contains an error message
     * with a null correlation identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testMessageReceived_MessageEnvelope_ErrorMessage_Uncorrelated()
        throws Exception
    {
        final AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode> remoteNode = getRemoteNode();
        final IMocksControl mocksControl = getMocksControl();
        final IServiceContext serviceContext = mocksControl.createMock( IServiceContext.class );
        final ErrorMessage message = new ErrorMessage();
        message.setId( IMessage.MINIMUM_ID );
        message.setCorrelationId( IMessage.NULL_CORRELATION_ID );
        message.setError( TableNetworkError.UNSPECIFIED_ERROR );
        final MessageEnvelope messageEnvelope = MessageEnvelope.fromMessage( message );
        mocksControl.replay();
        remoteNode.started( serviceContext );

        remoteNode.messageReceived( messageEnvelope );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link AbstractRemoteNode#messageReceived} method does
     * nothing in response to a message envelope that contains an unhandled
     * error message with a non-null correlation identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testMessageReceived_MessageEnvelope_UnhandledMessage_Correlated_Error()
        throws Exception
    {
        final AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode> remoteNode = getRemoteNode();
        final IMocksControl mocksControl = getMocksControl();
        final IServiceContext serviceContext = mocksControl.createMock( IServiceContext.class );
        final ErrorMessage message = new ErrorMessage();
        message.setId( IMessage.MINIMUM_ID );
        message.setCorrelationId( IMessage.MINIMUM_ID );
        message.setError( TableNetworkError.UNSPECIFIED_ERROR );
        final MessageEnvelope messageEnvelope = MessageEnvelope.fromMessage( message );
        mocksControl.replay();
        remoteNode.started( serviceContext );

        remoteNode.messageReceived( messageEnvelope );

        mocksControl.verify();
    }

    /**
     * Ensures the {@link AbstractRemoteNode#messageReceived} method sends an
     * error message in response to a message envelope that contains an
     * unhandled non-error message with a non-null correlation identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testMessageReceived_MessageEnvelope_UnhandledMessage_Correlated_NonError()
        throws Exception
    {
        final AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode> remoteNode = getRemoteNode();
        final IMocksControl mocksControl = getMocksControl();
        final IServiceContext serviceContext = mocksControl.createMock( IServiceContext.class );
        final Capture<IMessage> messageCapture = new Capture<>();
        serviceContext.sendMessage( EasyMock.capture( messageCapture ) );
        final FakeMessage message = new FakeMessage();
        message.setId( IMessage.MINIMUM_ID );
        message.setCorrelationId( IMessage.MAXIMUM_ID );
        final MessageEnvelope messageEnvelope = MessageEnvelope.fromMessage( message );
        mocksControl.replay();
        remoteNode.started( serviceContext );

        remoteNode.messageReceived( messageEnvelope );

        mocksControl.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( TableNetworkError.UNHANDLED_MESSAGE, ((ErrorMessage)messageCapture.getValue()).getError() );
    }

    /**
     * Ensures the {@link AbstractRemoteNode#messageReceived} method sends an
     * error message in response to a message envelope that contains an
     * unhandled message with a null correlation identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testMessageReceived_MessageEnvelope_UnhandledMessage_Uncorrelated()
        throws Exception
    {
        final AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode> remoteNode = getRemoteNode();
        final IMocksControl mocksControl = getMocksControl();
        final IServiceContext serviceContext = mocksControl.createMock( IServiceContext.class );
        final Capture<IMessage> messageCapture = new Capture<>();
        serviceContext.sendMessage( EasyMock.capture( messageCapture ) );
        final FakeMessage message = new FakeMessage();
        message.setId( IMessage.MINIMUM_ID );
        message.setCorrelationId( IMessage.NULL_CORRELATION_ID );
        final MessageEnvelope messageEnvelope = MessageEnvelope.fromMessage( message );
        mocksControl.replay();
        remoteNode.started( serviceContext );

        remoteNode.messageReceived( messageEnvelope );

        mocksControl.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( TableNetworkError.UNHANDLED_MESSAGE, ((ErrorMessage)messageCapture.getValue()).getError() );
    }

    /**
     * Ensures the {@link AbstractRemoteNode#messageReceived} method sends an
     * error message in response to a message envelope that contains an unknown
     * message.
     */
    @Test
    public void testMessageReceived_MessageEnvelope_UnknownMessage()
    {
        final AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode> remoteNode = getRemoteNode();
        final IMocksControl mocksControl = getMocksControl();
        final IServiceContext serviceContext = mocksControl.createMock( IServiceContext.class );
        final Capture<IMessage> messageCapture = new Capture<>();
        serviceContext.sendMessage( EasyMock.capture( messageCapture ) );
        final MessageEnvelope.HeaderBuilder headerBuilder = new MessageEnvelope.HeaderBuilder();
        final MessageEnvelope messageEnvelope = MessageEnvelope.fromByteArray( //
            headerBuilder //
                .setId( IMessage.MINIMUM_ID )//
                .setCorrelationId( IMessage.NULL_CORRELATION_ID )//
                .setBodyLength( 0 )//
                .toHeader().toByteArray() );
        mocksControl.replay();
        remoteNode.started( serviceContext );

        remoteNode.messageReceived( messageEnvelope );

        mocksControl.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( TableNetworkError.UNKNOWN_MESSAGE, ((ErrorMessage)messageCapture.getValue()).getError() );
    }

    /**
     * Ensures the {@link AbstractRemoteNode#registerUncorrelatedMessageHandler}
     * method throws an exception when passed a message type that is present in
     * the message handler collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterUncorrelatedMessageHandler_Type_Present()
    {
        final AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode> remoteNode = getRemoteNode();
        remoteNode.registerUncorrelatedMessageHandler( nonNull( IMessage.class ), EasyMock.createMock( IMessageHandler.class ) );

        remoteNode.registerUncorrelatedMessageHandler( nonNull( IMessage.class ), EasyMock.createMock( IMessageHandler.class ) );
    }
}
