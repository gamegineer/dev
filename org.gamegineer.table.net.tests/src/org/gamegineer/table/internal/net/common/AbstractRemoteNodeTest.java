/*
 * AbstractRemoteNodeTest.java
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
 * Created on Apr 14, 2011 at 11:03:54 PM.
 */

package org.gamegineer.table.internal.net.common;

import static org.junit.Assert.assertEquals;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.INode;
import org.gamegineer.table.internal.net.IRemoteNode;
import org.gamegineer.table.internal.net.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.transport.FakeMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;
import org.gamegineer.table.net.TableNetworkError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.common.AbstractRemoteNode} class.
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
        super();
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
    @SuppressWarnings( "unchecked" )
    private INode<IRemoteNode> createMockLocalNode()
    {
        return mocksControl_.createMock( INode.class );
    }

    /**
     * Creates a new instance of the {@code AbstractRemoteNode} class.
     * 
     * @param node
     *        The local table network node; must not be {@code null}.
     * 
     * @return A new instance of the {@code AbstractRemoteNode} class; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code node} is {@code null}.
     */
    /* @NonNull */
    private static AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode> createRemoteNode(
        /* @NonNull */
        final INode<IRemoteNode> node )
    {
        return new AbstractRemoteNode<INode<IRemoteNode>, IRemoteNode>( node )
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
        final INode<IRemoteNode> localNode = createMockLocalNode();
        EasyMock.expect( localNode.getLock() ).andReturn( new Object() ).anyTimes();
        remoteNode_ = createRemoteNode( localNode );
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
        remoteNode_ = null;
        mocksControl_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table network node.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_Node_Null()
    {
        createRemoteNode( null );
    }

    /**
     * Ensures the {@code messageReceived} method does nothing in response to a
     * message envelope that contains an error message with a null correlation
     * identifier.
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
     * Ensures the {@code messageReceived} method does nothing in response to a
     * message envelope that contains an unhandled error message with a non-null
     * correlation identifier.
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
     * Ensures the {@code messageReceived} method sends an error message in
     * response to a message envelope that contains an unhandled non-error
     * message with a non-null correlation identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testMessageReceived_MessageEnvelope_UnhandledMessage_Correlated_NonError()
        throws Exception
    {
        final IServiceContext serviceContext = mocksControl_.createMock( IServiceContext.class );
        final Capture<IMessage> messageCapture = new Capture<IMessage>();
        EasyMock.expect( serviceContext.sendMessage( EasyMock.capture( messageCapture ) ) ).andReturn( true );
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
     * Ensures the {@code messageReceived} method sends an error message in
     * response to a message envelope that contains an unhandled message with a
     * null correlation identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testMessageReceived_MessageEnvelope_UnhandledMessage_Uncorrelated()
        throws Exception
    {
        final IServiceContext serviceContext = mocksControl_.createMock( IServiceContext.class );
        final Capture<IMessage> messageCapture = new Capture<IMessage>();
        EasyMock.expect( serviceContext.sendMessage( EasyMock.capture( messageCapture ) ) ).andReturn( true );
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
     * Ensures the {@code messageReceived} method sends an error message in
     * response to a message envelope that contains an unknown message.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testMessageReceived_MessageEnvelope_UnknownMessage()
    {
        final IServiceContext serviceContext = mocksControl_.createMock( IServiceContext.class );
        final Capture<IMessage> messageCapture = new Capture<IMessage>();
        EasyMock.expect( serviceContext.sendMessage( EasyMock.capture( messageCapture ) ) ).andReturn( true );
        final MessageEnvelope messageEnvelope = new MessageEnvelope( IMessage.MINIMUM_ID, IMessage.NULL_CORRELATION_ID, new byte[ 0 ] );
        mocksControl_.replay();
        remoteNode_.started( serviceContext );

        remoteNode_.messageReceived( messageEnvelope );

        mocksControl_.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( TableNetworkError.UNKNOWN_MESSAGE, ((ErrorMessage)messageCapture.getValue()).getError() );
    }

    /**
     * Ensures the {@code registerUncorrelatedMessageHandler} method throws an
     * exception when passed a {@code null} message handler.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterUncorrelatedMessageHandler_MessageHandler_Null()
    {
        remoteNode_.registerUncorrelatedMessageHandler( IMessage.class, null );
    }

    /**
     * Ensures the {@code registerUncorrelatedMessageHandler} method throws an
     * exception when passed a {@code null} message type.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterUncorrelatedMessageHandler_Type_Null()
    {
        remoteNode_.registerUncorrelatedMessageHandler( null, EasyMock.createMock( IRemoteNodeController.IMessageHandler.class ) );
    }

    /**
     * Ensures the {@code registerUncorrelatedMessageHandler} method throws an
     * exception when passed a message type that is present in the message
     * handler collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterUncorrelatedMessageHandler_Type_Present()
    {
        remoteNode_.registerUncorrelatedMessageHandler( IMessage.class, EasyMock.createMock( IRemoteNodeController.IMessageHandler.class ) );

        remoteNode_.registerUncorrelatedMessageHandler( IMessage.class, EasyMock.createMock( IRemoteNodeController.IMessageHandler.class ) );
    }
}
