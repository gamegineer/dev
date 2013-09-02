/*
 * ObjectUtils.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Jun 10, 2012 at 5:49:26 PM.
 */

package org.gamegineer.common.core.util;

import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful methods for working with instances of {@link Object}.
 */
@ThreadSafe
public final class ObjectUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ObjectUtils} class.
     */
    private ObjectUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates the specified objects are equal.
     * 
     * @param obj1
     *        The first object to be compared; may be {@code null}.
     * @param obj2
     *        The second object to be compared; may be {@code null}.
     * 
     * @return {@code true} if the specified objects are equal; otherwise
     *         {@code false}.
     */
    public static boolean equals(
        /* @Nullable */
        final Object obj1,
        /* @Nullable */
        final Object obj2 )
    {
        if( obj1 == null )
        {
            return obj2 == null;
        }
        else if( obj2 == null )
        {
            return false;
        }

        return obj1.equals( obj2 );
    }

    /**
     * Gets a hash code for the specified object.
     * 
     * @param obj
     *        The object for which a hash code is to be calculated; may be
     *        {@code null}.
     * 
     * @return A hash code for the specified object.
     */
    public static int hashCode(
        /* @Nullable */
        final Object obj )
    {
        if( obj == null )
        {
            return 0;
        }

        return obj.hashCode();
    }

    /**
     * Gets a string representation of the specified object.
     * 
     * @param obj
     *        The object for which a string representation is desired; may be
     *        {@code null}.
     * 
     * @return A string representation of the specified object; never
     *         {@code null}.
     */
    /* @NonNull */
    public static String toString(
        /* @Nullable */
        final Object obj )
    {
        if( obj == null )
        {
            return "(null)"; //$NON-NLS-1$
        }

        return obj.toString();
    }
}
