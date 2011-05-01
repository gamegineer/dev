/*
 * BeginAuthenticationRequestMessageHandlerTest.java
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
 * Created on Apr 29, 2011 at 10:09:08 PM.
 */

package org.gamegineer.table.internal.net.client;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.common.Authenticator;
import org.gamegineer.table.internal.net.common.IRemoteTableGateway.IMessageHandler;
import org.gamegineer.table.internal.net.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.common.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.NetworkTableError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.client.BeginAuthenticationRequestMessageHandler}
 * class.
 */
public final class BeginAuthenticationRequestMessageHandlerTest
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
     * Initializes a new instance of the {@code
     * BeginAuthenticationRequestMessageHandlerTest} class.
     */
    public BeginAuthenticationRequestMessageHandlerTest()
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
     * Ensures the {@code handleMessage} method correctly handles a begin
     * authentication request message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHandleMessage_BeginAuthenticationRequestMessage()
        throws Exception
    {
        final String playerName = "playerName"; //$NON-NLS-1$
        final SecureString password = new SecureString( "password".toCharArray() ); //$NON-NLS-1$
        final ITableGatewayContext tableGatewayContext = mocksControl_.createMock( ITableGatewayContext.class );
        EasyMock.expect( tableGatewayContext.getLocalPlayerName() ).andReturn( playerName );
        EasyMock.expect( tableGatewayContext.getPassword() ).andReturn( new SecureString( password ) );
        final IRemoteServerTableGateway remoteTableGateway = mocksControl_.createMock( IRemoteServerTableGateway.class );
        EasyMock.expect( remoteTableGateway.getContext() ).andReturn( tableGatewayContext ).anyTimes();
        final Capture<IMessage> messageCapture = new Capture<IMessage>();
        EasyMock.expect( remoteTableGateway.sendMessage( EasyMock.capture( messageCapture ), EasyMock.notNull( IMessageHandler.class ) ) ).andReturn( true );
        mocksControl_.replay();

        final BeginAuthenticationRequestMessage message = new BeginAuthenticationRequestMessage();
        final byte[] challenge = new byte[] {
            1, 2, 3, 4
        };
        message.setChallenge( challenge );
        final byte[] salt = new byte[] {
            5, 6, 7, 8
        };
        message.setSalt( salt );
        final BeginAuthenticationRequestMessageHandler messageHandler = new BeginAuthenticationRequestMessageHandler( remoteTableGateway );
        messageHandler.handleMessage( message );

        mocksControl_.verify();
        assertEquals( BeginAuthenticationResponseMessage.class, messageCapture.getValue().getClass() );
        assertEquals( message.getId(), messageCapture.getValue().getCorrelationId() );
        final BeginAuthenticationResponseMessage responseMessage = (BeginAuthenticationResponseMessage)messageCapture.getValue();
        assertEquals( playerName, responseMessage.getPlayerName() );
        final Authenticator authenticator = new Authenticator();
        assertArrayEquals( authenticator.createResponse( challenge, password, salt ), responseMessage.getResponse() );
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles a begin
     * authentication request message in the case when the attempt to send the
     * response message fails.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHandleMessage_BeginAuthenticationRequestMessage_SendResponseMessageFails()
    {
        final ITableGatewayContext tableGatewayContext = mocksControl_.createMock( ITableGatewayContext.class );
        EasyMock.expect( tableGatewayContext.getLocalPlayerName() ).andReturn( "playerName" ); //$NON-NLS-1$
        EasyMock.expect( tableGatewayContext.getPassword() ).andReturn( new SecureString( "password".toCharArray() ) ); //$NON-NLS-1$
        final IRemoteServerTableGateway remoteTableGateway = mocksControl_.createMock( IRemoteServerTableGateway.class );
        EasyMock.expect( remoteTableGateway.getContext() ).andReturn( tableGatewayContext ).anyTimes();
        EasyMock.expect( remoteTableGateway.sendMessage( EasyMock.notNull( IMessage.class ), EasyMock.notNull( IMessageHandler.class ) ) ).andReturn( false );
        remoteTableGateway.close( NetworkTableError.TRANSPORT_ERROR );
        mocksControl_.replay();

        final BeginAuthenticationRequestMessage message = new BeginAuthenticationRequestMessage();
        message.setChallenge( new byte[] {
            1, 2, 3, 4
        } );
        message.setSalt( new byte[] {
            5, 6, 7, 8
        } );
        final BeginAuthenticationRequestMessageHandler messageHandler = new BeginAuthenticationRequestMessageHandler( remoteTableGateway );
        messageHandler.handleMessage( message );

        mocksControl_.verify();
    }
}
