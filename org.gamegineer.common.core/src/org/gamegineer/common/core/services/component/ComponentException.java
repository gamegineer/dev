/*
 * ComponentException.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Apr 12, 2008 at 8:52:44 PM.
 */

package org.gamegineer.common.core.services.component;

/**
 * A checked exception that indicates an error occurred within the component
 * service or one of its participants.
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
public abstract class ComponentException
    extends Exception
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 3507566325273810049L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentException} class with
     * no detail message and no cause.
     */
    protected ComponentException()
    {
        super();
    }

    /**
     * Initializes a new instance of the {@code ComponentException} class with
     * the specified detail message and no cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     */
    protected ComponentException(
        /* @Nullable */
        final String message )
    {
        super( message );
    }

    /**
     * Initializes a new instance of the {@code ComponentException} class with
     * no detail message and the specified cause.
     * 
     * @param cause
     *        The cause; may be {@code null}.
     */
    protected ComponentException(
        /* @Nullable */
        final Throwable cause )
    {
        super( cause );
    }

    /**
     * Initializes a new instance of the {@code ComponentException} class with
     * the specified detail message and cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     * @param cause
     *        The cause; may be {@code null}.
     */
    protected ComponentException(
        /* @Nullable */
        final String message,
        /* @Nullable */
        final Throwable cause )
    {
        super( message, cause );
    }
}
