/*
 * NonNlsMessages.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Sep 19, 2009 at 12:12:47 AM.
 */

package org.gamegineer.table.internal.ui.impl;

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import java.net.URL;
import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

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

    // --- Activator --------------------------------------------------------

    /** An error occurred while saving the user preferences. */
    public static String Activator_saveUserPreferenecs_error = ""; //$NON-NLS-1$

    // --- BundleImages -----------------------------------------------------

    /** An error occurred while reading the image. */
    public static String BundleImages_getImage_readError = ""; //$NON-NLS-1$

    // --- ComponentStrategyUIRegistryExtensionPointAdapter -----------------

    /**
     * An error occurred while creating the component strategy user interface of
     * a component strategy user interface configuration element.
     */
    public static String ComponentStrategyUIRegistryExtensionPointAdapter_createObject_createComponentStrategyUIError = ""; //$NON-NLS-1$

    /** The component strategy identifier is missing. */
    public static String ComponentStrategyUIRegistryExtensionPointAdapter_createObject_missingId = ""; //$NON-NLS-1$

    // --- ComponentSurfaceDesignUIRegistryExtensionPointAdapter ------------

    /** The bundle hosting the component surface design icon was not found. */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_iconBundleNotFound = ""; //$NON-NLS-1$

    /** The component surface design icon file was not found. */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_iconFileNotFound = ""; //$NON-NLS-1$

    /** The component surface design icon path is missing. */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_missingIconPath = ""; //$NON-NLS-1$

    /** The component surface design identifier is missing. */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_missingId = ""; //$NON-NLS-1$

    /** The component surface design name is missing. */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_missingName = ""; //$NON-NLS-1$

    // --- TableRunner ------------------------------------------------------

    /** The frame window could not be opened. */
    public static String TableRunner_openFrame_error = ""; //$NON-NLS-1$

    /** The table environment factory service is not available. */
    public static String TableRunner_openFrame_tableEnvironmentFactoryNotAvailable = ""; //$NON-NLS-1$

    /** The table network factory service is not available. */
    public static String TableRunner_openFrame_tableNetworkFactoryNotAvailable = ""; //$NON-NLS-1$

    /** The runner is already running or has already finished. */
    public static String TableRunner_state_notPristine = ""; //$NON-NLS-1$


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

    // --- BundleImages -----------------------------------------------------

    /**
     * Gets the formatted message indicating an error occurred while reading the
     * specified image.
     * 
     * @param imageUrl
     *        The URL of the image; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while reading
     *         the specified image; never {@code null}.
     */
    static String BundleImages_getImage_readError(
        final URL imageUrl )
    {
        return nonNull( bind( BundleImages_getImage_readError, imageUrl ) );
    }

    // --- ComponentSurfaceDesignUIRegistryExtensionPointAdapter ------------

    /**
     * Gets the formatted message indicating the bundle hosting the component
     * surface design icon file was not found.
     * 
     * @param name
     *        The bundle name; must not be {@code null}.
     * 
     * @return The formatted message indicating the bundle hosting the component
     *         surface design icon file was not found; never {@code null}.
     */
    static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_iconBundleNotFound(
        final String name )
    {
        return nonNull( bind( ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_iconBundleNotFound, name ) );
    }

    /**
     * Gets the formatted message indicating the component surface design icon
     * file was not found.
     * 
     * @param bundle
     *        The bundle hosting the component surface design icon file; must
     *        not be {@code null}.
     * @param path
     *        The bundle path of the component surface design icon file; must
     *        not be {@code null}.
     * 
     * @return The formatted message indicating the component surface design
     *         icon file was not found; never {@code null}.
     */
    static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_iconFileNotFound(
        final Bundle bundle,
        final String path )
    {
        return nonNull( bind( ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createObject_iconFileNotFound, bundle.getSymbolicName(), path ) );
    }
}
