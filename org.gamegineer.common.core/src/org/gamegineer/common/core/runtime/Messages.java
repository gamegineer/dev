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
 * Created on May 26, 2008 at 9:48:38 PM.
 */

package org.gamegineer.common.core.runtime;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

/**
 * A utility class to manage localized messages for the package.
 */
@ThreadSafe
final class Messages
    extends NLS
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the associated resource bundle. */
    private static final String BUNDLE_NAME = "org.gamegineer.common.core.runtime.Messages"; //$NON-NLS-1$

    // --- Assert -----------------------------------------------------------

    /** The default message for an illegal argument exception. */
    public static String Assert_assertArgumentLegal_defaultMessage;

    /** The default message for a null pointer exception. */
    public static String Assert_assertArgumentNotNull_defaultMessage;

    /** The default parameter name for exception messages. */
    public static String Assert_defaultParamName;

    /** The message format for all exceptions. */
    public static String Assert_message;


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

    // --- Assert -----------------------------------------------------------

    /**
     * Gets the formatted message for all exceptions.
     * 
     * @param paramName
     *        The name of the parameter that caused the exception; must not be
     *        {@code null}.
     * @param message
     *        The exception detail message; must not be {@code null}.
     * 
     * @return The formatted message for all exceptions; never {@code null}.
     */
    /* @NonNull */
    static String Assert_message(
        /* @NonNull */
        final String paramName,
        /* @NonNull */
        final String message )
    {
        return bind( Assert_message, paramName, message );
    }
}
