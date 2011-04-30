/*
 * HelloResponseMessageHandler.java
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
 * Created on Apr 23, 2011 at 4:02:18 PM.
 */

package org.gamegineer.table.internal.net.client;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.common.messages.HelloResponseMessage;

/**
 * A message handler for the {@link HelloResponseMessage} message.
 */
@Immutable
final class HelloResponseMessageHandler
    extends RemoteServerTableGateway.AbstractMessageHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloResponseMessageHandler}
     * class.
     * 
     * @param remoteTableGateway
     *        The remote table gateway associated with the message handler; must
     *        not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code remoteTableGateway} is {@code null}.
     */
    HelloResponseMessageHandler(
        /* @NonNull */
        final IRemoteServerTableGateway remoteTableGateway )
    {
        super( remoteTableGateway );
    }


    // ======================================================================
    // Methods
    // ======================================================================

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

        System.out.println( String.format( "ClientService : received error '%s' in response to hello request (id=%d, correlation-id=%d)", //$NON-NLS-1$
            message.getError(), //
            Integer.valueOf( message.getId() ), //
            Integer.valueOf( message.getCorrelationId() ) ) );
        getRemoteTableGateway().close();
    }

    /**
     * Handles a {@code HelloResponseMessage} message.
     * 
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( "unused" )
    private void handleMessage(
        /* @NonNull */
        final HelloResponseMessage message )
    {
        assert message != null;

        System.out.println( String.format( "ClientService : received hello response with chosen version '%d' (id=%d, correlation-id=%d)", //$NON-NLS-1$
            Integer.valueOf( message.getChosenProtocolVersion() ), //
            Integer.valueOf( message.getId() ), //
            Integer.valueOf( message.getCorrelationId() ) ) );
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway.AbstractMessageHandler#handleUnexpectedMessage()
     */
    @Override
    protected void handleUnexpectedMessage()
    {
        System.out.println( "ClientService : received unexpected message in response to hello request" ); //$NON-NLS-1$
        getRemoteTableGateway().close();
    }
}
