/*
 * IContainerModelListener.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jan 26, 2010 at 10:41:39 PM.
 */

package org.gamegineer.table.internal.ui.model;

import java.util.EventListener;

/**
 * The listener interface for use by clients to be notified of changes to the
 * container model state.
 */
public interface IContainerModelListener
    extends EventListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the container associated with the model has changed.
     * 
     * @param event
     *        The event describing the container model; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void containerChanged(
        /* @NonNull */
        ContainerModelEvent event );

    /**
     * Invoked after the container model has gained or lost the logical focus.
     * 
     * @param event
     *        The event describing the container model; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void containerModelFocusChanged(
        /* @NonNull */
        ContainerModelEvent event );
}
