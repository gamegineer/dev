/*
 * NetworkTableException.java
 * Copyright 2008-2010 Gamegineer.org
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

/**
 * A checked exception that indicates an error occurred within a network table.
 */
public final class NetworkTableException
    extends Exception
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -8623337465480942177L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableException} class
     * with no detail message and no cause.
     */
    public NetworkTableException()
    {
        super();
    }

    /**
     * Initializes a new instance of the {@code NetworkTableException} class
     * with the specified detail message and no cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     */
    public NetworkTableException(
        /* @Nullable */
        final String message )
    {
        super( message );
    }

    /**
     * Initializes a new instance of the {@code NetworkTableException} class
     * with no detail message and the specified cause.
     * 
     * @param cause
     *        The cause; may be {@code null}.
     */
    public NetworkTableException(
        /* @Nullable */
        final Throwable cause )
    {
        super( cause );
    }

    /**
     * Initializes a new instance of the {@code NetworkTableException} class
     * with the specified detail message and cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     * @param cause
     *        The cause; may be {@code null}.
     */
    public NetworkTableException(
        /* @Nullable */
        final String message,
        /* @Nullable */
        final Throwable cause )
    {
        super( message, cause );
    }
}