/*
 * BeginAuthenticationRequestMessageHandlerTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.node.client.handlers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.node.IMessageHandler;
import org.gamegineer.table.internal.net.node.client.IClientNode;
import org.gamegineer.table.internal.net.node.client.IRemoteServerNodeController;
import org.gamegineer.table.internal.net.node.common.Authenticator;
import org.gamegineer.table.internal.net.node.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.node.common.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link BeginAuthenticationRequestMessageHandler}
 * class.
 */
public final class BeginAuthenticationRequestMessageHandlerTest
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
     * Initializes a new instance of the
     * {@code BeginAuthenticationRequestMessageHandlerTest} class.
     */
    public BeginAuthenticationRequestMessageHandlerTest()
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
        messageHandler_ = BeginAuthenticationRequestMessageHandler.INSTANCE;
    }

    /**
     * Ensures the
     * {@link BeginAuthenticationRequestMessageHandler#handleMessage} method
     * correctly handles a begin authentication request message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHandleMessage_BeginAuthenticationRequestMessage()
        throws Exception
    {
        final String playerName = "playerName"; //$NON-NLS-1$
        final SecureString password = new SecureString( "password".toCharArray() ); //$NON-NLS-1$
        final IClientNode localNode = mocksControl_.createMock( IClientNode.class );
        EasyMock.expect( localNode.getPlayerName() ).andReturn( playerName );
        EasyMock.expect( localNode.getPassword() ).andReturn( new SecureString( password ) );
        final IRemoteServerNodeController remoteNodeController = mocksControl_.createMock( IRemoteServerNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        final Capture<IMessage> messageCapture = new Capture<>();
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.notNull( IMessageHandler.class ) );
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
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
        assertEquals( BeginAuthenticationResponseMessage.class, messageCapture.getValue().getClass() );
        assertEquals( message.getId(), messageCapture.getValue().getCorrelationId() );
        final BeginAuthenticationResponseMessage responseMessage = (BeginAuthenticationResponseMessage)messageCapture.getValue();
        assertEquals( playerName, responseMessage.getPlayerName() );
        final Authenticator authenticator = new Authenticator();
        assertArrayEquals( authenticator.createResponse( challenge, password, salt ), responseMessage.getResponse() );
    }
}
