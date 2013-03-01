/*
 * IDragContext.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Feb 18, 2013 at 8:34:08 PM.
 */

package org.gamegineer.table.core;

import java.awt.Point;

/**
 * The context for a table drag-and-drop operation.
 * 
 * @noextend This interface is not intended to be extended by clients.
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IDragContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Cancels the drag-and-drop operation.
     * 
     * @throws java.lang.IllegalStateException
     *         If the drag-and-drop operation is not active.
     */
    public void cancel();

    /**
     * Continues the drag-and-drop operation.
     * 
     * @param location
     *        The new drag location in table coordinates; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the drag-and-drop operation is not active.
     * @throws java.lang.NullPointerException
     *         If {@code location} is {@code null}.
     */
    public void drag(
        /* @NonNull */
        Point location );

    /**
     * Ends the drag-and-drop operation.
     * 
     * @param location
     *        The ending drag location in table coordinates; must not be
     *        {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the drag-and-drop operation is not active.
     * @throws java.lang.NullPointerException
     *         If {@code location} is {@code null}.
     */
    public void drop(
        /* @NonNull */
        Point location );
}
