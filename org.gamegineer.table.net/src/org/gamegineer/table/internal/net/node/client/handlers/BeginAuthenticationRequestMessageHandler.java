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

package org.gamegineer.table.internal.net.node.client.handlers;

import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.node.client.IClientNode;
import org.gamegineer.table.internal.net.node.client.IRemoteServerNodeController;
import org.gamegineer.table.internal.net.node.common.Authenticator;
import org.gamegineer.table.internal.net.node.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.node.common.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A message handler for the {@link BeginAuthenticationRequestMessage} message.
 */
@Immutable
public final class BeginAuthenticationRequestMessageHandler
    extends AbstractClientMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final BeginAuthenticationRequestMessageHandler INSTANCE = new BeginAuthenticationRequestMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * BeginAuthenticationRequestMessageHandler} class.
     */
    private BeginAuthenticationRequestMessageHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code BeginAuthenticationRequestMessage} message.
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
        final IRemoteServerNodeController remoteNodeController,
        /* @NonNull */
        final BeginAuthenticationRequestMessage message )
    {
        assert remoteNodeController != null;
        assert message != null;

        final IClientNode localNode = remoteNodeController.getLocalNode();
        final BeginAuthenticationResponseMessage response = new BeginAuthenticationResponseMessage();
        response.setCorrelationId( message.getId() );
        response.setPlayerName( localNode.getPlayerName() );

        final SecureString password = localNode.getPassword();
        try
        {
            final Authenticator authenticator = new Authenticator();
            response.setResponse( authenticator.createResponse( message.getChallenge(), password, message.getSalt() ) );
            remoteNodeController.sendMessage( response, EndAuthenticationMessageHandler.INSTANCE );
        }
        catch( final TableNetworkException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.BeginAuthenticationRequestMessageHandler_beginAuthenticationResponseFailed, e );
            remoteNodeController.close( e.getError() );
        }
        finally
        {
            password.dispose();
        }
    }
}
