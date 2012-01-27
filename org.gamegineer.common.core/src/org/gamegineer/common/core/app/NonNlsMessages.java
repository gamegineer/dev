/*
 * NonNlsMessages.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jan 26, 2012 at 8:47:46 PM.
 */

package org.gamegineer.common.core.app;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage non-localized messages for the package.
 */
@ThreadSafe
final class NonNlsMessages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    // --- BrandingUtils ----------------------------------------------------

    /** An error occurred while parsing the application version. */
    public static String BrandingUtils_getVersion_parseError;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code NonNlsMessages} class.
     */
    static
    {
        NLS.initializeMessages( NonNlsMessages.class.getName(), NonNlsMessages.class );
    }

    /**
     * Initializes a new instance of the {@code NonNlsMessages} class.
     */
    private NonNlsMessages()
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
     * @param versionString
     *        The application version string; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the application version; never {@code null}.
     */
    /* @NonNull */
    static String BrandingUtils_getVersion_parseError(
        /* @NonNull */
        final String versionString )
    {
        return bind( BrandingUtils_getVersion_parseError, versionString );
    }
}
