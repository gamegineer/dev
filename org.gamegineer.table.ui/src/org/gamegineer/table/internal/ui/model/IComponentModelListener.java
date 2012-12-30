/*
 * IComponentModelListener.java
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
 * Created on Dec 25, 2009 at 9:31:37 PM.
 */

package org.gamegineer.table.internal.ui.model;

import java.util.EventListener;

/**
 * The listener interface for use by clients to be notified of changes to the
 * component model state.
 */
public interface IComponentModelListener
    extends EventListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the bounds of the component associated with the model have
     * changed.
     * 
     * @param event
     *        The event describing the component model; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void componentBoundsChanged(
        /* @NonNull */
        ComponentModelEvent event );

    /**
     * Invoked after any attribute of the component associated with the model
     * has changed.
     * 
     * @param event
     *        The event describing the component model; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void componentChanged(
        /* @NonNull */
        ComponentModelEvent event );

    /**
     * Invoked after the component model has gained or lost the logical focus.
     * 
     * @param event
     *        The event describing the component model; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void componentModelFocusChanged(
        /* @NonNull */
        ComponentModelEvent event );

    /**
     * Invoked after the component model has gained or lost the logical hover.
     * 
     * @param event
     *        The event describing the component model; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void componentModelHoverChanged(
        /* @NonNull */
        ComponentModelEvent event );

    /**
     * Invoked after the orientation of the component associated with the model
     * has changed.
     * 
     * @param event
     *        The event describing the component model; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void componentOrientationChanged(
        /* @NonNull */
        ComponentModelEvent event );

    /**
     * Invoked after a surface design of the component associated with the model
     * has changed.
     * 
     * @param event
     *        The event describing the component model; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void componentSurfaceDesignChanged(
        /* @NonNull */
        ComponentModelEvent event );
}
