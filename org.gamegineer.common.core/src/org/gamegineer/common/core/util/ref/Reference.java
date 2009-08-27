/*
 * Reference.java
 * Copyright 2008 Gamegineer.org
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
 * Created on May 26, 2008 at 10:55:10 PM.
 */

package org.gamegineer.common.core.util.ref;

/**
 * A reference object.
 * 
 * <p>
 * This class provides a facility for programmers to implement methods with
 * "out" and "in-out" parameters.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 * 
 * @param <T>
 *        The type of the referent.
 */
public class Reference<T>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The referent. */
    private T m_referent;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Reference} class with no initial
     * referent.
     */
    public Reference()
    {
        this( null );
    }

    /**
     * Initializes a new instance of the {@code Reference} class with the
     * specified referent.
     * 
     * @param referent
     *        The referent; may be {@code null}.
     */
    public Reference(
        /* @Nullable */
        final T referent )
    {
        m_referent = referent;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the referent.
     * 
     * @return The referent; may be {@code null}.
     */
    /* @Nullable */
    public T get()
    {
        return m_referent;
    }

    /**
     * Sets the referent.
     * 
     * @param referent
     *        The referent; may be {@code null}.
     */
    public void set(
        /* @Nullable */
        final T referent )
    {
        m_referent = referent;
    }
}
