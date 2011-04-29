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

import java.util.logging.Level;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.Loggers;
import org.gamegineer.table.internal.net.common.messages.EndAuthenticationMessage;
import org.gamegineer.table.net.NetworkTableException;

/**
 * A message handler for the {@link EndAuthenticationMessage} message.
 */
@Immutable
final class EndAuthenticationMessageHandler
    extends RemoteServerTableGateway.AbstractMessageHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EndAuthenticationMessageHandler}
     * class.
     * 
     * @param remoteTableGateway
     *        The remote table gateway associated with the message handler; must
     *        not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code remoteTableGateway} is {@code null}.
     */
    EndAuthenticationMessageHandler(
        /* @NonNull */
        final IRemoteServerTableGateway remoteTableGateway )
    {
        super( remoteTableGateway );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles an {@code EndAuthenticationMessage} message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( {
        "boxing", "unused"
    } )
    private void handleMessage(
        /* @NonNull */
        final EndAuthenticationMessage message )
    {
        assert message != null;

        final IRemoteServerTableGateway remoteTableGateway = getRemoteTableGateway();

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
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway.AbstractMessageHandler#handleUnsupportedMessage()
     */
    @Override
    protected void handleUnsupportedMessage()
    {
        System.out.println( "ClientService : received unknown response to BeginAuthenticationResponseMessage" ); //$NON-NLS-1$
        getRemoteTableGateway().close();
    }
}
