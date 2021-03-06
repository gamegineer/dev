/*
 * GoodbyeMessageHandler.java
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
 * Created on Jun 18, 2011 at 11:30:17 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client.handlers;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.impl.Debug;
import org.gamegineer.table.internal.net.impl.node.client.IRemoteServerNodeController;
import org.gamegineer.table.internal.net.impl.node.common.messages.GoodbyeMessage;
import org.gamegineer.table.net.TableNetworkError;

/**
 * A message handler for the {@link GoodbyeMessage} message.
 */
@Immutable
public final class GoodbyeMessageHandler
    extends AbstractClientMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final GoodbyeMessageHandler INSTANCE = new GoodbyeMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GoodbyeMessageHandler} class.
     */
    private GoodbyeMessageHandler()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code GoodbyeMessage} message.
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
        final GoodbyeMessage message )
    {
        Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Received goodbye message from server" ); //$NON-NLS-1$
        remoteNodeController.getLocalNode().disconnect( TableNetworkError.SERVER_SHUTDOWN );
    }
}
