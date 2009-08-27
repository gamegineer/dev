/*
 * Messages.java
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
 * Created on Sep 25, 2008 at 10:50:33 PM.
 */

package org.gamegineer.client.internal.product.console;

import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage localized messages for the package.
 */
final class Messages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the associated resource bundle. */
    private static final String BUNDLE_NAME = "org.gamegineer.client.internal.product.console.Messages"; //$NON-NLS-1$

    // --- Application ------------------------------------------------------

    /** An error occurred while parsing the application version. */
    public static String Application_createConsoleAdvisor_parseVersionError;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Messages} class.
     */
    static
    {
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
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

    // --- Application ------------------------------------------------------

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * application version.
     * 
     * @param version
     *        The application version; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the application version; never {@code null}.
     */
    /* @NonNull */
    static String Application_createConsoleAdvisor_parseVersionError(
        /* @NonNull */
        final String version )
    {
        return bind( Application_createConsoleAdvisor_parseVersionError, version );
    }
}
