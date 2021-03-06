/*
 * IMainModelListener.java
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
 * Created on Apr 13, 2010 at 9:59:18 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import java.util.EventListener;

/**
 * The listener interface for use by clients to be notified of changes to the
 * main model state.
 */
public interface IMainModelListener
    extends EventListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the main model state has changed.
     * 
     * @param event
     *        The event describing the main model.
     */
    public void mainModelStateChanged(
        MainModelEvent event );
}
