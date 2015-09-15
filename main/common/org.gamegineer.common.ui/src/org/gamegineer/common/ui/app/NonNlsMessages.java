/*
 * NonNlsMessages.java
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
 * Created on Jan 23, 2012 at 7:51:22 PM.
 */

package org.gamegineer.common.ui.app;

import java.net.URL;
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

    // --- BrandingUIUtils --------------------------------------------------

    /** An error occurred while reading the image. */
    public static String BrandingUIUtils_getImage_readError = ""; //$NON-NLS-1$


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
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- BrandingUIUtils --------------------------------------------------

    /**
     * Gets the formatted message indicating an error occurred while reading the
     * specified image.
     * 
     * @param imageUrl
     *        The URL of the image; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while reading
     *         the specified image; never {@code null}.
     */
    static String BrandingUIUtils_getImage_readError(
        final URL imageUrl )
    {
        return bind( BrandingUIUtils_getImage_readError, imageUrl );
    }
}
