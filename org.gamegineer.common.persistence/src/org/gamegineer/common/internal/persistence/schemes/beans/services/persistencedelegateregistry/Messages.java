/*
 * Messages.java
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
 * Created on May 6, 2010 at 11:45:48 PM.
 */

package org.gamegineer.common.internal.persistence.schemes.beans.services.persistencedelegateregistry;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage localized messages for the package.
 */
@ThreadSafe
final class Messages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    // --- PersistenceDelegateRegistry --------------------------------------

    /** A duplicate persistence delegate class name was detected. */
    public static String PersistenceDelegateRegistry_getPersistenceDelegateMap_duplicateClassName;

    /** An error occurred while parsing the persistence delegate definition. */
    public static String PersistenceDelegateRegistry_getForeignPersistenceDelegateMap_parseError;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Messages} class.
     */
    static
    {
        NLS.initializeMessages( Messages.class.getName(), Messages.class );
    }

    /**
     * Initializes a new instance of the {@code Messages} class.
     */
    private Messages()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- PersistenceDelegateRegistry --------------------------------------

    /**
     * Gets the formatted message indicating a duplicate persistence delegate
     * class name was detected.
     * 
     * @param className
     *        The persistence delegate class name; must not be {@code null}.
     * 
     * @return The formatted message indicating a duplicate persistence delegate
     *         class name identifier was detected; never {@code null}.
     */
    /* @NonNull */
    static String PersistenceDelegateRegistry_getPersistenceDelegateMap_duplicateClassName(
        /* @NonNull */
        final String className )
    {
        return bind( PersistenceDelegateRegistry_getPersistenceDelegateMap_duplicateClassName, className );
    }

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * persistence delegate definition.
     * 
     * @param className
     *        The persistence delegate class name; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the persistence delegate definition; never {@code null}.
     */
    /* @NonNull */
    static String PersistenceDelegateRegistry_getForeignPersistenceDelegateMap_parseError(
        /* @NonNull */
        final String className )
    {
        return bind( PersistenceDelegateRegistry_getForeignPersistenceDelegateMap_parseError, className );
    }
}
