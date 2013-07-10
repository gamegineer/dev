/*
 * AbstractRemoteNodeTest.java
 * Copyright 2008-2013 Gamegineer.org
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

package org.gamegineer.table.internal.net.node;

import static org.junit.Assert.assertEquals;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.transport.FakeMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;
import org.gamegineer.table.net.TableNetworkError;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.AbstractRemoteNode} class.
 */
public final class AbstractRemoteNodeTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The remote node under test in the fixture. */
    private AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode> remoteNode_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRemoteNodeTest} class.
     */
    public AbstractRemoteNodeTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a mock local node for use in the fixture.
     * 
     * @return A mock local node for use in the fixture; never {@code null}.
     */
    /* @NonNull */
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
    /* @NonNull */
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
     * 
     * @throws java.lang.NullPointerException
     *         If {@code nodeLayer} or {@code node} is {@code null}.
     */
    /* @NonNull */
    private static AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode> createRemoteNode(
        /* @NonNull */
        final INodeLayer nodeLayer,
        /* @NonNull */
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
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        mocksControl_ = EasyMock.createControl();
        remoteNode_ = createRemoteNode( createMockNodeLayer(), createMockLocalNode() );
    }

    /**
     * Ensures the {@link AbstractRemoteNode#AbstractRemoteNode} constructor
     * throws an exception when passed a {@code null} table network node.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Node_Null()
    {
        createRemoteNode( EasyMock.createMock( INodeLayer.class ), null );
    }

    /**
     * Ensures the {@link AbstractRemoteNode#AbstractRemoteNode} constructor
     * throws an exception when passed a {@code null} node layer.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_NodeLayer_Null()
    {
        createRemoteNode( null, EasyMock.createMock( INode.class ) );
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
        final IServiceContext serviceContext = mocksControl_.createMock( IServiceContext.class );
        final ErrorMessage message = new ErrorMessage();
        message.setId( IMessage.MINIMUM_ID );
        message.setCorrelationId( IMessage.NULL_CORRELATION_ID );
        message.setError( TableNetworkError.UNSPECIFIED_ERROR );
        final MessageEnvelope messageEnvelope = MessageEnvelope.fromMessage( message );
        mocksControl_.replay();
        remoteNode_.started( serviceContext );

        remoteNode_.messageReceived( messageEnvelope );

        mocksControl_.verify();
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
        final IServiceContext serviceContext = mocksControl_.createMock( IServiceContext.class );
        final ErrorMessage message = new ErrorMessage();
        message.setId( IMessage.MINIMUM_ID );
        message.setCorrelationId( IMessage.MINIMUM_ID );
        message.setError( TableNetworkError.UNSPECIFIED_ERROR );
        final MessageEnvelope messageEnvelope = MessageEnvelope.fromMessage( message );
        mocksControl_.replay();
        remoteNode_.started( serviceContext );

        remoteNode_.messageReceived( messageEnvelope );

        mocksControl_.verify();
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
        final IServiceContext serviceContext = mocksControl_.createMock( IServiceContext.class );
        final Capture<IMessage> messageCapture = new Capture<>();
        serviceContext.sendMessage( EasyMock.capture( messageCapture ) );
        final FakeMessage message = new FakeMessage();
        message.setId( IMessage.MINIMUM_ID );
        message.setCorrelationId( IMessage.MAXIMUM_ID );
        final MessageEnvelope messageEnvelope = MessageEnvelope.fromMessage( message );
        mocksControl_.replay();
        remoteNode_.started( serviceContext );

        remoteNode_.messageReceived( messageEnvelope );

        mocksControl_.verify();
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
        final IServiceContext serviceContext = mocksControl_.createMock( IServiceContext.class );
        final Capture<IMessage> messageCapture = new Capture<>();
        serviceContext.sendMessage( EasyMock.capture( messageCapture ) );
        final FakeMessage message = new FakeMessage();
        message.setId( IMessage.MINIMUM_ID );
        message.setCorrelationId( IMessage.NULL_CORRELATION_ID );
        final MessageEnvelope messageEnvelope = MessageEnvelope.fromMessage( message );
        mocksControl_.replay();
        remoteNode_.started( serviceContext );

        remoteNode_.messageReceived( messageEnvelope );

        mocksControl_.verify();
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
        final IServiceContext serviceContext = mocksControl_.createMock( IServiceContext.class );
        final Capture<IMessage> messageCapture = new Capture<>();
        serviceContext.sendMessage( EasyMock.capture( messageCapture ) );
        final MessageEnvelope.HeaderBuilder headerBuilder = new MessageEnvelope.HeaderBuilder();
        final MessageEnvelope messageEnvelope = MessageEnvelope.fromByteArray( //
            headerBuilder //
                .setId( IMessage.MINIMUM_ID )//
                .setCorrelationId( IMessage.NULL_CORRELATION_ID )//
                .setBodyLength( 0 )//
                .toHeader().toByteArray() );
        mocksControl_.replay();
        remoteNode_.started( serviceContext );

        remoteNode_.messageReceived( messageEnvelope );

        mocksControl_.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( TableNetworkError.UNKNOWN_MESSAGE, ((ErrorMessage)messageCapture.getValue()).getError() );
    }

    /**
     * Ensures the {@link AbstractRemoteNode#registerUncorrelatedMessageHandler}
     * method throws an exception when passed a {@code null} message handler.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterUncorrelatedMessageHandler_MessageHandler_Null()
    {
        remoteNode_.registerUncorrelatedMessageHandler( IMessage.class, null );
    }

    /**
     * Ensures the {@link AbstractRemoteNode#registerUncorrelatedMessageHandler}
     * method throws an exception when passed a {@code null} message type.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterUncorrelatedMessageHandler_Type_Null()
    {
        remoteNode_.registerUncorrelatedMessageHandler( null, EasyMock.createMock( IMessageHandler.class ) );
    }

    /**
     * Ensures the {@link AbstractRemoteNode#registerUncorrelatedMessageHandler}
     * method throws an exception when passed a message type that is present in
     * the message handler collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterUncorrelatedMessageHandler_Type_Present()
    {
        remoteNode_.registerUncorrelatedMessageHandler( IMessage.class, EasyMock.createMock( IMessageHandler.class ) );

        remoteNode_.registerUncorrelatedMessageHandler( IMessage.class, EasyMock.createMock( IMessageHandler.class ) );
    }
}
