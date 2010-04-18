/*
 * MockCardModelListener.java
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
 * Created on Dec 25, 2009 at 11:21:55 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicInteger;
import net.jcip.annotations.ThreadSafe;

/**
 * Mock implementation of
 * {@link org.gamegineer.table.internal.ui.model.ICardModelListener}.
 */
@ThreadSafe
public class MockCardModelListener
    implements ICardModelListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The count of card model state changed events received. */
    private final AtomicInteger cardModelStateChangedEventCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockCardModelListener} class.
     */
    public MockCardModelListener()
    {
        cardModelStateChangedEventCount_ = new AtomicInteger( 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.ui.model.ICardModelListener#cardModelStateChanged(org.gamegineer.table.internal.ui.model.CardModelEvent)
     */
    public void cardModelStateChanged(
        final CardModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        cardModelStateChangedEventCount_.incrementAndGet();
    }

    /**
     * Gets the count of card model state changed events received.
     * 
     * @return The count of card model state changed events received.
     */
    public final int getCardModelStateChangedEventCount()
    {
        return cardModelStateChangedEventCount_.get();
    }
}
