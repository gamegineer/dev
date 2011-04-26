/*
 * EndAuthenticationMessageHandler.java
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
 * Created on Apr 22, 2011 at 4:34:46 PM.
 */

package org.gamegineer.table.internal.net.client;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.common.IRemoteTableGateway.IMessageHandler;
import org.gamegineer.table.internal.net.transport.IMessage;
import org.gamegineer.table.internal.net.transport.messages.EndAuthenticationMessage;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A message handler for the {@link EndAuthenticationMessage} message.
 */
@Immutable
final class EndAuthenticationMessageHandler
    implements IMessageHandler<IRemoteServerTableGateway>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EndAuthenticationMessageHandler}
     * class.
     */
    EndAuthenticationMessageHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles an {@code EndAuthenticationMessage} message.
     * 
     * @param remoteTableGateway
     *        The remote table gateway that received the message; must not be
     *        {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( "boxing" )
    private void handleEndAuthenticationMessage(
        /* @NonNull */
        final IRemoteServerTableGateway remoteTableGateway,
        /* @NonNull */
        final EndAuthenticationMessage message )
    {
        assert remoteTableGateway != null;
        assert message != null;

        if( message.getException() != null )
        {
            System.out.println( String.format( "ClientService : failed authentication (id=%d, correlation-id=%d) with exception: ", message.getId(), message.getCorrelationId() ) + message.getException() ); //$NON-NLS-1$
            remoteTableGateway.close();
        }
        else
        {
            System.out.println( String.format( "ClientService : completed authentication successfully (id=%d, correlation-id=%d): ", message.getId(), message.getCorrelationId() ) ); //$NON-NLS-1$
            remoteTableGateway.setPlayerName( "<<server>>" ); //$NON-NLS-1$
            try
            {
                remoteTableGateway.getContext().addTableGateway( remoteTableGateway );
            }
            catch( final NetworkTableException e )
            {
                Loggers.getDefaultLogger().log( Level.SEVERE, Messages.RemoteServerTableGateway_serverTableGatewayNotRegistered, e );
                remoteTableGateway.setPlayerName( null );
                remoteTableGateway.close();
            }
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

        if( message instanceof EndAuthenticationMessage )
        {
            handleEndAuthenticationMessage( remoteTableGateway, (EndAuthenticationMessage)message );
        }
        else
        {
            // TODO: send correlated error message
            System.out.println( "ClientService : received unknown response to BeginAuthenticationResponseMessage" ); //$NON-NLS-1$
            remoteTableGateway.close();
        }
    }
}
