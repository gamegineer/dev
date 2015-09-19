/*
 * MementoException.java
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
 * Created on Apr 8, 2010 at 11:17:46 PM.
 */

package org.gamegineer.common.core.util.memento;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A checked exception that indicates a memento could not be created or
 * restored.
 */
@ThreadSafe
public final class MementoException
    extends Exception
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 8398188239737456590L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MementoException} class with no
     * detail message and no cause.
     */
    public MementoException()
    {
    }

    /**
     * Initializes a new instance of the {@code MementoException} class with the
     * specified detail message and no cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     */
    public MementoException(
        final @Nullable String message )
    {
        super( message );
    }

    /**
     * Initializes a new instance of the {@code MementoException} class with no
     * detail message and specified cause.
     * 
     * @param cause
     *        The cause; may be {@code null}.
     */
    public MementoException(
        final @Nullable Throwable cause )
    {
        super( cause );
    }

    /**
     * Initializes a new instance of the {@code MementoException} class with the
     * specified detail message and cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     * @param cause
     *        The cause; may be {@code null}.
     */
    public MementoException(
        final @Nullable String message,
        final @Nullable Throwable cause )
    {
        super( message, cause );
    }
}
