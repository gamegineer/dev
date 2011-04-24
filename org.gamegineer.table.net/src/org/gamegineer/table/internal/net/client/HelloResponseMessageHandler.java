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

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.common.IRemoteTableGateway.IMessageHandler;
import org.gamegineer.table.internal.net.transport.messages.HelloResponseMessage;

/**
 * A message handler for the {@link HelloResponseMessage} message.
 */
@Immutable
final class HelloResponseMessageHandler
    implements IMessageHandler<IRemoteServerTableGateway, HelloResponseMessage>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloResponseMessageHandler}
     * class.
     */
    HelloResponseMessageHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.common.IMessageHandler#handleMessage(org.gamegineer.table.internal.net.common.IRemoteTableGateway, org.gamegineer.table.internal.net.transport.IMessage)
     */
    @Override
    @SuppressWarnings( "boxing" )
    public void handleMessage(
        final IRemoteServerTableGateway remoteTableGateway,
        final HelloResponseMessage message )
    {
        assertArgumentNotNull( remoteTableGateway, "remoteTableGateway" ); //$NON-NLS-1$
        assertArgumentNotNull( message, "message" ); //$NON-NLS-1$

        if( message.getException() != null )
        {
            System.out.println( String.format( "ClientService : received hello response (id=%d, correlation-id=%d) with exception: ", message.getId(), message.getCorrelationId() ) + message.getException() ); //$NON-NLS-1$
            remoteTableGateway.close();
        }
        else
        {
            System.out.println( String.format( "ClientService : received hello response (id=%d, correlation-id=%d) with chosen version '%d'", message.getId(), message.getCorrelationId(), message.getChosenProtocolVersion() ) ); //$NON-NLS-1$
        }
    }
}
