/*
 * MockTableListener.java
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
 * Created on Oct 16, 2009 at 11:36:20 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicInteger;
import net.jcip.annotations.ThreadSafe;

/**
 * Mock implementation of {@link org.gamegineer.table.core.ITableListener}.
 */
@ThreadSafe
public class MockTableListener
    implements ITableListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The count of card pile added events received. */
    private final AtomicInteger cardPileAddedEventCount_;

    /** The count of card pile removed events received. */
    private final AtomicInteger cardPileRemovedEventCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockTableListener} class.
     */
    public MockTableListener()
    {
        cardPileAddedEventCount_ = new AtomicInteger( 0 );
        cardPileRemovedEventCount_ = new AtomicInteger( 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ITableListener#cardPileAdded(org.gamegineer.table.core.TableContentChangedEvent)
     */
    @Override
    public void cardPileAdded(
        final TableContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        cardPileAddedEventCount_.incrementAndGet();
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardPileRemoved(org.gamegineer.table.core.TableContentChangedEvent)
     */
    @Override
    public void cardPileRemoved(
        final TableContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        cardPileRemovedEventCount_.incrementAndGet();
    }

    /**
     * Gets the count of card pile added events received.
     * 
     * @return The count of card pile added events received.
     */
    public final int getCardPileAddedEventCount()
    {
        return cardPileAddedEventCount_.get();
    }

    /**
     * Gets the count of card pile removed events received.
     * 
     * @return The count of card pile removed events received.
     */
    public final int getCardPileRemovedEventCount()
    {
        return cardPileRemovedEventCount_.get();
    }
}
