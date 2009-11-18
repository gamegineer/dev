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
 * Created on Nov 17, 2009 at 10:35:58 PM.
 */

package org.gamegineer.table.internal.core.services.carddesignregistry;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.core.CardDesignId;

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
    private static final String BUNDLE_NAME = "org.gamegineer.table.internal.core.services.carddesignregistry.Messages"; //$NON-NLS-1$

    // --- CardDesignRegistry -----------------------------------------------

    /** A duplicate card design identifier was detected. */
    public static String CardDesignRegistry_getCardDesignMap_duplicateId;

    /** An error occurred while parsing the card design definition. */
    public static String CardDesignRegistry_getForeignCardDesigns_parseError;


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

    // --- CardDesignRegistry -----------------------------------------------

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
    static String CardDesignRegistry_getCardDesignMap_duplicateId(
        /* @NonNull */
        final CardDesignId cardDesignId )
    {
        return bind( CardDesignRegistry_getCardDesignMap_duplicateId, cardDesignId );
    }

    /**
     * Gets the formatted message indicating an error occurred while parsing the
     * card design definition.
     * 
     * @param cardDesignId
     *        The card design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating an error occurred while parsing
     *         the card design definition; never {@code null}.
     */
    /* @NonNull */
    static String CardDesignRegistry_getForeignCardDesigns_parseError(
        /* @NonNull */
        final String cardDesignId )
    {
        return bind( CardDesignRegistry_getForeignCardDesigns_parseError, cardDesignId );
    }
}
