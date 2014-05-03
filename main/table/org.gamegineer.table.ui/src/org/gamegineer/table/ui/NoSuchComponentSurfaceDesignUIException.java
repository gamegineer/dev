/*
 * NoSuchComponentSurfaceDesignUIException.java
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
 * Created on Aug 18, 2012 at 8:20:12 PM.
 */

package org.gamegineer.table.ui;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A checked exception that indicates a component surface design user interface
 * cannot be found.
 */
@ThreadSafe
public final class NoSuchComponentSurfaceDesignUIException
    extends Exception
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 2545650597966857574L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code NoSuchComponentSurfaceDesignUIException} class with no detail
     * message and no cause.
     */
    public NoSuchComponentSurfaceDesignUIException()
    {
    }

    /**
     * Initializes a new instance of the
     * {@code NoSuchComponentSurfaceDesignUIException} class with the specified
     * detail message and no cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     */
    public NoSuchComponentSurfaceDesignUIException(
        @Nullable
        final String message )
    {
        super( message );
    }

    /**
     * Initializes a new instance of the
     * {@code NoSuchComponentSurfaceDesignUIException} class with no detail
     * message and specified cause.
     * 
     * @param cause
     *        The cause; may be {@code null}.
     */
    public NoSuchComponentSurfaceDesignUIException(
        @Nullable
        final Throwable cause )
    {
        super( cause );
    }

    /**
     * Initializes a new instance of the
     * {@code NoSuchComponentSurfaceDesignUIException} class with the specified
     * detail message and cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     * @param cause
     *        The cause; may be {@code null}.
     */
    public NoSuchComponentSurfaceDesignUIException(
        @Nullable
        final String message,
        @Nullable
        final Throwable cause )
    {
        super( message, cause );
    }
}
