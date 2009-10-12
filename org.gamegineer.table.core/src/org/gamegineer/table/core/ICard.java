/*
 * ICard.java
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
 * Created on Oct 9, 2009 at 11:59:10 PM.
 */

package org.gamegineer.table.core;

/**
 * A card.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICard
{
    // ======================================================================
    // Methods
    // ======================================================================

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
    public CardDesign getBackDesign();

    /**
     * Gets the design on the face of this card.
     * 
     * @return The design on the face of this card; never {@code null}.
     */
    /* @NonNull */
    public CardDesign getFaceDesign();

    /**
     * Gets the orientation of this card.
     * 
     * @return The orientation of this card; never {@code null}.
     */
    /* @NonNull */
    public CardOrientation getOrientation();

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
}
