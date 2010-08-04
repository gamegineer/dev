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
 * Created on Jan 19, 2010 at 11:26:56 PM.
 */

package org.gamegineer.table.internal.core.services.cardpilebasedesignregistry;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.core.CardPileBaseDesignId;

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

    // --- CardPileBaseDesignRegistry ---------------------------------------

    /**
     * A card pile base design is already registered for the specified
     * identifier.
     */
    public static String CardPileBaseDesignRegistry_registerCardPileBaseDesign_cardPileBaseDesign_registered;

    /**
     * The card pile base design is not registered for the specified identifier.
     */
    public static String CardPileBaseDesignRegistry_unregisterCardPileBaseDesign_cardPileBaseDesign_unregistered;

    // --- CardPileBaseDesignRegistryExtensionPointAdapter ------------------

    /** The card pile base design registry service is already bound. */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_bindCardPileBaseDesignRegistry_bound;

    /** The extension registry service is already bound. */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_bindExtensionRegistry_bound;

    /**
     * An error occurred while parsing the height attribute of a card pile base
     * design configuration element.
     */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_createCardPileBaseDesign_parseHeightError;

    /**
     * An error occurred while parsing the width attribute of a card pile base
     * design configuration element.
     */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_createCardPileBaseDesign_parseWidthError;

    /**
     * An error occurred while parsing the card pile base design configuration
     * element.
     */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_registerCardPileBaseDesign_parseError;

    /** The card pile base design registry service is not bound. */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_unbindCardPileBaseDesignRegistry_notBound;

    /** The extension registry service is not bound. */
    public static String CardPileBaseDesignRegistryExtensionPointAdapter_unbindExtensionRegistry_notBound;


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

    // --- CardPileBaseDesignRegistry ---------------------------------------

    /**
     * Gets the formatted message indicating a card pile base design is already
     * registered for the specified identifier.
     * 
     * @param cardPileBaseDesignId
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating a card pile base design is
     *         already registered for the specified identifier; never {@code
     *         null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignRegistry_registerCardPileBaseDesign_cardPileBaseDesign_registered(
        /* @NonNull */
        final CardPileBaseDesignId cardPileBaseDesignId )
    {
        return bind( CardPileBaseDesignRegistry_registerCardPileBaseDesign_cardPileBaseDesign_registered, cardPileBaseDesignId );
    }

    /**
     * Gets the formatted message indicating the card pile base design is not
     * registered for the specified identifier.
     * 
     * @param cardPileBaseDesignId
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the card pile base design is not
     *         registered for the specified identifier; never {@code null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignRegistry_unregisterCardPileBaseDesign_cardPileBaseDesign_unregistered(
        /* @NonNull */
        final CardPileBaseDesignId cardPileBaseDesignId )
    {
        return bind( CardPileBaseDesignRegistry_unregisterCardPileBaseDesign_cardPileBaseDesign_unregistered, cardPileBaseDesignId );
    }

    // --- CardPileBaseDesignRegistryExtensionPointAdapter ------------------

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * card pile base design configuration element.
     * 
     * @param cardPileBaseDesignId
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the card pile base design configuration element; never {@code
     *         null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignRegistryExtensionPointAdapter_registerCardPileBaseDesign_parseError(
        /* @NonNull */
        final String cardPileBaseDesignId )
    {
        return bind( CardPileBaseDesignRegistryExtensionPointAdapter_registerCardPileBaseDesign_parseError, cardPileBaseDesignId );
    }
}
