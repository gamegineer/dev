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

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.common.messages.EndAuthenticationMessage;
import org.gamegineer.table.internal.net.common.messages.ErrorMessage;
import org.gamegineer.table.net.TableNetworkError;

/**
 * A message handler for the {@link EndAuthenticationMessage} message.
 */
@Immutable
final class EndAuthenticationMessageHandler
    extends RemoteServerTableProxy.AbstractMessageHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EndAuthenticationMessageHandler}
     * class.
     * 
     * @param remoteTableProxyController
     *        The control interface for the remote table proxy associated with
     *        the message handler; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code remoteTableProxyController} is {@code null}.
     */
    EndAuthenticationMessageHandler(
        /* @NonNull */
        final IRemoteServerTableProxyController remoteTableProxyController )
    {
        super( remoteTableProxyController );
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
    @SuppressWarnings( "unused" )
    private void handleMessage(
        /* @NonNull */
        final EndAuthenticationMessage message )
    {
        assert message != null;

        System.out.println( String.format( "ClientService : received authentication confirmation (id=%d, correlation-id=%d)", //$NON-NLS-1$
            Integer.valueOf( message.getId() ), //
            Integer.valueOf( message.getCorrelationId() ) ) );
        final IRemoteServerTableProxyController controller = getRemoteTableProxyController();
        controller.setPlayerName( "<<server>>" ); //$NON-NLS-1$
        controller.getLocalNode().addTableProxy( controller.getProxy() );
    }

    /**
     * Handles an {@code ErrorMessage} message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( "unused" )
    private void handleMessage(
        /* @NonNull */
        final ErrorMessage message )
    {
        assert message != null;

        System.out.println( String.format( "ClientService : received error '%s' in response to begin authentication response (id=%d, correlation-id=%d)", //$NON-NLS-1$
            message.getError(), //
            Integer.valueOf( message.getId() ), //
            Integer.valueOf( message.getCorrelationId() ) ) );
        getRemoteTableProxyController().close( message.getError() );
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableProxy.AbstractMessageHandler#handleUnexpectedMessage()
     */
    @Override
    protected void handleUnexpectedMessage()
    {
        System.out.println( "ClientService : received unknown message in response to begin authentication response" ); //$NON-NLS-1$
        getRemoteTableProxyController().close( TableNetworkError.UNEXPECTED_MESSAGE );
    }
}
