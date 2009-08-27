/*
 * GameSystemUiException.java
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
 * Created on Feb 26, 2009 at 11:16:21 PM.
 */

package org.gamegineer.game.ui.system;

/**
 * A checked exception that indicates an error occurred during the creation of a
 * game system user interface.
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
public final class GameSystemUiException
    extends Exception
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -4832129492116030084L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemUiException} class
     * with no detail message and no cause.
     */
    public GameSystemUiException()
    {
        super();
    }

    /**
     * Initializes a new instance of the {@code GameSystemUiException} class
     * with the specified detail message and no cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     */
    public GameSystemUiException(
        /* @Nullable */
        final String message )
    {
        super( message );
    }

    /**
     * Initializes a new instance of the {@code GameSystemUiException} class
     * with no detail message and the specified cause.
     * 
     * @param cause
     *        The cause; may be {@code null}.
     */
    public GameSystemUiException(
        /* @Nullable */
        final Throwable cause )
    {
        super( cause );
    }

    /**
     * Initializes a new instance of the {@code GameSystemUiException} class
     * with the specified detail message and cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     * @param cause
     *        The cause; may be {@code null}.
     */
    public GameSystemUiException(
        /* @Nullable */
        final String message,
        /* @Nullable */
        final Throwable cause )
    {
        super( message, cause );
    }
}
