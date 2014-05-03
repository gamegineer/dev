/*
 * MainModelListener.java
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
 * Created on Aug 3, 2011 at 9:09:04 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import net.jcip.annotations.Immutable;

/**
 * Default implementation of {@link IMainModelListener}.
 * 
 * <p>
 * All methods of this class do nothing.
 * </p>
 */
@Immutable
public class MainModelListener
    implements IMainModelListener
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MainModelListener} class.
     */
    public MainModelListener()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.IMainModelListener#mainModelStateChanged(org.gamegineer.table.internal.ui.impl.model.MainModelEvent)
     */
    @Override
    public void mainModelStateChanged(
        @SuppressWarnings( "unused" )
        final MainModelEvent event )
    {
        // do nothing
    }
}
