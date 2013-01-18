/*
 * ComparableUtils.java
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
 * Created on Jan 17, 2013 at 11:30:40 PM.
 */

package org.gamegineer.common.core.util;

import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful methods for working with instances of
 * {@link java.lang.Comparable}.
 */
@ThreadSafe
public final class ComparableUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComparableUtils} class.
     */
    private ComparableUtils()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Compares two objects.
     * 
     * @param <T>
     *        The type of the objects to compare.
     * 
     * @param obj1
     *        The first object to compare; may be {@code null}.
     * @param obj2
     *        The second object to compare; may be {@code null}.
     * 
     * @return A negative integer if the first object is less than the second
     *         object; a positive integer if the first object is greater than
     *         the second object; or zero if the first object is equal to the
     *         second object.
     */
    public static <T extends Comparable<T>> int compareTo(
        /* @Nullable */
        final T obj1,
        /* @Nullable */
        final T obj2 )
    {
        if( obj1 == null )
        {
            return (obj2 == null) ? 0 : -1;
        }
        else if( obj2 == null )
        {
            return +1;
        }

        return obj1.compareTo( obj2 );
    }
}
