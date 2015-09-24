/*
 * IDragStrategy.java
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
 * Created on Mar 8, 2013 at 9:28:56 PM.
 */

package org.gamegineer.table.core.dnd;

import java.util.List;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;

/**
 * A strategy for customizing the behavior of a drag-and-drop operation.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IDragStrategy
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates the components being dragged can be dropped on the specified
     * container.
     * 
     * @param dropContainer
     *        The drop container.
     * 
     * @return {@code true} if the components being dragged on the specified
     *         container; otherwise {@code false}.
     */
    public boolean canDrop(
        IContainer dropContainer );

    /**
     * Gets the collection of components to be dragged.
     * 
     * @return The collection of components to be dragged. An empty collection
     *         indicates no drag-and-drop operation should occur.
     */
    public List<IComponent> getDragComponents();
}
