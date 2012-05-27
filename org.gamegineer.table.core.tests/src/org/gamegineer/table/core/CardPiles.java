/*
 * CardPiles.java
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
 * Created on Apr 20, 2010 at 10:04:00 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;

/**
 * A factory for creating various types of card piles suitable for testing.
 */
@ThreadSafe
public final class CardPiles
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPiles} class.
     */
    private CardPiles()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card pile with unique surface designs for the specified
     * table environment.
     * 
     * @param tableEnvironment
     *        The table environment associated with the new card pile; must not
     *        be {@code null}.
     * 
     * @return A new card pile; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code tableEnvironment} is {@code null}.
     */
    /* @NonNull */
    public static ICardPile createUniqueCardPile(
        /* @NonNull */
        final ITableEnvironment tableEnvironment )
    {
        final ICardPile cardPile = tableEnvironment.createCardPile();
        cardPile.setSurfaceDesign( CardPileOrientation.BASE, ComponentSurfaceDesigns.createUniqueComponentSurfaceDesign() );
        return cardPile;
    }
}
