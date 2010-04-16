/*
 * MainModelContentChangedEvent.java
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
 * Created on Apr 14, 2010 at 10:43:52 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;

/**
 * An event used to notify listeners that the content of a main model has
 * changed.
 */
@ThreadSafe
public final class MainModelContentChangedEvent
    extends MainModelEvent
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 671072553737823444L;

    /** The table model associated with the event. */
    private final TableModel tableModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainModelContentChangedEvent}
     * class.
     * 
     * @param source
     *        The main model that fired the event; must not be {@code null}.
     * @param tableModel
     *        The table model associated with the event; must not be {@code
     *        null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code source} is {@code null}.
     * @throws java.lang.NullPointerException
     *         If {@code tableModel} is {@code null}.
     */
    public MainModelContentChangedEvent(
        /* @NonNull */
        @SuppressWarnings( "hiding" )
        final MainModel source,
        /* @NonNull */
        final TableModel tableModel )
    {
        super( source );

        assertArgumentNotNull( tableModel, "tableModel" ); //$NON-NLS-1$

        tableModel_ = tableModel;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the table model associated with the event.
     * 
     * @return The table model associated with the event; never {@code null}.
     */
    /* @NonNull */
    public TableModel getTableModel()
    {
        return tableModel_;
    }
}
