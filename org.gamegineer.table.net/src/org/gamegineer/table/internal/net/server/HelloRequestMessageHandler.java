/*
 * HelloRequestMessageHandler.java
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
 * Created on Apr 23, 2011 at 4:33:04 PM.
 */

package org.gamegineer.table.internal.net.server;

import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.common.Authenticator;
import org.gamegineer.table.internal.net.common.ProtocolVersions;
import org.gamegineer.table.internal.net.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.common.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.common.messages.HelloResponseMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A message handler for the {@link HelloRequestMessage} message.
 */
@Immutable
final class HelloRequestMessageHandler
    extends RemoteClientTableGateway.AbstractMessageHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloRequestMessageHandler}
     * class.
     * 
     * @param remoteTableGateway
     *        The remote table gateway associated with the message handler; must
     *        not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code remoteTableGateway} is {@code null}.
     */
    HelloRequestMessageHandler(
        /* @NonNull */
        final IRemoteClientTableGateway remoteTableGateway )
    {
        super( remoteTableGateway );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code HelloRequestMessage} message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( "unused" )
    private void handleMessage(
        /* @NonNull */
        final HelloRequestMessage message )
    {
        assert message != null;

        System.out.println( String.format( "ServerService : received hello request with supported version '%d' (id=%d, correlation-id=%d)", //$NON-NLS-1$
            Integer.valueOf( message.getSupportedProtocolVersion() ), //
            Integer.valueOf( message.getId() ), //
            Integer.valueOf( message.getCorrelationId() ) ) );

        final IRemoteClientTableGateway remoteTableGateway = getRemoteTableGateway();

        final IMessage responseMessage;
        if( message.getSupportedProtocolVersion() >= ProtocolVersions.VERSION_1 )
        {
            final HelloResponseMessage helloResponseMessage = new HelloResponseMessage();
            helloResponseMessage.setChosenProtocolVersion( ProtocolVersions.VERSION_1 );
            responseMessage = helloResponseMessage;
        }
        else
        {
            final ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setError( TableNetworkError.UNSUPPORTED_PROTOCOL_VERSION );
            responseMessage = errorMessage;
        }

        responseMessage.setCorrelationId( message.getId() );
        if( !remoteTableGateway.sendMessage( responseMessage, null ) )
        {
            remoteTableGateway.close( TableNetworkError.TRANSPORT_ERROR );
            return;
        }

        if( responseMessage instanceof ErrorMessage )
        {
            System.out.println( "ServerService : server does not support client protocol version" ); //$NON-NLS-1$
            remoteTableGateway.close( ((ErrorMessage)responseMessage).getError() );
            return;
        }

        try
        {
            final Authenticator authenticator = new Authenticator();
            final BeginAuthenticationRequestMessage beginAuthenticationRequest = new BeginAuthenticationRequestMessage();
            final byte[] challenge = authenticator.createChallenge();
            remoteTableGateway.setChallenge( challenge );
            beginAuthenticationRequest.setChallenge( challenge );
            final byte[] salt = authenticator.createSalt();
            remoteTableGateway.setSalt( salt );
            beginAuthenticationRequest.setSalt( salt );

            if( !remoteTableGateway.sendMessage( beginAuthenticationRequest, new BeginAuthenticationResponseMessageHandler( remoteTableGateway ) ) )
            {
                remoteTableGateway.close( TableNetworkError.TRANSPORT_ERROR );
            }
        }
        catch( final TableNetworkException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.HelloRequestMessageHandler_beginAuthenticationRequestFailed, e );
            remoteTableGateway.close( e.getError() );
        }
    }
}
