/*
 * CancelControlRequestMessageHandler.java
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
 * Created on Sep 2, 2011 at 10:53:23 PM.
 */

package org.gamegineer.table.internal.net.impl.node.server.handlers;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.impl.node.common.messages.CancelControlRequestMessage;
import org.gamegineer.table.internal.net.impl.node.server.IRemoteClientNodeController;

/**
 * A message handler for the {@link CancelControlRequestMessage} message.
 */
@Immutable
public final class CancelControlRequestMessageHandler
    extends AbstractServerMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final CancelControlRequestMessageHandler INSTANCE = new CancelControlRequestMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CancelControlRequestMessageHandler} class.
     */
    private CancelControlRequestMessageHandler()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code CancelControlRequestMessage} message.
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
        final IRemoteClientNodeController remoteNodeController,
        final CancelControlRequestMessage message )
    {
        remoteNodeController.getLocalNode().cancelControlRequest();
    }
}
