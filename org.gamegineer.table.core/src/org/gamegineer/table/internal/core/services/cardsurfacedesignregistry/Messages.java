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
 * Created on Nov 17, 2009 at 10:35:58 PM.
 */

package org.gamegineer.table.internal.core.services.cardsurfacedesignregistry;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.core.CardSurfaceDesignId;

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

    // --- CardSurfaceDesignRegistry ----------------------------------------

    /**
     * A card surface design is already registered for the specified identifier.
     */
    public static String CardSurfaceDesignRegistry_registerCardSurfaceDesign_cardSurfaceDesign_registered;

    /** The card surface design is not registered for the specified identifier. */
    public static String CardSurfaceDesignRegistry_unregisterCardSurfaceDesign_cardSurfaceDesign_unregistered;

    // --- CardSurfaceDesignRegistryExtensionPointAdapter -------------------

    /** The card surface design registry service is already bound. */
    public static String CardSurfaceDesignRegistryExtensionPointAdapter_bindCardSurfaceDesignRegistry_bound;

    /** The extension registry service is already bound. */
    public static String CardSurfaceDesignRegistryExtensionPointAdapter_bindExtensionRegistry_bound;

    /**
     * An error occurred while parsing the card surface design configuration
     * element.
     */
    public static String CardSurfaceDesignRegistryExtensionPointAdapter_createCardSurfaceDesign_parseError;

    /** The card surface design registry service is not bound. */
    public static String CardSurfaceDesignRegistryExtensionPointAdapter_unbindCardSurfaceDesignRegistry_notBound;

    /** The extension registry service is not bound. */
    public static String CardSurfaceDesignRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound;


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

    // --- CardSurfaceDesignRegistry ----------------------------------------

    /**
     * Gets the formatted message indicating a card surface design is already
     * registered for the specified identifier.
     * 
     * @param cardSurfaceDesignId
     *        The card surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a card surface design is already
     *         registered for the specified identifier; never {@code null}.
     */
    /* @NonNull */
    static String CardSurfaceDesignRegistry_registerCardSurfaceDesign_cardSurfaceDesign_registered(
        /* @NonNull */
        final CardSurfaceDesignId cardSurfaceDesignId )
    {
        return bind( CardSurfaceDesignRegistry_registerCardSurfaceDesign_cardSurfaceDesign_registered, cardSurfaceDesignId );
    }

    /**
     * Gets the formatted message indicating the card surface design is not
     * registered for the specified identifier.
     * 
     * @param cardSurfaceDesignId
     *        The card surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the card surface design is not
     *         registered for the specified identifier; never {@code null}.
     */
    /* @NonNull */
    static String CardSurfaceDesignRegistry_unregisterCardSurfaceDesign_cardSurfaceDesign_unregistered(
        /* @NonNull */
        final CardSurfaceDesignId cardSurfaceDesignId )
    {
        return bind( CardSurfaceDesignRegistry_unregisterCardSurfaceDesign_cardSurfaceDesign_unregistered, cardSurfaceDesignId );
    }

    // --- CardSurfaceDesignRegistryExtensionPointAdapter ------------------

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * card surface design configuration element.
     * 
     * @param cardSurfaceDesignId
     *        The card surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the card surface design configuration element; never {@code null}
     *         .
     */
    /* @NonNull */
    static String CardSurfaceDesignRegistryExtensionPointAdapter_createCardSurfaceDesign_parseError(
        /* @NonNull */
        final String cardSurfaceDesignId )
    {
        return bind( CardSurfaceDesignRegistryExtensionPointAdapter_createCardSurfaceDesign_parseError, cardSurfaceDesignId );
    }
}
