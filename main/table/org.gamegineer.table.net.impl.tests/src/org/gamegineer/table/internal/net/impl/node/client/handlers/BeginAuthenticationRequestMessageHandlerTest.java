/*
 * BeginAuthenticationRequestMessageHandlerTest.java
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
 * Created on Apr 29, 2011 at 10:09:08 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client.handlers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.util.Optional;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.impl.node.IMessageHandler;
import org.gamegineer.table.internal.net.impl.node.client.IClientNode;
import org.gamegineer.table.internal.net.impl.node.client.IRemoteServerNodeController;
import org.gamegineer.table.internal.net.impl.node.common.Authenticator;
import org.gamegineer.table.internal.net.impl.node.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.impl.transport.IMessage;
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

    /** The mocks control for use in the fixture. */
    private Optional<IMocksControl> mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code BeginAuthenticationRequestMessageHandlerTest} class.
     */
    public BeginAuthenticationRequestMessageHandlerTest()
    {
        mocksControl_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the message handler under test in the fixture.
     * 
     * @return The message handler under test in the fixture; never {@code null}
     *         .
     */
    private IMessageHandler getMessageHandler()
    {
        return BeginAuthenticationRequestMessageHandler.INSTANCE;
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
        final IMocksControl mocksControl = getMocksControl();
        final String playerName = "playerName"; //$NON-NLS-1$
        final SecureString password = new SecureString( "password".toCharArray() ); //$NON-NLS-1$
        final IClientNode localNode = mocksControl.createMock( IClientNode.class );
        EasyMock.expect( localNode.getPlayerName() ).andReturn( playerName );
        EasyMock.expect( localNode.getPassword() ).andReturn( new SecureString( password ) );
        final IRemoteServerNodeController remoteNodeController = mocksControl.createMock( IRemoteServerNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        final Capture<IMessage> messageCapture = new Capture<>();
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.<@NonNull IMessageHandler>notNull() );
        mocksControl.replay();

        final BeginAuthenticationRequestMessage message = new BeginAuthenticationRequestMessage();
        final byte[] challenge = new byte[] {
            1, 2, 3, 4
        };
        message.setChallenge( challenge );
        final byte[] salt = new byte[] {
            5, 6, 7, 8
        };
        message.setSalt( salt );
        getMessageHandler().handleMessage( remoteNodeController, message );

        mocksControl.verify();
        assertEquals( BeginAuthenticationResponseMessage.class, messageCapture.getValue().getClass() );
        assertEquals( message.getId(), messageCapture.getValue().getCorrelationId() );
        final BeginAuthenticationResponseMessage responseMessage = (BeginAuthenticationResponseMessage)messageCapture.getValue();
        assertEquals( playerName, responseMessage.getPlayerName() );
        final Authenticator authenticator = new Authenticator();
        assertArrayEquals( authenticator.createResponse( challenge, password, salt ), responseMessage.getResponse() );
    }
}
