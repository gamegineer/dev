/*
 * GoodbyeMessageHandler.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jun 23, 2011 at 11:05:16 PM.
 */

package org.gamegineer.table.internal.net.node.server.handlers;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.Debug;
import org.gamegineer.table.internal.net.node.common.messages.GoodbyeMessage;
import org.gamegineer.table.internal.net.node.server.IRemoteClientNodeController;
import org.gamegineer.table.net.TableNetworkError;

/**
 * A message handler for the {@link GoodbyeMessage} message.
 */
@Immutable
public final class GoodbyeMessageHandler
    extends AbstractServerMessageHandler
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
     *        message; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( {
        "static-method", "unused"
    } )
    private void handleMessage(
        /* @NonNull */
        final IRemoteClientNodeController remoteNodeController,
        /* @NonNull */
        final GoodbyeMessage message )
    {
        assert remoteNodeController != null;
        assert message != null;

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, "Received goodbye message from client" ); //$NON-NLS-1$
        remoteNodeController.close( TableNetworkError.CLIENT_SHUTDOWN );
    }
}
