/*
 * NonNlsMessages.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Mar 29, 2013 at 10:12:40 PM.
 */

package org.gamegineer.cards.internal.ui.strategies;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.core.ComponentStrategyId;

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

    // --- ComponentStrategyUIExtensionFactory ------------------------------

    /** The component strategy identifier is unknown. */
    public static String ComponentStrategyUIExtensionFactory_create_unknownId;

    /** The component strategy identifier is missing. */
    public static String ComponentStrategyUIExtensionFactory_setInitializationData_missingId;


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
    }


    // ======================================================================
    // Methods
    // ======================================================================

    // --- ComponentStrategyUIExtensionFactory ------------------------------

    /**
     * Gets the formatted message indicating the component strategy identifier
     * is unknown.
     * 
     * @param componentStrategyId
     *        The component strategy identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the component strategy
     *         identifier is unknown; never {@code null}.
     */
    /* @NonNull */
    static String ComponentStrategyUIExtensionFactory_create_unknownId(
        /* @NonNull */
        final ComponentStrategyId componentStrategyId )
    {
        return bind( ComponentStrategyUIExtensionFactory_create_unknownId, componentStrategyId );
    }
}
