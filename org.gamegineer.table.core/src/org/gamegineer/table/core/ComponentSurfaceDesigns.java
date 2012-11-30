/*
 * ComponentSurfaceDesigns.java
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
 * Created on Aug 16, 2012 at 9:38:38 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;

/**
 * A collection of common component surface designs.
 */
@ThreadSafe
public final class ComponentSurfaceDesigns
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The null component surface design. */
    public static final ComponentSurfaceDesign NULL = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.table.componentSurfaceDesigns.null" ), 0, 0 ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentSurfaceDesigns} class.
     */
    private ComponentSurfaceDesigns()
    {
    }
}
