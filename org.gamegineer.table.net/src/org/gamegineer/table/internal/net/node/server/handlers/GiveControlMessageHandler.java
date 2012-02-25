/*
 * GiveControlMessageHandler.java
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
 * Created on Sep 2, 2011 at 10:53:04 PM.
 */

package org.gamegineer.table.internal.net.node.server.handlers;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.Debug;
import org.gamegineer.table.internal.net.node.common.messages.GiveControlMessage;
import org.gamegineer.table.internal.net.node.server.IRemoteClientNodeController;

/**
 * A message handler for the {@link GiveControlMessage} message.
 */
@Immutable
public final class GiveControlMessageHandler
    extends AbstractServerMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final GiveControlMessageHandler INSTANCE = new GiveControlMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GiveControlMessageHandler}
     * class.
     */
    private GiveControlMessageHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code GiveControlMessage} message.
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
        final GiveControlMessage message )
    {
        assert remoteNodeController != null;
        assert message != null;

        Debug.getDefault().trace( Debug.OPTION_DEFAULT, String.format( "Received request to give control to player '%s'", message.getPlayerName() ) ); //$NON-NLS-1$
        remoteNodeController.getLocalNode().giveControl( message.getPlayerName() );
    }
}
