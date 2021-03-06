/*
 * EndAuthenticationMessageHandler.java
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
 * Created on Apr 22, 2011 at 4:34:46 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client.handlers;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.impl.Debug;
import org.gamegineer.table.internal.net.impl.node.client.ClientNodeConstants;
import org.gamegineer.table.internal.net.impl.node.client.IRemoteServerNodeController;
import org.gamegineer.table.internal.net.impl.node.common.messages.EndAuthenticationMessage;
import org.gamegineer.table.internal.net.impl.node.common.messages.ErrorMessage;
import org.gamegineer.table.net.TableNetworkError;

/**
 * A message handler for the {@link EndAuthenticationMessage} message.
 */
@Immutable
public final class EndAuthenticationMessageHandler
    extends AbstractClientMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final EndAuthenticationMessageHandler INSTANCE = new EndAuthenticationMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EndAuthenticationMessageHandler}
     * class.
     */
    private EndAuthenticationMessageHandler()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles an {@code EndAuthenticationMessage} message.
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
        final IRemoteServerNodeController remoteNodeController,
        final EndAuthenticationMessage message )
    {
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, //
            String.format( "Received authentication confirmation (id=%d, correlation-id=%d)", //$NON-NLS-1$
                Integer.valueOf( message.getId() ), //
                Integer.valueOf( message.getCorrelationId() ) ) );
        remoteNodeController.bind( ClientNodeConstants.SERVER_PLAYER_NAME );
    }

    /**
     * Handles an {@code ErrorMessage} message.
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
        final IRemoteServerNodeController remoteNodeController,
        final ErrorMessage message )
    {
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, //
            String.format( "Received error '%s' in response to begin authentication response (id=%d, correlation-id=%d)", //$NON-NLS-1$
                message.getError(), //
                Integer.valueOf( message.getId() ), //
                Integer.valueOf( message.getCorrelationId() ) ) );
        remoteNodeController.close( message.getError() );
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
