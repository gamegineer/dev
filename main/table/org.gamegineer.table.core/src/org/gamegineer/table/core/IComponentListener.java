/*
 * IComponentListener.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Mar 27, 2012 at 8:41:47 PM.
 */

package org.gamegineer.table.core;

import java.util.EventListener;

/**
 * The listener interface for use by clients to be notified of changes to the
 * component state.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IComponentListener
    extends EventListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the component bounds have changed.
     * 
     * @param event
     *        The event describing the component; must not be {@code null}.
     */
    public void componentBoundsChanged(
        ComponentEvent event );

    /**
     * Invoked after the component orientation has changed.
     * 
     * @param event
     *        The event describing the component; must not be {@code null}.
     */
    public void componentOrientationChanged(
        ComponentEvent event );

    /**
     * Invoked after a component surface design has changed.
     * 
     * @param event
     *        The event describing the component; must not be {@code null}.
     */
    public void componentSurfaceDesignChanged(
        ComponentEvent event );
}
