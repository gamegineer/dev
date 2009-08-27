/*
 * EngineConfigurationException.java
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
 * Created on Jun 28, 2008 at 10:36:36 PM.
 */

package org.gamegineer.engine.core;

/**
 * A checked exception that indicates an engine could not be created which
 * satisfies a specific configuration.
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
public final class EngineConfigurationException
    extends EngineException
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 8422020179166408612L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EngineConfigurationException}
     * class with no detail message and no cause.
     */
    public EngineConfigurationException()
    {
        super();
    }

    /**
     * Initializes a new instance of the {@code EngineConfigurationException}
     * class with the specified detail message and no cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     */
    public EngineConfigurationException(
        /* @Nullable */
        final String message )
    {
        super( message );
    }

    /**
     * Initializes a new instance of the {@code EngineConfigurationException}
     * class with no detail message and the specified cause.
     * 
     * @param cause
     *        The cause; may be {@code null}.
     */
    public EngineConfigurationException(
        /* @Nullable */
        final Throwable cause )
    {
        super( cause );
    }

    /**
     * Initializes a new instance of the {@code EngineConfigurationException}
     * class with the specified detail message and cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     * @param cause
     *        The cause; may be {@code null}.
     */
    public EngineConfigurationException(
        /* @Nullable */
        final String message,
        /* @Nullable */
        final Throwable cause )
    {
        super( message, cause );
    }
}
