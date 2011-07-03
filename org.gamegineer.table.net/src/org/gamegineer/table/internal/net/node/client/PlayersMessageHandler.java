/*
 * PlayersMessageHandler.java
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
 * Created on May 20, 2011 at 9:36:06 PM.
 */

package org.gamegineer.table.internal.net.node.client;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.node.common.messages.PlayersMessage;

/**
 * A message handler for the {@link PlayersMessage} message.
 */
@Immutable
final class PlayersMessageHandler
    extends AbstractClientMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    static final PlayersMessageHandler INSTANCE = new PlayersMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayersMessageHandler} class.
     */
    private PlayersMessageHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code PlayersMessage} message.
     * 
     * @param remoteNodeController
     *        The control interface for the remote node that received the
     *        message; must not be {@code null}.
     * @param message
     *        The message; must not be {@code null}.
     */
    @SuppressWarnings( "unused" )
    private void handleMessage(
        /* @NonNull */
        final IRemoteServerNodeController remoteNodeController,
        /* @NonNull */
        final PlayersMessage message )
    {
        assert remoteNodeController != null;
        assert message != null;

        remoteNodeController.getLocalNode().setPlayers( message.getPlayers() );
    }
}
