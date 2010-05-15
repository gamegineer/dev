/*
 * CardPileContentChangedEventDelegate.java
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
 * Created on Jan 10, 2010 at 10:33:22 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileContentChangedEvent;

/**
 * An implementation of
 * {@link org.gamegineer.table.core.ICardPileContentChangedEvent} to which
 * implementations of
 * {@link org.gamegineer.table.core.CardPileContentChangedEvent} can delegate
 * their behavior.
 */
@Immutable
final class CardPileContentChangedEventDelegate
    extends CardPileEventDelegate
    implements ICardPileContentChangedEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card associated with the event. */
    private final ICard card_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * CardPileContentChangedEventDelegate} class.
     * 
     * @param cardPile
     *        The card pile that fired the event; must not be {@code null}.
     * @param card
     *        The card associated with the event; must not be {@code null}.
     */
    CardPileContentChangedEventDelegate(
        /* @NonNull */
        final ICardPile cardPile,
        /* @NonNull */
        final ICard card )
    {
        super( cardPile );

        assert card != null;

        card_ = card;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICardPileContentChangedEvent#getCard()
     */
    @Override
    public ICard getCard()
    {
        return card_;
    }
}
