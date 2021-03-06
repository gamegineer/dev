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
 * Created on Sep 18, 2009 at 11:17:23 PM.
 */

package org.gamegineer.table.internal.product;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.ui.TableResult;
import org.osgi.framework.Version;

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

    // --- Application ------------------------------------------------------

    /** The application was cancelled. */
    public static String Application_start_cancelled = ""; //$NON-NLS-1$

    /** The application is starting. */
    public static String Application_start_starting = ""; //$NON-NLS-1$

    /** The application has stopped. */
    public static String Application_start_stopped = ""; //$NON-NLS-1$

    /** The table runner factory service is not available. */
    public static String Application_start_tableRunnerFactoryNotAvailable = ""; //$NON-NLS-1$

    /** Failed to cancel the application task. */
    public static String Application_stop_cancelFailed = ""; //$NON-NLS-1$

    /** Attempting to stop the application. */
    public static String Application_stop_stopping = ""; //$NON-NLS-1$


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

    // --- Application ------------------------------------------------------

    /**
     * Gets the formatted message indicating the application is starting.
     * 
     * @param version
     *        The application version.
     * 
     * @return The formatted message indicating the application is starting.
     */
    static String Application_start_starting(
        final Version version )
    {
        return bind( Application_start_starting, version.toString() );
    }

    /**
     * Gets the formatted message indicating the application has stopped.
     * 
     * @param result
     *        The table result.
     * 
     * @return The formatted message indicating the application has stopped.
     */
    static String Application_start_stopped(
        final TableResult result )
    {
        return bind( Application_start_stopped, result.toString() );
    }
}
