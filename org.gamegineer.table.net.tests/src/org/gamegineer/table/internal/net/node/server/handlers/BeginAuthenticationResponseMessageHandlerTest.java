/*
 * BeginAuthenticationResponseMessageHandlerTest.java
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
 * Created on Apr 29, 2011 at 10:47:38 PM.
 */

package org.gamegineer.table.internal.net.node.server.handlers;

import static org.junit.Assert.assertEquals;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.node.IMessageHandler;
import org.gamegineer.table.internal.net.node.common.Authenticator;
import org.gamegineer.table.internal.net.node.common.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.node.common.messages.EndAuthenticationMessage;
import org.gamegineer.table.internal.net.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.node.server.IRemoteClientNodeController;
import org.gamegineer.table.internal.net.node.server.IServerNode;
import org.gamegineer.table.internal.net.transport.FakeMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.server.handlers.BeginAuthenticationResponseMessageHandler}
 * class.
 */
public final class BeginAuthenticationResponseMessageHandlerTest
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
     * {@code BeginAuthenticationResponseMessageHandlerTest} class.
     */
    public BeginAuthenticationResponseMessageHandlerTest()
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
        messageHandler_ = BeginAuthenticationResponseMessageHandler.INSTANCE;
    }

    /**
     * Ensures the
     * {@link BeginAuthenticationResponseMessageHandler#handleMessage} method
     * correctly handles a begin authentication response message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHandleMessage_BeginAuthenticationResponseMessage()
        throws Exception
    {
        final String playerName = "playerName"; //$NON-NLS-1$
        final SecureString password = new SecureString( "password".toCharArray() ); //$NON-NLS-1$
        final byte[] challenge = new byte[] {
            1, 2, 3, 4
        };
        final byte[] salt = new byte[] {
            5, 6, 7, 8
        };
        final Authenticator authenticator = new Authenticator();
        final byte[] response = authenticator.createResponse( challenge, password, salt );
        final IServerNode localNode = mocksControl_.createMock( IServerNode.class );
        EasyMock.expect( localNode.getPassword() ).andReturn( new SecureString( password ) );
        final IRemoteClientNodeController remoteNodeController = mocksControl_.createMock( IRemoteClientNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        EasyMock.expect( remoteNodeController.getChallenge() ).andReturn( challenge ).anyTimes();
        EasyMock.expect( remoteNodeController.getSalt() ).andReturn( salt ).anyTimes();
        EasyMock.expect( localNode.isPlayerConnected( playerName ) ).andReturn( false );
        final Capture<IMessage> messageCapture = new Capture<>();
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.isNull( IMessageHandler.class ) );
        remoteNodeController.bind( playerName );
        mocksControl_.replay();

        final BeginAuthenticationResponseMessage message = new BeginAuthenticationResponseMessage();
        message.setPlayerName( playerName );
        message.setResponse( response );
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
        assertEquals( EndAuthenticationMessage.class, messageCapture.getValue().getClass() );
        assertEquals( message.getId(), messageCapture.getValue().getCorrelationId() );
    }

    /**
     * Ensures the
     * {@link BeginAuthenticationResponseMessageHandler#handleMessage} method
     * correctly handles a begin authentication response message in the case
     * where authentication fails.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHandleMessage_BeginAuthenticationResponseMessage_AuthenticationFailed()
        throws Exception
    {
        final String playerName = "playerName"; //$NON-NLS-1$
        final SecureString password = new SecureString( "password".toCharArray() ); //$NON-NLS-1$
        final byte[] challenge = new byte[] {
            1, 2, 3, 4
        };
        final byte[] salt = new byte[] {
            5, 6, 7, 8
        };
        final byte[] response = new byte[] {
            9, 10, 11, 12
        };
        final IServerNode localNode = mocksControl_.createMock( IServerNode.class );
        EasyMock.expect( localNode.getPassword() ).andReturn( new SecureString( password ) );
        final IRemoteClientNodeController remoteNodeController = mocksControl_.createMock( IRemoteClientNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        EasyMock.expect( remoteNodeController.getChallenge() ).andReturn( challenge ).anyTimes();
        EasyMock.expect( remoteNodeController.getSalt() ).andReturn( salt ).anyTimes();
        final Capture<IMessage> messageCapture = new Capture<>();
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.isNull( IMessageHandler.class ) );
        remoteNodeController.close( TableNetworkError.AUTHENTICATION_FAILED );
        mocksControl_.replay();

        final BeginAuthenticationResponseMessage message = new BeginAuthenticationResponseMessage();
        message.setPlayerName( playerName );
        message.setResponse( response );
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( message.getId(), messageCapture.getValue().getCorrelationId() );
        assertEquals( TableNetworkError.AUTHENTICATION_FAILED, ((ErrorMessage)messageCapture.getValue()).getError() );
    }

    /**
     * Ensures the
     * {@link BeginAuthenticationResponseMessageHandler#handleMessage} method
     * correctly handles a begin authentication response message in the case
     * where the player name is already registered.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testHandleMessage_BeginAuthenticationResponseMessage_DuplicatePlayerName()
        throws Exception
    {
        final String playerName = "playerName"; //$NON-NLS-1$
        final SecureString password = new SecureString( "password".toCharArray() ); //$NON-NLS-1$
        final byte[] challenge = new byte[] {
            1, 2, 3, 4
        };
        final byte[] salt = new byte[] {
            5, 6, 7, 8
        };
        final Authenticator authenticator = new Authenticator();
        final byte[] response = authenticator.createResponse( challenge, password, salt );
        final IServerNode localNode = mocksControl_.createMock( IServerNode.class );
        EasyMock.expect( localNode.getPassword() ).andReturn( new SecureString( password ) );
        final IRemoteClientNodeController remoteNodeController = mocksControl_.createMock( IRemoteClientNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        EasyMock.expect( remoteNodeController.getChallenge() ).andReturn( challenge ).anyTimes();
        EasyMock.expect( remoteNodeController.getSalt() ).andReturn( salt ).anyTimes();
        EasyMock.expect( localNode.isPlayerConnected( playerName ) ).andReturn( true );
        final Capture<IMessage> messageCapture = new Capture<>();
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.isNull( IMessageHandler.class ) );
        remoteNodeController.close( TableNetworkError.DUPLICATE_PLAYER_NAME );
        mocksControl_.replay();

        final BeginAuthenticationResponseMessage message = new BeginAuthenticationResponseMessage();
        message.setPlayerName( playerName );
        message.setResponse( response );
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
        assertEquals( ErrorMessage.class, messageCapture.getValue().getClass() );
        assertEquals( message.getId(), messageCapture.getValue().getCorrelationId() );
        assertEquals( TableNetworkError.DUPLICATE_PLAYER_NAME, ((ErrorMessage)messageCapture.getValue()).getError() );
    }

    /**
     * Ensures the
     * {@link BeginAuthenticationResponseMessageHandler#handleMessage} method
     * correctly handles an unexpected message.
     */
    @Test
    public void testHandleMessage_UnexpectedMessage()
    {
        final IRemoteClientNodeController remoteNodeController = mocksControl_.createMock( IRemoteClientNodeController.class );
        remoteNodeController.sendMessage( EasyMock.notNull( IMessage.class ), EasyMock.isNull( IMessageHandler.class ) );
        remoteNodeController.close( TableNetworkError.UNEXPECTED_MESSAGE );
        mocksControl_.replay();

        final FakeMessage message = new FakeMessage();
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
    }
}
