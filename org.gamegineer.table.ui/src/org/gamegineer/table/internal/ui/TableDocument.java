/*
 * TableDocument.java
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
 * Created on Oct 6, 2009 at 11:17:00 PM.
 */

package org.gamegineer.table.internal.ui;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ITable;
import org.gamegineer.table.core.TableFactory;

/**
 * A document in the table application.
 */
@ThreadSafe
final class TableDocument
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table associated with this document. */
    private final ITable table_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableDocument} class.
     */
    TableDocument()
    {
        table_ = TableFactory.createTable();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the table associated with this document.
     * 
     * @return The table associated with this document; never {@code null}.
     */
    /* @NonNull */
    ITable getTable()
    {
        return table_;
    }
}
