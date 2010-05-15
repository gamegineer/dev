/*
 * InternalCardPileContentChangedEvent.java
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
 * Created on Jan 10, 2010 at 10:40:37 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardPileContentChangedEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileContentChangedEvent;

/**
 * Implementation of
 * {@link org.gamegineer.table.core.CardPileContentChangedEvent}.
 */
@ThreadSafe
final class InternalCardPileContentChangedEvent
    extends CardPileContentChangedEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 8660206340682973013L;

    /**
     * The card pile content changed event implementation to which all behavior
     * is delegated.
     */
    private final ICardPileContentChangedEvent delegate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * InternalCardPileContentChangedEvent} class.
     * 
     * @param delegate
     *        The card pile content changed event implementation to which all
     *        behavior is delegated; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code delegate} is {@code null}.
     */
    private InternalCardPileContentChangedEvent(
        /* @NonNull */
        final ICardPileContentChangedEvent delegate )
    {
        super( delegate.getCardPile() );

        delegate_ = delegate;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code InternalCardPileContentChangedEvent}
     * class.
     * 
     * @param cardPile
     *        The card pile that fired the event; must not be {@code null}.
     * @param card
     *        The card associated with the event; must not be {@code null}.
     * 
     * @return A new instance of the {@code InternalCardPileContentChangedEvent}
     *         class; never {@code null}.
     */
    static InternalCardPileContentChangedEvent createCardPileContentChangedEvent(
        /* @NonNull */
        final ICardPile cardPile,
        /* @NonNull */
        final ICard card )
    {
        assert cardPile != null;
        assert card != null;

        return new InternalCardPileContentChangedEvent( new CardPileContentChangedEventDelegate( cardPile, card ) );
    }

    /*
     * @see org.gamegineer.table.core.ICardPileContentChangedEvent#getCard()
     */
    @Override
    public ICard getCard()
    {
        return delegate_.getCard();
    }

    /*
     * @see org.gamegineer.table.core.ICardPileEvent#getCardPile()
     */
    @Override
    public ICardPile getCardPile()
    {
        return delegate_.getCardPile();
    }
}
