/*
 * InternalCardEvent.java
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
 * Created on Oct 24, 2009 at 10:16:00 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardEvent;

/**
 * Implementation of {@link org.gamegineer.table.core.CardEvent}.
 */
@ThreadSafe
final class InternalCardEvent
    extends CardEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -4136351175727140117L;

    /** The card event implementation to which all behavior is delegated. */
    private final ICardEvent delegate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InternalCardEvent} class.
     * 
     * @param delegate
     *        The card event implementation to which all behavior is delegated;
     *        must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code delegate} is {@code null}.
     */
    private InternalCardEvent(
        /* @NonNull */
        final ICardEvent delegate )
    {
        super( delegate.getCard() );

        delegate_ = delegate;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code InternalCardEvent} class.
     * 
     * @param card
     *        The card that fired the event; must not be {@code null}.
     * 
     * @return A new instance of the {@code InternalCardEvent} class; never
     *         {@code null}.
     */
    static InternalCardEvent createCardEvent(
        /* @NonNull */
        final ICard card )
    {
        assert card != null;

        return new InternalCardEvent( new CardEventDelegate( card ) );
    }

    /*
     * @see org.gamegineer.table.core.ICardEvent#getCard()
     */
    public ICard getCard()
    {
        return delegate_.getCard();
    }
}
