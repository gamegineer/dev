/*
 * ComponentModelListener.java
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
 * Created on Aug 3, 2011 at 8:35:08 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import net.jcip.annotations.Immutable;

/**
 * Default implementation of {@link IComponentModelListener}.
 * 
 * <p>
 * All methods of this class do nothing.
 * </p>
 */
@Immutable
public class ComponentModelListener
    implements IComponentModelListener
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentModelListener} class.
     */
    public ComponentModelListener()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.IComponentModelListener#componentBoundsChanged(org.gamegineer.table.internal.ui.impl.model.ComponentModelEvent)
     */
    @Override
    public void componentBoundsChanged(
        final ComponentModelEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.IComponentModelListener#componentChanged(org.gamegineer.table.internal.ui.impl.model.ComponentModelEvent)
     */
    @Override
    public void componentChanged(
        final ComponentModelEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.IComponentModelListener#componentModelFocusChanged(org.gamegineer.table.internal.ui.impl.model.ComponentModelEvent)
     */
    @Override
    public void componentModelFocusChanged(
        final ComponentModelEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.IComponentModelListener#componentModelHoverChanged(org.gamegineer.table.internal.ui.impl.model.ComponentModelEvent)
     */
    @Override
    public void componentModelHoverChanged(
        final ComponentModelEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.IComponentModelListener#componentOrientationChanged(org.gamegineer.table.internal.ui.impl.model.ComponentModelEvent)
     */
    @Override
    public void componentOrientationChanged(
        final ComponentModelEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.IComponentModelListener#componentSurfaceDesignChanged(org.gamegineer.table.internal.ui.impl.model.ComponentModelEvent)
     */
    @Override
    public void componentSurfaceDesignChanged(
        final ComponentModelEvent event )
    {
        // do nothing
    }
}
