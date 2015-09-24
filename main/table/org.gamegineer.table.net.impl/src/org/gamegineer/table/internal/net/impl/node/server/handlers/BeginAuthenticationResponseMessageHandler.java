/*
 * BeginAuthenticationResponseMessageHandler.java
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
 * Created on Apr 23, 2011 at 4:06:56 PM.
 */

package org.gamegineer.table.internal.net.impl.node.server.handlers;

import java.util.Arrays;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.impl.Debug;
import org.gamegineer.table.internal.net.impl.Loggers;
import org.gamegineer.table.internal.net.impl.node.common.Authenticator;
import org.gamegineer.table.internal.net.impl.node.common.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.EndAuthenticationMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.impl.node.server.IRemoteClientNodeController;
import org.gamegineer.table.internal.net.impl.node.server.IServerNode;
import org.gamegineer.table.internal.net.impl.transport.IMessage;
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
     * Initializes a new instance of the
     * {@code BeginAuthenticationResponseMessageHandler} class.
     */
    private BeginAuthenticationResponseMessageHandler()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Authenticates the specified remote player.
     * 
     * @param remoteNodeController
     *        The control interface for the remote node that received the
     *        message.
     * @param playerName
     *        The remote player name.
     * @param response
     *        The challenge response submitted by the remote player.
     * 
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the remote player cannot be authenticated.
     */
    private static void authenticate(
        final IRemoteClientNodeController remoteNodeController,
        final String playerName,
        final byte[] response )
        throws TableNetworkException
    {
        final IServerNode localNode = remoteNodeController.getLocalNode();
        final SecureString password = localNode.getPassword();
        try
        {
            final byte[] challenge = remoteNodeController.getChallenge();
            assert challenge != null;
            final byte[] salt = remoteNodeController.getSalt();
            assert salt != null;
            final Authenticator authenticator = new Authenticator();
            final byte[] expectedResponse = authenticator.createResponse( challenge, password, salt );
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
     *        message.
     * @param message
     *        The message.
     */
    @SuppressWarnings( {
        "static-method", "unused"
    } )
    private void handleMessage(
        final IRemoteClientNodeController remoteNodeController,
        final BeginAuthenticationResponseMessage message )
    {
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
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.BeginAuthenticationResponseMessageHandler_authenticationFailed, e );
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
     * @see org.gamegineer.table.internal.net.impl.node.AbstractMessageHandler#handleUnexpectedMessage(org.gamegineer.table.internal.net.impl.node.IRemoteNodeController)
     */
    @Override
    protected void handleUnexpectedMessage(
        final IRemoteClientNodeController remoteNodeController )
    {
        remoteNodeController.close( TableNetworkError.UNEXPECTED_MESSAGE );
    }
}
