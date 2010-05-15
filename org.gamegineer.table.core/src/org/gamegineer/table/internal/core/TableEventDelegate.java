/*
 * TableEventDelegate.java
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
 * Created on Oct 16, 2009 at 10:53:06 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.ITableEvent;

/**
 * An implementation of {@link org.gamegineer.table.core.ITableEvent} to which
 * implementations of {@link org.gamegineer.table.core.TableEvent} can delegate
 * their behavior.
 */
@Immutable
class TableEventDelegate
    implements ITableEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table that fired the event. */
    private final ITable table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableEventDelegate} class.
     * 
     * @param table
     *        The table that fired the event; must not be {@code null}.
     */
    TableEventDelegate(
        /* @NonNull */
        final ITable table )
    {
        assert table != null;

        table_ = table;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.ITableEvent#getTable()
     */
    @Override
    public final ITable getTable()
    {
        return table_;
    }
}
