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
 * Created on May 26, 2008 at 9:48:38 PM.
 */

package org.gamegineer.common.core.runtime;

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

    // --- Assert -----------------------------------------------------------

    /** The default message for an illegal argument exception. */
    public static String Assert_assertArgumentLegal_defaultMessage = ""; //$NON-NLS-1$

    /** The default parameter name for exception messages. */
    public static String Assert_defaultParamName = ""; //$NON-NLS-1$

    /** The message format for all exceptions. */
    public static String Assert_message = ""; //$NON-NLS-1$


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

    // --- Assert -----------------------------------------------------------

    /**
     * Gets the formatted message for all exceptions.
     * 
     * @param paramName
     *        The name of the parameter that caused the exception.
     * @param message
     *        The exception detail message.
     * 
     * @return The formatted message for all exceptions.
     */
    static String Assert_message(
        final String paramName,
        final String message )
    {
        return bind( Assert_message, paramName, message );
    }
}
