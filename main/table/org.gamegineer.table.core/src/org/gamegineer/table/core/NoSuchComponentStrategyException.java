/*
 * NoSuchComponentStrategyException.java
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
 * Created on Aug 11, 2012 at 10:07:29 PM.
 */

package org.gamegineer.table.core;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A checked exception that indicates a component strategy cannot be found.
 */
@ThreadSafe
public final class NoSuchComponentStrategyException
    extends Exception
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 3021733784006968665L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code NoSuchComponentStrategyException} class with no detail message and
     * no cause.
     */
    public NoSuchComponentStrategyException()
    {
    }

    /**
     * Initializes a new instance of the
     * {@code NoSuchComponentStrategyException} class with the specified detail
     * message and no cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     */
    public NoSuchComponentStrategyException(
        final @Nullable String message )
    {
        super( message );
    }

    /**
     * Initializes a new instance of the
     * {@code NoSuchComponentStrategyException} class with no detail message and
     * specified cause.
     * 
     * @param cause
     *        The cause; may be {@code null}.
     */
    public NoSuchComponentStrategyException(
        final @Nullable Throwable cause )
    {
        super( cause );
    }

    /**
     * Initializes a new instance of the
     * {@code NoSuchComponentStrategyException} class with the specified detail
     * message and cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     * @param cause
     *        The cause; may be {@code null}.
     */
    public NoSuchComponentStrategyException(
        final @Nullable String message,
        final @Nullable Throwable cause )
    {
        super( message, cause );
    }
}
