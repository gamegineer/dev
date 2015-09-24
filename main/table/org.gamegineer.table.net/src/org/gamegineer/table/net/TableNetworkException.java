/*
 * TableNetworkException.java
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
 * Created on Nov 4, 2010 at 11:42:36 PM.
 */

package org.gamegineer.table.net;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A checked exception that indicates an error occurred within a table network.
 */
@ThreadSafe
public final class TableNetworkException
    extends Exception
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -8623337465480942177L;

    /** The table network error associated with the exception. */
    private final TableNetworkError error_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkException} class
     * with the specified error, no detail message, and no cause.
     * 
     * @param error
     *        The table network error associated with the exception.
     */
    public TableNetworkException(
        final TableNetworkError error )
    {
        this( error, error.toString(), null );
    }

    /**
     * Initializes a new instance of the {@code TableNetworkException} class
     * with the specified error, the specified detail message, and no cause.
     * 
     * @param error
     *        The table network error associated with the exception.
     * @param message
     *        The detail message.
     */
    public TableNetworkException(
        final TableNetworkError error,
        final @Nullable String message )
    {
        this( error, message, null );
    }

    /**
     * Initializes a new instance of the {@code TableNetworkException} class
     * with the specified error, no detail message, and the specified cause.
     * 
     * @param error
     *        The table network error associated with the exception.
     * @param cause
     *        The cause.
     */
    public TableNetworkException(
        final TableNetworkError error,
        final @Nullable Throwable cause )
    {
        this( error, error.toString(), cause );
    }

    /**
     * Initializes a new instance of the {@code TableNetworkException} class
     * with the specified error, detail message, and cause.
     * 
     * @param error
     *        The table network error associated with the exception.
     * @param message
     *        The detail message.
     * @param cause
     *        The cause.
     */
    public TableNetworkException(
        final TableNetworkError error,
        final @Nullable String message,
        final @Nullable Throwable cause )
    {
        super( message, cause );

        error_ = error;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the table network error associated with the exception.
     * 
     * @return The table network error associated with the exception.
     */
    public TableNetworkError getError()
    {
        return error_;
    }
}
