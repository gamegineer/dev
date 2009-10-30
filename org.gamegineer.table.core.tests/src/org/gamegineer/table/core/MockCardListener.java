/*
 * MockCardListener.java
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
 * Created on Oct 24, 2009 at 9:21:00 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicInteger;
import net.jcip.annotations.ThreadSafe;

/**
 * Mock implementation of {@link org.gamegineer.table.core.ICardListener}.
 */
@ThreadSafe
public class MockCardListener
    implements ICardListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The count of card flipped events received. */
    private final AtomicInteger cardFlippedEventCount_;

    /** The count of card location changed events received. */
    private final AtomicInteger cardLocationChangedEventCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockCardListener} class.
     */
    public MockCardListener()
    {
        cardFlippedEventCount_ = new AtomicInteger( 0 );
        cardLocationChangedEventCount_ = new AtomicInteger( 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICardListener#cardFlipped(org.gamegineer.table.core.CardEvent)
     */
    public void cardFlipped(
        final CardEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        cardFlippedEventCount_.incrementAndGet();
    }

    /*
     * @see org.gamegineer.table.core.ICardListener#cardLocationChanged(org.gamegineer.table.core.CardEvent)
     */
    public void cardLocationChanged(
        final CardEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        cardLocationChangedEventCount_.incrementAndGet();
    }

    /**
     * Gets the count of card flipped events received.
     * 
     * @return The count of card flipped events received.
     */
    public final int getCardFlippedEventCount()
    {
        return cardFlippedEventCount_.get();
    }

    /**
     * Gets the count of card location changed events received.
     * 
     * @return The count of card location changed events received.
     */
    public final int getCardLocationChangedEventCount()
    {
        return cardLocationChangedEventCount_.get();
    }
}
