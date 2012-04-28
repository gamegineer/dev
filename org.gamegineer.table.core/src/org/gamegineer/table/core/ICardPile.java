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
     * Gets the base location of this card pile in table coordinates.
     * 
     * @return The base location of this card pile in table coordinates; never
     *         {@code null}.
     */
    /* @NonNull */
    public Point getBaseLocation();

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
