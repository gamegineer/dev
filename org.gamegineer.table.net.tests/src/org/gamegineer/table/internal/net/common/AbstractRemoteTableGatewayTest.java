/*
 * AbstractRemoteTableGatewayTest.java
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
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.transport.FakeMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.IServiceContext;
import org.gamegineer.table.internal.net.transport.MessageEnvelope;
import org.gamegineer.table.net.NetworkTableError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway}
 * class.
 */
public final class AbstractRemoteTableGatewayTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;

    /** The remote table gateway under test in the fixture. */
    private AbstractRemoteTableGateway remoteTableGateway_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractRemoteTableGatewayTest}
     * class.
     */
    public AbstractRemoteTableGatewayTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code AbstractRemoteTableGateway} class.
     * 
     * @param tableGatewayContext
     *        The table gateway context; must not be {@code null}.
     * 
     * @return A new instance of the {@code AbstractRemoteTableGateway} class;
     *         never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableGatewayContext} is {@code null}.
     */
    /* @NonNull */
    private static AbstractRemoteTableGateway createRemoteTableGateway(
        /* @NonNull */
        final ITableGatewayContext tableGatewayContext )
    {
        return new AbstractRemoteTableGateway( tableGatewayContext )
        {
            // no overrides
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
        remoteTableGateway_ = createRemoteTableGateway( EasyMock.createMock( ITableGatewayContext.class ) );
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
        remoteTableGateway_ = null;
        mocksControl_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table gateway context.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_TableGatewayContext_Null()
    {
        createRemoteTableGateway( null );
    }

    /**
     * Ensures the {@code messageReceived} method sends an error message in
     * response to a message envelope that contains an unhandled message with a
     * non-null correlation identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testMessageReceived_MessageEnvelope_UnhandledMessage_Correlated()
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
        remoteTableGateway_.started( serviceContext );

        remoteTableGateway_.messageReceived( messageEnvelope );

        mocksControl_.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( NetworkTableError.UNHANDLED_MESSAGE, ((ErrorMessage)messageCapture.getValue()).getError() );
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
        remoteTableGateway_.started( serviceContext );

        remoteTableGateway_.messageReceived( messageEnvelope );

        mocksControl_.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( NetworkTableError.UNHANDLED_MESSAGE, ((ErrorMessage)messageCapture.getValue()).getError() );
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
        remoteTableGateway_.started( serviceContext );

        remoteTableGateway_.messageReceived( messageEnvelope );

        mocksControl_.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( NetworkTableError.UNKNOWN_MESSAGE, ((ErrorMessage)messageCapture.getValue()).getError() );
    }

    /**
     * Ensures the {@code registerUncorrelatedMessageHandler} method throws an
     * exception when passed a {@code null} message handler.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterUncorrelatedMessageHandler_MessageHandler_Null()
    {
        remoteTableGateway_.registerUncorrelatedMessageHandler( IMessage.class, null );
    }

    /**
     * Ensures the {@code registerUncorrelatedMessageHandler} method throws an
     * exception when passed a {@code null} message type.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterUncorrelatedMessageHandler_Type_Null()
    {
        remoteTableGateway_.registerUncorrelatedMessageHandler( null, EasyMock.createMock( IRemoteTableGateway.IMessageHandler.class ) );
    }

    /**
     * Ensures the {@code registerUncorrelatedMessageHandler} method throws an
     * exception when passed a message type that is present in the message
     * handler collection.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testRegisterUncorrelatedMessageHandler_Type_Present()
    {
        remoteTableGateway_.registerUncorrelatedMessageHandler( IMessage.class, EasyMock.createMock( IRemoteTableGateway.IMessageHandler.class ) );

        remoteTableGateway_.registerUncorrelatedMessageHandler( IMessage.class, EasyMock.createMock( IRemoteTableGateway.IMessageHandler.class ) );
    }
}
