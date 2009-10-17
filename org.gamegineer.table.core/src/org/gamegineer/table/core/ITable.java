/*
 * ITable.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Oct 6, 2009 at 10:56:35 PM.
 */

package org.gamegineer.table.core;

import java.util.Collection;

/**
 * A virtual game table.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ITable
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified card to this table.
     * 
     * <p>
     * This method does nothing if the specified card is already on the table.
     * </p>
     * 
     * @param card
     *        The card; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code card} is {@code null}.
     */
    public void addCard(
        /* @NonNull */
        ICard card );

    /**
     * Adds the specified table listener to this table.
     * 
     * @param listener
     *        The table listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered table listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addTableListener(
        /* @NonNull */
        ITableListener listener );

    /**
     * Gets the collection of cards on this table.
     * 
     * @return The collection of cards on this table; never {@code null}.
     */
    /* @NonNull */
    public Collection<ICard> getCards();

    /**
     * Removes the specified card from this table.
     * 
     * <p>
     * This method does nothing if the specified card is not on the table.
     * </p>
     * 
     * @param card
     *        The card; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code card} is {@code null}.
     */
    public void removeCard(
        /* @NonNull */
        ICard card );

    /**
     * Removes the specified table listener from this table.
     * 
     * @param listener
     *        The table listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered table listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeTableListener(
        /* @NonNull */
        ITableListener listener );
}
