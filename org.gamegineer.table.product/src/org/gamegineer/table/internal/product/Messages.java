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
 * Created on Sep 18, 2009 at 11:17:23 PM.
 */

package org.gamegineer.table.internal.product;

import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.ui.TableResult;
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
    private static final String BUNDLE_NAME = "org.gamegineer.table.internal.product.Messages"; //$NON-NLS-1$

    // --- Application ------------------------------------------------------

    /** An error occurred while parsing the application version. */
    public static String Application_createTableAdvisor_parseVersionError;

    /** The application was cancelled. */
    public static String Application_start_cancelled;

    /** The application is starting. */
    public static String Application_start_starting;

    /** Failed to stop the application. */
    public static String Application_start_stopFailed;

    /** The application has stopped. */
    public static String Application_start_stopped;

    /** Failed to cancel the application task. */
    public static String Application_stop_cancelFailed;

    /** Attempting to stop the application. */
    public static String Application_stop_stopping;


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
    static String Application_createTableAdvisor_parseVersionError(
        /* @NonNull */
        final String version )
    {
        return bind( Application_createTableAdvisor_parseVersionError, version );
    }

    /**
     * Gets the formatted message indicating the application is starting.
     * 
     * @param version
     *        The application version; must not be {@code null}.
     * 
     * @return The formatted message indicating the application is starting;
     *         never {@code null}.
     */
    /* @NonNull */
    static String Application_start_starting(
        /* @NonNull */
        final Version version )
    {
        return bind( Application_start_starting, version.toString() );
    }

    /**
     * Gets the formatted message indicating the application has stopped.
     * 
     * @param result
     *        The table result; must not be {@code null}.
     * 
     * @return The formatted message indicating the application has stopped;
     *         never {@code null}.
     */
    /* @NonNull */
    static String Application_start_stopped(
        /* @NonNull */
        final TableResult result )
    {
        return bind( Application_start_stopped, result.toString() );
    }
}
