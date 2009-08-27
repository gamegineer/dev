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
 * Created on Oct 24, 2008 at 12:06:13 AM.
 */

package org.gamegineer.client.internal.ui.console.commandlet;

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
    private static final String BUNDLE_NAME = "org.gamegineer.client.internal.ui.console.commandlet.Messages"; //$NON-NLS-1$

    // --- CommandletArguments ----------------------------------------------

    /** The named value list entry is malformed. */
    public static String CommandletArguments_parseNamedValueList_malformedEntry;

    // --- CommandletParser -------------------------------------------------

    /** An error occurred when creating the commandlet component. */
    public static String CommandletParser_createCommandletExecutor_componentError;

    /** The commandlet name is ambiguous. */
    public static String CommandletParser_output_ambiguousCommandlet;

    /** The commandlet could not be created. */
    public static String CommandletParser_output_createCommandletError;

    /** The commandlet is malformed. */
    public static String CommandletParser_output_malformedCommandlet;

    /** The component is not a commandlet. */
    public static String CommandletParser_output_notCommandlet;

    /** The commandlet does not exist. */
    public static String CommandletParser_output_unknownCommandlet;

    /** A parse error occurred. */
    public static String CommandletParser_parse_parseError;


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

    // --- CommandletArguments ----------------------------------------------

    /**
     * Gets the formatted message indicating the named value list entry is
     * malformed.
     * 
     * @param entry
     *        The malformed named value list entry; must not be {@code null}.
     * 
     * @return The formatted message indicating the named value list entry is
     *         malformed; never {@code null}.
     */
    /* @NonNull */
    static String CommandletArguments_parseNamedValueList_malformedEntry(
        /* @NonNull */
        final String entry )
    {
        return bind( CommandletArguments_parseNamedValueList_malformedEntry, entry );
    }

    // --- CommandletParser -------------------------------------------------

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
    static String CommandletParser_createCommandletExecutor_componentError(
        /* @NonNull */
        final String name )
    {
        return bind( CommandletParser_createCommandletExecutor_componentError, name );
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
    static String CommandletParser_output_ambiguousCommandlet(
        /* @NonNull */
        final String name,
        /* @NonNull */
        final Set<String> classNames )
    {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter( sw );
        pw.println( bind( CommandletParser_output_ambiguousCommandlet, name ) );

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
     * Gets the formatted message indicating the commandlet could not be
     * created.
     * 
     * @param name
     *        The commandlet name; must not be {@code null}.
     * 
     * @return The formatted message indicating the commandlet could not be
     *         created; never {@code null}.
     */
    /* @NonNull */
    static String CommandletParser_output_createCommandletError(
        /* @NonNull */
        final String name )
    {
        return bind( CommandletParser_output_createCommandletError, name );
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
    static String CommandletParser_output_notCommandlet(
        /* @NonNull */
        final String name )
    {
        return bind( CommandletParser_output_notCommandlet, name );
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
    static String CommandletParser_output_unknownCommandlet(
        /* @NonNull */
        final String name )
    {
        return bind( CommandletParser_output_unknownCommandlet, name );
    }
}
