/*
 * ComponentListener.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Mar 27, 2012 at 8:49:54 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.Immutable;

/**
 * Default implementation of {@link IComponentListener}.
 * 
 * <p>
 * All methods of this class do nothing.
 * </p>
 */
@Immutable
public class ComponentListener
    implements IComponentListener
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentListener} class.
     */
    public ComponentListener()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.core.IComponentListener#componentBoundsChanged(org.gamegineer.table.core.ComponentEvent)
     */
    @Override
    public void componentBoundsChanged(
        @SuppressWarnings( "unused" )
        final ComponentEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.core.IComponentListener#componentOrientationChanged(org.gamegineer.table.core.ComponentEvent)
     */
    @Override
    public void componentOrientationChanged(
        @SuppressWarnings( "unused" )
        final ComponentEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.core.IComponentListener#componentSurfaceDesignChanged(org.gamegineer.table.core.ComponentEvent)
     */
    @Override
    public void componentSurfaceDesignChanged(
        @SuppressWarnings( "unused" )
        final ComponentEvent event )
    {
        // do nothing
    }
}
