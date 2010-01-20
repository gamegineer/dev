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

package org.gamegineer.table.internal.core.services.cardpiledesignregistry;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.core.CardPileDesignId;

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
    private static final String BUNDLE_NAME = "org.gamegineer.table.internal.core.services.cardpiledesignregistry.Messages"; //$NON-NLS-1$

    // --- CardPileDesignRegistry -------------------------------------------

    /** A duplicate card pile design identifier was detected. */
    public static String CardPileDesignRegistry_getCardPileDesignMap_duplicateId;

    /** An error occurred while parsing the card pile design definition. */
    public static String CardPileDesignRegistry_getForeignCardPileDesigns_parseError;


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

    // --- CardPileDesignRegistry -------------------------------------------

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
    static String CardPileDesignRegistry_getCardPileDesignMap_duplicateId(
        /* @NonNull */
        final CardPileDesignId cardPileDesignId )
    {
        return bind( CardPileDesignRegistry_getCardPileDesignMap_duplicateId, cardPileDesignId );
    }

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * card pile design definition.
     * 
     * @param cardPileDesignId
     *        The card pile design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the card pile design definition; never {@code null}.
     */
    /* @NonNull */
    static String CardPileDesignRegistry_getForeignCardPileDesigns_parseError(
        /* @NonNull */
        final String cardPileDesignId )
    {
        return bind( CardPileDesignRegistry_getForeignCardPileDesigns_parseError, cardPileDesignId );
    }
}
