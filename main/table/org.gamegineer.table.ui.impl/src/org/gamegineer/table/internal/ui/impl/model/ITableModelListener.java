/*
 * ITableModelListener.java
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
 * Created on Dec 28, 2009 at 9:00:54 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

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
     * Invoked after the table or table network associated with the model has
     * changed.
     * 
     * @param event
     *        The event describing the table model.
     */
    public void tableChanged(
        TableModelEvent event );

    /**
     * Invoked after the table model dirty flag has changed.
     * 
     * @param event
     *        The event describing the table model.
     */
    public void tableModelDirtyFlagChanged(
        TableModelEvent event );

    /**
     * Invoked after the table model file has changed.
     * 
     * @param event
     *        The event describing the table model.
     */
    public void tableModelFileChanged(
        TableModelEvent event );

    /**
     * Invoked after the table model focus has changed.
     * 
     * @param event
     *        The event describing the table model.
     */
    public void tableModelFocusChanged(
        TableModelEvent event );

    /**
     * Invoked after the table model hover has changed.
     * 
     * @param event
     *        The event describing the table model.
     */
    public void tableModelHoverChanged(
        TableModelEvent event );

    /**
     * Invoked after the table model origin offset has changed.
     * 
     * @param event
     *        The event describing the table model.
     */
    public void tableModelOriginOffsetChanged(
        TableModelEvent event );
}
