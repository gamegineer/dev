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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.common.Authenticator;
import org.gamegineer.table.internal.net.common.IRemoteTableGateway.IMessageHandler;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.transport.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A message handler for the {@link BeginAuthenticationRequestMessage} message.
 */
@Immutable
final class BeginAuthenticationRequestMessageHandler
    implements IMessageHandler<IRemoteServerTableGateway>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * BeginAuthenticationRequestMessageHandler} class.
     */
    BeginAuthenticationRequestMessageHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code BeginAuthenticationRequestMessage} message.
     * 
     * @param remoteTableGateway
     *        The remote table gateway that received the message; must not be
     *        {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    private void handleBeginAuthenticationRequestMessage(
        /* @NonNull */
        final IRemoteServerTableGateway remoteTableGateway,
        /* @NonNull */
        final BeginAuthenticationRequestMessage message )
    {
        assert remoteTableGateway != null;
        assert message != null;

        final ITableGatewayContext context = remoteTableGateway.getContext();
        final BeginAuthenticationResponseMessage response = new BeginAuthenticationResponseMessage();
        response.setCorrelationId( message.getId() );
        response.setPlayerName( context.getLocalPlayerName() );

        final SecureString password = context.getPassword();
        try
        {
            final Authenticator authenticator = new Authenticator();
            final byte[] authResponse = authenticator.createResponse( message.getChallenge(), password, message.getSalt() );
            response.setResponse( authResponse );

            if( !remoteTableGateway.sendMessage( response, new EndAuthenticationMessageHandler() ) )
            {
                remoteTableGateway.close();
            }
        }
        catch( final NetworkTableException e )
        {
            // TODO: in this case, and probably elsewhere, need to communicate the error
            // to the network table so it can eventually be reported to the local user via the UI
            // --> may not serialize exceptions in messages, but rather use an enum to force
            // specification of error.  actual exception should be logged locally.
            System.out.println( "ClientService : failed to generate authentication response with exception: " + e ); //$NON-NLS-1$
            remoteTableGateway.close();
        }
        finally
        {
            password.dispose();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.common.IMessageHandler#handleMessage(org.gamegineer.table.internal.net.common.IRemoteTableGateway, org.gamegineer.table.internal.net.transport.IMessage)
     */
    @Override
    public void handleMessage(
        final IRemoteServerTableGateway remoteTableGateway,
        final IMessage message )
    {
        assertArgumentNotNull( remoteTableGateway, "remoteTableGateway" ); //$NON-NLS-1$
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$
        assert message instanceof BeginAuthenticationRequestMessage;

        handleBeginAuthenticationRequestMessage( remoteTableGateway, (BeginAuthenticationRequestMessage)message );
    }
}
