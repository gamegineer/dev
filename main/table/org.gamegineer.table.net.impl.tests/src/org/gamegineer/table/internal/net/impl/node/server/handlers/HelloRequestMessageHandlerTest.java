/*
 * HelloRequestMessageHandlerTest.java
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
 * Created on Apr 29, 2011 at 9:24:02 PM.
 */

package org.gamegineer.table.internal.net.impl.node.server.handlers;

import static org.junit.Assert.assertEquals;
import java.util.Optional;
import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.net.impl.node.IMessageHandler;
import org.gamegineer.table.internal.net.impl.node.common.ProtocolVersions;
import org.gamegineer.table.internal.net.impl.node.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.HelloResponseMessage;
import org.gamegineer.table.internal.net.impl.node.server.IRemoteClientNodeController;
import org.gamegineer.table.internal.net.impl.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link HelloRequestMessageHandler} class.
 */
public final class HelloRequestMessageHandlerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private Optional<IMocksControl> mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloRequestMessageHandlerTest}
     * class.
     */
    public HelloRequestMessageHandlerTest()
    {
        mocksControl_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the message handler under test in the fixture.
     * 
     * @return The message handler under test in the fixture.
     */
    private IMessageHandler getMessageHandler()
    {
        return HelloRequestMessageHandler.INSTANCE;
    }

    /**
     * Gets the fixture mocks control.
     * 
     * @return The fixture mocks control.
     */
    private IMocksControl getMocksControl()
    {
        return mocksControl_.get();
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
    }

    /**
     * Ensures the {@link HelloRequestMessageHandler#handleMessage} method
     * correctly handles a hello request message in the case when the client
     * specifies a supported protocol version.
     */
    @Test
    public void testHandleMessage_HelloRequestMessage_SupportedProtocolVersion()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IRemoteClientNodeController remoteNodeController = mocksControl.createMock( IRemoteClientNodeController.class );
        final Capture<IMessage> messageCapture = new Capture<>( CaptureType.ALL );
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.<@Nullable IMessageHandler>isNull() );
        remoteNodeController.setChallenge( EasyMock.<byte @NonNull []>notNull() );
        remoteNodeController.setSalt( EasyMock.<byte @NonNull []>notNull() );
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.<@NonNull IMessageHandler>notNull() );
        mocksControl.replay();

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setSupportedProtocolVersion( ProtocolVersions.VERSION_1 );
        getMessageHandler().handleMessage( remoteNodeController, message );

        mocksControl.verify();
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
     * Ensures the {@link HelloRequestMessageHandler#handleMessage} method
     * correctly handles a hello request message in the case when the client
     * specifies an unsupported protocol version.
     */
    @Test
    public void testHandleMessage_HelloRequestMessage_UnsupportedProtocolVersion()
    {
        final IMocksControl mocksControl = getMocksControl();
        final IRemoteClientNodeController remoteNodeController = mocksControl.createMock( IRemoteClientNodeController.class );
        final Capture<IMessage> messageCapture = new Capture<>();
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.<@Nullable IMessageHandler>isNull() );
        remoteNodeController.close( TableNetworkError.UNSUPPORTED_PROTOCOL_VERSION );
        mocksControl.replay();

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setSupportedProtocolVersion( 0 );
        getMessageHandler().handleMessage( remoteNodeController, message );

        mocksControl.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( message.getId(), messageCapture.getValue().getCorrelationId() );
        assertEquals( TableNetworkError.UNSUPPORTED_PROTOCOL_VERSION, ((ErrorMessage)messageCapture.getValue()).getError() );
    }
}
