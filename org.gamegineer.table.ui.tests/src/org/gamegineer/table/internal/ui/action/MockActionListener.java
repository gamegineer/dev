/*
 * MockActionListener.java
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
 * Created on Oct 18, 2009 at 10:31:15 PM.
 */

package org.gamegineer.table.internal.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;
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
    private final AtomicInteger actionPerformedEventCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockActionListener} class.
     */
    MockActionListener()
    {
        actionPerformedEventCount_ = new AtomicInteger( 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(
        @SuppressWarnings( "unused" )
        final ActionEvent e )
    {
        actionPerformedEventCount_.incrementAndGet();
    }

    /**
     * Gets the count of action performed events received.
     * 
     * @return The count of action performed events received.
     */
    int getActionPerformedEventCount()
    {
        return actionPerformedEventCount_.get();
    }
}
