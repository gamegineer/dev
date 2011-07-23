/*
 * CardPileContentChangedEvent.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Jan 10, 2010 at 10:13:08 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;

/**
 * An event used to notify listeners that the content of a card pile has
 * changed.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
@ThreadSafe
public class CardPileContentChangedEvent
    extends CardPileEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -2958345168587653231L;

    /** The card associated with the event. */
    private final ICard card_;

    /** The index of the card associated with the event. */
    private final int cardIndex_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileContentChangedEvent}
     * class.
     * 
     * @param source
     *        The card pile that fired the event; must not be {@code null}.
     * @param card
     *        The card associated with the event; must not be {@code null}.
     * @param cardIndex
     *        The index of the card associated with the event.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null} or if {@code cardIndex} is
     *         negative.
     * @throws java.lang.NullPointerException
     *         If {@code card} is {@code null}.
     */
    public CardPileContentChangedEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final ICardPile source,
        /* @NonNull */
        final ICard card,
        final int cardIndex )
    {
        super( source );

        assertArgumentNotNull( card, "card" ); //$NON-NLS-1$
        assertArgumentLegal( cardIndex >= 0, "cardIndex", NonNlsMessages.CardPileContentChangedEvent_ctor_cardIndex_negative ); //$NON-NLS-1$

        card_ = card;
        cardIndex_ = cardIndex;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card associated with the event.
     * 
     * @return The card associated with the event; never {@code null}.
     */
    /* @NonNull */
    public final ICard getCard()
    {
        return card_;
    }

    /**
     * Gets the index of the card associated with the event.
     * 
     * @return The index of the card associated with the event.
     */
    public final int getCardIndex()
    {
        return cardIndex_;
    }
}
