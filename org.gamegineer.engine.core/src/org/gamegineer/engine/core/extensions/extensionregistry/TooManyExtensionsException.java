/*
 * TooManyExtensionsException.java
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
 * Created on Apr 17, 2008 at 9:41:33 PM.
 */

package org.gamegineer.engine.core.extensions.extensionregistry;

import org.gamegineer.engine.core.EngineException;

/**
 * A checked exception that indicates an extension for a specific extension type
 * has already been registered.
 * 
 * <p>
 * This class is not intended to be extended by clients.
 * </p>
 */
public final class TooManyExtensionsException
    extends EngineException
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = -5228245490551578548L;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TooManyExtensionsException}
     * class with no detail message and no cause.
     */
    public TooManyExtensionsException()
    {
        super();
    }

    /**
     * Initializes a new instance of the {@code TooManyExtensionsException}
     * class with the specified detail message and no cause.
     * 
     * @param message
     *        The detail message; may be {@code null}.
     */
    public TooManyExtensionsException(
        /* @Nullable */
        final String message )
    {
        super( message );
    }
}
