/*
 * RemoteServerTableGateway.java
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
 * Created on Apr 10, 2011 at 5:34:39 PM.
 */

package org.gamegineer.table.internal.net.client;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.net.ITableGatewayContext;
import org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway;
import org.gamegineer.table.internal.net.common.ProtocolVersions;
import org.gamegineer.table.internal.net.common.messages.BeginAuthenticationRequestMessage;
import org.gamegineer.table.internal.net.common.messages.HelloRequestMessage;
import org.gamegineer.table.net.TableNetworkError;

/**
 * A gateway to a remote server table.
 * 
 * <p>
 * This gateway provides a network service that represents the client half of
 * the table network protocol.
 * </p>
 */
@ThreadSafe
final class RemoteServerTableGateway
    extends AbstractRemoteTableGateway
    implements IRemoteServerTableGateway
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RemoteServerTableGateway} class.
     * 
     * @param context
     *        The table gateway context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code context} is {@code null}.
     */
    RemoteServerTableGateway(
        /* @NonNull */
        final ITableGatewayContext context )
    {
        super( context );

        registerUncorrelatedMessageHandler( BeginAuthenticationRequestMessage.class, new BeginAuthenticationRequestMessageHandler( this ) );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway#closed(org.gamegineer.table.net.TableNetworkError)
     */
    @Override
    protected void closed(
        final TableNetworkError error )
    {
        assert Thread.holdsLock( getLock() );

        super.closed( error );

        getContext().disconnectTableNetwork( error );
    }

    /*
     * @see org.gamegineer.table.internal.net.common.AbstractRemoteTableGateway#opened()
     */
    @Override
    protected void opened()
    {
        assert Thread.holdsLock( getLock() );

        super.opened();

        final HelloRequestMessage message = new HelloRequestMessage();
        message.setSupportedProtocolVersion( ProtocolVersions.VERSION_1 );
        if( !sendMessage( message, new HelloResponseMessageHandler( this ) ) )
        {
            close( TableNetworkError.TRANSPORT_ERROR );
        }
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Superclass for all message handlers associated with a remote server table
     * gateway.
     */
    @Immutable
    static abstract class AbstractMessageHandler
        extends AbstractRemoteTableGateway.AbstractMessageHandler<IRemoteServerTableGateway>
    {
        // ==================================================================
        // Constructors
        // ==================================================================

        /**
         * Initializes a new instance of the {@code AbstractMessageHandler}
         * class.
         * 
         * @param remoteTableGateway
         *        The remote table gateway associated with the message handler;
         *        must not be {@code null}.
         * 
         * @throws java.lang.NullPointerException
         *         If {@code remoteTableGateway} is {@code null}.
         */
        AbstractMessageHandler(
            /* @NonNull */
            final IRemoteServerTableGateway remoteTableGateway )
        {
            super( remoteTableGateway );
        }
    }
}
