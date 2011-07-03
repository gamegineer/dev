/*
 * CardOrientationMessageHandler.java
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

package org.gamegineer.table.internal.net.node.client;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.node.common.messages.CardOrientationMessage;

/**
 * A message handler for the {@link CardOrientationMessage} message.
 */
@Immutable
final class CardOrientationMessageHandler
    extends AbstractMessageHandler
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    static final CardOrientationMessageHandler INSTANCE = new CardOrientationMessageHandler();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardOrientationMessageHandler}
     * class.
     */
    private CardOrientationMessageHandler()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Handles a {@code CardOrientationMessage} message.
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
        final CardOrientationMessage message )
    {
        assert remoteNodeController != null;
        assert message != null;

        remoteNodeController.getLocalNode().getTableManager().setCardOrientation( //
            remoteNodeController.getTable(), //
            message.getCardPileIndex(), //
            message.getCardIndex(), //
            message.getCardOrientation() );
    }
}
