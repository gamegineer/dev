/*
 * HelloRequestMessageHandlerTest.java
 * Copyright 2008-2012 Gamegineer.org
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

package org.gamegineer.table.internal.net.node.server.handlers;

import static org.junit.Assert.assertEquals;
import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.node.IMessageHandler;
import org.gamegineer.table.internal.net.node.common.ProtocolVersions;
import org.gamegineer.table.internal.net.node.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.node.common.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.node.common.messages.HelloResponseMessage;
import org.gamegineer.table.internal.net.node.server.IRemoteClientNodeController;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.server.handlers.HelloRequestMessageHandler}
 * class.
 */
public final class HelloRequestMessageHandlerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The message handler under test in the fixture. */
    private IMessageHandler messageHandler_;

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
        messageHandler_ = HelloRequestMessageHandler.INSTANCE;
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles a hello
     * request message in the case when the client specifies a supported
     * protocol version.
     */
    @Test
    public void testHandleMessage_HelloRequestMessage_SupportedProtocolVersion()
    {
        final IRemoteClientNodeController remoteNodeController = mocksControl_.createMock( IRemoteClientNodeController.class );
        final Capture<IMessage> messageCapture = new Capture<IMessage>( CaptureType.ALL );
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.isNull( IMessageHandler.class ) );
        remoteNodeController.setChallenge( EasyMock.notNull( byte[].class ) );
        remoteNodeController.setSalt( EasyMock.notNull( byte[].class ) );
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.notNull( IMessageHandler.class ) );
        mocksControl_.replay();

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setSupportedProtocolVersion( ProtocolVersions.VERSION_1 );
        messageHandler_.handleMessage( remoteNodeController, message );

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
    @Test
    public void testHandleMessage_HelloRequestMessage_UnsupportedProtocolVersion()
    {
        final IRemoteClientNodeController remoteNodeController = mocksControl_.createMock( IRemoteClientNodeController.class );
        final Capture<IMessage> messageCapture = new Capture<IMessage>();
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.isNull( IMessageHandler.class ) );
        remoteNodeController.close( TableNetworkError.UNSUPPORTED_PROTOCOL_VERSION );
        mocksControl_.replay();

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setSupportedProtocolVersion( 0 );
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( message.getId(), messageCapture.getValue().getCorrelationId() );
        assertEquals( TableNetworkError.UNSUPPORTED_PROTOCOL_VERSION, ((ErrorMessage)messageCapture.getValue()).getError() );
    }
}
