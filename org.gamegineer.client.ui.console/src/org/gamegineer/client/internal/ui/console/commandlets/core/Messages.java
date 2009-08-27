/*
 * CommandletMessages.java
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
 * Created on Mar 27, 2009 at 11:25:54 PM.
 */

package org.gamegineer.client.internal.ui.console.commandlets.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Set;
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
    private static final String BUNDLE_NAME = "org.gamegineer.client.internal.ui.console.commandlets.core.Messages"; //$NON-NLS-1$

    // --- HelpCommandlet ---------------------------------------------------

    /** An error occurred when creating the commandlet component. */
    public static String HelpCommandlet_getCommandletHelp_componentError;

    /** The component is not a commandlet. */
    public static String HelpCommandlet_getCommandletHelp_notCommandlet;

    /** The help detailed description. */
    public static String HelpCommandlet_help_detailedDescription;

    /** The help synopsis. */
    public static String HelpCommandlet_help_synopsis;

    /** The commandlet name is ambiguous. */
    public static String HelpCommandlet_output_ambiguousCommandlet;

    /** A help detailed description is not available. */
    public static String HelpCommandlet_output_noHelpDetailedDescriptionAvailable;

    /** A help synopsis is not available. */
    public static String HelpCommandlet_output_noHelpSynopsisAvailable;

    /** The commandlet does not exist. */
    public static String HelpCommandlet_output_unknownCommandlet;

    // --- QuitCommandlet ---------------------------------------------------

    /** The help detailed description. */
    public static String QuitCommandlet_help_detailedDescription;

    /** The help synopsis. */
    public static String QuitCommandlet_help_synopsis;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code CommandletMessages} class.
     */
    static
    {
        NLS.initializeMessages( BUNDLE_NAME, Messages.class );
    }

    /**
     * Initializes a new instance of the {@code CommandletMessages} class.
     */
    private Messages()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- HelpCommandlet ---------------------------------------------------

    /**
     * Gets the formatted message indicating an error occurred when creating the
     * commandlet component.
     * 
     * @param name
     *        The commandlet name; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred when creating
     *         the commandlet component; never {@code null}.
     */
    /* @NonNull */
    static String HelpCommandlet_getCommandletHelp_componentError(
        /* @NonNull */
        final String name )
    {
        return bind( HelpCommandlet_getCommandletHelp_componentError, name );
    }

    /**
     * Gets the formatted message indicating the component is not a commandlet.
     * 
     * @param name
     *        The commandlet name; must not be {@code null}.
     * 
     * @return The formatted message indicating the component is not a
     *         commandlet; never {@code null}.
     */
    /* @NonNull */
    static String HelpCommandlet_getCommandletHelp_notCommandlet(
        /* @NonNull */
        final String name )
    {
        return bind( HelpCommandlet_getCommandletHelp_notCommandlet, name );
    }

    /**
     * Gets the formatted message indicating the commandlet name is ambiguous.
     * 
     * @param name
     *        The commandlet name; must not be {@code null}.
     * @param classNames
     *        The set of commandlet class names that potentially match
     *        {@code name}; must not be {@code null}.
     * 
     * @return The formatted message indicating the commandlet name is
     *         ambiguous; never {@code null}.
     */
    /* @NonNull */
    static String HelpCommandlet_output_ambiguousCommandlet(
        /* @NonNull */
        final String name,
        /* @NonNull */
        final Set<String> classNames )
    {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter( sw );
        pw.println( bind( HelpCommandlet_output_ambiguousCommandlet, name ) );

        for( final Iterator<String> iter = classNames.iterator(); iter.hasNext(); )
        {
            pw.print( iter.next() );
            if( iter.hasNext() )
            {
                pw.println();
            }
        }

        return sw.toString();
    }

    /**
     * Gets the formatted message indicating the commandlet does not exist.
     * 
     * @param name
     *        The commandlet name; must not be {@code null}.
     * 
     * @return The formatted message indicating the commandlet does not exist;
     *         never {@code null}.
     */
    /* @NonNull */
    static String HelpCommandlet_output_unknownCommandlet(
        /* @NonNull */
        final String name )
    {
        return bind( HelpCommandlet_output_unknownCommandlet, name );
    }
}
