/*
 * CardPileIncrementMessageHandler.java
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
 * Created on Jul 12, 2011 at 8:57:24 PM.
 */

package org.gamegineer.table.internal.net.node.common.handlers;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.node.IRemoteNodeController;
import org.gamegineer.table.internal.net.node.common.messages.CardPileIncrementMessage;

/**
 * A message handler for the {@link CardPileIncrementMessage} message.
 */
@Immutable
public final class CardPileIncrementMessageHandler
    extends AbstractCommonMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final CardPileIncrementMessageHandler INSTANCE = new CardPileIncrementMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileIncrementMessageHandler}
     * class.
     */
    private CardPileIncrementMessageHandler()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code CardPileIncrementMessage} message.
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
        final CardPileIncrementMessage message )
    {
        assert remoteNodeController != null;
        assert message != null;

        remoteNodeController.getLocalNode().getTableManager().incrementCardPileState( //
            remoteNodeController.getTable(), //
            message.getIndex(), //
            message.getIncrement() );
    }
}
