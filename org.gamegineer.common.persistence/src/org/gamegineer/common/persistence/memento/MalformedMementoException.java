/*
 * MalformedMementoException.java
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
 * Created on Apr 8, 2010 at 11:17:46 PM.
 */

package org.gamegineer.common.persistence.memento;

import net.jcip.annotations.NotThreadSafe;

/**
 * A checked exception that indicates a memento is malformed because it is
 * missing or has an illegal value for one or more required attributes.
 */
@NotThreadSafe
public final class MalformedMementoException
    extends Exception
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 8398188239737456590L;

    /**
     * The name of the attribute that caused the memento to be malformed.
     * 
     * @serial
     */
    private String attributeName_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MalformedMementoException} class
     * with no attribute name, no detail message, and no cause.
     */
    public MalformedMementoException()
    {
        this( null );
    }

    /**
     * Initializes a new instance of the {@code MalformedMementoException} class
     * with the specified attribute name, no detail message, and no cause.
     * 
     * @param attributeName
     *        The name of the attribute that caused the memento to be malformed;
     *        may be {@code null}.
     */
    public MalformedMementoException(
        /* @Nullable */
        final String attributeName )
    {
        this( attributeName, null, null );
    }

    /**
     * Initializes a new instance of the {@code MalformedMementoException} class
     * with the specified attribute name, specified detail message, and no
     * cause.
     * 
     * @param attributeName
     *        The name of the attribute that caused the memento to be malformed;
     *        may be {@code null}.
     * @param message
     *        The detail message; may be {@code null}.
     */
    public MalformedMementoException(
        /* @Nullable */
        final String attributeName,
        /* @Nullable */
        final String message )
    {
        this( attributeName, message, null );
    }

    /**
     * Initializes a new instance of the {@code MalformedMementoException} class
     * with the specified attribute name, no detail message, and specified
     * cause.
     * 
     * @param attributeName
     *        The name of the attribute that caused the memento to be malformed;
     *        may be {@code null}.
     * @param cause
     *        The cause; may be {@code null}.
     */
    public MalformedMementoException(
        /* @Nullable */
        final String attributeName,
        /* @Nullable */
        final Throwable cause )
    {
        this( attributeName, null, cause );
    }

    /**
     * Initializes a new instance of the {@code MalformedMementoException} class
     * with the specified attribute name, detail message, and cause.
     * 
     * @param attributeName
     *        The name of the attribute that caused the memento to be malformed;
     *        may be {@code null}.
     * @param message
     *        The detail message; may be {@code null}.
     * @param cause
     *        The cause; may be {@code null}.
     */
    public MalformedMementoException(
        /* @Nullable */
        final String attributeName,
        /* @Nullable */
        final String message,
        /* @Nullable */
        final Throwable cause )
    {
        super( message, cause );

        attributeName_ = attributeName;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the name of the attribute that caused the memento to be malformed.
     * 
     * @return The name of the attribute that caused the memento to be
     *         malformed; may be {@code null}.
     */
    /* @Nullable */
    public String getAttributeName()
    {
        return attributeName_;
    }
}
