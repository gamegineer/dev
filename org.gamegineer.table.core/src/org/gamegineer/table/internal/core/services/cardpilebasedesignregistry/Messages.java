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

    /** The name of the associated resource bundle. */
    private static final String BUNDLE_NAME = "org.gamegineer.table.internal.core.services.cardpilebasedesignregistry.Messages"; //$NON-NLS-1$

    // --- CardPileBaseDesignRegistry ---------------------------------------

    /** A duplicate card pile base design identifier was detected. */
    public static String CardPileBaseDesignRegistry_getCardPileBaseDesignMap_duplicateId;

    /** An error occurred while parsing the card pile base design definition. */
    public static String CardPileBaseDesignRegistry_getForeignCardPileBaseDesigns_parseError;


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

    // --- CardPileBaseDesignRegistry ---------------------------------------

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
    static String CardPileBaseDesignRegistry_getCardPileBaseDesignMap_duplicateId(
        /* @NonNull */
        final CardPileBaseDesignId cardPileBaseDesignId )
    {
        return bind( CardPileBaseDesignRegistry_getCardPileBaseDesignMap_duplicateId, cardPileBaseDesignId );
    }

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * card pile base design definition.
     * 
     * @param cardPileBaseDesignId
     *        The card pile base design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the card pile base design definition; never {@code null}.
     */
    /* @NonNull */
    static String CardPileBaseDesignRegistry_getForeignCardPileBaseDesigns_parseError(
        /* @NonNull */
        final String cardPileBaseDesignId )
    {
        return bind( CardPileBaseDesignRegistry_getForeignCardPileBaseDesigns_parseError, cardPileBaseDesignId );
    }
}
