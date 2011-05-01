/*
 * HelloRequestMessageHandlerTest.java
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
 * Created on Apr 29, 2011 at 9:24:02 PM.
 */

package org.gamegineer.table.internal.net.server;

import static org.junit.Assert.assertEquals;
import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.common.ProtocolVersions;
import org.gamegineer.table.internal.net.common.IRemoteTableGateway.IMessageHandler;
import org.gamegineer.table.internal.net.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.common.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.common.messages.HelloResponseMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.NetworkTableError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.server.HelloRequestMessageHandler}
 * class.
 */
public final class HelloRequestMessageHandlerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloRequestMessageHandlerTest}
     * class.
     */
    public HelloRequestMessageHandlerTest()
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
        mocksControl_ = EasyMock.createControl();
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
        mocksControl_ = null;
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles a hello
     * request message in the case when the attempt to send the begin
     * authentication request message fails.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHandleMessage_HelloRequestMessage_SendBeginAuthenticationRequestMessageFails()
    {
        final IRemoteClientTableGateway remoteTableGateway = mocksControl_.createMock( IRemoteClientTableGateway.class );
        final Capture<IMessage> messageCapture = new Capture<IMessage>( CaptureType.ALL );
        EasyMock.expect( remoteTableGateway.sendMessage( EasyMock.capture( messageCapture ), EasyMock.isNull( IMessageHandler.class ) ) ).andReturn( true );
        remoteTableGateway.setChallenge( EasyMock.notNull( byte[].class ) );
        remoteTableGateway.setSalt( EasyMock.notNull( byte[].class ) );
        EasyMock.expect( remoteTableGateway.sendMessage( EasyMock.capture( messageCapture ), EasyMock.notNull( IMessageHandler.class ) ) ).andReturn( false );
        remoteTableGateway.close( NetworkTableError.TRANSPORT_ERROR );
        mocksControl_.replay();

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setSupportedProtocolVersion( ProtocolVersions.VERSION_1 );
        final HelloRequestMessageHandler messageHandler = new HelloRequestMessageHandler( remoteTableGateway );
        messageHandler.handleMessage( message );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles a hello
     * request message in the case when the attempt to send the response message
     * fails.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHandleMessage_HelloRequestMessage_SendResponseMessageFails()
    {
        final IRemoteClientTableGateway remoteTableGateway = mocksControl_.createMock( IRemoteClientTableGateway.class );
        EasyMock.expect( remoteTableGateway.sendMessage( EasyMock.notNull( IMessage.class ), EasyMock.isNull( IMessageHandler.class ) ) ).andReturn( false );
        remoteTableGateway.close( NetworkTableError.TRANSPORT_ERROR );
        mocksControl_.replay();

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setSupportedProtocolVersion( ProtocolVersions.VERSION_1 );
        final HelloRequestMessageHandler messageHandler = new HelloRequestMessageHandler( remoteTableGateway );
        messageHandler.handleMessage( message );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles a hello
     * request message in the case when the client specifies a supported
     * protocol version.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHandleMessage_HelloRequestMessage_SupportedProtocolVersion()
    {
        final IRemoteClientTableGateway remoteTableGateway = mocksControl_.createMock( IRemoteClientTableGateway.class );
        final Capture<IMessage> messageCapture = new Capture<IMessage>( CaptureType.ALL );
        EasyMock.expect( remoteTableGateway.sendMessage( EasyMock.capture( messageCapture ), EasyMock.isNull( IMessageHandler.class ) ) ).andReturn( true );
        remoteTableGateway.setChallenge( EasyMock.notNull( byte[].class ) );
        remoteTableGateway.setSalt( EasyMock.notNull( byte[].class ) );
        EasyMock.expect( remoteTableGateway.sendMessage( EasyMock.capture( messageCapture ), EasyMock.notNull( IMessageHandler.class ) ) ).andReturn( true );
        mocksControl_.replay();

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setSupportedProtocolVersion( ProtocolVersions.VERSION_1 );
        final HelloRequestMessageHandler messageHandler = new HelloRequestMessageHandler( remoteTableGateway );
        messageHandler.handleMessage( message );

        mocksControl_.verify();
        assertEquals( 2, messageCapture.getValues().size() );
        final IMessage firstResponseMessage = messageCapture.getValues().get( 0 );
        assertEquals( HelloResponseMessage.class, firstResponseMessage.getClass() );
        assertEquals( message.getId(), firstResponseMessage.getCorrelationId() );
        assertEquals( ProtocolVersions.VERSION_1, ((HelloResponseMessage)firstResponseMessage).getChosenProtocolVersion() );
        final IMessage secondResponseMessage = messageCapture.getValues().get( 1 );
        assertEquals( BeginAuthenticationRequestMessage.class, secondResponseMessage.getClass() );
        assertEquals( IMessage.NULL_CORRELATION_ID, secondResponseMessage.getCorrelationId() );
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles a hello
     * request message in the case when the client specifies an unsupported
     * protocol version.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHandleMessage_HelloRequestMessage_UnsupportedProtocolVersion()
    {
        final IRemoteClientTableGateway remoteTableGateway = mocksControl_.createMock( IRemoteClientTableGateway.class );
        final Capture<IMessage> messageCapture = new Capture<IMessage>();
        EasyMock.expect( remoteTableGateway.sendMessage( EasyMock.capture( messageCapture ), EasyMock.isNull( IMessageHandler.class ) ) ).andReturn( true );
        remoteTableGateway.close( NetworkTableError.UNSUPPORTED_PROTOCOL_VERSION );
        mocksControl_.replay();

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setSupportedProtocolVersion( 0 );
        final HelloRequestMessageHandler messageHandler = new HelloRequestMessageHandler( remoteTableGateway );
        messageHandler.handleMessage( message );

        mocksControl_.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( message.getId(), messageCapture.getValue().getCorrelationId() );
        assertEquals( NetworkTableError.UNSUPPORTED_PROTOCOL_VERSION, ((ErrorMessage)messageCapture.getValue()).getError() );
    }
}
