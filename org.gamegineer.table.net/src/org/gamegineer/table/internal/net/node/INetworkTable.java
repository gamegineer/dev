/*
 * INetworkTable.java
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
 * Created on Jun 27, 2011 at 7:51:04 PM.
 */

package org.gamegineer.table.internal.net.node;

/**
 * A table connected to the table network.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface INetworkTable
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Increments the state of the specified card pile.
     * 
     * @param cardPileIndex
     *        The card pile index.
     * @param cardPileIncrement
     *        The incremental change to the state of the card pile; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardPileIndex} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code cardPileIncrement} is {@code null}.
     */
    public void incrementCardPileState(
        int cardPileIndex,
        /* @NonNull */
        CardPileIncrement cardPileIncrement );

    /**
     * Increments the state of the specified card.
     * 
     * @param cardPileIndex
     *        The card pile index.
     * @param cardIndex
     *        The card index.
     * @param cardIncrement
     *        The incremental change to the state of the card; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardPileIndex} or {@code cardIndex} is negative.
     * @throws java.lang.NullPointerException
     *         If {@code cardIncrement} is {@code null}.
     */
    public void incrementCardState(
        int cardPileIndex,
        int cardIndex,
        /* @NonNull */
        CardIncrement cardIncrement );

    /**
     * Increments the state of the table.
     * 
     * @param tableIncrement
     *        The incremental change to the state of the table; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableIncrement} is {@code null}.
     */
    public void incrementTableState(
        /* @NonNull */
        TableIncrement tableIncrement );

    /**
     * Sets the state of the table.
     * 
     * @param tableMemento
     *        The memento containing the table state; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableMemento} is {@code null}.
     */
    public void setTableState(
        /* @NonNull */
        Object tableMemento );
}
