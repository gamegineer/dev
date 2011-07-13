/*
 * ITableManager.java
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
 * Created on Jun 27, 2011 at 7:51:16 PM.
 */

package org.gamegineer.table.internal.net.node;

/**
 * Manages the local and remote tables connected to a node on the table network
 * to ensure they remain synchronized.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITableManager
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Increments the state of the specified card throughout the table network
     * 
     * @param sourceTable
     *        The table that originated the request; must not be {@code null}.
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
     *         If {@code sourceTable} or {@code cardIncrement} is {@code null}.
     */
    public void incrementCardState(
        /* @NonNull */
        INetworkTable sourceTable,
        int cardPileIndex,
        int cardIndex,
        /* @NonNull */
        CardIncrement cardIncrement );

    /**
     * Sets the state of the table throughout the table network.
     * 
     * @param sourceTable
     *        The table that originated the request; must not be {@code null}.
     * @param tableMemento
     *        The memento containing the table state; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code sourceTable} or {@code tableMemento} is {@code null}.
     */
    public void setTableState(
        /* @NonNull */
        INetworkTable sourceTable,
        /* @NonNull */
        Object tableMemento );
}
