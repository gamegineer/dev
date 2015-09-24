/*
 * TableMessageHandler.java
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
 * Created on Jun 16, 2011 at 11:32:43 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.handlers;

import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.table.internal.net.impl.node.IRemoteNodeController;
import org.gamegineer.table.internal.net.impl.node.common.messages.TableMessage;

/**
 * A message handler for the {@link TableMessage} message.
 */
@Immutable
public final class TableMessageHandler
    extends AbstractCommonMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final TableMessageHandler INSTANCE = new TableMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableMessageHandler} class.
     */
    private TableMessageHandler()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code TableMessage} message.
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
        final TableMessage message )
    {
        remoteNodeController.getLocalNode().getTableManager().setTableState( //
            remoteNodeController.getTable(), //
            message.getMemento() );
    }
}
