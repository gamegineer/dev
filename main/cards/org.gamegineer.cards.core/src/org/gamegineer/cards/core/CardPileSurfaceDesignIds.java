/*
 * CardPileSurfaceDesignIds.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Dec 1, 2012 at 8:34:55 PM.
 */

package org.gamegineer.cards.core;

import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.core.ComponentSurfaceDesignId;

/**
 * A collection of card pile surface design identifiers published by this
 * bundle.
 */
@ThreadSafe
public final class CardPileSurfaceDesignIds
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The base card pile surface design identifier. */
    public static final ComponentSurfaceDesignId BASE = ComponentSurfaceDesignId.fromString( "org.gamegineer.cards.cardPileSurfaceDesigns.base" ); //$NON-NLS-1$

    /** The default card pile surface design identifier. */
    public static final ComponentSurfaceDesignId DEFAULT = ComponentSurfaceDesignId.fromString( "org.gamegineer.cards.cardPileSurfaceDesigns.default" ); //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code CardPileSurfaceDesignIds} class.
     */
    private CardPileSurfaceDesignIds()
    {
    }
}
