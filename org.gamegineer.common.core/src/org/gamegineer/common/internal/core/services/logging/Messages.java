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
 * Created on May 22, 2008 at 11:19:18 PM.
 */

package org.gamegineer.common.internal.core.services.logging;

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
    private static final String BUNDLE_NAME = "org.gamegineer.common.internal.core.services.logging.Messages"; //$NON-NLS-1$

    // --- AbstractLoggingComponentFactory ----------------------------------

    /** The requested class name is unsupported. */
    public static String AbstractLoggingComponentFactory_createComponent_unsupportedType;

    /** Logging component creation failed. */
    public static String AbstractLoggingComponentFactory_createLoggingComponent_failed;

    /** The fully-qualified component name must contain at least one dot. */
    public static String AbstractLoggingComponentFactory_createNamedLoggingComponent_nameNoDots;


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

    // --- AbstractLoggingComponentFactory ----------------------------------

    /**
     * Gets the formatted message indicating the requested class name is
     * unsupported.
     * 
     * @param className
     *        The class name; must not be {@code null}.
     * 
     * @return The formatted message indicating the requested class name is
     *         unsupported; never {@code null}.
     */
    /* @NonNull */
    static String AbstractLoggingComponentFactory_createComponent_unsupportedType(
        /* @NonNull */
        final String className )
    {
        return bind( AbstractLoggingComponentFactory_createComponent_unsupportedType, className );
    }

    /**
     * Gets the formatted message indicating logging component creation failed.
     * 
     * @param instanceName
     *        The instance name of the logging component; must not be
     *        {@code null}.
     * @param className
     *        The class name of the logging component; must not be {@code null}.
     * 
     * @return The formatted message indicating logging component creation
     *         failed; never {@code null}.
     */
    /* @NonNull */
    static String AbstractLoggingComponentFactory_createLoggingComponent_failed(
        /* @NonNull */
        final String instanceName,
        /* @NonNull */
        final String className )
    {
        return bind( AbstractLoggingComponentFactory_createLoggingComponent_failed, instanceName, className );
    }
}
