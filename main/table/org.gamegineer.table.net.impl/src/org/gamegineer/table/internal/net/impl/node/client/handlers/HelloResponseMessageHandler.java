/*
 * HelloResponseMessageHandler.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl.node.client.handlers;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.impl.Debug;
import org.gamegineer.table.internal.net.impl.node.client.IRemoteServerNodeController;
import org.gamegineer.table.internal.net.impl.node.common.ProtocolVersions;
import org.gamegineer.table.internal.net.impl.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.HelloResponseMessage;
import org.gamegineer.table.net.TableNetworkError;

/**
 * A message handler for the {@link HelloResponseMessage} message.
 */
@Immutable
public final class HelloResponseMessageHandler
    extends AbstractClientMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final HelloResponseMessageHandler INSTANCE = new HelloResponseMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloResponseMessageHandler}
     * class.
     */
    private HelloResponseMessageHandler()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles an {@code ErrorMessage} message.
     * 
     * @param remoteNodeController
     *        The control interface for the remote node that received the
     *        message; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( {
        "static-method", "unused"
    } )
    private void handleMessage(
        final IRemoteServerNodeController remoteNodeController,
        final ErrorMessage message )
    {
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, //
            String.format( "Received error '%s' in response to hello request (id=%d, correlation-id=%d)", //$NON-NLS-1$
                message.getError(), //
                Integer.valueOf( message.getId() ), //
                Integer.valueOf( message.getCorrelationId() ) ) );
        remoteNodeController.close( message.getError() );
    }

    /**
     * Handles a {@code HelloResponseMessage} message.
     * 
     * @param remoteNodeController
     *        The control interface for the remote node that received the
     *        message; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( {
        "static-method", "unused"
    } )
    private void handleMessage(
        final IRemoteServerNodeController remoteNodeController,
        final HelloResponseMessage message )
    {
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, //
            String.format( "Received hello response with chosen version '%d' (id=%d, correlation-id=%d)", //$NON-NLS-1$
                Integer.valueOf( message.getChosenProtocolVersion() ), //
                Integer.valueOf( message.getId() ), //
                Integer.valueOf( message.getCorrelationId() ) ) );
        if( message.getChosenProtocolVersion() != ProtocolVersions.VERSION_1 )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Received unsupported chosen protocol version" ); //$NON-NLS-1$
            remoteNodeController.close( TableNetworkError.UNSUPPORTED_PROTOCOL_VERSION );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.impl.node.AbstractMessageHandler#handleUnexpectedMessage(org.gamegineer.table.internal.net.impl.node.IRemoteNodeController)
     */
    @Override
    protected void handleUnexpectedMessage(
        final IRemoteServerNodeController remoteNodeController )
    {
        remoteNodeController.close( TableNetworkError.UNEXPECTED_MESSAGE );
    }
}
