/*
 * TableModelListener.java
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
 * Created on Aug 3, 2011 at 9:00:55 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import net.jcip.annotations.Immutable;

/**
 * Default implementation of {@link ITableModelListener}.
 * 
 * <p>
 * All methods of this class do nothing.
 * </p>
 */
@Immutable
public class TableModelListener
    implements ITableModelListener
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableModelListener} class.
     */
    public TableModelListener()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.ITableModelListener#tableChanged(org.gamegineer.table.internal.ui.impl.model.TableModelEvent)
     */
    @Override
    public void tableChanged(
        @SuppressWarnings( "unused" )
        final TableModelEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.ITableModelListener#tableModelDirtyFlagChanged(org.gamegineer.table.internal.ui.impl.model.TableModelEvent)
     */
    @Override
    public void tableModelDirtyFlagChanged(
        @SuppressWarnings( "unused" )
        final TableModelEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.ITableModelListener#tableModelFileChanged(org.gamegineer.table.internal.ui.impl.model.TableModelEvent)
     */
    @Override
    public void tableModelFileChanged(
        @SuppressWarnings( "unused" )
        final TableModelEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.ITableModelListener#tableModelFocusChanged(org.gamegineer.table.internal.ui.impl.model.TableModelEvent)
     */
    @Override
    public void tableModelFocusChanged(
        @SuppressWarnings( "unused" )
        final TableModelEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.ITableModelListener#tableModelHoverChanged(org.gamegineer.table.internal.ui.impl.model.TableModelEvent)
     */
    @Override
    public void tableModelHoverChanged(
        @SuppressWarnings( "unused" )
        final TableModelEvent event )
    {
        // do nothing
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.internal.ui.impl.model.ITableModelListener#tableModelOriginOffsetChanged(org.gamegineer.table.internal.ui.impl.model.TableModelEvent)
     */
    @Override
    public void tableModelOriginOffsetChanged(
        @SuppressWarnings( "unused" )
        final TableModelEvent event )
    {
        // do nothing
    }
}
