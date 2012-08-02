/*
 * NullContainerStrategy.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Aug 1, 2012 at 10:47:51 PM.
 */

package org.gamegineer.table.internal.core;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.core.IContainerLayout;
import org.gamegineer.table.core.IContainerStrategy;
import org.gamegineer.table.core.TabletopLayouts;

/**
 * A null container strategy.
 */
@Immutable
public final class NullContainerStrategy
    extends NullComponentStrategy
    implements IContainerStrategy
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullContainerStrategy} class.
     */
    public NullContainerStrategy()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.core.IContainerStrategy#getDefaultLayout()
     */
    @Override
    public IContainerLayout getDefaultLayout()
    {
        // TODO: absolute layout should be a "common" layout not necessarily associated with tabletops
        return TabletopLayouts.ABSOLUTE;
    }

    /*
     * @see org.gamegineer.table.internal.core.NullComponentStrategy#isFocusable()
     */
    @Override
    public boolean isFocusable()
    {
        return true;
    }
}
