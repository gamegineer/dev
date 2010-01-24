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
 * Created on Jan 23, 2010 at 9:37:53 PM.
 */

package org.gamegineer.table.internal.ui.services.cardpiledesignuiregistry;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.core.CardPileDesignId;
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
    private static final String BUNDLE_NAME = "org.gamegineer.table.internal.ui.services.cardpiledesignuiregistry.Messages"; //$NON-NLS-1$

    // --- CardPileDesignUIRegistry -----------------------------------------

    /** The bundle hosting the card pile design icon was not found. */
    public static String CardPileDesignUIRegistry_createCardPileDesignUI_iconBundleNotFound;

    /** The card pile design icon file was not found. */
    public static String CardPileDesignUIRegistry_createCardPileDesignUI_iconFileNotFound;

    /** The card pile design icon path is missing. */
    public static String CardPileDesignUIRegistry_createCardPileDesignUI_missingIconPath;

    /** The card pile design identifier is missing. */
    public static String CardPileDesignUIRegistry_createCardPileDesignUI_missingId;

    /** The card pile design name is missing. */
    public static String CardPileDesignUIRegistry_createCardPileDesignUI_missingName;

    /** A duplicate card pile design identifier was detected. */
    public static String CardPileDesignUIRegistry_getCardPileDesignUIMap_duplicateId;

    /**
     * An error occurred while parsing the card pile design user interface
     * definition.
     */
    public static String CardPileDesignUIRegistry_getForeignCardPileDesignUIs_parseError;


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

    // --- CardPileDesignUIRegistry -----------------------------------------

    /**
     * Gets the formatted message indicating the bundle hosting the card pile
     * design icon file was not found.
     * 
     * @param name
     *        The bundle name; must not be {@code null}.
     * 
     * @return The formatted message indicating the bundle hosting the card pile
     *         design icon file was not found; never {@code null}.
     */
    /* @NonNull */
    static String CardPileDesignUIRegistry_createCardPileDesignUI_iconBundleNotFound(
        /* @NonNull */
        final String name )
    {
        return bind( CardPileDesignUIRegistry_createCardPileDesignUI_iconBundleNotFound, name );
    }

    /**
     * Gets the formatted message indicating the card pile design icon file was
     * not found.
     * 
     * @param bundle
     *        The bundle hosting the card pile design icon file; must not be
     *        {@code null}.
     * @param path
     *        The bundle path of the card pile design icon file; must not be
     *        {@code null}.
     * 
     * @return The formatted message indicating the card pile design icon file
     *         was not found; never {@code null}.
     */
    /* @NonNull */
    static String CardPileDesignUIRegistry_createCardPileDesignUI_iconFileNotFound(
        /* @NonNull */
        final Bundle bundle,
        /* @NonNull */
        final String path )
    {
        return bind( CardPileDesignUIRegistry_createCardPileDesignUI_iconFileNotFound, bundle.getSymbolicName(), path );
    }

    /**
     * Gets the formatted message indicating a duplicate card pile design
     * identifier was detected.
     * 
     * @param cardPileDesignId
     *        The card pile design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a duplicate card pile design
     *         identifier was detected; never {@code null}.
     */
    /* @NonNull */
    static String CardPileDesignUIRegistry_getCardPileDesignUIMap_duplicateId(
        /* @NonNull */
        final CardPileDesignId cardPileDesignId )
    {
        return bind( CardPileDesignUIRegistry_getCardPileDesignUIMap_duplicateId, cardPileDesignId );
    }

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * card pile design user interface definition.
     * 
     * @param cardPileDesignId
     *        The card pile design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the card pile design user interface definition; never {@code
     *         null}.
     */
    /* @NonNull */
    static String CardPileDesignUIRegistry_getForeignCardPileDesignUIs_parseError(
        /* @NonNull */
        final String cardPileDesignId )
    {
        return bind( CardPileDesignUIRegistry_getForeignCardPileDesignUIs_parseError, cardPileDesignId );
    }
}
