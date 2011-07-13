/*
 * CardIncrementMessageHandler.java
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
 * Created on Jun 30, 2011 at 10:35:04 PM.
 */

package org.gamegineer.table.internal.net.node.common.handlers;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.node.IRemoteNodeController;
import org.gamegineer.table.internal.net.node.common.messages.CardIncrementMessage;

/**
 * A message handler for the {@link CardIncrementMessage} message.
 */
@Immutable
public final class CardIncrementMessageHandler
    extends AbstractCommonMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final CardIncrementMessageHandler INSTANCE = new CardIncrementMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardIncrementMessageHandler}
     * class.
     */
    private CardIncrementMessageHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code CardIncrementMessage} message.
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
        final IRemoteNodeController<?> remoteNodeController,
        /* @NonNull */
        final CardIncrementMessage message )
    {
        assert remoteNodeController != null;
        assert message != null;

        remoteNodeController.getLocalNode().getTableManager().incrementCardState( //
            remoteNodeController.getTable(), //
            message.getCardPileIndex(), //
            message.getCardIndex(), //
            message.getIncrement() );
    }
}
