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

package org.gamegineer.table.internal.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentSurfaceDesign;
import org.gamegineer.table.core.ComponentSurfaceDesignId;

/**
 * A collection of common component surface designs.
 */
@ThreadSafe
public final class ComponentSurfaceDesigns
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default card surface design. */
    public static final ComponentSurfaceDesign DEFAULT_CARD = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.cardSurfaceDesigns.default" ), 96, 71 ); //$NON-NLS-1$

    /** The default card pile surface design. */
    public static final ComponentSurfaceDesign DEFAULT_CARD_PILE = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.cardPileSurfaceDesigns.default" ), 96, 71 ); //$NON-NLS-1$

    /** The default tabletop surface design. */
    public static final ComponentSurfaceDesign DEFAULT_TABLETOP = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.tabletopSurfaceDesigns.default" ), Short.MAX_VALUE, Short.MAX_VALUE ); //$NON-NLS-1$

    /** The null component surface design. */
    public static final ComponentSurfaceDesign NULL = new ComponentSurfaceDesign( ComponentSurfaceDesignId.fromString( "org.gamegineer.componentSurfaceDesigns.null" ), 0, 0 ); //$NON-NLS-1$


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
