/*
 * ITableModelListener.java
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
 * Created on Dec 28, 2009 at 9:00:54 PM.
 */

package org.gamegineer.table.internal.ui.model;

import java.util.EventListener;

/**
 * The listener interface for use by clients to be notified of changes to the
 * table model state.
 */
public interface ITableModelListener
    extends EventListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the focused card pile on the table has changed.
     * 
     * @param event
     *        The event describing the focus change; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void cardPileFocusChanged(
        /* @NonNull */
        TableModelEvent event );

    /**
     * Invoked after the table model state has changed.
     * 
     * @param event
     *        The event describing the table model; must not be {@code null} .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void tableModelStateChanged(
        /* @NonNull */
        TableModelEvent event );

    /**
     * Invoked after the table origin offset has changed.
     * 
     * @param event
     *        The event describing the origin offset change; must not be {@code
     *        null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void tableOriginOffsetChanged(
        /* @NonNull */
        TableModelEvent event );
}
