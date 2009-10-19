/*
 * MockActionListener.java
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
 * Created on Oct 18, 2009 at 10:31:15 PM.
 */

package org.gamegineer.table.internal.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Mock implementation of {@link java.awt.event.ActionListener}.
 */
@ThreadSafe
final class MockActionListener
    implements ActionListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The count of action performed events received. */
    @GuardedBy( "lock_" )
    private int actionPerformedEventCount_;

    /** The instance lock. */
    private final Object lock_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockActionListener} class.
     */
    MockActionListener()
    {
        lock_ = new Object();
        actionPerformedEventCount_ = 0;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(
        @SuppressWarnings( "unused" )
        final ActionEvent e )
    {
        synchronized( lock_ )
        {
            ++actionPerformedEventCount_;
        }
    }

    /**
     * Gets the count of action performed events received.
     * 
     * @return The count of action performed events received.
     */
    int getActionPerformedEventCount()
    {
        synchronized( lock_ )
        {
            return actionPerformedEventCount_;
        }
    }
}
