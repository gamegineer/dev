/*
 * InternalTableContentChangedEvent.java
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
 * Created on Oct 16, 2009 at 11:09:32 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ICardPile;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableContentChangedEvent;
import org.gamegineer.table.core.TableContentChangedEvent;

/**
 * Implementation of {@link org.gamegineer.table.core.TableContentChangedEvent}.
 */
@ThreadSafe
final class InternalTableContentChangedEvent
    extends TableContentChangedEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 685439950163321404L;

    /**
     * The table content changed event implementation to which all behavior is
     * delegated.
     */
    private final ITableContentChangedEvent delegate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * InternalTableContentChangedEvent} class.
     * 
     * @param delegate
     *        The table content changed event implementation to which all
     *        behavior is delegated; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code delegate} is {@code null}.
     */
    private InternalTableContentChangedEvent(
        /* @NonNull */
        final ITableContentChangedEvent delegate )
    {
        super( delegate.getTable() );

        delegate_ = delegate;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code InternalTableContentChangedEvent}
     * class.
     * 
     * @param table
     *        The table that fired the event; must not be {@code null}.
     * @param cardPile
     *        The card pile associated with the event; must not be {@code null}.
     * 
     * @return A new instance of the {@code InternalTableContentChangedEvent}
     *         class; never {@code null}.
     */
    static InternalTableContentChangedEvent createTableContentChangedEvent(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final ICardPile cardPile )
    {
        assert table != null;
        assert cardPile != null;

        return new InternalTableContentChangedEvent( new TableContentChangedEventDelegate( table, cardPile ) );
    }

    /*
     * @see org.gamegineer.table.core.ITableContentChangedEvent#getCardPile()
     */
    public ICardPile getCardPile()
    {
        return delegate_.getCardPile();
    }

    /*
     * @see org.gamegineer.table.core.ITableEvent#getTable()
     */
    public ITable getTable()
    {
        return delegate_.getTable();
    }
}
