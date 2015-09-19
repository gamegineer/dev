/*
 * IEqualityComparator.java
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
 * Created on May 17, 2012 at 8:23:46 PM.
 */

package org.gamegineer.common.core.util;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Defines an algorithm for the comparison of objects for equality.
 * 
 * @param <T>
 *        The type of the object to be compared.
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IEqualityComparator<T>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Indicates the specified objects are equal.
     * 
     * @param obj1
     *        The first object to compare; may be {@code null}.
     * @param obj2
     *        The second object to compare; may be {@code null}.
     * 
     * @return {@code true} if the specified objects are equal; otherwise
     *         {@code false}.
     * 
     * @throws java.lang.ClassCastException
     *         If the type of one or both of the objects prevents them from
     *         being compared by this comparator.
     */
    public boolean equals(
        @Nullable T obj1,
        @Nullable T obj2 );

    /**
     * Gets a hash code for the specified object.
     * 
     * @param obj
     *        The object for which a hash code is to be calculated; may be
     *        {@code null}.
     * 
     * @return A hash code for the specified object.
     * 
     * @throws java.lang.ClassCastException
     *         If the type of the object prevents it from being compared by this
     *         comparator.
     */
    public int hashCode(
        @Nullable T obj );
}
