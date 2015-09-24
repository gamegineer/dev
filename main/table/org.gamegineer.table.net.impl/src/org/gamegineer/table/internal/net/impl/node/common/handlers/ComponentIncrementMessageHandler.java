/*
 * ComponentIncrementMessageHandler.java
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
 * Created on Jun 30, 2011 at 10:35:04 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.handlers;

import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.table.internal.net.impl.node.IRemoteNodeController;
import org.gamegineer.table.internal.net.impl.node.common.messages.ComponentIncrementMessage;

/**
 * A message handler for the {@link ComponentIncrementMessage} message.
 */
@Immutable
public final class ComponentIncrementMessageHandler
    extends AbstractCommonMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final ComponentIncrementMessageHandler INSTANCE = new ComponentIncrementMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code ComponentIncrementMessageHandler} class.
     */
    private ComponentIncrementMessageHandler()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code ComponentIncrementMessage} message.
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
        final IRemoteNodeController<@NonNull ?> remoteNodeController,
        final ComponentIncrementMessage message )
    {
        remoteNodeController.getLocalNode().getTableManager().incrementComponentState( //
            remoteNodeController.getTable(), //
            message.getPath(), //
            message.getIncrement() );
    }
}
