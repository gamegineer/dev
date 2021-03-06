/*
 * ComparableUtils.java
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
 * Created on Jan 17, 2013 at 11:30:40 PM.
 */

package org.gamegineer.common.core.util;

import java.util.Comparator;
import java.util.Objects;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A collection of useful methods for working with instances of
 * {@link Comparable}.
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
     *        The first object to compare.
     * @param obj2
     *        The second object to compare.
     * 
     * @return A negative integer if the first object is less than the second
     *         object; a positive integer if the first object is greater than
     *         the second object; or zero if the first object is equal to the
     *         second object.
     */
    public static <T extends Comparable<T>> int compareTo(
        final @Nullable T obj1,
        final @Nullable T obj2 )
    {
        return Objects.compare( obj1, obj2, Comparator.nullsFirst( Comparator.<@Nullable T>naturalOrder() ) );
    }
}
