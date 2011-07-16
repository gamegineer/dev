/*
 * BeginAuthenticationResponseMessageHandler.java
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
 * Created on Apr 23, 2011 at 4:06:56 PM.
 */

package org.gamegineer.table.internal.net.node.server.handlers;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Arrays;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.Debug;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.node.common.Authenticator;
import org.gamegineer.table.internal.net.node.common.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.node.common.messages.EndAuthenticationMessage;
import org.gamegineer.table.internal.net.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.node.server.IRemoteClientNodeController;
import org.gamegineer.table.internal.net.node.server.IServerNode;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A message handler for the {@link BeginAuthenticationResponseMessage} message.
 */
@Immutable
public final class BeginAuthenticationResponseMessageHandler
    extends AbstractServerMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final BeginAuthenticationResponseMessageHandler INSTANCE = new BeginAuthenticationResponseMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * BeginAuthenticationResponseMessageHandler} class.
     */
    private BeginAuthenticationResponseMessageHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Authenticates the specified remote player.
     * 
     * @param remoteNodeController
     *        The control interface for the remote node that received the
     *        message; must not be {@code null}.
     * @param playerName
     *        The remote player name; must not be {@code null}.
     * @param response
     *        The challenge response submitted by the remote player; must not be
     *        {@code null}.
     * 
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the remote player cannot be authenticated.
     */
    private static void authenticate(
        /* @NonNull */
        final IRemoteClientNodeController remoteNodeController,
        /* @NonNull */
        final String playerName,
        /* @NonNull */
        final byte[] response )
        throws TableNetworkException
    {
        assert remoteNodeController != null;
        assert playerName != null;
        assert response != null;

        final IServerNode localNode = remoteNodeController.getLocalNode();
        final SecureString password = localNode.getPassword();
        try
        {
            final Authenticator authenticator = new Authenticator();
            final byte[] expectedResponse = authenticator.createResponse( remoteNodeController.getChallenge(), password, remoteNodeController.getSalt() );
            if( Arrays.equals( expectedResponse, response ) )
            {
                Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Client authenticated" ); //$NON-NLS-1$
            }
            else
            {
                throw new TableNetworkException( TableNetworkError.AUTHENTICATION_FAILED );
            }

            if( localNode.isPlayerConnected( playerName ) )
            {
                throw new TableNetworkException( TableNetworkError.DUPLICATE_PLAYER_NAME );
            }
        }
        finally
        {
            password.dispose();
        }
    }

    /**
     * Handles a {@code BeginAuthenticationResponseMessage} message.
     * 
     * @param remoteNodeController
     *        The control interface for the remote node that received the
     *        message; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( "unused" )
    private void handleMessage(
        /* @NonNull */
        final IRemoteClientNodeController remoteNodeController,
        /* @NonNull */
        final BeginAuthenticationResponseMessage message )
    {
        assert remoteNodeController != null;
        assert message != null;

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, //
            String.format( "Received begin authentication response (id=%d, correlation-id=%d)", //$NON-NLS-1$
                Integer.valueOf( message.getId() ), //
                Integer.valueOf( message.getCorrelationId() ) ) );

        IMessage responseMessage;
        try
        {
            authenticate( remoteNodeController, message.getPlayerName(), message.getResponse() );
            final EndAuthenticationMessage endAuthenticationMessage = new EndAuthenticationMessage();
            responseMessage = endAuthenticationMessage;
        }
        catch( final TableNetworkException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.BeginAuthenticationResponseMessageHandler_authenticationFailed, e );
            final ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setError( e.getError() );
            responseMessage = errorMessage;
        }

        responseMessage.setCorrelationId( message.getId() );
        remoteNodeController.sendMessage( responseMessage, null );
        if( responseMessage instanceof ErrorMessage )
        {
            remoteNodeController.close( ((ErrorMessage)responseMessage).getError() );
        }
        else
        {
            remoteNodeController.bind( message.getPlayerName() );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractMessageHandler#handleUnexpectedMessage(org.gamegineer.table.internal.net.node.IRemoteNodeController)
     */
    @Override
    protected void handleUnexpectedMessage(
        final IRemoteClientNodeController remoteNodeController )
    {
        assertArgumentNotNull( remoteNodeController, "remoteNodeController" ); //$NON-NLS-1$

        remoteNodeController.close( TableNetworkError.UNEXPECTED_MESSAGE );
    }
}
