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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Arrays;
import net.jcip.annotations.Immutable;
import org.gamegineer.common.core.security.SecureString;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.common.Authenticator;
import org.gamegineer.table.internal.net.common.IRemoteTableGateway.IMessageHandler;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.messages.BeginAuthenticationResponseMessage;
import org.gamegineer.table.internal.net.transport.messages.EndAuthenticationMessage;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A message handler for the {@link BeginAuthenticationResponseMessage} message.
 */
@Immutable
final class BeginAuthenticationResponseMessageHandler
    implements IMessageHandler<IRemoteClientTableGateway>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * BeginAuthenticationResponseMessageHandler} class.
     */
    BeginAuthenticationResponseMessageHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code BeginAuthenticationResponseMessage} message.
     * 
     * @param remoteTableGateway
     *        The remote table gateway that received the message; must not be
     *        {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( "boxing" )
    private void handleBeginAuthenticationResponseMessage(
        /* @NonNull */
        final IRemoteClientTableGateway remoteTableGateway,
        /* @NonNull */
        final BeginAuthenticationResponseMessage message )
    {
        assert remoteTableGateway != null;
        assert message != null;

        final EndAuthenticationMessage endAuthenticationMessage = new EndAuthenticationMessage();
        endAuthenticationMessage.setCorrelationId( message.getId() );

        final ITableGatewayContext context = remoteTableGateway.getContext();
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
     * @see org.gamegineer.table.internal.net.common.IMessageHandler#handleMessage(org.gamegineer.table.internal.net.common.IRemoteTableGateway, org.gamegineer.table.internal.net.transport.IMessage)
     */
    @Override
    public void handleMessage(
        final IRemoteClientTableGateway remoteTableGateway,
        final IMessage message )
    {
        assertArgumentNotNull( remoteTableGateway, "remoteTableGateway" ); //$NON-NLS-1$
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$

        if( message instanceof BeginAuthenticationResponseMessage )
        {
            handleBeginAuthenticationResponseMessage( remoteTableGateway, (BeginAuthenticationResponseMessage)message );
        }
        else
        {
            // TODO: send correlated error message
            System.out.println( "ClientService : received unknown response to HelloResponseMessage" ); //$NON-NLS-1$
            remoteTableGateway.close();
        }
    }
}
