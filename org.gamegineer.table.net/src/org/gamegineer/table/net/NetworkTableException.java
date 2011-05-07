/*
 * NetworkTableException.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Nov 4, 2010 at 11:42:36 PM.
 */

package org.gamegineer.table.net;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.ThreadSafe;

/**
 * A checked exception that indicates an error occurred within a network table.
 */
@ThreadSafe
public final class NetworkTableException
    extends Exception
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -8623337465480942177L;

    /** The network table error associated with the exception. */
    private final NetworkTableError error_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableException} class
     * with the specified error, no detail message, and no cause.
     * 
     * @param error
     *        The network table error associated with the exception; must not be
     *        {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code error} is {@code null}.
     */
    public NetworkTableException(
        /* @NonNull */
        final NetworkTableError error )
    {
        this( error, error.toString(), null );
    }

    /**
     * Initializes a new instance of the {@code NetworkTableException} class
     * with the specified error, the specified detail message, and no cause.
     * 
     * @param error
     *        The network table error associated with the exception; must not be
     *        {@code null}.
     * @param message
     *        The detail message; may be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code error} is {@code null}.
     */
    public NetworkTableException(
        /* @NonNull */
        final NetworkTableError error,
        /* @Nullable */
        final String message )
    {
        this( error, message, null );
    }

    /**
     * Initializes a new instance of the {@code NetworkTableException} class
     * with the specified error, no detail message, and the specified cause.
     * 
     * @param error
     *        The network table error associated with the exception; must not be
     *        {@code null}.
     * @param cause
     *        The cause; may be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code error} is {@code null}.
     */
    public NetworkTableException(
        /* @NonNull */
        final NetworkTableError error,
        /* @Nullable */
        final Throwable cause )
    {
        this( error, error.toString(), cause );
    }

    /**
     * Initializes a new instance of the {@code NetworkTableException} class
     * with the specified error, detail message, and cause.
     * 
     * @param error
     *        The network table error associated with the exception; must not be
     *        {@code null}.
     * @param message
     *        The detail message; may be {@code null}.
     * @param cause
     *        The cause; may be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code error} is {@code null}.
     */
    public NetworkTableException(
        /* @NonNull */
        final NetworkTableError error,
        /* @Nullable */
        final String message,
        /* @Nullable */
        final Throwable cause )
    {
        super( message, cause );

        assertArgumentNotNull( error, "error" ); //$NON-NLS-1$

        error_ = error;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the network table error associated with the exception.
     * 
     * @return The network table error associated with the exception; never
     *         {@code null}.
     */
    /* @NonNull */
    public NetworkTableError getError()
    {
        return error_;
    }
}
