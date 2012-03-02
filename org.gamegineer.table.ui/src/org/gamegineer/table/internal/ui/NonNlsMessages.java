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
import org.gamegineer.table.core.CardPileBaseDesignId;
import org.gamegineer.table.core.CardSurfaceDesignId;
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

    // --- CardPileBaseDesignUIRegistry -------------------------------------

    /**
     * A card pile base design user interface is already registered for the
     * specified identifier.
     */
    public static String CardPileBaseDesignUIRegistry_registerCardPileBaseDesignUI_cardPileBaseDesignUI_registered;

    /**
     * The card pile base design user interface is not registered for the
     * specified identifier.
     */
    public static String CardPileBaseDesignUIRegistry_unregisterCardPileBaseDesignUI_cardPileBaseDesignUI_unregistered;

    // --- CardPileBaseDesignUIRegistryExtensionPointAdapter ----------------

    /**
     * The card pile base design user interface registry service is already
     * bound.
     */
    public static String CardPileBaseDesignUIRegistryExtensionPointAdapter_bindCardPileBaseDesignUIRegistry_bound;

    /** The extension registry service is already bound. */
    public static String CardPileBaseDesignUIRegistryExtensionPointAdapter_bindExtensionRegistry_bound;

    /** The bundle hosting the card pile base design icon was not found. */
    public static String CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUI_iconBundleNotFound;

    /** The card pile base design icon file was not found. */
    public static String CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUI_iconFileNotFound;

    /** The card pile base design icon path is missing. */
    public static String CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUI_missingIconPath;

    /** The card pile base design identifier is missing. */
    public static String CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUI_missingId;

    /** The card pile base design name is missing. */
    public static String CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUI_missingName;

    /**
     * An error occurred while parsing the card pile base design user interface
     * configuration element.
     */
    public static String CardPileBaseDesignUIRegistryExtensionPointAdapter_registerCardPileBaseDesignUI_parseError;

    /** The card pile base design user interface registry service is not bound. */
    public static String CardPileBaseDesignUIRegistryExtensionPointAdapter_unbindCardPileBaseDesignUIRegistry_notBound;

    /** The extension registry service is not bound. */
    public static String CardPileBaseDesignUIRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound;

    // --- CardSurfaceDesignUIRegistry --------------------------------------

    /**
     * A card surface design user interface is already registered for the
     * specified identifier.
     */
    public static String CardSurfaceDesignUIRegistry_registerCardSurfaceDesignUI_cardSurfaceDesignUI_registered;

    /**
     * The card surface design user interface is not registered for the
     * specified identifier.
     */
    public static String CardSurfaceDesignUIRegistry_unregisterCardSurfaceDesignUI_cardSurfaceDesignUI_unregistered;

    // --- CardSurfaceDesignUIRegistryExtensionPointAdapter -----------------

    /**
     * The card surface design user interface registry service is already bound.
     */
    public static String CardSurfaceDesignUIRegistryExtensionPointAdapter_bindCardSurfaceDesignUIRegistry_bound;

    /** The extension registry service is already bound. */
    public static String CardSurfaceDesignUIRegistryExtensionPointAdapter_bindExtensionRegistry_bound;

    /** The bundle hosting the card surface design icon was not found. */
    public static String CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_iconBundleNotFound;

    /** The card surface design icon file was not found. */
    public static String CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_iconFileNotFound;

    /** The card surface design icon path is missing. */
    public static String CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_missingIconPath;

    /** The card surface design identifier is missing. */
    public static String CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_missingId;

    /** The card surface design name is missing. */
    public static String CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_missingName;

    /**
     * An error occurred while parsing the card surface design user interface
     * configuration element.
     */
    public static String CardSurfaceDesignUIRegistryExtensionPointAdapter_registerCardSurfaceDesignUI_parseError;

    /** The card surface design user interface registry service is not bound. */
    public static String CardSurfaceDesignUIRegistryExtensionPointAdapter_unbindCardSurfaceDesignUIRegistry_notBound;

    /** The extension registry service is not bound. */
    public static String CardSurfaceDesignUIRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound;

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
        super();
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

    // --- CardPileBaseDesignUIRegistry -------------------------------------

    /**
     * Gets the formatted message indicating a card pile base design user
     * interface is already registered for the specified identifier.
     * 
     * @param cardPileBaseDesignId
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a card pile base design user
     *         interface is already registered for the specified identifier;
     *         never {@code null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignUIRegistry_registerCardPileBaseDesignUI_cardPileBaseDesignUI_registered(
        /* @NonNull */
        final CardPileBaseDesignId cardPileBaseDesignId )
    {
        return bind( CardPileBaseDesignUIRegistry_registerCardPileBaseDesignUI_cardPileBaseDesignUI_registered, cardPileBaseDesignId );
    }

    /**
     * Gets the formatted message indicating the card pile base design user
     * interface is not registered for the specified identifier.
     * 
     * @param cardPileBaseDesignId
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the card pile base design user
     *         interface is not registered for the specified identifier; never
     *         {@code null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignUIRegistry_unregisterCardPileBaseDesignUI_cardPileBaseDesignUI_unregistered(
        /* @NonNull */
        final CardPileBaseDesignId cardPileBaseDesignId )
    {
        return bind( CardPileBaseDesignUIRegistry_unregisterCardPileBaseDesignUI_cardPileBaseDesignUI_unregistered, cardPileBaseDesignId );
    }

    // --- CardPileBaseDesignUIRegistryExtensionPointAdapter ----------------

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
    static String CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUI_iconBundleNotFound(
        /* @NonNull */
        final String name )
    {
        return bind( CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUI_iconBundleNotFound, name );
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
    static String CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUI_iconFileNotFound(
        /* @NonNull */
        final Bundle bundle,
        /* @NonNull */
        final String path )
    {
        return bind( CardPileBaseDesignUIRegistryExtensionPointAdapter_createCardPileBaseDesignUI_iconFileNotFound, bundle.getSymbolicName(), path );
    }

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * card pile base design user interface configuration element.
     * 
     * @param cardPileBaseDesignId
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the card pile base design user interface configuration element;
     *         never {@code null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignUIRegistryExtensionPointAdapter_registerCardPileBaseDesignUI_parseError(
        /* @NonNull */
        final String cardPileBaseDesignId )
    {
        return bind( CardPileBaseDesignUIRegistryExtensionPointAdapter_registerCardPileBaseDesignUI_parseError, cardPileBaseDesignId );
    }

    // --- CardSurfaceDesignUIRegistry --------------------------------------

    /**
     * Gets the formatted message indicating a card surface design user
     * interface is already registered for the specified identifier.
     * 
     * @param cardSurfaceDesignId
     *        The card surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a card surface design user
     *         interface is already registered for the specified identifier;
     *         never {@code null}.
     */
    /* @NonNull */
    static String CardSurfaceDesignUIRegistry_registerCardSurfaceDesignUI_cardSurfaceDesignUI_registered(
        /* @NonNull */
        final CardSurfaceDesignId cardSurfaceDesignId )
    {
        return bind( CardSurfaceDesignUIRegistry_registerCardSurfaceDesignUI_cardSurfaceDesignUI_registered, cardSurfaceDesignId );
    }

    /**
     * Gets the formatted message indicating the card surface design user
     * interface is not registered for the specified identifier.
     * 
     * @param cardSurfaceDesignId
     *        The card surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the card surface design user
     *         interface is not registered for the specified identifier; never
     *         {@code null}.
     */
    /* @NonNull */
    static String CardSurfaceDesignUIRegistry_unregisterCardSurfaceDesignUI_cardSurfaceDesignUI_unregistered(
        /* @NonNull */
        final CardSurfaceDesignId cardSurfaceDesignId )
    {
        return bind( CardSurfaceDesignUIRegistry_unregisterCardSurfaceDesignUI_cardSurfaceDesignUI_unregistered, cardSurfaceDesignId );
    }

    // --- CardSurfaceDesignUIRegistryExtensionPointAdapter -----------------

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
    static String CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_iconBundleNotFound(
        /* @NonNull */
        final String name )
    {
        return bind( CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_iconBundleNotFound, name );
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
    static String CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_iconFileNotFound(
        /* @NonNull */
        final Bundle bundle,
        /* @NonNull */
        final String path )
    {
        return bind( CardSurfaceDesignUIRegistryExtensionPointAdapter_createCardSurfaceDesignUI_iconFileNotFound, bundle.getSymbolicName(), path );
    }

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * card surface design user interface configuration element.
     * 
     * @param cardSurfaceDesignId
     *        The card surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the card surface design user interface configuration element;
     *         never {@code null}.
     */
    /* @NonNull */
    static String CardSurfaceDesignUIRegistryExtensionPointAdapter_registerCardSurfaceDesignUI_parseError(
        /* @NonNull */
        final String cardSurfaceDesignId )
    {
        return bind( CardSurfaceDesignUIRegistryExtensionPointAdapter_registerCardSurfaceDesignUI_parseError, cardSurfaceDesignId );
    }
}
