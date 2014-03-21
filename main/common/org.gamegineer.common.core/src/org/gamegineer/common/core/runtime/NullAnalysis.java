/*
 * NullAnalysis.java
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
 * Created on Mar 15, 2014 at 8:25:21 PM.
 */

package org.gamegineer.common.core.runtime;

import java.util.concurrent.atomic.AtomicReference;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A collection of useful methods for working with the null analysis engine.
 */
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
     * Casts the specified value with unspecified nullity to a non-{@code null}
     * value.
     * 
     * <p>
     * This method is intended to be used to assume a certain nullity without
     * actually enforcing it at runtime. It is primarily used to adapt methods
     * that state in their contract they return {@code null}, but are intended
     * to be passed as non-{@code null} arguments.
     * </p>
     * 
     * <p>
     * For example, the various EasyMock factory methods for producing non-
     * {@code null} argument types and captures necessarily return {@code null}.
     * Thus, the {@link #nonNull} method would be inappropriate as it will throw
     * an {@link AssertionError} at runtime.
     * </p>
     * 
     * @param <T>
     *        The type of the value.
     * 
     * @param value
     *        The value; may be {@code null}.
     * 
     * @return The value; assumed to be {@code null} at compile time, but may in
     *         fact be {@code null} at runtime.
     */
    @SuppressWarnings( "null" )
    public static <T> T assumeNonNull(
        @Nullable
        final T value )
    {
        if( value != null )
        {
            return value;
        }

        final AtomicReference<T> nullReference = new AtomicReference<>( null );
        return nullReference.get();
    }

    /**
     * Casts the specified possibly {@code null} value to a non-{@code null}
     * value.
     * 
     * <p>
     * This method is intended to be used as a bridge between legacy APIs that
     * have an unspecified nullity and code that employs null analysis. It is
     * assumed that the legacy API whose return value is passed to this method
     * has been verified to return a non-{@code null} value. If the specified
     * value is {@code null}, an {@link AssertionError} will be raised.
     * </p>
     * 
     * @param <T>
     *        The type of the value.
     * 
     * @param value
     *        The value; may be {@code null}.
     * 
     * @return The value; never {@code null}.
     * 
     * @throws java.lang.AssertionError
     *         If {@code value} is {@code null}.
     */
    public static <T> T nonNull(
        @Nullable
        final T value )
    {
        assert value != null;
        return value;
    }
}
