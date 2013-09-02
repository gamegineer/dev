/*
 * IContainerListener.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Mar 29, 2012 at 8:17:29 PM.
 */

package org.gamegineer.table.core;

import java.util.EventListener;

/**
 * The listener interface for use by clients to be notified of changes to the
 * container state.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IContainerListener
    extends EventListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after a component has been added to the container.
     * 
     * @param event
     *        The event describing the added component; must not be {@code null}
     *        .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void componentAdded(
        /* @NonNull */
        ContainerContentChangedEvent event );

    /**
     * Invoked after a component has been removed from the container.
     * 
     * @param event
     *        The event describing the removed component; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void componentRemoved(
        /* @NonNull */
        ContainerContentChangedEvent event );

    /**
     * Invoked after the container layout has changed.
     * 
     * @param event
     *        The event describing the container; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void containerLayoutChanged(
        /* @NonNull */
        ContainerEvent event );
}
