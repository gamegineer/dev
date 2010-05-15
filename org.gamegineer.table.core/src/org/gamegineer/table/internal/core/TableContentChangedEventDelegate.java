/*
 * TableContentChangedEventDelegate.java
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
 * Created on Oct 16, 2009 at 11:00:59 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableContentChangedEvent;

/**
 * An implementation of
 * {@link org.gamegineer.table.core.ITableContentChangedEvent} to which
 * implementations of {@link org.gamegineer.table.core.TableContentChangedEvent}
 * can delegate their behavior.
 */
@Immutable
final class TableContentChangedEventDelegate
    extends TableEventDelegate
    implements ITableContentChangedEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card pile associated with the event. */
    private final ICardPile cardPile_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * TableContentChangedEventDelegate} class.
     * 
     * @param table
     *        The table that fired the event; must not be {@code null}.
     * @param cardPile
     *        The card pile associated with the event; must not be {@code null}.
     */
    TableContentChangedEventDelegate(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final ICardPile cardPile )
    {
        super( table );

        assert cardPile != null;

        cardPile_ = cardPile;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ITableContentChangedEvent#getCardPile()
     */
    @Override
    public ICardPile getCardPile()
    {
        return cardPile_;
    }
}
