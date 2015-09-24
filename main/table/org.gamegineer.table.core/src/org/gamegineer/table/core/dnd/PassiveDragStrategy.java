/*
 * PassiveDragStrategy.java
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
 * Created on Jun 27, 2013 at 11:39:23 PM.
 */

package org.gamegineer.table.core.dnd;

import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;

/**
 * Implementation of {@link IDragStrategy} that only delegates all operations to
 * its successor drag strategy.
 */
@Immutable
public final class PassiveDragStrategy
    implements IDragStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The successor drag strategy. */
    private final IDragStrategy successorDragStrategy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PassiveDragStrategy} class.
     * 
     * @param successorDragStrategy
     *        The successor drag strategy.
     */
    public PassiveDragStrategy(
        final IDragStrategy successorDragStrategy )
    {
        successorDragStrategy_ = successorDragStrategy;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.dnd.IDragStrategy#canDrop(org.gamegineer.table.core.IContainer)
     */
    @Override
    public boolean canDrop(
        final IContainer dropContainer )
    {
        return successorDragStrategy_.canDrop( dropContainer );
    }

    /*
     * @see org.gamegineer.table.core.dnd.IDragStrategy#getDragComponents()
     */
    @Override
    public List<IComponent> getDragComponents()
    {
        return successorDragStrategy_.getDragComponents();
    }
}
