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

package org.gamegineer.table.internal.net.node.server;

import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.node.common.Authenticator;
import org.gamegineer.table.internal.net.node.common.ProtocolVersions;
import org.gamegineer.table.internal.net.node.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.node.common.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.node.common.messages.HelloResponseMessage;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A message handler for the {@link HelloRequestMessage} message.
 */
@Immutable
final class HelloRequestMessageHandler
    extends AbstractMessageHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloRequestMessageHandler}
     * class.
     * 
     * @param remoteNodeController
     *        The control interface for the remote node associated with the
     *        message handler; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code remoteNodeController} is {@code null}.
     */
    HelloRequestMessageHandler(
        /* @NonNull */
        final IRemoteClientNodeController remoteNodeController )
    {
        super( remoteNodeController );
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

        final IRemoteClientNodeController controller = getRemoteNodeController();

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
        if( !controller.sendMessage( responseMessage, null ) )
        {
            controller.close( TableNetworkError.TRANSPORT_ERROR );
            return;
        }

        if( responseMessage instanceof ErrorMessage )
        {
            System.out.println( "ServerService : server does not support client protocol version" ); //$NON-NLS-1$
            controller.close( ((ErrorMessage)responseMessage).getError() );
            return;
        }

        try
        {
            final Authenticator authenticator = new Authenticator();
            final BeginAuthenticationRequestMessage beginAuthenticationRequest = new BeginAuthenticationRequestMessage();
            final byte[] challenge = authenticator.createChallenge();
            controller.setChallenge( challenge );
            beginAuthenticationRequest.setChallenge( challenge );
            final byte[] salt = authenticator.createSalt();
            controller.setSalt( salt );
            beginAuthenticationRequest.setSalt( salt );

            if( !controller.sendMessage( beginAuthenticationRequest, new BeginAuthenticationResponseMessageHandler( controller ) ) )
            {
                controller.close( TableNetworkError.TRANSPORT_ERROR );
            }
        }
        catch( final TableNetworkException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, Messages.HelloRequestMessageHandler_beginAuthenticationRequestFailed, e );
            controller.close( e.getError() );
        }
    }
}
