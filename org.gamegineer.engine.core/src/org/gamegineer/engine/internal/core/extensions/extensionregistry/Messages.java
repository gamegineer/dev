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
 * Created on May 11, 2008 at 10:52:07 PM.
 */

package org.gamegineer.engine.internal.core.extensions.extensionregistry;

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
    private static final String BUNDLE_NAME = "org.gamegineer.engine.internal.core.extensions.extensionregistry.Messages"; //$NON-NLS-1$

    // --- Common -----------------------------------------------------------

    /** The extension registry extension is not available. */
    public static String Common_extensionRegistryExtension_unavailable;

    // --- ExtensionRegistryExtension ---------------------------------------

    /** The extension registry does not exist. */
    public static String ExtensionRegistryExtension_getExtensionRegistry_noExtensionRegistry;

    /** Unexpected exception thrown from IExtension.start(). */
    public static String ExtensionRegistryExtension_registerExtension_unexpectedException;

    /** The extension does not match the registered extension. */
    public static String ExtensionRegistryExtension_unregisterExtension_extensionUnregistered;

    /** The extension failed to stop. */
    public static String ExtensionRegistryExtension_unregisterExtension_fail;

    /** Unexpected exception thrown from IExtension.stop(). */
    public static String ExtensionRegistryExtension_unregisterExtension_unexpectedException;


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

    /**
     * Gets the formatted message indicating an unexpected exception was thrown
     * from IExtension.start().
     * 
     * @param extensionTypeName
     *        The extension type name; must not be {@code null}.
     * 
     * @return The formatted message indicating an unexpected exception was
     *         thrown from IExtension.start(); never {@code null}.
     */
    /* @NonNull */
    static String ExtensionRegistryExtension_registerExtension_unexpectedException(
        /* @NonNull */
        final String extensionTypeName )
    {
        return bind( ExtensionRegistryExtension_registerExtension_unexpectedException, extensionTypeName );
    }

    /**
     * Gets the formatted message indicating the extension failed to stop.
     * 
     * @param extensionTypeName
     *        The extension type name; must not be {@code null}.
     * 
     * @return The formatted message indicating the extension failed to stop;
     *         never {@code null}.
     */
    /* @NonNull */
    static String ExtensionRegistryExtension_unregisterExtension_fail(
        /* @NonNull */
        final String extensionTypeName )
    {
        return bind( ExtensionRegistryExtension_unregisterExtension_fail, extensionTypeName );
    }

    /**
     * Gets the formatted message indicating an unexpected exception was thrown
     * from IExtension.stop().
     * 
     * @param extensionTypeName
     *        The extension type name; must not be {@code null}.
     * 
     * @return The formatted message indicating an unexpected exception was
     *         thrown from IExtension.stop(); never {@code null}.
     */
    /* @NonNull */
    static String ExtensionRegistryExtension_unregisterExtension_unexpectedException(
        /* @NonNull */
        final String extensionTypeName )
    {
        return bind( ExtensionRegistryExtension_unregisterExtension_unexpectedException, extensionTypeName );
    }
}
