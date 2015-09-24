/*
 * ComponentModelVector.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Jun 4, 2013 at 8:13:26 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import net.jcip.annotations.Immutable;

/**
 * A direction relative to a specific component model.
 */
@Immutable
public final class ComponentModelVector
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component axis along which the vector is directed. */
    private final ComponentAxis direction_;

    /** The component model that represents the vector origin. */
    private final ComponentModel origin_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentModelVector} class.
     * 
     * @param origin
     *        The component model that represents the vector origin.
     * @param direction
     *        The component axis along which the vector is directed.
     */
    public ComponentModelVector(
        final ComponentModel origin,
        final ComponentAxis direction )
    {
        direction_ = direction;
        origin_ = origin;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component axis along which the vector is directed.
     * 
     * @return The component axis along which the vector is directed.
     */
    public ComponentAxis getDirection()
    {
        return direction_;
    }

    /**
     * Gets the component model that represents the vector origin.
     * 
     * @return The component model that represents the vector origin.
     */
    public ComponentModel getOrigin()
    {
        return origin_;
    }
}
