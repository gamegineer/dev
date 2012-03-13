/*
 * NonNlsMessages.java
 * Copyright 2008-2012 Gamegineer.org
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

package org.gamegineer.common.internal.core.logging;

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

    // --- AbstractLoggingComponentFactory ----------------------------------

    /** Logging component creation failed. */
    public static String AbstractLoggingComponentFactory_createLoggingComponent_failed;

    /** The fully-qualified component name must contain at least one dot. */
    public static String AbstractLoggingComponentFactory_createNamedLoggingComponent_nameNoDots;

    /** A component factory is no longer available. */
    public static String AbstractLoggingComponentFactory_createNamedLoggingComponent_noComponentFactoryAvailable;

    /** No component factory is available. */
    public static String AbstractLoggingComponentFactory_findComponentFactory_noComponentFactoryAvailable;

    /** The filter syntax is invalid. */
    public static String AbstractLoggingComponentFactory_getComponentFactory_invalidFilterSyntax;

    /** The property value is illegal. */
    public static String AbstractLoggingComponentFactory_getComponentProperty_illegalPropertyValue;

    /** No component properties specified. */
    public static String AbstractLoggingComponentFactory_newInstance_noComponentProperties;

    // --- FrameworkLogHandlerFactory ---------------------------------------

    /** An illegal component type name was specified. */
    public static String FrameworkLogHandlerFactory_createLoggingComponent_illegalTypeName;

    /** No framework log service is available. */
    public static String FrameworkLogHandlerFactory_createLoggingComponent_noFrameworkLogAvailable;


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

    // --- AbstractLoggingComponentFactory ----------------------------------

    /**
     * Gets the formatted message indicating logging component creation failed.
     * 
     * @param typeName
     *        The type name of the logging component; must not be {@code null}.
     * 
     * @return The formatted message indicating logging component creation
     *         failed; never {@code null}.
     */
    /* @NonNull */
    static String AbstractLoggingComponentFactory_createLoggingComponent_failed(
        /* @NonNull */
        final String typeName )
    {
        return bind( AbstractLoggingComponentFactory_createLoggingComponent_failed, typeName );
    }

    /**
     * Gets the formatted message indicating no component factory is available
     * for the specified logging component type.
     * 
     * @param typeName
     *        The type name of the logging component; must not be {@code null}.
     * 
     * @return The formatted message indicating no component factory is
     *         available for the specified logging component type; never
     *         {@code null}.
     */
    /* @NonNull */
    static String AbstractLoggingComponentFactory_findComponentFactory_noComponentFactoryAvailable(
        /* @NonNull */
        final String typeName )
    {
        return bind( AbstractLoggingComponentFactory_findComponentFactory_noComponentFactoryAvailable, typeName );
    }

    /**
     * Gets the formatted message indicating the property value is illegal.
     * 
     * @param propertyName
     *        The property name; must not be {@code null}.
     * 
     * @return The formatted message indicating the property value is illegal;
     *         never {@code null}.
     */
    /* @NonNull */
    static String AbstractLoggingComponentFactory_getComponentProperty_illegalPropertyValue(
        /* @NonNull */
        final String propertyName )
    {
        return bind( AbstractLoggingComponentFactory_getComponentProperty_illegalPropertyValue, propertyName );
    }
}
