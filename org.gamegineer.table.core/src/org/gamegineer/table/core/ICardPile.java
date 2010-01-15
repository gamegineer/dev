/*
 * ICardPile.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Jan 9, 2010 at 10:59:42 PM.
 */

package org.gamegineer.table.core;

/**
 * A card pile.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICardPile
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified card to the top of this card pile.
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
     * Adds the specified card pile listener to this card pile.
     * 
     * @param listener
     *        The card pile listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered card pile listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addCardPileListener(
        /* @NonNull */
        ICardPileListener listener );

    /**
     * Gets the card at the top of this card pile.
     * 
     * @return The card at the top of this card pile or {@code null} if this
     *         card pile is empty.
     */
    /* @Nullable */
    public ICard getCard();

    /**
     * Indicates this card pile is empty.
     * 
     * @return {@code true} if this card pile is empty; otherwise {@code false}.
     */
    public boolean isEmpty();

    /**
     * Removes the card at the top of this card pile.
     * 
     * @return The card that was removed or {@code null} if this card pile is
     *         empty.
     */
    /* @Nullable */
    public ICard removeCard();

    /**
     * Removes the specified card pile listener from this card pile.
     * 
     * @param listener
     *        The card pile listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered card pile listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeCardPileListener(
        /* @NonNull */
        ICardPileListener listener );
}
