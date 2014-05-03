/*
 * ContainerModelListener.java
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
 * Created on Aug 3, 2011 at 8:46:12 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import net.jcip.annotations.Immutable;

/**
 * Default implementation of {@link IContainerModelListener}.
 * 
 * <p>
 * All methods of this class do nothing.
 * </p>
 */
@Immutable
public class ContainerModelListener
    implements IContainerModelListener
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerModelListener} class.
     */
    public ContainerModelListener()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.IContainerModelListener#componentModelAdded(org.gamegineer.table.internal.ui.impl.model.ContainerModelContentChangedEvent)
     */
    @Override
    public void componentModelAdded(
        @SuppressWarnings( "unused" )
        final ContainerModelContentChangedEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.IContainerModelListener#componentModelRemoved(org.gamegineer.table.internal.ui.impl.model.ContainerModelContentChangedEvent)
     */
    @Override
    public void componentModelRemoved(
        @SuppressWarnings( "unused" )
        final ContainerModelContentChangedEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.IContainerModelListener#containerLayoutChanged(org.gamegineer.table.internal.ui.impl.model.ContainerModelEvent)
     */
    @Override
    public void containerLayoutChanged(
        @SuppressWarnings( "unused" )
        final ContainerModelEvent event )
    {
        // do nothing
    }
}
