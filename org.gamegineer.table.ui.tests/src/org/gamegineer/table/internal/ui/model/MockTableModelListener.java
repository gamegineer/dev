/*
 * MockTableModelListener.java
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
 * Created on Dec 28, 2009 at 9:14:54 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicInteger;
import net.jcip.annotations.ThreadSafe;

/**
 * Mock implementation of
 * {@link org.gamegineer.table.internal.ui.model.ITableModelListener}.
 */
@ThreadSafe
public class MockTableModelListener
    implements ITableModelListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The count of card focus changed events received. */
    private final AtomicInteger cardFocusChangedEventCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockTableModelListener} class.
     */
    public MockTableModelListener()
    {
        cardFocusChangedEventCount_ = new AtomicInteger( 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.ui.model.ICardModelListener#cardFocusChanged(org.gamegineer.table.internal.ui.model.CardModelEvent)
     */
    public void cardFocusChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        cardFocusChangedEventCount_.incrementAndGet();
    }

    /**
     * Gets the count of card focus changed events received.
     * 
     * @return The count of card focus changed events received.
     */
    public final int getCardFocusChangedEventCount()
    {
        return cardFocusChangedEventCount_.get();
    }
}
