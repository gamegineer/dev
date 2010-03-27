/*
 * MockTableModelListener.java
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

    /** The count of card pile focus changed events received. */
    private final AtomicInteger cardPileFocusChangedEventCount_;

    /** The count of origin offset changed events received. */
    private final AtomicInteger originOffsetChangedEventCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockTableModelListener} class.
     */
    public MockTableModelListener()
    {
        cardPileFocusChangedEventCount_ = new AtomicInteger( 0 );
        originOffsetChangedEventCount_ = new AtomicInteger( 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#cardPileFocusChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    public void cardPileFocusChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        cardPileFocusChangedEventCount_.incrementAndGet();
    }

    /**
     * Gets the count of card pile focus changed events received.
     * 
     * @return The count of card pile focus changed events received.
     */
    public final int getCardPileFocusChangedEventCount()
    {
        return cardPileFocusChangedEventCount_.get();
    }

    /**
     * Gets the count of origin offset changed events received.
     * 
     * @return The count of origin offset changed events received.
     */
    public final int getOriginOffsetChangedEventCount()
    {
        return originOffsetChangedEventCount_.get();
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ITableModelListener#originOffsetChanged(org.gamegineer.table.internal.ui.model.TableModelEvent)
     */
    public void originOffsetChanged(
        final TableModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        originOffsetChangedEventCount_.incrementAndGet();
    }
}
