/*
 * GameConfigurationException.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Jul 17, 2008 at 10:48:59 PM.
 */

package org.gamegineer.game.core;

/**
 * A checked exception that indicates a game could not be created which
 * satisfies a specific configuration.
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
public final class GameConfigurationException
    extends GameException
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 6139068066369816471L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameConfigurationException}
     * class with no detail message and no cause.
     */
    public GameConfigurationException()
    {
        super();
    }

    /**
     * Initializes a new instance of the {@code GameConfigurationException}
     * class with the specified detail message and no cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     */
    public GameConfigurationException(
        /* @Nullable */
        final String message )
    {
        super( message );
    }

    /**
     * Initializes a new instance of the {@code GameConfigurationException}
     * class with no detail message and the specified cause.
     * 
     * @param cause
     *        The cause; may be {@code null}.
     */
    public GameConfigurationException(
        /* @Nullable */
        final Throwable cause )
    {
        super( cause );
    }

    /**
     * Initializes a new instance of the {@code GameConfigurationException}
     * class with the specified detail message and cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     * @param cause
     *        The cause; may be {@code null}.
     */
    public GameConfigurationException(
        /* @Nullable */
        final String message,
        /* @Nullable */
        final Throwable cause )
    {
        super( message, cause );
    }
}
