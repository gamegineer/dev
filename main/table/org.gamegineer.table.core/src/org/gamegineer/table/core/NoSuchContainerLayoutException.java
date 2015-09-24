/*
 * NoSuchContainerLayoutException.java
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
 * Created on Aug 11, 2012 at 9:16:23 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A checked exception that indicates a container layout cannot be found.
 */
@ThreadSafe
public final class NoSuchContainerLayoutException
    extends Exception
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 6710790293683109725L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NoSuchContainerLayoutException}
     * class with no detail message and no cause.
     */
    public NoSuchContainerLayoutException()
    {
    }

    /**
     * Initializes a new instance of the {@code NoSuchContainerLayoutException}
     * class with the specified detail message and no cause.
     * 
     * @param message
     *        The detail message.
     */
    public NoSuchContainerLayoutException(
        final @Nullable String message )
    {
        super( message );
    }

    /**
     * Initializes a new instance of the {@code NoSuchContainerLayoutException}
     * class with no detail message and specified cause.
     * 
     * @param cause
     *        The cause.
     */
    public NoSuchContainerLayoutException(
        final @Nullable Throwable cause )
    {
        super( cause );
    }

    /**
     * Initializes a new instance of the {@code NoSuchContainerLayoutException}
     * class with the specified detail message and cause.
     * 
     * @param message
     *        The detail message.
     * @param cause
     *        The cause.
     */
    public NoSuchContainerLayoutException(
        final @Nullable String message,
        final @Nullable Throwable cause )
    {
        super( message, cause );
    }
}
