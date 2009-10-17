/*
 * CardChangeEventDelegate.java
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
 * Created on Oct 16, 2009 at 11:00:59 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardChangeEvent;
import org.gamegineer.table.core.ITable;

/**
 * An implementation of {@link org.gamegineer.table.core.ICardChangeEvent} to
 * which implementations of {@link org.gamegineer.table.core.CardChangeEvent}
 * can delegate their behavior.
 */
@Immutable
final class CardChangeEventDelegate
    extends TableEventDelegate
    implements ICardChangeEvent
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
     * Initializes a new instance of the {@code CardChangeEventDelegate} class.
     * 
     * @param table
     *        The table that fired the event; must not be {@code null}.
     * @param card
     *        The card associated with the event; must not be {@code null}.
     */
    CardChangeEventDelegate(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final ICard card )
    {
        super( table );

        assert card != null;

        card_ = card;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICardChangeEvent#getCard()
     */
    public ICard getCard()
    {
        return card_;
    }
}
