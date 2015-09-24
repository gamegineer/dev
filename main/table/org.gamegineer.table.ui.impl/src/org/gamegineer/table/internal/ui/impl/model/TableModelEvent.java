/*
 * TableModelEvent.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Dec 28, 2009 at 9:01:48 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import java.util.EventObject;
import net.jcip.annotations.ThreadSafe;

/**
 * An event fired by a table model.
 */
@ThreadSafe
public final class TableModelEvent
    extends EventObject
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -7540911812153989528L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableModelEvent} class.
     * 
     * @param source
     *        The table model that fired the event.
     */
    public TableModelEvent(
        final TableModel source )
    {
        super( source );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the table model that fired the event.
     * 
     * @return The table model that fired the event.
     */
    public TableModel getTableModel()
    {
        return (TableModel)getSource();
    }
}
