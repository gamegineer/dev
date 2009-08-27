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
 * Created on Oct 5, 2008 at 11:36:32 PM.
 */

package org.gamegineer.client.internal.ui.console;

import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Version;

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
    private static final String BUNDLE_NAME = "org.gamegineer.client.internal.ui.console.Messages"; //$NON-NLS-1$

    // --- CommandLineOptions -----------------------------------------------

    /** The description for the "help" option. */
    public static String CommandLineOptions_help_description;

    // --- Console ----------------------------------------------------------

    /** The console banner message. */
    public static String Console_output_bannerMessage;

    /** An unexpected error occurred during commandlet execution. */
    public static String Console_output_unexpectedCommandletException;

    /** An error occurred during commandlet execution. */
    public static String Console_run_commandletException;

    /** An unexpected error occurred during commandlet execution. */
    public static String Console_run_unexpectedCommandletException;

    /** The console is not pristine. */
    public static String Console_state_notPristine;

    // --- Statelet ---------------------------------------------------------

    /** The attribute does not exist. */
    public static String Statelet_attribute_absent;

    /** The attribute already exists. */
    public static String Statelet_attribute_present;


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

    // --- Console ----------------------------------------------------------

    /**
     * Gets the formatted console banner message.
     * 
     * @param version
     *        The application version; must not be {@code null}.
     * 
     * @return The formatted console banner message; never {@code null}.
     */
    /* @NonNull */
    static String Console_output_bannerMessage(
        /* @NonNull */
        final Version version )
    {
        return bind( Console_output_bannerMessage, version.toString() );
    }

    // --- Statelet ---------------------------------------------------------

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
    static String Statelet_attribute_absent(
        /* @NonNull */
        final String attributeName )
    {
        return bind( Statelet_attribute_absent, attributeName );
    }

    /**
     * Gets the formatted message indicating the attribute already exists.
     * 
     * @param attributeName
     *        The attribute name; must not be {@code null}.
     * 
     * @return The formatted message indicating the attribute already exists;
     *         never {@code null}.
     */
    /* @NonNull */
    static String Statelet_attribute_present(
        /* @NonNull */
        final String attributeName )
    {
        return bind( Statelet_attribute_present, attributeName );
    }
}
