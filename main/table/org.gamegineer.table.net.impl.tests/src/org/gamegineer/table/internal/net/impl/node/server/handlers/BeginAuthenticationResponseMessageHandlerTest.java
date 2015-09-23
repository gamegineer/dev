/*
 * BeginAuthenticationResponseMessageHandlerTest.java
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
 * Created on Apr 29, 2011 at 10:47:38 PM.
 */

package org.gamegineer.table.internal.net.impl.node.server.handlers;

import static org.junit.Assert.assertEquals;
import java.util.Optional;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.impl.node.IMessageHandler;
import org.gamegineer.table.internal.net.impl.node.common.Authenticator;
import org.gamegineer.table.internal.net.impl.node.common.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.EndAuthenticationMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.impl.node.server.IRemoteClientNodeController;
import org.gamegineer.table.internal.net.impl.node.server.IServerNode;
import org.gamegineer.table.internal.net.impl.transport.FakeMessage;
import org.gamegineer.table.internal.net.impl.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link BeginAuthenticationResponseMessageHandler}
 * class.
 */
public final class BeginAuthenticationResponseMessageHandlerTest
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
     * {@code BeginAuthenticationResponseMessageHandlerTest} class.
     */
    public BeginAuthenticationResponseMessageHandlerTest()
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
        return BeginAuthenticationResponseMessageHandler.INSTANCE;
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
        final IMocksControl mocksControl = getMocksControl();
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
        final IServerNode localNode = mocksControl.createMock( IServerNode.class );
        EasyMock.expect( localNode.getPassword() ).andReturn( new SecureString( password ) );
        final IRemoteClientNodeController remoteNodeController = mocksControl.createMock( IRemoteClientNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        EasyMock.expect( remoteNodeController.getChallenge() ).andReturn( challenge ).anyTimes();
        EasyMock.expect( remoteNodeController.getSalt() ).andReturn( salt ).anyTimes();
        EasyMock.expect( localNode.isPlayerConnected( playerName ) ).andReturn( false );
        final Capture<IMessage> messageCapture = new Capture<>();
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.<@Nullable IMessageHandler>isNull() );
        remoteNodeController.bind( playerName );
        mocksControl.replay();

        final BeginAuthenticationResponseMessage message = new BeginAuthenticationResponseMessage();
        message.setPlayerName( playerName );
        message.setResponse( response );
        getMessageHandler().handleMessage( remoteNodeController, message );

        mocksControl.verify();
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
        final IMocksControl mocksControl = getMocksControl();
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
        final IServerNode localNode = mocksControl.createMock( IServerNode.class );
        EasyMock.expect( localNode.getPassword() ).andReturn( new SecureString( password ) );
        final IRemoteClientNodeController remoteNodeController = mocksControl.createMock( IRemoteClientNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        EasyMock.expect( remoteNodeController.getChallenge() ).andReturn( challenge ).anyTimes();
        EasyMock.expect( remoteNodeController.getSalt() ).andReturn( salt ).anyTimes();
        final Capture<IMessage> messageCapture = new Capture<>();
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.<@Nullable IMessageHandler>isNull() );
        remoteNodeController.close( TableNetworkError.AUTHENTICATION_FAILED );
        mocksControl.replay();

        final BeginAuthenticationResponseMessage message = new BeginAuthenticationResponseMessage();
        message.setPlayerName( playerName );
        message.setResponse( response );
        getMessageHandler().handleMessage( remoteNodeController, message );

        mocksControl.verify();
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
        final IMocksControl mocksControl = getMocksControl();
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
        final IServerNode localNode = mocksControl.createMock( IServerNode.class );
        EasyMock.expect( localNode.getPassword() ).andReturn( new SecureString( password ) );
        final IRemoteClientNodeController remoteNodeController = mocksControl.createMock( IRemoteClientNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        EasyMock.expect( remoteNodeController.getChallenge() ).andReturn( challenge ).anyTimes();
        EasyMock.expect( remoteNodeController.getSalt() ).andReturn( salt ).anyTimes();
        EasyMock.expect( localNode.isPlayerConnected( playerName ) ).andReturn( true );
        final Capture<IMessage> messageCapture = new Capture<>();
        remoteNodeController.sendMessage( EasyMock.capture( messageCapture ), EasyMock.<@Nullable IMessageHandler>isNull() );
        remoteNodeController.close( TableNetworkError.DUPLICATE_PLAYER_NAME );
        mocksControl.replay();

        final BeginAuthenticationResponseMessage message = new BeginAuthenticationResponseMessage();
        message.setPlayerName( playerName );
        message.setResponse( response );
        getMessageHandler().handleMessage( remoteNodeController, message );

        mocksControl.verify();
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
        final IMocksControl mocksControl = getMocksControl();
        final IRemoteClientNodeController remoteNodeController = mocksControl.createMock( IRemoteClientNodeController.class );
        remoteNodeController.sendMessage( EasyMock.<@NonNull IMessage>notNull(), EasyMock.<@Nullable IMessageHandler>isNull() );
        remoteNodeController.close( TableNetworkError.UNEXPECTED_MESSAGE );
        mocksControl.replay();

        final FakeMessage message = new FakeMessage();
        getMessageHandler().handleMessage( remoteNodeController, message );

        mocksControl.verify();
    }
}
