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
 * Created on Apr 6, 2012 at 9:56:03 PM.
 */

package org.gamegineer.common.core.util;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;

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

    // --- ExtensibleEnum ---------------------------------------------------

    /** The ordinal is out of range. */
    public static String ExtensibleEnum_ordinal_outOfRange = ""; //$NON-NLS-1$

    /** The enum type has no constant with the specified name. */
    public static String ExtensibleEnum_valueOf_nameIllegal = ""; //$NON-NLS-1$


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

    // --- AbstractLoggingComponentFactory ----------------------------------

    /**
     * Gets the formatted message indicating the ordinal is out of range.
     * 
     * @param ordinal
     *        The ordinal of the enum constant; must not be {@code null}.
     * 
     * @return The formatted message indicating the ordinal is out of range;
     *         never {@code null}.
     */
    @SuppressWarnings( "boxing" )
    static String ExtensibleEnum_ordinal_outOfRange(
        final int ordinal )
    {
        return bind( ExtensibleEnum_ordinal_outOfRange, ordinal );
    }

    /**
     * Gets the formatted message indicating the enum type has no constant with
     * the specified name.
     * 
     * @param type
     *        The enum type; must not be {@code null}.
     * @param name
     *        The name of the enum constant; must not be {@code null}.
     * 
     * @return The formatted message indicating the enum type has no constant
     *         with the specified name; never {@code null}.
     */
    static String ExtensibleEnum_valueOf_nameIllegal(
        final Class<?> type,
        final String name )
    {
        return bind( ExtensibleEnum_valueOf_nameIllegal, type.getName(), name );
    }
}
