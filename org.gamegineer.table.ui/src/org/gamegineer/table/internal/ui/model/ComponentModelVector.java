/*
 * ComponentModelVector.java
 * Copyright 2008-2013 Gamegineer.org
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

package org.gamegineer.table.internal.ui.model;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
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
     *        The component model that represents the vector origin; must not be
     *        {@code null}.
     * @param direction
     *        The component axis along which the vector is directed; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code origin} or {@code axis} is {@code null}.
     */
    public ComponentModelVector(
        /* @NonNull */
        final ComponentModel origin,
        /* @NonNull */
        final ComponentAxis direction )
    {
        assertArgumentNotNull( origin, "origin" ); //$NON-NLS-1$
        assertArgumentNotNull( direction, "direction" ); //$NON-NLS-1$

        direction_ = direction;
        origin_ = origin;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the component axis along which the vector is directed.
     * 
     * @return The component axis along which the vector is directed; never
     *         {@code null}.
     */
    /* @NonNull */
    public ComponentAxis getDirection()
    {
        return direction_;
    }

    /**
     * Gets the component model that represents the vector origin.
     * 
     * @return The component model that represents the vector origin; never
     *         {@code null}.
     */
    /* @NonNull */
    public ComponentModel getOrigin()
    {
        return origin_;
    }
}
