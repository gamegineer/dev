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
 * Created on May 22, 2008 at 11:19:18 PM.
 */

package org.gamegineer.common.internal.core.impl.logging;

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
    public static String AbstractLoggingComponentFactory_createLoggingComponent_failed = ""; //$NON-NLS-1$

    /** The fully-qualified component name must contain at least one dot. */
    public static String AbstractLoggingComponentFactory_createNamedLoggingComponent_nameNoDots = ""; //$NON-NLS-1$

    /** A component factory is no longer available. */
    public static String AbstractLoggingComponentFactory_createNamedLoggingComponent_noComponentFactoryAvailable = ""; //$NON-NLS-1$

    /** No component factory is available. */
    public static String AbstractLoggingComponentFactory_findComponentFactory_noComponentFactoryAvailable = ""; //$NON-NLS-1$

    /** The filter syntax is invalid. */
    public static String AbstractLoggingComponentFactory_getComponentFactory_invalidFilterSyntax = ""; //$NON-NLS-1$

    /** The property value is illegal. */
    public static String AbstractLoggingComponentFactory_getComponentProperty_illegalPropertyValue = ""; //$NON-NLS-1$

    /** No component properties specified. */
    public static String AbstractLoggingComponentFactory_newInstance_noComponentProperties = ""; //$NON-NLS-1$

    // --- FrameworkLogHandlerFactory ---------------------------------------

    /** An illegal component type name was specified. */
    public static String FrameworkLogHandlerFactory_createLoggingComponent_illegalTypeName = ""; //$NON-NLS-1$

    /** No framework log service is available. */
    public static String FrameworkLogHandlerFactory_createLoggingComponent_noFrameworkLogAvailable = ""; //$NON-NLS-1$


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
     *        The type name of the logging component.
     * 
     * @return The formatted message indicating logging component creation
     *         failed.
     */
    static String AbstractLoggingComponentFactory_createLoggingComponent_failed(
        final String typeName )
    {
        return bind( AbstractLoggingComponentFactory_createLoggingComponent_failed, typeName );
    }

    /**
     * Gets the formatted message indicating no component factory is available
     * for the specified logging component type.
     * 
     * @param typeName
     *        The type name of the logging component.
     * 
     * @return The formatted message indicating no component factory is
     *         available for the specified logging component type.
     */
    static String AbstractLoggingComponentFactory_findComponentFactory_noComponentFactoryAvailable(
        final String typeName )
    {
        return bind( AbstractLoggingComponentFactory_findComponentFactory_noComponentFactoryAvailable, typeName );
    }

    /**
     * Gets the formatted message indicating the property value is illegal.
     * 
     * @param propertyName
     *        The property name.
     * 
     * @return The formatted message indicating the property value is illegal.
     */
    static String AbstractLoggingComponentFactory_getComponentProperty_illegalPropertyValue(
        final String propertyName )
    {
        return bind( AbstractLoggingComponentFactory_getComponentProperty_illegalPropertyValue, propertyName );
    }
}
