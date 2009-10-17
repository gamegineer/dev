/*
 * MockTableListener.java
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
 * Created on Oct 16, 2009 at 11:36:20 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.GuardedBy;
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

    /** The count of card added events received. */
    @GuardedBy( "lock_" )
    private int cardAddedEventCount_;

    /** The count of card removed events received. */
    @GuardedBy( "lock_" )
    private int cardRemovedEventCount_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockTableListener} class.
     */
    public MockTableListener()
    {
        lock_ = new Object();
        cardAddedEventCount_ = 0;
        cardRemovedEventCount_ = 0;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ITableListener#cardAdded(org.gamegineer.table.core.CardChangeEvent)
     */
    public void cardAdded(
        final CardChangeEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            ++cardAddedEventCount_;
        }
    }

    /*
     * @see org.gamegineer.table.core.ITableListener#cardRemoved(org.gamegineer.table.core.CardChangeEvent)
     */
    public void cardRemoved(
        final CardChangeEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        synchronized( lock_ )
        {
            ++cardRemovedEventCount_;
        }
    }

    /**
     * Gets the count of card added events received.
     * 
     * @return The count of card added events received.
     */
    public final int getCardAddedEventCount()
    {
        synchronized( lock_ )
        {
            return cardAddedEventCount_;
        }
    }

    /**
     * Gets the count of card removed events received.
     * 
     * @return The count of card removed events received.
     */
    public final int getCardRemovedEventCount()
    {
        synchronized( lock_ )
        {
            return cardRemovedEventCount_;
        }
    }
}
