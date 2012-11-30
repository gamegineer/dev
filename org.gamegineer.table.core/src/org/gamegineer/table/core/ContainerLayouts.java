/*
 * ContainerLayouts.java
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
 * Created on Aug 11, 2012 at 9:52:18 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.internal.core.layouts.InternalContainerLayouts;

/**
 * A collection of common container layouts.
 */
@ThreadSafe
public final class ContainerLayouts
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * A layout in which the container is laid out with all components at their
     * absolute position in table coordinates.
     */
    public static final IContainerLayout ABSOLUTE = InternalContainerLayouts.ABSOLUTE;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ContainerLayouts}.
     */
    private ContainerLayouts()
    {
    }
}
