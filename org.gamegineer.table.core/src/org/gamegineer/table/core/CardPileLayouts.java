/*
 * CardPileLayouts.java
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
 * Created on May 5, 2012 at 9:56:34 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.AccordianContainerLayout;
import org.gamegineer.table.internal.core.StackedContainerLayout;

/**
 * A collection of layouts for card piles.
 */
@ThreadSafe
public final class CardPileLayouts
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * Indicates the card pile is laid out as an accordian. Beginning with the
     * card at the bottom of the card pile, each successive card is offset
     * immediately below it.
     */
    public static final IContainerLayout ACCORDIAN_DOWN = new AccordianContainerLayout( 0, 18 );

    /**
     * Indicates the card pile is laid out as an accordian. Beginning with the
     * card at the bottom of the card pile, each successive card is offset
     * immediately to the left of it.
     */
    public static final IContainerLayout ACCORDIAN_LEFT = new AccordianContainerLayout( 16, 0 );

    /**
     * Indicates the card pile is laid out as an accordian. Beginning with the
     * card at the bottom of the card pile, each successive card is offset
     * immediately to the right of it.
     */
    public static final IContainerLayout ACCORDIAN_RIGHT = new AccordianContainerLayout( -16, 0 );

    /**
     * Indicates the card pile is laid out as an accordian. Beginning with the
     * card at the bottom of the card pile, each successive card is offset
     * immediately above it.
     */
    public static final IContainerLayout ACCORDIAN_UP = new AccordianContainerLayout( 0, -18 );

    /**
     * Indicates the card pile is laid out with one card placed on top of the
     * other with no offset.
     */
    public static final IContainerLayout STACKED = new StackedContainerLayout( 10, 2, 1 );


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileLayouts} class.
     */
    private CardPileLayouts()
    {
    }
}
