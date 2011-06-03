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

package org.gamegineer.table.internal.net.node.client;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.Debug;
import org.gamegineer.table.internal.net.node.common.ProtocolVersions;
import org.gamegineer.table.internal.net.node.common.messages.ErrorMessage;
import org.gamegineer.table.internal.net.node.common.messages.HelloResponseMessage;
import org.gamegineer.table.net.TableNetworkError;

/**
 * A message handler for the {@link HelloResponseMessage} message.
 */
@Immutable
final class HelloResponseMessageHandler
    extends AbstractMessageHandler
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloResponseMessageHandler}
     * class.
     * 
     * @param remoteNodeController
     *        The control interface for the remote node associated with the
     *        message handler; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code remoteNodeController} is {@code null}.
     */
    HelloResponseMessageHandler(
        /* @NonNull */
        final IRemoteServerNodeController remoteNodeController )
    {
        super( remoteNodeController );
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

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, //
            String.format( "Received error '%s' in response to hello request (id=%d, correlation-id=%d)", //$NON-NLS-1$
                message.getError(), //
                Integer.valueOf( message.getId() ), //
                Integer.valueOf( message.getCorrelationId() ) ) );
        getRemoteNodeController().close( message.getError() );
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

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, //
            String.format( "Received hello response with chosen version '%d' (id=%d, correlation-id=%d)", //$NON-NLS-1$
                Integer.valueOf( message.getChosenProtocolVersion() ), //
                Integer.valueOf( message.getId() ), //
                Integer.valueOf( message.getCorrelationId() ) ) );
        if( message.getChosenProtocolVersion() != ProtocolVersions.VERSION_1 )
        {
            Debug.getDefault().trace( Debug.OPTION_DEFAULT, "received unsupported chosen protocol version" ); //$NON-NLS-1$
            getRemoteNodeController().close( TableNetworkError.UNSUPPORTED_PROTOCOL_VERSION );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.AbstractMessageHandler#handleUnexpectedMessage()
     */
    @Override
    protected void handleUnexpectedMessage()
    {
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Received unexpected message in response to hello request" ); //$NON-NLS-1$
        getRemoteNodeController().close( TableNetworkError.UNEXPECTED_MESSAGE );
    }
}