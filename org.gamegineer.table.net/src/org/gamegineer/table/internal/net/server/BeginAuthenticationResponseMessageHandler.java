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

package org.gamegineer.table.internal.net.server;

import java.util.Arrays;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.ITableNetworkNode;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.common.Authenticator;
import org.gamegineer.table.internal.net.common.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.common.messages.EndAuthenticationMessage;
import org.gamegineer.table.internal.net.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A message handler for the {@link BeginAuthenticationResponseMessage} message.
 */
@Immutable
final class BeginAuthenticationResponseMessageHandler
    extends RemoteClientTableProxy.AbstractMessageHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * BeginAuthenticationResponseMessageHandler} class.
     * 
     * @param remoteTableProxyController
     *        The control interface for the remote table proxy associated with
     *        the message handler; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code remoteTableProxyController} is {@code null}.
     */
    BeginAuthenticationResponseMessageHandler(
        /* @NonNull */
        final IRemoteClientTableProxyController remoteTableProxyController )
    {
        super( remoteTableProxyController );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Authenticates the specified remote player.
     * 
     * @param playerName
     *        The remote player name; must not be {@code null}.
     * @param response
     *        The challenge response submitted by the remote player; must not be
     *        {@code null}.
     * 
     * @throws org.gamegineer.table.net.TableNetworkException
     *         If the remote player cannot be authenticated.
     */
    private void authenticate(
        /* @NonNull */
        final String playerName,
        /* @NonNull */
        final byte[] response )
        throws TableNetworkException
    {
        assert playerName != null;
        assert response != null;

        final IRemoteClientTableProxyController controller = getRemoteTableProxyController();
        final ITableNetworkNode node = controller.getLocalNode();
        final SecureString password = node.getPassword();
        try
        {
            final Authenticator authenticator = new Authenticator();
            final byte[] expectedResponse = authenticator.createResponse( controller.getChallenge(), password, controller.getSalt() );
            if( Arrays.equals( expectedResponse, response ) )
            {
                System.out.println( "ServerService : client authenticated" ); //$NON-NLS-1$
            }
            else
            {
                throw new TableNetworkException( TableNetworkError.AUTHENTICATION_FAILED );
            }

            if( node.isTableProxyPresent( playerName ) )
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
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( "unused" )
    private void handleMessage(
        /* @NonNull */
        final BeginAuthenticationResponseMessage message )
    {
        assert message != null;

        System.out.println( String.format( "ServerService : received begin authentication response (id=%d, correlation-id=%d)", //$NON-NLS-1$
            Integer.valueOf( message.getId() ), //
            Integer.valueOf( message.getCorrelationId() ) ) );

        IMessage responseMessage;
        final IRemoteClientTableProxyController controller = getRemoteTableProxyController();
        try
        {
            authenticate( message.getPlayerName(), message.getResponse() );
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
        if( controller.sendMessage( responseMessage, null ) )
        {
            if( responseMessage instanceof ErrorMessage )
            {
                controller.close( ((ErrorMessage)responseMessage).getError() );
            }
            else
            {
                controller.setPlayerName( message.getPlayerName() );
                controller.getLocalNode().addTableProxy( controller.getProxy() );
            }
        }
        else
        {
            controller.close( TableNetworkError.TRANSPORT_ERROR );
            // TODO: all these close() calls smell bad; may just allow handleMessage() to throw
            // an exception that will force the remote table proxy to close.
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableProxy.AbstractMessageHandler#handleUnexpectedMessage()
     */
    @Override
    protected void handleUnexpectedMessage()
    {
        System.out.println( "ServerService : received unknown message in response to begin authentication request" ); //$NON-NLS-1$
        getRemoteTableProxyController().close( TableNetworkError.UNEXPECTED_MESSAGE );
    }
}
