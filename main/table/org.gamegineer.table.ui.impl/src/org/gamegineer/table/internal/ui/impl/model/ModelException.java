/*
 * ModelException.java
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
 * Created on Apr 23, 2010 at 10:41:11 PM.
 */

package org.gamegineer.table.internal.ui.impl.model;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A checked exception that indicates an error occurred within a model.
 */
@ThreadSafe
public final class ModelException
    extends Exception
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 1985732492160760788L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ModelException} class with no
     * detail message and no cause.
     */
    public ModelException()
    {
    }

    /**
     * Initializes a new instance of the {@code ModelException} class with the
     * specified detail message and no cause.
     * 
     * @param message
     *        The detail message.
     */
    public ModelException(
        final @Nullable String message )
    {
        super( message );
    }

    /**
     * Initializes a new instance of the {@code ModelException} class with no
     * detail message and the specified cause.
     * 
     * @param cause
     *        The cause.
     */
    public ModelException(
        final @Nullable Throwable cause )
    {
        super( cause );
    }

    /**
     * Initializes a new instance of the {@code ModelException} class with the
     * specified detail message and cause.
     * 
     * @param message
     *        The detail message.
     * @param cause
     *        The cause.
     */
    public ModelException(
        final @Nullable String message,
        final @Nullable Throwable cause )
    {
        super( message, cause );
    }
}
