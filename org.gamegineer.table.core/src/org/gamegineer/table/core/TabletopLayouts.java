/*
 * TabletopLayouts.java
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
 * Created on Jul 4, 2012 at 7:49:48 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.AbsoluteContainerLayout;

/**
 * A collection of layouts for tabletops.
 */
@ThreadSafe
public final class TabletopLayouts
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * Indicates the tabletop is laid out with all components at their absolute
     * position in table coordinates.
     */
    public static final IContainerLayout ABSOLUTE = new AbsoluteContainerLayout();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TabletopLayouts} class.
     */
    private TabletopLayouts()
    {
    }
}
