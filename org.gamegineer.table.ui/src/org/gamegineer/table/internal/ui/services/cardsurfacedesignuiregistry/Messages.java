/*
 * Messages.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Nov 21, 2009 at 10:40:49 PM.
 */

package org.gamegineer.table.internal.ui.services.cardsurfacedesignuiregistry;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.core.CardSurfaceDesignId;
import org.osgi.framework.Bundle;

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

    // --- CardSurfaceDesignUIRegistry --------------------------------------

    /** The bundle hosting the card surface design icon was not found. */
    public static String CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_iconBundleNotFound;

    /** The card surface design icon file was not found. */
    public static String CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_iconFileNotFound;

    /** The card surface design icon path is missing. */
    public static String CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_missingIconPath;

    /** The card surface design identifier is missing. */
    public static String CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_missingId;

    /** The card surface design name is missing. */
    public static String CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_missingName;

    /** No package administration service is available. */
    public static String CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_noPackageAdminService;

    /** A duplicate card surface design identifier was detected. */
    public static String CardSurfaceDesignUIRegistry_getCardSurfaceDesignUIMap_duplicateId;

    /** The extension registry is not available. */
    public static String CardSurfaceDesignUIRegistry_getForeignCardSurfaceDesignUIs_noExtensionRegistry;

    /**
     * An error occurred while parsing the card surface design user interface
     * definition.
     */
    public static String CardSurfaceDesignUIRegistry_getForeignCardSurfaceDesignUIs_parseError;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Messages} class.
     */
    static
    {
        NLS.initializeMessages( Messages.class.getName(), Messages.class );
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

    // --- CardSurfaceDesignUIRegistry --------------------------------------

    /**
     * Gets the formatted message indicating the bundle hosting the card surface
     * design icon file was not found.
     * 
     * @param name
     *        The bundle name; must not be {@code null}.
     * 
     * @return The formatted message indicating the bundle hosting the card
     *         surface design icon file was not found; never {@code null}.
     */
    /* @NonNull */
    static String CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_iconBundleNotFound(
        /* @NonNull */
        final String name )
    {
        return bind( CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_iconBundleNotFound, name );
    }

    /**
     * Gets the formatted message indicating the card surface design icon file
     * was not found.
     * 
     * @param bundle
     *        The bundle hosting the card surface design icon file; must not be
     *        {@code null}.
     * @param path
     *        The bundle path of the card surface design icon file; must not be
     *        {@code null}.
     * 
     * @return The formatted message indicating the card surface design icon
     *         file was not found; never {@code null}.
     */
    /* @NonNull */
    static String CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_iconFileNotFound(
        /* @NonNull */
        final Bundle bundle,
        /* @NonNull */
        final String path )
    {
        return bind( CardSurfaceDesignUIRegistry_createCardSurfaceDesignUI_iconFileNotFound, bundle.getSymbolicName(), path );
    }

    /**
     * Gets the formatted message indicating a duplicate card surface design
     * identifier was detected.
     * 
     * @param cardSurfaceDesignId
     *        The card surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a duplicate card surface design
     *         identifier was detected; never {@code null}.
     */
    /* @NonNull */
    static String CardSurfaceDesignUIRegistry_getCardSurfaceDesignUIMap_duplicateId(
        /* @NonNull */
        final CardSurfaceDesignId cardSurfaceDesignId )
    {
        return bind( CardSurfaceDesignUIRegistry_getCardSurfaceDesignUIMap_duplicateId, cardSurfaceDesignId );
    }

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * card surface design user interface definition.
     * 
     * @param cardSurfaceDesignId
     *        The card surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the card surface design user interface definition; never {@code
     *         null}.
     */
    /* @NonNull */
    static String CardSurfaceDesignUIRegistry_getForeignCardSurfaceDesignUIs_parseError(
        /* @NonNull */
        final String cardSurfaceDesignId )
    {
        return bind( CardSurfaceDesignUIRegistry_getForeignCardSurfaceDesignUIs_parseError, cardSurfaceDesignId );
    }
}
