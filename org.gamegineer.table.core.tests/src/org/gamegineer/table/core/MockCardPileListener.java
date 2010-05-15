/*
 * MockCardPileListener.java
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
 * Created on Jan 10, 2010 at 10:20:44 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicInteger;
import net.jcip.annotations.ThreadSafe;

/**
 * Mock implementation of {@link org.gamegineer.table.core.ICardPileListener}.
 */
@ThreadSafe
public class MockCardPileListener
    implements ICardPileListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The count of card added events received. */
    private final AtomicInteger cardAddedEventCount_;

    /** The count of card pile bounds changed events received. */
    private final AtomicInteger cardPileBoundsChangedEventCount_;

    /** The count of card removed events received. */
    private final AtomicInteger cardRemovedEventCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockCardPileListener} class.
     */
    public MockCardPileListener()
    {
        cardAddedEventCount_ = new AtomicInteger( 0 );
        cardPileBoundsChangedEventCount_ = new AtomicInteger( 0 );
        cardRemovedEventCount_ = new AtomicInteger( 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ICardPileListener#cardAdded(org.gamegineer.table.core.CardPileContentChangedEvent)
     */
    @Override
    public void cardAdded(
        final CardPileContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        cardAddedEventCount_.incrementAndGet();
    }

    /*
     * @see org.gamegineer.table.core.ICardPileListener#cardPileBoundsChanged(org.gamegineer.table.core.CardPileEvent)
     */
    @Override
    public void cardPileBoundsChanged(
        final CardPileEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        cardPileBoundsChangedEventCount_.incrementAndGet();
    }

    /*
     * @see org.gamegineer.table.core.ICardPileListener#cardRemoved(org.gamegineer.table.core.CardPileContentChangedEvent)
     */
    @Override
    public void cardRemoved(
        final CardPileContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        cardRemovedEventCount_.incrementAndGet();
    }

    /**
     * Gets the count of card added events received.
     * 
     * @return The count of card added events received.
     */
    public final int getCardAddedEventCount()
    {
        return cardAddedEventCount_.get();
    }

    /**
     * Gets the count of card pile bounds changed events received.
     * 
     * @return The count of card pile bounds changed events received.
     */
    public final int getCardPileBoundsChangedEventCount()
    {
        return cardPileBoundsChangedEventCount_.get();
    }

    /**
     * Gets the count of card removed events received.
     * 
     * @return The count of card removed events received.
     */
    public final int getCardRemovedEventCount()
    {
        return cardRemovedEventCount_.get();
    }
}
