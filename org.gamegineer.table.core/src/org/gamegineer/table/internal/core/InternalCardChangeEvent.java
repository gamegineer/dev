/*
 * InternalCardChangeEvent.java
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
 * Created on Oct 16, 2009 at 11:09:32 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.CardChangeEvent;
import org.gamegineer.table.core.ICard;
import org.gamegineer.table.core.ICardChangeEvent;
import org.gamegineer.table.core.ITable;

/**
 * Implementation of {@link org.gamegineer.table.core.CardChangeEvent}.
 */
@ThreadSafe
final class InternalCardChangeEvent
    extends CardChangeEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 685439950163321404L;

    /** The card change event implementation to which all behavior is delegated. */
    private final ICardChangeEvent delegate_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code InternalCardChangeEvent} class.
     * 
     * @param delegate
     *        The card change event implementation to which all behavior is
     *        delegated; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code delegate} is {@code null}.
     */
    private InternalCardChangeEvent(
        /* @NonNull */
        final ICardChangeEvent delegate )
    {
        super( delegate.getTable() );

        delegate_ = delegate;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code InternalCardChangeEvent} class.
     * 
     * @param table
     *        The table that fired the event; must not be {@code null}.
     * @param card
     *        The card associated with the event; must not be {@code null}.
     * 
     * @return A new instance of the {@code InternalCardChangeEvent} class;
     *         never {@code null}.
     */
    static InternalCardChangeEvent createCardChangeEvent(
        /* @NonNull */
        final ITable table,
        /* @NonNull */
        final ICard card )
    {
        assert table != null;
        assert card != null;

        return new InternalCardChangeEvent( new CardChangeEventDelegate( table, card ) );
    }

    /*
     * @see org.gamegineer.table.core.ICardChangeEvent#getCard()
     */
    public ICard getCard()
    {
        return delegate_.getCard();
    }

    /*
     * @see org.gamegineer.table.core.ITableEvent#getTable()
     */
    public ITable getTable()
    {
        return delegate_.getTable();
    }
}
