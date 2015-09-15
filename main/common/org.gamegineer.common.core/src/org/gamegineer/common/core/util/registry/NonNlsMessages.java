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
 * Created on Nov 16, 2012 at 9:44:28 PM.
 */

package org.gamegineer.common.core.util.registry;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.core.runtime.IConfigurationElement;
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

    // --- AbstractRegistry -------------------------------------------------

    /** An object is already registered for the specified identifier. */
    public static String AbstractRegistry_registerObject_object_registered = ""; //$NON-NLS-1$

    /** The object is not registered for the specified identifier. */
    public static String AbstractRegistry_unregisterObject_object_unregistered = ""; //$NON-NLS-1$

    // --- AbstractRegistryExtensionPointAdapter ----------------------------

    /** The extension registry service is already bound. */
    public static String AbstractRegistryExtensionPointAdapter_bindExtensionRegistry_bound = ""; //$NON-NLS-1$

    /** The object registry service is already bound. */
    public static String AbstractRegistryExtensionPointAdapter_bindObjectRegistry_bound = ""; //$NON-NLS-1$

    /** An error occurred while parsing the object configuration element. */
    public static String AbstractRegistryExtensionPointAdapter_registerObject_parseError = ""; //$NON-NLS-1$

    /** The extension registry service is not bound. */
    public static String AbstractRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound = ""; //$NON-NLS-1$

    /** The object registry service is not bound. */
    public static String AbstractRegistryExtensionPointAdapter_unbindObjectRegistry_notBound = ""; //$NON-NLS-1$


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

    // --- AbstractRegistry -------------------------------------------------

    /**
     * Gets the formatted message indicating an object is already registered for
     * the specified identifier.
     * 
     * @param id
     *        The object identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an object is already registered
     *         for the specified identifier; never {@code null}.
     */
    static String AbstractRegistry_registerObject_object_registered(
        final Object id )
    {
        return bind( AbstractRegistry_registerObject_object_registered, id );
    }

    /**
     * Gets the formatted message indicating the object is not registered for
     * the specified identifier.
     * 
     * @param id
     *        The object identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the object is not registered for
     *         the specified identifier; never {@code null}.
     */
    static String AbstractRegistry_unregisterObject_object_unregistered(
        final Object id )
    {
        return bind( AbstractRegistry_unregisterObject_object_unregistered, id );
    }

    // --- AbstractRegistryExtensionPointAdapter ----------------------------

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * object configuration element.
     * 
     * @param configurationElement
     *        The configuration element; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the object configuration element; never {@code null}.
     */
    static String AbstractRegistryExtensionPointAdapter_registerObject_parseError(
        final IConfigurationElement configurationElement )
    {
        return bind( AbstractRegistryExtensionPointAdapter_registerObject_parseError, configurationElement.getDeclaringExtension().getUniqueIdentifier(), configurationElement.getContributor().getName() );
    }
}
