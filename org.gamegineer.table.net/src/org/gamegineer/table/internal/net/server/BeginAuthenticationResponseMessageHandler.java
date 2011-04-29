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
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.common.Authenticator;
import org.gamegineer.table.internal.net.common.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.common.messages.EndAuthenticationMessage;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A message handler for the {@link BeginAuthenticationResponseMessage} message.
 */
@Immutable
final class BeginAuthenticationResponseMessageHandler
    extends RemoteClientTableGateway.AbstractMessageHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * BeginAuthenticationResponseMessageHandler} class.
     * 
     * @param remoteTableGateway
     *        The remote table gateway associated with the message handler; must
     *        not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code remoteTableGateway} is {@code null}.
     */
    BeginAuthenticationResponseMessageHandler(
        /* @NonNull */
        final IRemoteClientTableGateway remoteTableGateway )
    {
        super( remoteTableGateway );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code BeginAuthenticationResponseMessage} message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( {
        "boxing", "unused"
    } )
    private void handleMessage(
        /* @NonNull */
        final BeginAuthenticationResponseMessage message )
    {
        assert message != null;

        final IRemoteClientTableGateway remoteTableGateway = getRemoteTableGateway();
        final ITableGatewayContext context = remoteTableGateway.getContext();

        final EndAuthenticationMessage endAuthenticationMessage = new EndAuthenticationMessage();
        endAuthenticationMessage.setCorrelationId( message.getId() );

        final SecureString password = context.getPassword();
        try
        {
            final Authenticator authenticator = new Authenticator();
            final byte[] expectedResponse = authenticator.createResponse( remoteTableGateway.getChallenge(), password, remoteTableGateway.getSalt() );
            if( Arrays.equals( expectedResponse, message.getResponse() ) )
            {
                System.out.println( String.format( "ServerService : client authenticated (id=%d, correlation-id=%d)", message.getId(), message.getCorrelationId() ) ); //$NON-NLS-1$
                remoteTableGateway.setPlayerName( message.getPlayerName() );
                try
                {
                    context.addTableGateway( remoteTableGateway );
                }
                catch( final NetworkTableException e )
                {
                    System.out.println( "ServerService: a player with the same name is already registered" ); //$NON-NLS-1$
                    remoteTableGateway.setPlayerName( null );
                    throw e;
                }
            }
            else
            {
                System.out.println( String.format( "ServerService : client failed authentication (id=%d, correlation-id=%d)", message.getId(), message.getCorrelationId() ) ); //$NON-NLS-1$
                throw new NetworkTableException( "failed authentication" ); //$NON-NLS-1$
            }
        }
        catch( final NetworkTableException e )
        {
            endAuthenticationMessage.setException( e );
        }
        finally
        {
            password.dispose();
        }

        if( remoteTableGateway.sendMessage( endAuthenticationMessage, null ) )
        {
            if( endAuthenticationMessage.getException() != null )
            {
                remoteTableGateway.close();
            }
        }
        else
        {
            remoteTableGateway.close();
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway.AbstractMessageHandler#handleUnsupportedMessage()
     */
    @Override
    protected void handleUnsupportedMessage()
    {
        System.out.println( "ServerService : received unknown response to HelloResponseMessage" ); //$NON-NLS-1$
        getRemoteTableGateway().close();
    }

}
