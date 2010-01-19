/*
 * InternalCardPileEvent.java
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
 * Created on Jan 18, 2010 at 11:29:00 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardPileEvent;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ICardPileEvent;

/**
 * Implementation of {@link org.gamegineer.table.core.CardPileEvent}.
 */
@ThreadSafe
final class InternalCardPileEvent
    extends CardPileEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 4910322863812692667L;

    /** The card pile event implementation to which all behavior is delegated. */
    private final ICardPileEvent delegate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InternalCardPileEvent} class.
     * 
     * @param delegate
     *        The card pile event implementation to which all behavior is
     *        delegated; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code delegate} is {@code null}.
     */
    private InternalCardPileEvent(
        /* @NonNull */
        final ICardPileEvent delegate )
    {
        super( delegate.getCardPile() );

        delegate_ = delegate;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code InternalCardPileEvent} class.
     * 
     * @param cardPile
     *        The card pile that fired the event; must not be {@code null}.
     * 
     * @return A new instance of the {@code InternalCardPileEvent} class; never
     *         {@code null}.
     */
    static InternalCardPileEvent createCardPileEvent(
        /* @NonNull */
        final ICardPile cardPile )
    {
        assert cardPile != null;

        return new InternalCardPileEvent( new CardPileEventDelegate( cardPile ) );
    }

    /*
     * @see org.gamegineer.table.core.ICardPileEvent#getCardPile()
     */
    public ICardPile getCardPile()
    {
        return delegate_.getCardPile();
    }
}
