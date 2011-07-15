/*
 * TableContentChangedEvent.java
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
 * Created on Oct 16, 2009 at 10:03:00 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;

/**
 * An event used to notify listeners that the content of a table has changed.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
@ThreadSafe
public class TableContentChangedEvent
    extends TableEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 5470840745138469127L;

    /** The card pile associated with the event. */
    private final ICardPile cardPile_;

    /** The index of the card pile associated with the event. */
    private final int cardPileIndex_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableContentChangedEvent} class.
     * 
     * @param source
     *        The table that fired the event; must not be {@code null}.
     * @param cardPile
     *        The card pile associated with the event; must not be {@code null}.
     * @param cardPileIndex
     *        The index of the card pile associated with the event.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null} or if {@cardPileIndex} is
     *         negative.
     * @throws java.lang.NullPointerException
     *         If {@code cardPile} is {@code null}.
     */
    public TableContentChangedEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final ITable source,
        /* @NonNull */
        final ICardPile cardPile,
        final int cardPileIndex )
    {
        super( source );

        assertArgumentNotNull( cardPile, "cardPile" ); //$NON-NLS-1$
        assertArgumentLegal( cardPileIndex >= 0, "cardPileIndex", Messages.TableContentChangedEvent_ctor_cardPileIndex_negative ); //$NON-NLS-1$

        cardPile_ = cardPile;
        cardPileIndex_ = cardPileIndex;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the card pile associated with the event.
     * 
     * @return The card pile associated with the event; never {@code null}.
     */
    /* @NonNull */
    public final ICardPile getCardPile()
    {
        return cardPile_;
    }

    /**
     * Gets the index of the card pile associated with the event.
     * 
     * @return The index of the card pile associated with the event.
     */
    public final int getCardPileIndex()
    {
        return cardPileIndex_;
    }
}
