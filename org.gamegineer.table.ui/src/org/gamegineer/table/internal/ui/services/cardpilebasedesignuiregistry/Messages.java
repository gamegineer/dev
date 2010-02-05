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

package org.gamegineer.table.internal.ui.services.cardpilebasedesignuiregistry;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.core.CardPileBaseDesignId;
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
    private static final String BUNDLE_NAME = "org.gamegineer.table.internal.ui.services.cardpilebasedesignuiregistry.Messages"; //$NON-NLS-1$

    // --- CardPileBaseDesignUIRegistry -------------------------------------

    /** The bundle hosting the card pile base design icon was not found. */
    public static String CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_iconBundleNotFound;

    /** The card pile base design icon file was not found. */
    public static String CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_iconFileNotFound;

    /** The card pile base design icon path is missing. */
    public static String CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_missingIconPath;

    /** The card pile base design identifier is missing. */
    public static String CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_missingId;

    /** The card pile base design name is missing. */
    public static String CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_missingName;

    /** A duplicate card pile base design identifier was detected. */
    public static String CardPileBaseDesignUIRegistry_getCardPileBaseDesignUIMap_duplicateId;

    /**
     * An error occurred while parsing the card pile base design user interface
     * definition.
     */
    public static String CardPileBaseDesignUIRegistry_getForeignCardPileBaseDesignUIs_parseError;


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

    // --- CardPileBaseDesignUIRegistry -------------------------------------

    /**
     * Gets the formatted message indicating the bundle hosting the card pile
     * base design icon file was not found.
     * 
     * @param name
     *        The bundle name; must not be {@code null}.
     * 
     * @return The formatted message indicating the bundle hosting the card pile
     *         base design icon file was not found; never {@code null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_iconBundleNotFound(
        /* @NonNull */
        final String name )
    {
        return bind( CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_iconBundleNotFound, name );
    }

    /**
     * Gets the formatted message indicating the card pile base design icon file
     * was not found.
     * 
     * @param bundle
     *        The bundle hosting the card pile base design icon file; must not
     *        be {@code null}.
     * @param path
     *        The bundle path of the card pile base design icon file; must not
     *        be {@code null}.
     * 
     * @return The formatted message indicating the card pile base design icon
     *         file was not found; never {@code null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_iconFileNotFound(
        /* @NonNull */
        final Bundle bundle,
        /* @NonNull */
        final String path )
    {
        return bind( CardPileBaseDesignUIRegistry_createCardPileBaseDesignUI_iconFileNotFound, bundle.getSymbolicName(), path );
    }

    /**
     * Gets the formatted message indicating a duplicate card pile base design
     * identifier was detected.
     * 
     * @param cardPileBaseDesignId
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a duplicate card pile base
     *         design identifier was detected; never {@code null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignUIRegistry_getCardPileBaseDesignUIMap_duplicateId(
        /* @NonNull */
        final CardPileBaseDesignId cardPileBaseDesignId )
    {
        return bind( CardPileBaseDesignUIRegistry_getCardPileBaseDesignUIMap_duplicateId, cardPileBaseDesignId );
    }

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * card pile base design user interface definition.
     * 
     * @param cardPileBaseDesignId
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the card pile base design user interface definition; never
     *         {@code null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignUIRegistry_getForeignCardPileBaseDesignUIs_parseError(
        /* @NonNull */
        final String cardPileBaseDesignId )
    {
        return bind( CardPileBaseDesignUIRegistry_getForeignCardPileBaseDesignUIs_parseError, cardPileBaseDesignId );
    }
}
