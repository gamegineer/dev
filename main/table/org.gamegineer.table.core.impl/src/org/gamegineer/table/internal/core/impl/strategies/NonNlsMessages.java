/*
 * NonNlsMessages.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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
 * Created on Aug 20, 2012 at 8:08:31 PM.
 */

package org.gamegineer.table.internal.core.impl.strategies;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
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

    // --- ComponentStrategyExtensionFactory --------------------------------

    /** The component strategy identifier is unknown. */
    public static String ComponentStrategyExtensionFactory_create_unknownId = ""; //$NON-NLS-1$

    /** The component strategy identifier is missing. */
    public static String ComponentStrategyExtensionFactory_setInitializationData_missingId = ""; //$NON-NLS-1$


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

    // --- ComponentStrategyExtensionFactory --------------------------------

    /**
     * Gets the formatted message indicating the component strategy identifier
     * is unknown.
     * 
     * @param componentStrategyId
     *        The component strategy identifier.
     * 
     * @return The formatted message indicating the component strategy
     *         identifier is unknown.
     */
    static String ComponentStrategyExtensionFactory_create_unknownId(
        final @Nullable ComponentStrategyId componentStrategyId )
    {
        return bind( ComponentStrategyExtensionFactory_create_unknownId, componentStrategyId );
    }
}
