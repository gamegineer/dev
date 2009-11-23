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
 * Created on Nov 21, 2009 at 10:40:49 PM.
 */

package org.gamegineer.table.internal.ui.services.carddesignuiregistry;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.core.CardDesignId;
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

    /** The name of the associated resource bundle. */
    private static final String BUNDLE_NAME = "org.gamegineer.table.internal.ui.services.carddesignuiregistry.Messages"; //$NON-NLS-1$

    // --- CardDesignUIRegistry ---------------------------------------------

    /** The bundle hosting the card design icon was not found. */
    public static String CardDesignUIRegistry_createCardDesignUI_iconBundleNotFound;

    /** The card design icon file was not found. */
    public static String CardDesignUIRegistry_createCardDesignUI_iconFileNotFound;

    /** The card design icon path is missing. */
    public static String CardDesignUIRegistry_createCardDesignUI_missingIconPath;

    /** The card design identifier is missing. */
    public static String CardDesignUIRegistry_createCardDesignUI_missingId;

    /** The card design name is missing. */
    public static String CardDesignUIRegistry_createCardDesignUI_missingName;

    /** A duplicate card design identifier was detected. */
    public static String CardDesignUIRegistry_getCardDesignUIMap_duplicateId;

    /**
     * An error occurred while parsing the card design user interface
     * definition.
     */
    public static String CardDesignUIRegistry_getForeignCardDesignUIs_parseError;


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

    // --- CardDesignUIRegistry ---------------------------------------------

    /**
     * Gets the formatted message indicating the bundle hosting the card design
     * icon file was not found.
     * 
     * @param name
     *        The bundle name; must not be {@code null}.
     * 
     * @return The formatted message indicating the bundle hosting the card
     *         design icon file was not found; never {@code null}.
     */
    /* @NonNull */
    static String CardDesignUIRegistry_createCardDesignUI_iconBundleNotFound(
        /* @NonNull */
        final String name )
    {
        return bind( CardDesignUIRegistry_createCardDesignUI_iconBundleNotFound, name );
    }

    /**
     * Gets the formatted message indicating the card design icon file was not
     * found.
     * 
     * @param bundle
     *        The bundle hosting the card design icon file; must not be {@code
     *        null}.
     * @param path
     *        The bundle path of the card design icon file; must not be {@code
     *        null}.
     * 
     * @return The formatted message indicating the card design icon file was
     *         not found; never {@code null}.
     */
    /* @NonNull */
    static String CardDesignUIRegistry_createCardDesignUI_iconFileNotFound(
        /* @NonNull */
        final Bundle bundle,
        /* @NonNull */
        final String path )
    {
        return bind( CardDesignUIRegistry_createCardDesignUI_iconFileNotFound, bundle.getSymbolicName(), path );
    }

    /**
     * Gets the formatted message indicating a duplicate card design identifier
     * was detected.
     * 
     * @param cardDesignId
     *        The card design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a duplicate card design
     *         identifier was detected; never {@code null}.
     */
    /* @NonNull */
    static String CardDesignUIRegistry_getCardDesignUIMap_duplicateId(
        /* @NonNull */
        final CardDesignId cardDesignId )
    {
        return bind( CardDesignUIRegistry_getCardDesignUIMap_duplicateId, cardDesignId );
    }

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * card design user interface definition.
     * 
     * @param cardDesignId
     *        The card design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the card design user interface definition; never {@code null}.
     */
    /* @NonNull */
    static String CardDesignUIRegistry_getForeignCardDesignUIs_parseError(
        /* @NonNull */
        final String cardDesignId )
    {
        return bind( CardDesignUIRegistry_getForeignCardDesignUIs_parseError, cardDesignId );
    }
}
