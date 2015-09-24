/*
 * IDragSource.java
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
 * Created on Mar 16, 2013 at 8:01:36 PM.
 */

package org.gamegineer.table.core.dnd;

import java.awt.Point;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.core.IComponent;

/**
 * A table extension that initiates a drag-and-drop operation.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IDragSource
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Begins a drag-and-drop operation in the associated table.
     * 
     * @param location
     *        The beginning drag location in table coordinates.
     * @param component
     *        The component from which the drag-and-drop operation will begin.
     * @param dragStrategyFactory
     *        A factory for creating the drag strategy for the drag-and-drop
     *        operation.
     * 
     * @return A context defining the new drag-and-drop operation or
     *         {@code null} if a drag-and-drop operation is not possible for the
     *         specified arguments.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code component} does not exist in the associated table or
     *         {@code component} has no container (i.e. {@code component} is the
     *         tabletop).
     * @throws java.lang.IllegalStateException
     *         If there is an active drag-and-drop operation in the associated
     *         table.
     */
    public @Nullable IDragContext beginDrag(
        Point location,
        IComponent component,
        IDragStrategyFactory dragStrategyFactory );
}
