/*
 * HelloResponseMessageHandlerTest.java
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
 * Created on Apr 29, 2011 at 10:00:40 PM.
 */

package org.gamegineer.table.internal.net.client;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.common.ProtocolVersions;
import org.gamegineer.table.internal.net.common.IRemoteTableGateway.IMessageHandler;
import org.gamegineer.table.internal.net.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.common.messages.HelloResponseMessage;
import org.gamegineer.table.internal.net.transport.FakeMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.NetworkTableError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.client.HelloResponseMessageHandler}
 * class.
 */
public final class HelloResponseMessageHandlerTest
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
     * Initializes a new instance of the {@code HelloResponseMessageHandlerTest}
     * class.
     */
    public HelloResponseMessageHandlerTest()
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
     * Ensures the {@code handleMessage} method correctly handles an error
     * message.
     */
    @Test
    public void testHandleMessage_ErrorMessage()
    {
        final IRemoteServerTableGateway remoteTableGateway = mocksControl_.createMock( IRemoteServerTableGateway.class );
        remoteTableGateway.close( NetworkTableError.UNSPECIFIED_ERROR );
        mocksControl_.replay();

        final ErrorMessage message = new ErrorMessage();
        message.setError( NetworkTableError.UNSPECIFIED_ERROR );
        final HelloResponseMessageHandler messageHandler = new HelloResponseMessageHandler( remoteTableGateway );
        messageHandler.handleMessage( message );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles a hello
     * response message.
     */
    @Test
    public void testHandleMessage_HelloResponseMessage()
    {
        final IRemoteServerTableGateway remoteTableGateway = mocksControl_.createMock( IRemoteServerTableGateway.class );
        mocksControl_.replay();

        final HelloResponseMessage message = new HelloResponseMessage();
        message.setChosenProtocolVersion( ProtocolVersions.VERSION_1 );
        final HelloResponseMessageHandler messageHandler = new HelloResponseMessageHandler( remoteTableGateway );
        messageHandler.handleMessage( message );

        mocksControl_.verify();
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles an unexpected
     * message.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHandleMessage_UnexpectedMessage()
    {
        final IRemoteServerTableGateway remoteTableGateway = mocksControl_.createMock( IRemoteServerTableGateway.class );
        EasyMock.expect( remoteTableGateway.sendMessage( EasyMock.notNull( IMessage.class ), EasyMock.isNull( IMessageHandler.class ) ) ).andReturn( true );
        remoteTableGateway.close( NetworkTableError.UNEXPECTED_MESSAGE );
        mocksControl_.replay();

        final FakeMessage message = new FakeMessage();
        final HelloResponseMessageHandler messageHandler = new HelloResponseMessageHandler( remoteTableGateway );
        messageHandler.handleMessage( message );

        mocksControl_.verify();
    }
}
