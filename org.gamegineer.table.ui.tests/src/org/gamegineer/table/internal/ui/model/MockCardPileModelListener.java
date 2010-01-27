/*
 * MockCardPileModelListener.java
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
 * Created on Jan 26, 2010 at 10:50:29 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicInteger;
import net.jcip.annotations.ThreadSafe;

/**
 * Mock implementation of
 * {@link org.gamegineer.table.internal.ui.model.ICardPileModelListener}.
 */
@ThreadSafe
public class MockCardPileModelListener
    implements ICardPileModelListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The count of card pile focus gained events received. */
    private final AtomicInteger cardPileFocusGainedEventCount_;

    /** The count of card pile focus lost events received. */
    private final AtomicInteger cardPileFocusLostEventCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockCardPileModelListener}
     * class.
     */
    public MockCardPileModelListener()
    {
        cardPileFocusGainedEventCount_ = new AtomicInteger( 0 );
        cardPileFocusLostEventCount_ = new AtomicInteger( 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.ui.model.ICardPileModelListener#cardPileFocusGained(org.gamegineer.table.internal.ui.model.CardPileModelEvent)
     */
    public void cardPileFocusGained(
        final CardPileModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        cardPileFocusGainedEventCount_.incrementAndGet();
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.ICardPileModelListener#cardPileFocusLost(org.gamegineer.table.internal.ui.model.CardPileModelEvent)
     */
    public void cardPileFocusLost(
        final CardPileModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        cardPileFocusLostEventCount_.incrementAndGet();
    }

    /**
     * Gets the count of card pile focus gained events received.
     * 
     * @return The count of card pile focus gained events received.
     */
    public final int getCardPileFocusGainedEventCount()
    {
        return cardPileFocusGainedEventCount_.get();
    }

    /**
     * Gets the count of card pile focus lost events received.
     * 
     * @return The count of card pile focus lost events received.
     */
    public final int getCardPileFocusLostEventCount()
    {
        return cardPileFocusLostEventCount_.get();
    }
}
