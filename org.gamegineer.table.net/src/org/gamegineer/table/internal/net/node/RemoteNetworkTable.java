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
import org.gamegineer.table.internal.net.node.common.messages.CardIncrementMessage;
import org.gamegineer.table.internal.net.node.common.messages.CardPileIncrementMessage;
import org.gamegineer.table.internal.net.node.common.messages.TableIncrementMessage;
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
     * @see org.gamegineer.table.internal.net.node.INetworkTable#dispose()
     */
    @Override
    public void dispose()
    {
        // do nothing
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#incrementCardPileState(int, org.gamegineer.table.internal.net.node.CardPileIncrement)
     */
    @Override
    public void incrementCardPileState(
        final int cardPileIndex,
        final CardPileIncrement cardPileIncrement )
    {
        assertArgumentLegal( cardPileIndex >= 0, "cardPileIndex" ); //$NON-NLS-1$
        assertArgumentNotNull( cardPileIncrement, "cardPileIncrement" ); //$NON-NLS-1$

        final CardPileIncrementMessage message = new CardPileIncrementMessage();
        message.setIncrement( cardPileIncrement );
        message.setIndex( cardPileIndex );
        remoteNodeController_.sendMessage( message, null );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#incrementCardState(int, int, org.gamegineer.table.internal.net.node.CardIncrement)
     */
    @Override
    public void incrementCardState(
        final int cardPileIndex,
        final int cardIndex,
        final CardIncrement cardIncrement )
    {
        assertArgumentLegal( cardPileIndex >= 0, "cardPileIndex" ); //$NON-NLS-1$
        assertArgumentLegal( cardIndex >= 0, "cardIndex" ); //$NON-NLS-1$
        assertArgumentNotNull( cardIncrement, "cardIncrement" ); //$NON-NLS-1$

        final CardIncrementMessage message = new CardIncrementMessage();
        message.setCardPileIndex( cardPileIndex );
        message.setIncrement( cardIncrement );
        message.setIndex( cardIndex );
        remoteNodeController_.sendMessage( message, null );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#incrementTableState(org.gamegineer.table.internal.net.node.TableIncrement)
     */
    @Override
    public void incrementTableState(
        final TableIncrement tableIncrement )
    {
        assertArgumentNotNull( tableIncrement, "tableIncrement" ); //$NON-NLS-1$

        final TableIncrementMessage message = new TableIncrementMessage();
        message.setIncrement( tableIncrement );
        remoteNodeController_.sendMessage( message, null );
    }

    /*
     * @see org.gamegineer.table.internal.net.node.INetworkTable#setTableState(java.lang.Object)
     */
    @Override
    public void setTableState(
        final Object tableMemento )
    {
        assertArgumentNotNull( tableMemento, "tableMemento" ); //$NON-NLS-1$

        final TableMessage message = new TableMessage();
        message.setMemento( tableMemento );
        remoteNodeController_.sendMessage( message, null );
    }
}
