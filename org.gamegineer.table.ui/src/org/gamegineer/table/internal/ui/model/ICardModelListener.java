/*
 * ICardModelListener.java
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
 * Created on Dec 25, 2009 at 9:31:37 PM.
 */

package org.gamegineer.table.internal.ui.model;

import java.util.EventListener;

/**
 * The listener interface for use by clients to be notified of changes to the
 * card model state.
 */
public interface ICardModelListener
    extends EventListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked after the card has gained the logical focus.
     * 
     * @param event
     *        The event describing the card; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void cardFocusGained(
        /* @NonNull */
        CardModelEvent event );

    /**
     * Invoked after the card has lost the logical focus.
     * 
     * @param event
     *        The event describing the card; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code event} is {@code null}.
     */
    public void cardFocusLost(
        /* @NonNull */
        CardModelEvent event );
}
