/*
 * RemoteNetworkTable.java
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
 * Created on Jul 2, 2011 at 9:05:43 PM.
 */

package org.gamegineer.table.internal.net.node;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.CardOrientation;
import org.gamegineer.table.internal.net.node.common.messages.CardOrientationMessage;
import org.gamegineer.table.internal.net.node.common.messages.TableMessage;

/**
 * Adapts a remote table to {@link INetworkTable}.
 */
@Immutable
final class RemoteNetworkTable
    implements INetworkTable
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The control interface for the remote node associated with the table. */
    private final IRemoteNodeController<?> remoteNodeController_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RemoteNetworkTable} class.
     * 
     * @param remoteNodeController
     *        The control interface for the remote node associated with the
     *        table; must not be {@code null}.
     */
    RemoteNetworkTable(
        /* @NonNull */
        final IRemoteNodeController<?> remoteNodeController )
    {
        assert remoteNodeController != null;

        remoteNodeController_ = remoteNodeController;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#setCardOrientation(int, int, org.gamegineer.table.core.CardOrientation)
     */
    @Override
    public void setCardOrientation(
        final int cardPileIndex,
        final int cardIndex,
        final CardOrientation cardOrientation )
    {
        assertArgumentLegal( cardPileIndex >= 0, "cardPileIndex" ); //$NON-NLS-1$
        assertArgumentLegal( cardIndex >= 0, "cardIndex" ); //$NON-NLS-1$
        assertArgumentNotNull( cardOrientation, "cardOrientation" ); //$NON-NLS-1$

        final CardOrientationMessage message = new CardOrientationMessage();
        message.setCardPileIndex( cardPileIndex );
        message.setCardIndex( cardIndex );
        message.setCardOrientation( cardOrientation );
        synchronized( remoteNodeController_.getLock() )
        {
            remoteNodeController_.sendMessage( message, null );
        }
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#setTableMemento(java.lang.Object)
     */
    @Override
    public void setTableMemento(
        final Object tableMemento )
    {
        assertArgumentNotNull( tableMemento, "tableMemento" ); //$NON-NLS-1$

        final TableMessage message = new TableMessage();
        message.setMemento( tableMemento );
        synchronized( remoteNodeController_.getLock() )
        {
            remoteNodeController_.sendMessage( message, null );
        }
    }
}
