/*
 * IMainModelListener.java
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
 * Created on Apr 13, 2010 at 9:59:18 PM.
 */

package org.gamegineer.table.internal.ui.model;

/**
 * The listener interface for use by clients to be notified of changes to the
 * main model state.
 */
public interface IMainModelListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the main model dirty flag has changed.
     * 
     * @param event
     *        The event describing the main model; must not be {@code null} .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void mainModelDirtyFlagChanged(
        /* @NonNull */
        MainModelEvent event );

    /**
     * Invoked after the main model file name has changed.
     * 
     * @param event
     *        The event describing the main model; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void mainModelFileNameChanged(
        /* @NonNull */
        MainModelEvent event );

    /**
     * Invoked after the main model state has changed.
     * 
     * @param event
     *        The event describing the main model; must not be {@code null} .
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void mainModelStateChanged(
        /* @NonNull */
        MainModelEvent event );

    /**
     * Invoked after a table has been closed.
     * 
     * @param event
     *        The event describing the closed table; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void tableClosed(
        /* @NonNull */
        MainModelContentChangedEvent event );

    /**
     * Invoked after a table has been opened.
     * 
     * @param event
     *        The event describing the opened table; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void tableOpened(
        /* @NonNull */
        MainModelContentChangedEvent event );
}
