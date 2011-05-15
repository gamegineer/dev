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

package org.gamegineer.table.internal.net.client;

import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.ITableNetworkNode;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.common.Authenticator;
import org.gamegineer.table.internal.net.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.common.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A message handler for the {@link BeginAuthenticationRequestMessage} message.
 */
@Immutable
final class BeginAuthenticationRequestMessageHandler
    extends RemoteServerTableGateway.AbstractMessageHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * BeginAuthenticationRequestMessageHandler} class.
     * 
     * @param remoteTableGateway
     *        The remote table gateway associated with the message handler; must
     *        not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code remoteTableGateway} is {@code null}.
     */
    BeginAuthenticationRequestMessageHandler(
        /* @NonNull */
        final IRemoteServerTableGateway remoteTableGateway )
    {
        super( remoteTableGateway );
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

        final IRemoteServerTableGateway remoteTableGateway = getRemoteTableGateway();
        final ITableNetworkNode node = remoteTableGateway.getLocalNode();
        final BeginAuthenticationResponseMessage response = new BeginAuthenticationResponseMessage();
        response.setCorrelationId( message.getId() );
        response.setPlayerName( node.getLocalPlayerName() );

        final SecureString password = node.getPassword();
        try
        {
            final Authenticator authenticator = new Authenticator();
            response.setResponse( authenticator.createResponse( message.getChallenge(), password, message.getSalt() ) );
            if( !remoteTableGateway.sendMessage( response, new EndAuthenticationMessageHandler( remoteTableGateway ) ) )
            {
                remoteTableGateway.close( TableNetworkError.TRANSPORT_ERROR );
            }
        }
        catch( final TableNetworkException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.BeginAuthenticationRequestMessageHandler_beginAuthenticationResponseFailed, e );
            remoteTableGateway.close( e.getError() );
        }
        finally
        {
            password.dispose();
        }
    }
}
