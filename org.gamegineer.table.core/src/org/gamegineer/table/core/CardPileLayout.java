/*
 * CardPileLayout.java
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
 * Created on Mar 29, 2010 at 9:52:15 PM.
 */

package org.gamegineer.table.core;

/**
 * Enumerates the possible card pile layouts.
 */
public enum CardPileLayout
{
    // ======================================================================
    // Enum Constants
    // ======================================================================

    /**
     * Indicates the card pile is laid out with one card placed on top of the
     * other with no offset.
     */
    STACKED,

    /**
     * Indicates the card pile is laid out as an accordian. Beginning with the
     * card at the bottom of the card pile, each successive card is offset
     * immediately above it.
     */
    ACCORDIAN_UP,

    /**
     * Indicates the card pile is laid out as an accordian. Beginning with the
     * card at the bottom of the card pile, each successive card is offset
     * immediately below it.
     */
    ACCORDIAN_DOWN,

    /**
     * Indicates the card pile is laid out as an accordian. Beginning with the
     * card at the bottom of the card pile, each successive card is offset
     * immediately to the left of it.
     */
    ACCORDIAN_LEFT,

    /**
     * Indicates the card pile is laid out as an accordian. Beginning with the
     * card at the bottom of the card pile, each successive card is offset
     * immediately to the right of it.
     */
    ACCORDIAN_RIGHT;
}
