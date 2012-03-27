/*
 * ICardPile.java
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
 * Created on Jan 9, 2010 at 10:59:42 PM.
 */

package org.gamegineer.table.core;

import java.awt.Point;
import java.util.List;

/**
 * A card pile.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICardPile
    extends IContainer
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
     * @throws java.lang.IllegalArgumentException
     *         If {@code card} is already contained in a card pile or if
     *         {@code card} was created by a table different from the table that
     *         created this card pile.
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
     * Adds the specified collection of cards to the top of this card pile.
     * 
     * @param cards
     *        The collection of cards to be added to this card pile; must not be
     *        {@code null}. The cards are added to the top of this card pile in
     *        the order they appear in the collection.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code cards} contains a {@code null} element; if any card is
     *         already contained in a card pile; or if any card was created by a
     *         table different from the table that created this card pile.
     * @throws java.lang.NullPointerException
     *         If {@code cards} is {@code null}.
     */
    public void addCards(
        /* @NonNull */
        List<ICard> cards );

    /**
     * Gets the base design of this card pile.
     * 
     * @return The base design of this card pile; never {@code null}.
     */
    /* @NonNull */
    public ICardPileBaseDesign getBaseDesign();

    /**
     * Gets the base location of this card pile in table coordinates.
     * 
     * @return The base location of this card pile in table coordinates; never
     *         {@code null}.
     */
    /* @NonNull */
    public Point getBaseLocation();

    /**
     * Gets the card in this card at the specified index.
     * 
     * @param index
     *        The card index.
     * 
     * @return The card in this card pile at the specified index; never
     *         {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code index} is less than zero or greater than or equal to
     *         the card count.
     */
    /* @NonNull */
    public ICard getCard(
        int index );

    /**
     * Gets the card in this card pile at the specified location.
     * 
     * <p>
     * If two or more cards occupy the specified location, the top-most card
     * will be returned.
     * </p>
     * 
     * <p>
     * In the stacked layout, only the top-most card will ever be returned and
     * only if the specified location lies within the bounds of the top-most
     * card. In any of the accordian layouts, any card may potentially be
     * returned.
     * </p>
     * 
     * <p>
     * Note that the returned card may have been moved by the time this method
     * returns to the caller. Therefore, callers should not cache the results of
     * this method for an extended period of time.
     * </p>
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The card in this card pile at the specified location or
     *         {@code null} if no card in this card pile is at that location.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code location} is {@code null}.
     */
    /* @Nullable */
    public ICard getCard(
        /* @NonNull */
        Point location );

    /**
     * Gets the count of cards contained in this card pile.
     * 
     * @return The count of cards contained in this card pile.
     */
    public int getCardCount();

    /**
     * Gets the index of the specified card in this card pile.
     * 
     * @param card
     *        The card; must not be {@code null}.
     * 
     * @return The index of the specified card in this card pile.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code card} is not contained in this card pile.
     * @throws java.lang.NullPointerException
     *         If {@code card} is {@code null}.
     */
    public int getCardIndex(
        /* @NonNull */
        ICard card );

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
     * Gets the table that contains this card pile.
     * 
     * @return The table that contains this card pile or {@code null} if this
     *         card pile is not contained in a table.
     */
    /* @Nullable */
    public ITable getTable();

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
     * Removes all cards in this card pile.
     * 
     * @return The collection of cards removed from this card pile; never
     *         {@code null}. The cards are returned in order from the card at
     *         the bottom of the card pile to the card at the top of the card
     *         pile.
     */
    /* @NonNull */
    public List<ICard> removeCards();

    /**
     * Removes all cards in this card pile from the card at the specified
     * location to the top-most card.
     * 
     * @param location
     *        The location in table coordinates; must not be {@code null}.
     * 
     * @return The collection of cards removed from this card pile; never
     *         {@code null}. The cards are returned in order from the card at
     *         the specified location to the card at the top of the card pile.
     */
    /* @NonNull */
    public List<ICard> removeCards(
        /* @NonNull */
        Point location );

    /**
     * Sets the base design of this card pile.
     * 
     * @param baseDesign
     *        The base design of this card pile; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code baseDesign} is {@code null}.
     */
    public void setBaseDesign(
        /* @NonNull */
        ICardPileBaseDesign baseDesign );

    /**
     * Sets the base location of this card pile in table coordinates.
     * 
     * @param baseLocation
     *        The base location of this card pile in table coordinates; must not
     *        be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code baseLocation} is {@code null}.
     */
    public void setBaseLocation(
        /* @NonNull */
        Point baseLocation );

    /**
     * Sets the layout of cards within this card pile.
     * 
     * @param layout
     *        The layout of cards within this card pile; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code layout} is {@code null}.
     */
    public void setLayout(
        /* @NonNull */
        CardPileLayout layout );
}
