/*
 * ComponentSurfaceDesignId.java
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
 * Created on Apr 6, 2012 at 11:04:55 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Identifies the design of a component surface.
 */
@Immutable
public final class ComponentSurfaceDesignId
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The underlying identifier. */
    private final String id_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentSurfaceDesignId} class.
     * 
     * @param id
     *        The underlying identifier; must not be {@code null}.
     */
    private ComponentSurfaceDesignId(
        final String id )
    {
        id_ = id;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(
        @Nullable
        final Object obj )
    {
        if( this == obj )
        {
            return true;
        }

        if( !(obj instanceof ComponentSurfaceDesignId) )
        {
            return false;
        }

        final ComponentSurfaceDesignId other = (ComponentSurfaceDesignId)obj;
        return id_.equals( other.id_ );
    }

    /**
     * Creates a new instance of the {@code ComponentSurfaceDesignId} class from
     * the specified string representation of the identifier.
     * 
     * <p>
     * The string representation of an identifier has no specific format.
     * </p>
     * 
     * @param id
     *        The string representation of the identifier; must not be
     *        {@code null}.
     * 
     * @return A new instance of the {@code ComponentSurfaceDesignId} class;
     *         never {@code null}.
     */
    public static ComponentSurfaceDesignId fromString(
        final String id )
    {
        return new ComponentSurfaceDesignId( id );
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return id_.hashCode();
    }

    /**
     * Gets the string representation of this identifier.
     * 
     * <p>
     * The string representation of an identifier has no specific format.
     * </p>
     * 
     * @return The string representation of this identifier; never {@code null}.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return id_;
    }
}
