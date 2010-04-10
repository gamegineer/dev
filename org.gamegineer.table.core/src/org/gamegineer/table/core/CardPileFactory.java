/*
 * CardPileFactory.java
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
 * Created on Jan 14, 2010 at 11:33:34 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.common.persistence.memento.IMemento;
import org.gamegineer.common.persistence.memento.MalformedMementoException;
import org.gamegineer.table.internal.core.CardPile;

/**
 * A factory for creating card piles.
 */
@ThreadSafe
public final class CardPileFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileFactory} class.
     */
    private CardPileFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new card pile.
     * 
     * @param baseDesign
     *        The design of the card pile base; must not be {@code null}.
     * 
     * @return A new card pile; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code baseDesign} is {@code null}.
     */
    /* @NonNull */
    public static ICardPile createCardPile(
        /* @NonNull */
        final ICardPileBaseDesign baseDesign )
    {
        return new CardPile( baseDesign );
    }

    /**
     * Creates a new card pile whose state is initialized using the specified
     * memento.
     * 
     * @param memento
     *        The memento representing the initial card pile state; must not be
     *        {@code null}.
     * 
     * @return A new card pile; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code memento} is {@code null}.
     * @throws org.gamegineer.common.persistence.memento.MalformedMementoException
     *         If {@code memento} is malformed.
     */
    /* @NonNull */
    public static ICardPile createCardPile(
        /* @NonNull */
        final IMemento memento )
        throws MalformedMementoException
    {
        return CardPile.fromMemento( memento );
    }
}
