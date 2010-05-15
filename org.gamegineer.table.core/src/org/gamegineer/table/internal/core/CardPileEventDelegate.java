/*
 * CardPileEventDelegate.java
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
 * Created on Jan 10, 2010 at 10:26:24 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileEvent;

/**
 * An implementation of {@link org.gamegineer.table.core.ICardPileEvent} to
 * which implementations of {@link org.gamegineer.table.core.CardPileEvent} can
 * delegate their behavior.
 */
@Immutable
class CardPileEventDelegate
    implements ICardPileEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile that fired the event. */
    private final ICardPile cardPile_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileEventDelegate} class.
     * 
     * @param cardPile
     *        The card pile that fired the event; must not be {@code null}.
     */
    CardPileEventDelegate(
        /* @NonNull */
        final ICardPile cardPile )
    {
        assert cardPile != null;

        cardPile_ = cardPile;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICardPileEvent#getCardPile()
     */
    @Override
    public ICardPile getCardPile()
    {
        return cardPile_;
    }
}
