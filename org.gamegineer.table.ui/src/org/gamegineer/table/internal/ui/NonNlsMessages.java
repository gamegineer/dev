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
 * Created on Sep 19, 2009 at 12:12:47 AM.
 */

package org.gamegineer.table.internal.ui;

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
    public static String Activator_saveUserPreferenecs_error;

    // --- BundleImages -----------------------------------------------------

    /** An error occurred while reading the image. */
    public static String BundleImages_getImage_readError;

    // --- ComponentStrategyUIRegistryExtensionPointAdapter -----------------

    /** The component strategy user interface registry service is already bound. */
    public static String ComponentStrategyUIRegistryExtensionPointAdapter_bindComponentStrategyUIRegistry_bound;

    /** The extension registry service is already bound. */
    public static String ComponentStrategyUIRegistryExtensionPointAdapter_bindExtensionRegistry_bound;

    /**
     * An error occurred while creating the component strategy user interface of
     * a component strategy user interface configuration element.
     */
    public static String ComponentStrategyUIRegistryExtensionPointAdapter_createComponentStrategyUIRegistration_createComponentStrategyUIError;

    /** The component strategy identifier is missing. */
    public static String ComponentStrategyUIRegistryExtensionPointAdapter_createComponentStrategyUIRegistration_missingId;

    /**
     * An error occurred while parsing the component strategy user interface
     * configuration element.
     */
    public static String ComponentStrategyUIRegistryExtensionPointAdapter_registerComponentStrategyUI_parseError;

    /** The component strategy user interface registry service is not bound. */
    public static String ComponentStrategyUIRegistryExtensionPointAdapter_unbindComponentStrategyUIRegistry_notBound;

    /** The extension registry service is not bound. */
    public static String ComponentStrategyUIRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound;

    // --- ComponentSurfaceDesignUIRegistryExtensionPointAdapter ------------

    /**
     * The component surface design user interface registry service is already
     * bound.
     */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_bindComponentSurfaceDesignUIRegistry_bound;

    /** The extension registry service is already bound. */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_bindExtensionRegistry_bound;

    /** The bundle hosting the component surface design icon was not found. */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_iconBundleNotFound;

    /** The component surface design icon file was not found. */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_iconFileNotFound;

    /** The component surface design icon path is missing. */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_missingIconPath;

    /** The component surface design identifier is missing. */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_missingId;

    /** The component surface design name is missing. */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_missingName;

    /**
     * An error occurred while parsing the component surface design user
     * interface configuration element.
     */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_registerComponentSurfaceDesignUI_parseError;

    /**
     * The component surface design user interface registry service is not
     * bound.
     */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_unbindComponentSurfaceDesignUIRegistry_notBound;

    /** The extension registry service is not bound. */
    public static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound;

    // --- TableRunner ------------------------------------------------------

    /** The frame window could not be opened. */
    public static String TableRunner_openFrame_error;

    /** The runner is already running or has already finished. */
    public static String TableRunner_state_notPristine;


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
    /* @NonNull */
    static String BundleImages_getImage_readError(
        /* @NonNull */
        final URL imageUrl )
    {
        return bind( BundleImages_getImage_readError, imageUrl );
    }

    // --- ComponentStrategyUIRegistryExtensionPointAdapter -----------------

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * component strategy user interface configuration element.
     * 
     * @param componentStrategyId
     *        The component strategy identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the component strategy user interface configuration element;
     *         never {@code null}.
     */
    /* @NonNull */
    static String ComponentStrategyUIRegistryExtensionPointAdapter_registerComponentStrategyUI_parseError(
        /* @NonNull */
        final String componentStrategyId )
    {
        return bind( ComponentStrategyUIRegistryExtensionPointAdapter_registerComponentStrategyUI_parseError, componentStrategyId );
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
    /* @NonNull */
    static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_iconBundleNotFound(
        /* @NonNull */
        final String name )
    {
        return bind( ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_iconBundleNotFound, name );
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
    /* @NonNull */
    static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_iconFileNotFound(
        /* @NonNull */
        final Bundle bundle,
        /* @NonNull */
        final String path )
    {
        return bind( ComponentSurfaceDesignUIRegistryExtensionPointAdapter_createComponentSurfaceDesignUIRegistration_iconFileNotFound, bundle.getSymbolicName(), path );
    }

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * component surface design user interface configuration element.
     * 
     * @param componentSurfaceDesignId
     *        The component surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the component surface design user interface configuration
     *         element; never {@code null}.
     */
    /* @NonNull */
    static String ComponentSurfaceDesignUIRegistryExtensionPointAdapter_registerComponentSurfaceDesignUI_parseError(
        /* @NonNull */
        final String componentSurfaceDesignId )
    {
        return bind( ComponentSurfaceDesignUIRegistryExtensionPointAdapter_registerComponentSurfaceDesignUI_parseError, componentSurfaceDesignId );
    }
}
