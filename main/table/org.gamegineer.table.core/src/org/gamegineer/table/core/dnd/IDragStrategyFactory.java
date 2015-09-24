/*
 * IDragStrategyFactory.java
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
 * Created on Mar 9, 2013 at 8:44:19 PM.
 */

package org.gamegineer.table.core.dnd;

import org.gamegineer.table.core.IComponent;

/**
 * A factory for creating instances of {@link IDragStrategy}.
 */
public interface IDragStrategyFactory
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new drag strategy for a drag-and-drop operation that begins
     * with the specified component.
     * 
     * @param component
     *        The component from which the drag-and-drop operation will begin.
     * @param successorDragStrategy
     *        The successor drag strategy to which requests are forwarded if
     *        they cannot be handled.
     * 
     * @return A new drag strategy.
     */
    public IDragStrategy createDragStrategy(
        IComponent component,
        IDragStrategy successorDragStrategy );
}
