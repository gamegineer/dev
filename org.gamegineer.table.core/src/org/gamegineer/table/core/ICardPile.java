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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import org.gamegineer.common.persistence.memento.IMemento;

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
     * <p>
     * This method does nothing if the specified card is already in the card
     * pile.
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
     * Gets the design of the card pile base.
     * 
     * @return The design of the card pile base; never {@code null}.
     */
    /* @NonNull */
    public ICardPileBaseDesign getBaseDesign();

    /**
     * Gets the bounds of this card pile in table coordinates.
     * 
     * @return The bounds of this card pile in table coordinates; never {@code
     *         null} .
     */
    /* @NonNull */
    public Rectangle getBounds();

    /**
     * Gets the collection of cards in this card pile.
     * 
     * @return The collection of cards in this card pile; never {@code null}.
     *         The cards are returned in order from the card at the bottom of
     *         the card pile to the card at the top of the card pile.
     */
    /* @NonNull */
    public List<ICard> getCards();

    /**
     * Gets the layout of cards within this card pile.
     * 
     * @return The layout of cards within this card pile; never {@code null}.
     */
    /* @NonNull */
    public CardPileLayout getLayout();

    /**
     * Gets the location of this card pile in table coordinates.
     * 
     * @return The location of this card pile in table coordinates; never
     *         {@code null}.
     */
    /* @NonNull */
    public Point getLocation();

    /**
     * Gets a memento that represents the state of this card pile.
     * 
     * @return A memento that represents the state of this card pile; never
     *         {@code null}.
     */
    /* @NonNull */
    public IMemento getMemento();

    /**
     * Gets the size of this card pile in table coordinates.
     * 
     * @return The size of this card pile in table coordinates; never {@code
     *         null}.
     */
    /* @NonNull */
    public Dimension getSize();

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

    /**
     * Sets the layout of cards within this card pile.
     * 
     * @param layout
     *        The layout of cards within this card pile; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code layout} is {@code null}.
     */
    public void setLayout(
        /* @NonNull */
        CardPileLayout layout );

    /**
     * Sets the location of this card pile in table coordinates.
     * 
     * @param location
     *        The location of this card pile in table coordinates; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code location} is {@code null}.
     */
    public void setLocation(
        /* @NonNull */
        Point location );
}
