/*
 * ContainerLayoutId.java
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
 * Created on Aug 8, 2012 at 7:46:52 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.Immutable;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Identifies a container layout.
 */
@Immutable
public final class ContainerLayoutId
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
     * Initializes a new instance of the {@code ContainerLayoutId} class.
     * 
     * @param id
     *        The underlying identifier.
     */
    private ContainerLayoutId(
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
        final @Nullable Object obj )
    {
        if( this == obj )
        {
            return true;
        }

        if( !(obj instanceof ContainerLayoutId) )
        {
            return false;
        }

        final ContainerLayoutId other = (ContainerLayoutId)obj;
        return id_.equals( other.id_ );
    }

    /**
     * Creates a new instance of the {@code ContainerLayoutId} class from the
     * specified string representation of the identifier.
     * 
     * <p>
     * The string representation of an identifier has no specific format.
     * </p>
     * 
     * @param id
     *        The string representation of the identifier.
     * 
     * @return A new instance of the {@code ContainerLayoutId} class.
     */
    public static ContainerLayoutId fromString(
        final String id )
    {
        return new ContainerLayoutId( id );
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
     * @return The string representation of this identifier.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return id_;
    }
}
