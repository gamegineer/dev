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
 * Created on Jul 1, 2008 at 10:21:36 PM.
 */

package org.gamegineer.common.internal.persistence.memento;

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
    private static final String BUNDLE_NAME = "org.gamegineer.common.internal.persistence.memento.Messages"; //$NON-NLS-1$

    // --- Memento ----------------------------------------------------------

    /** The attribute does not exist. */
    public static String Memento_attribute_absent;


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

    // --- Memento ----------------------------------------------------------

    /**
     * Gets the formatted message indicating the attribute does not exist.
     * 
     * @param attributeName
     *        The attribute name; must not be {@code null}.
     * 
     * @return The formatted message indicating the attribute does not exist;
     *         never {@code null}.
     */
    /* @NonNull */
    static String Memento_attribute_absent(
        /* @NonNull */
        final String attributeName )
    {
        return bind( Memento_attribute_absent, attributeName );
    }
}
