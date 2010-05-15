/*
 * MockMainModelListener.java
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
 * Created on Apr 13, 2010 at 10:15:05 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.concurrent.atomic.AtomicInteger;
import net.jcip.annotations.ThreadSafe;

/**
 * Mock implementation of
 * {@link org.gamegineer.table.internal.ui.model.IMainModelListener}.
 */
@ThreadSafe
public class MockMainModelListener
    implements IMainModelListener
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The count of main model dirty flag changed events received. */
    private final AtomicInteger mainModelDirtyFlagChangedEventCount_;

    /** The count of main model state changed events received. */
    private final AtomicInteger mainModelStateChangedEventCount_;

    /** The count of table closed events received. */
    private final AtomicInteger tableClosedEventCount_;

    /** The count of table opened events received. */
    private final AtomicInteger tableOpenedEventCount_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MockMainModelListener} class.
     */
    public MockMainModelListener()
    {
        mainModelDirtyFlagChangedEventCount_ = new AtomicInteger( 0 );
        mainModelStateChangedEventCount_ = new AtomicInteger( 0 );
        tableClosedEventCount_ = new AtomicInteger( 0 );
        tableOpenedEventCount_ = new AtomicInteger( 0 );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the count of main model dirty flag changed events received.
     * 
     * @return The count of main model dirty flag changed events received.
     */
    public final int getMainModelDirtyFlagChangedEventCount()
    {
        return mainModelDirtyFlagChangedEventCount_.get();
    }

    /**
     * Gets the count of main model state changed events received.
     * 
     * @return The count of main model state changed events received.
     */
    public final int getMainModelStateChangedEventCount()
    {
        return mainModelStateChangedEventCount_.get();
    }

    /**
     * Gets the count of table closed events received.
     * 
     * @return The count of table closed events received.
     */
    public final int getTableClosedEventCount()
    {
        return tableClosedEventCount_.get();
    }

    /**
     * Gets the count of table opened events received.
     * 
     * @return The count of table opened events received.
     */
    public final int getTableOpenedEventCount()
    {
        return tableOpenedEventCount_.get();
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IMainModelListener#mainModelDirtyFlagChanged(org.gamegineer.table.internal.ui.model.MainModelEvent)
     */
    @Override
    public void mainModelDirtyFlagChanged(
        final MainModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        mainModelDirtyFlagChangedEventCount_.incrementAndGet();
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IMainModelListener#mainModelStateChanged(org.gamegineer.table.internal.ui.model.MainModelEvent)
     */
    @Override
    public void mainModelStateChanged(
        final MainModelEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        mainModelStateChangedEventCount_.incrementAndGet();
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IMainModelListener#tableClosed(org.gamegineer.table.internal.ui.model.MainModelContentChangedEvent)
     */
    @Override
    public void tableClosed(
        final MainModelContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        tableClosedEventCount_.incrementAndGet();
    }

    /*
     * @see org.gamegineer.table.internal.ui.model.IMainModelListener#tableOpened(org.gamegineer.table.internal.ui.model.MainModelContentChangedEvent)
     */
    @Override
    public void tableOpened(
        final MainModelContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$

        tableOpenedEventCount_.incrementAndGet();
    }
}
