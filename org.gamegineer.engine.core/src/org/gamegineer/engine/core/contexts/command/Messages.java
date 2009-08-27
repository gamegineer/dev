/*
 * Messages.java
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
 * Created on May 4, 2009 at 8:38:14 PM.
 */

package org.gamegineer.engine.core.contexts.command;

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
    private static final String BUNDLE_NAME = "org.gamegineer.engine.core.contexts.command.Messages"; //$NON-NLS-1$

    // --- CommandContextBuilder --------------------------------------------

    /** An attribute with the same name already exists. */
    public static String CommandContextBuilder_attribute_exists;


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

    // --- CommandContextBuilder --------------------------------------------

    /**
     * Gets the formatted message indicating an attribute with the same name
     * already exists.
     * 
     * @param name
     *        The attribute name; must not be {@code null}.
     * 
     * @return The formatted message indicating an attribute with the same name
     *         already exists; never {@code null}.
     */
    /* @NonNull */
    static String CommandContextBuilder_attribute_exists(
        /* @NonNull */
        final String name )
    {
        return bind( CommandContextBuilder_attribute_exists, name );
    }
}
