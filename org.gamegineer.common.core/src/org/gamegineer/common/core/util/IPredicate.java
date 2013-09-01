/*
 * IPredicate.java
 * Copyright 2008-2009 Gamegineer contributors and others.
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
 * Created on Oct 21, 2009 at 11:40:51 PM.
 */

package org.gamegineer.common.core.util;

/**
 * Transforms an object into a boolean result based on some conditions.
 * 
 * @param <T>
 *        The type of object evaluated by the predicate.
 */
public interface IPredicate<T>
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Evaluates the specified object according to the conditions of this
     * predicate.
     * 
     * @param obj
     *        The object to evaluate; may be {@code null}.
     * 
     * @return {@code true} if the specified object satisfies the conditions of
     *         this predicate; otherwise {@code false}.
     */
    public boolean evaluate(
        /* @Nullable */
        T obj );
}
