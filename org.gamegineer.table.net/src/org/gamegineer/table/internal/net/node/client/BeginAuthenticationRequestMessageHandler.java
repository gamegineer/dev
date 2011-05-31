/*
 * BeginAuthenticationRequestMessageHandler.java
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
 * Created on Apr 22, 2011 at 4:29:55 PM.
 */

package org.gamegineer.table.internal.net.node.client;

import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.node.common.Authenticator;
import org.gamegineer.table.internal.net.node.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.node.common.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A message handler for the {@link BeginAuthenticationRequestMessage} message.
 */
@Immutable
final class BeginAuthenticationRequestMessageHandler
    extends RemoteServerNode.AbstractMessageHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * BeginAuthenticationRequestMessageHandler} class.
     * 
     * @param remoteNodeController
     *        The control interface for the remote node associated with the
     *        message handler; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code remoteNodeController} is {@code null}.
     */
    BeginAuthenticationRequestMessageHandler(
        /* @NonNull */
        final IRemoteServerNodeController remoteNodeController )
    {
        super( remoteNodeController );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code BeginAuthenticationRequestMessage} message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( "unused" )
    private void handleMessage(
        /* @NonNull */
        final BeginAuthenticationRequestMessage message )
    {
        assert message != null;

        final IRemoteServerNodeController controller = getRemoteNodeController();
        final IClientNode localNode = controller.getLocalNode();
        final BeginAuthenticationResponseMessage response = new BeginAuthenticationResponseMessage();
        response.setCorrelationId( message.getId() );
        response.setPlayerName( localNode.getPlayerName() );

        final SecureString password = localNode.getPassword();
        try
        {
            final Authenticator authenticator = new Authenticator();
            response.setResponse( authenticator.createResponse( message.getChallenge(), password, message.getSalt() ) );
            if( !controller.sendMessage( response, new EndAuthenticationMessageHandler( controller ) ) )
            {
                controller.close( TableNetworkError.TRANSPORT_ERROR );
            }
        }
        catch( final TableNetworkException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.BeginAuthenticationRequestMessageHandler_beginAuthenticationResponseFailed, e );
            controller.close( e.getError() );
        }
        finally
        {
            password.dispose();
        }
    }
}
