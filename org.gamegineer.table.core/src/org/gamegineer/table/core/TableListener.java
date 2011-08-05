/*
 * TableListener.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Aug 3, 2011 at 8:39:38 PM.
 */

package org.gamegineer.table.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;

/**
 * Default implementation of {@link ITableListener}.
 * 
 * <p>
 * All methods of this class do nothing.
 * </p>
 */
@Immutable
public class TableListener
    implements ITableListener
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableListener} class.
     */
    public TableListener()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.core.ITableListener#cardPileAdded(org.gamegineer.table.core.TableContentChangedEvent)
     */
    @Override
    public void cardPileAdded(
        final TableContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }

    /**
     * This implementation does nothing.
     * 
     * @see org.gamegineer.table.core.ITableListener#cardPileRemoved(org.gamegineer.table.core.TableContentChangedEvent)
     */
    @Override
    public void cardPileRemoved(
        final TableContentChangedEvent event )
    {
        assertArgumentNotNull( event, "event" ); //$NON-NLS-1$
    }
}
