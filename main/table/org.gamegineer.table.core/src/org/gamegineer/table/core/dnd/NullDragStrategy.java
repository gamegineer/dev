/*
 * NullDragStrategy.java
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
 * Created on Mar 16, 2013 at 9:46:11 PM.
 */

package org.gamegineer.table.core.dnd;

import java.util.Collections;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.NonNull;
import org.gamegineer.table.core.IComponent;
import org.gamegineer.table.core.IContainer;

/**
 * Null implementation of {@link IDragStrategy} that suppresses drag-and-drop.
 */
@Immutable
public final class NullDragStrategy
    implements IDragStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The singleton instance of this class. */
    public static final NullDragStrategy INSTANCE = new NullDragStrategy();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullDragStrategy} class.
     */
    private NullDragStrategy()
    {
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
        return false;
    }

    /*
     * @see org.gamegineer.table.core.dnd.IDragStrategy#getDragComponents()
     */
    @Override
    public List<IComponent> getDragComponents()
    {
        return Collections.<@NonNull IComponent>emptyList();
    }
}
