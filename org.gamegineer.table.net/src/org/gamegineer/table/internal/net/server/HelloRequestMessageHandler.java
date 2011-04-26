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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.common.Authenticator;
import org.gamegineer.table.internal.net.common.ProtocolVersions;
import org.gamegineer.table.internal.net.common.IRemoteTableGateway.IMessageHandler;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.transport.messages.HelloRequestMessage;
import org.gamegineer.table.internal.net.transport.messages.HelloResponseMessage;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A message handler for the {@link HelloRequestMessage} message.
 */
@Immutable
final class HelloRequestMessageHandler
    implements IMessageHandler<IRemoteClientTableGateway>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloRequestMessageHandler}
     * class.
     */
    HelloRequestMessageHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code HelloRequestMessage} message.
     * 
     * @param remoteTableGateway
     *        The remote table gateway that received the message; must not be
     *        {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( "boxing" )
    private void handleHelloRequestMessage(
        /* @NonNull */
        final IRemoteClientTableGateway remoteTableGateway,
        /* @NonNull */
        final HelloRequestMessage message )
    {
        assert remoteTableGateway != null;
        assert message != null;

        System.out.println( String.format( "ServerService : received hello request (id=%d, correlation-id=%d) with supported version '%d'", message.getId(), message.getCorrelationId(), message.getSupportedProtocolVersion() ) ); //$NON-NLS-1$

        final HelloResponseMessage response = new HelloResponseMessage();
        response.setCorrelationId( message.getId() );
        if( message.getSupportedProtocolVersion() >= ProtocolVersions.VERSION_1 )
        {
            response.setChosenProtocolVersion( ProtocolVersions.VERSION_1 );
        }
        else
        {
            response.setException( new NetworkTableException( "unsupported protocol version" ) ); //$NON-NLS-1$
        }

        if( !remoteTableGateway.sendMessage( response, null ) )
        {
            remoteTableGateway.close();
            return;
        }

        if( response.getException() == null )
        {
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

                if( !remoteTableGateway.sendMessage( beginAuthenticationRequest, new BeginAuthenticationResponseMessageHandler() ) )
                {
                    remoteTableGateway.close();
                }
            }
            catch( final NetworkTableException e )
            {
                System.out.println( "ServerService : failed to generate begin authentication request with exception: " + e ); //$NON-NLS-1$
                remoteTableGateway.close();
            }
        }
        else
        {
            System.out.println( String.format( "ServerService : received hello request (id=%d, correlation-id=%d) but the requested version is unsupported", message.getId(), message.getCorrelationId() ) ); //$NON-NLS-1$
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
        assert message instanceof HelloRequestMessage;

        handleHelloRequestMessage( remoteTableGateway, (HelloRequestMessage)message );
    }
}
