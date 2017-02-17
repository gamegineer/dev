/*
 * NullAnalysis.java
 * Copyright 2008-2017 Gamegineer contributors and others.
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
 * Created on Mar 15, 2014 at 8:25:21 PM.
 */

package org.gamegineer.common.core.runtime;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * A collection of useful methods for working with the null analysis engine.
 */
@NonNullByDefault( {} )
@ThreadSafe
public final class NullAnalysis
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullAnalysis} class.
     */
    private NullAnalysis()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Casts the specified non-{@code null} value to a non-{@code null} value of
     * the specified type.
     * 
     * @param <T>
     *        The type of the cast value.
     * 
     * @param type
     *        The type to which the value is to be cast.
     * @param value
     *        The value.
     * 
     * @return The cast value.
     */
    public static <T> @NonNull T nonNull(
        final @NonNull Class<T> type,
        final @NonNull Object value )
    {
        final T castValue = type.cast( value );
        assert castValue != null;
        return castValue;
    }
}
