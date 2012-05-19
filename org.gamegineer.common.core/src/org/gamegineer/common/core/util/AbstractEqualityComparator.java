/*
 * AbstractEqualityComparator.java
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
 * Created on May 18, 2012 at 10:14:39 PM.
 */

package org.gamegineer.common.core.util;

import net.jcip.annotations.Immutable;

/**
 * Superclass for all implementations of {@link IEqualityComparator}.
 * 
 * @param <T>
 *        The type of the object to be compared.
 */
@Immutable
public abstract class AbstractEqualityComparator<T>
    implements IEqualityComparator<T>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractEqualityComparator}
     * class.
     */
    protected AbstractEqualityComparator()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.common.core.util.IEqualityComparator#equals(java.lang.Object, java.lang.Object)
     */
    @Override
    public final boolean equals(
        final T obj1,
        final T obj2 )
    {
        if( obj1 == null )
        {
            return obj2 == null;
        }
        else if( obj2 == null )
        {
            return false;
        }

        return equalsInternal( obj1, obj2 );
    }

    /**
     * Indicates the specified objects are equal.
     * 
     * @param obj1
     *        The first object to compare; must not be {@code null}.
     * @param obj2
     *        The second object to compare; must not be {@code null}.
     * 
     * @return {@code true} if the specified objects are equal; otherwise
     *         {@code false}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code obj1} or {@code obj2} is {@code null}.
     */
    protected abstract boolean equalsInternal(
        /* @NonNull */
        T obj1,
        /* @NonNull */
        T obj2 );

    /*
     * @see org.gamegineer.common.core.util.IEqualityComparator#hashCode(java.lang.Object)
     */
    @Override
    public final int hashCode(
        final T obj )
    {
        if( obj == null )
        {
            return 0;
        }

        return hashCodeInternal( obj );
    }

    /**
     * Gets a hash code for the specified object.
     * 
     * @param obj
     *        The object for which a hash code is to be calculated; must not be
     *        {@code null}.
     * 
     * @return A hash code for the specified object.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code obj} is {@code null}.
     */
    protected abstract int hashCodeInternal(
        /* @NonNull */
        T obj );
}
