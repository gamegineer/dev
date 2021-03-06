/*
 * HelloRequestMessageHandler.java
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
 * Created on Apr 23, 2011 at 4:33:04 PM.
 */

package org.gamegineer.table.internal.net.impl.node.server.handlers;

import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.impl.Debug;
import org.gamegineer.table.internal.net.impl.Loggers;
import org.gamegineer.table.internal.net.impl.node.common.Authenticator;
import org.gamegineer.table.internal.net.impl.node.common.ProtocolVersions;
import org.gamegineer.table.internal.net.impl.node.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.HelloResponseMessage;
import org.gamegineer.table.internal.net.impl.node.server.IRemoteClientNodeController;
import org.gamegineer.table.internal.net.impl.transport.IMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.gamegineer.table.net.TableNetworkException;

/**
 * A message handler for the {@link HelloRequestMessage} message.
 */
@Immutable
public final class HelloRequestMessageHandler
    extends AbstractServerMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final HelloRequestMessageHandler INSTANCE = new HelloRequestMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloRequestMessageHandler}
     * class.
     */
    private HelloRequestMessageHandler()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code HelloRequestMessage} message.
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
        final HelloRequestMessage message )
    {
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, //
            String.format( "Received hello request with supported version '%d' (id=%d, correlation-id=%d)", //$NON-NLS-1$
                Integer.valueOf( message.getSupportedProtocolVersion() ), //
                Integer.valueOf( message.getId() ), //
                Integer.valueOf( message.getCorrelationId() ) ) );

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
        remoteNodeController.sendMessage( responseMessage, null );

        if( responseMessage instanceof ErrorMessage )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Server does not support client protocol version" ); //$NON-NLS-1$
            remoteNodeController.close( ((ErrorMessage)responseMessage).getError() );
            return;
        }

        try
        {
            final Authenticator authenticator = new Authenticator();
            final BeginAuthenticationRequestMessage beginAuthenticationRequest = new BeginAuthenticationRequestMessage();
            final byte[] challenge = authenticator.createChallenge();
            remoteNodeController.setChallenge( challenge );
            beginAuthenticationRequest.setChallenge( challenge );
            final byte[] salt = authenticator.createSalt();
            remoteNodeController.setSalt( salt );
            beginAuthenticationRequest.setSalt( salt );
            remoteNodeController.sendMessage( beginAuthenticationRequest, BeginAuthenticationResponseMessageHandler.INSTANCE );
        }
        catch( final TableNetworkException e )
        {
            Loggers.getDefaultLogger().log( Level.SEVERE, NonNlsMessages.HelloRequestMessageHandler_beginAuthenticationRequestFailed, e );
            remoteNodeController.close( e.getError() );
        }
    }
}
