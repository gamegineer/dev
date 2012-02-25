/*
 * TableIncrementMessageHandler.java
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
 * Created on Jul 12, 2011 at 8:57:33 PM.
 */

package org.gamegineer.table.internal.net.node.common.handlers;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.node.IRemoteNodeController;
import org.gamegineer.table.internal.net.node.common.messages.TableIncrementMessage;

/**
 * A message handler for the {@link TableIncrementMessage} message.
 */
@Immutable
public final class TableIncrementMessageHandler
    extends AbstractCommonMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final TableIncrementMessageHandler INSTANCE = new TableIncrementMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableIncrementMessageHandler}
     * class.
     */
    private TableIncrementMessageHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code TableIncrementMessage} message.
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
        final IRemoteNodeController<?> remoteNodeController,
        /* @NonNull */
        final TableIncrementMessage message )
    {
        assert remoteNodeController != null;
        assert message != null;

        remoteNodeController.getLocalNode().getTableManager().incrementTableState( //
            remoteNodeController.getTable(), //
            message.getIncrement() );
    }
}
