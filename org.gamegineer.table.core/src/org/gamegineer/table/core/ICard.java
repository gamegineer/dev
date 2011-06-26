/*
 * ICard.java
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
 * Created on Oct 9, 2009 at 11:59:10 PM.
 */

package org.gamegineer.table.core;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import org.gamegineer.common.core.util.memento.IMementoOriginator;

/**
 * A card.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICard
    extends IMementoOriginator
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified card listener to this card.
     * 
     * @param listener
     *        The card listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is already a registered card listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void addCardListener(
        /* @NonNull */
        ICardListener listener );

    /**
     * Flips this card.
     */
    public void flip();

    /**
     * Gets the design on the back of this card.
     * 
     * @return The design on the back of this card; never {@code null}.
     */
    /* @NonNull */
    public ICardSurfaceDesign getBackDesign();

    /**
     * Gets the bounds of this card in table coordinates.
     * 
     * @return The bounds of this card in table coordinates; never {@code null}
     *         .
     */
    /* @NonNull */
    public Rectangle getBounds();

    /**
     * Gets the card pile that contains this card.
     * 
     * @return The card pile that contains this card or {@code null} if this
     *         card is not contained in a card pile.
     */
    /* @Nullable */
    public ICardPile getCardPile();

    /**
     * Gets the design on the face of this card.
     * 
     * @return The design on the face of this card; never {@code null}.
     */
    /* @NonNull */
    public ICardSurfaceDesign getFaceDesign();

    /**
     * Gets the location of this card in table coordinates.
     * 
     * @return The location of this card in table coordinates; never {@code
     *         null}.
     */
    /* @NonNull */
    public Point getLocation();

    /**
     * Gets the orientation of this card.
     * 
     * @return The orientation of this card; never {@code null}.
     */
    /* @NonNull */
    public CardOrientation getOrientation();

    /**
     * Gets the size of this card in table coordinates.
     * 
     * @return The size of this card in table coordinates; never {@code null}.
     */
    /* @NonNull */
    public Dimension getSize();

    /**
     * Removes the specified card listener from this card.
     * 
     * @param listener
     *        The card listener; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code listener} is not a registered card listener.
     * @throws java.lang.NullPointerException
     *         If {@code listener} is {@code null}.
     */
    public void removeCardListener(
        /* @NonNull */
        ICardListener listener );

    /**
     * Sets the card pile that contains this card.
     * 
     * @param cardPile
     *        The card pile that contains this card or {@code null} if this card
     *        is not contained in a card pile.
     */
    public void setCardPile(
        /* @Nullable */
        ICardPile cardPile );

    /**
     * Sets the location of this card in table coordinates.
     * 
     * @param location
     *        The location of this card in table coordinates; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code location} is {@code null}.
     */
    public void setLocation(
        /* @NonNull */
        Point location );

    /**
     * Sets the orientation of this card.
     * 
     * @param orientation
     *        The orientation of this card; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code orientation} is {@code null}.
     */
    public void setOrientation(
        /* @NonNull */
        CardOrientation orientation );

    /**
     * Sets the surface designs of this card.
     * 
     * @param backDesign
     *        The design on the back of the card; must not be {@code null}.
     * @param faceDesign
     *        The design on the face of the card; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code backDesign} and {@code faceDesign} do not have the same
     *         size.
     * @throws java.lang.NullPointerException
     *         If {@code backDesign} or {@code faceDesign} is {@code null}.
     */
    public void setSurfaceDesigns(
        /* @NonNull */
        ICardSurfaceDesign backDesign,
        /* @NonNull */
        ICardSurfaceDesign faceDesign );
}
