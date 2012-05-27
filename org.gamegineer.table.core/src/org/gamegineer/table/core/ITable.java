/*
 * ITable.java
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
 * Created on Oct 6, 2009 at 10:56:35 PM.
 */

package org.gamegineer.table.core;

import java.awt.Point;
import java.util.List;
import org.gamegineer.common.core.util.memento.IMementoOriginator;

/**
 * A virtual game table.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ITable
    extends IMementoOriginator
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified card pile to this table.
     * 
     * @param cardPile
     *        The card pile; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardPile} is already contained in the table or if
     *         {@code cardPile} was created by a different table.
     * @throws java.lang.NullPointerException
     *         If {@code cardPile} is {@code null}.
     */
    public void addCardPile(
        /* @NonNull */
        ICardPile cardPile );

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
     * Creates a new card.
     * 
     * @return A new card; never {@code null}. The new card is not contained in
     *         any card pile.
     */
    // TODO: remove this method
    /* @NonNull */
    public ICard createCard();

    /**
     * Creates a new card pile.
     * 
     * @return A new card pile; never {@code null}. The new card pile is not
     *         contained in the table.
     */
    // TODO: remove this method
    /* @NonNull */
    public ICardPile createCardPile();

    /**
     * Gets the card pile in this table at the specified index.
     * 
     * @param index
     *        The card pile index.
     * 
     * @return The card pile in this table at the specified index; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code index} is less than zero or greater than or equal to
     *         the card pile count.
     */
    /* @NonNull */
    public ICardPile getCardPile(
        int index );

    /**
     * Gets the card pile at the specified location.
     * 
     * <p>
     * If two or more card piles occupy the specified location, the card pile
     * most recently added to the table will be returned.
     * </p>
     * 
     * <p>
     * Note that the returned card pile may have been moved by the time this
     * method returns to the caller. Therefore, callers should not cache the
     * results of this method for an extended period of time.
     * </p>
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The card pile at the specified location or {@code null} if no
     *         card pile is at that location.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code location} is {@code null}.
     */
    /* @Nullable */
    public ICardPile getCardPile(
        /* @NonNull */
        Point location );

    /**
     * Gets the count of card piles contained in this table.
     * 
     * @return The count of card piles contained in this table.
     */
    public int getCardPileCount();

    /**
     * Gets the index of the specified card pile in this table.
     * 
     * @param cardPile
     *        The card pile; must not be {@code null}.
     * 
     * @return The index of the specified card pile in this table.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardPile} is not contained in this table.
     * @throws java.lang.NullPointerException
     *         If {@code cardPile} is {@code null}.
     */
    public int getCardPileIndex(
        /* @NonNull */
        ICardPile cardPile );

    /**
     * Gets the collection of card piles on this table.
     * 
     * @return The collection of card piles on this table; never {@code null}.
     *         The card piles are returned in the order they were added to the
     *         table from oldest to newest.
     */
    /* @NonNull */
    public List<ICardPile> getCardPiles();

    /**
     * Gets the table environment associated with this table.
     * 
     * @return The table environment associated with this table; never
     *         {@code null}.
     */
    /* @NonNull */
    public ITableEnvironment getTableEnvironment();

    /**
     * Removes the specified card pile from this table.
     * 
     * @param cardPile
     *        The card pile; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cardPile} is not contained in this table.
     * @throws java.lang.NullPointerException
     *         If {@code cardPile} is {@code null}.
     */
    public void removeCardPile(
        /* @NonNull */
        ICardPile cardPile );

    /**
     * Removes all card piles from this table.
     * 
     * @return The collection of card piles removed from this table; never
     *         {@code null}. The card piles are returned in the order they were
     *         added to the table from oldest to newest.
     */
    public List<ICardPile> removeCardPiles();

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
