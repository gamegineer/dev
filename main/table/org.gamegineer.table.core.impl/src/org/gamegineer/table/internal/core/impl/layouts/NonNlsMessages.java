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
 * Created on Aug 20, 2012 at 8:03:28 PM.
 */

package org.gamegineer.table.internal.core.impl.layouts;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.core.ContainerLayoutId;

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

    // --- AbstractContainerLayout ------------------------------------------

    /** The component index is negative. */
    public static String AbstractContainerLayout_getComponentOffsetAt_index_negative = ""; //$NON-NLS-1$

    // --- ContainerLayoutExtensionFactory ----------------------------------

    /** The container layout identifier is unknown. */
    public static String ContainerLayoutExtensionFactory_create_unknownId = ""; //$NON-NLS-1$

    /** The container layout identifier is missing. */
    public static String ContainerLayoutExtensionFactory_setInitializationData_missingId = ""; //$NON-NLS-1$


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

    // --- ContainerLayoutExtensionFactory ----------------------------------

    /**
     * Gets the formatted message indicating the container layout identifier is
     * unknown.
     * 
     * @param containerLayoutId
     *        The container layout identifier; may be {@code null}.
     * 
     * @return The formatted message indicating the container layout identifier
     *         is unknown; never {@code null}.
     */
    static String ContainerLayoutExtensionFactory_create_unknownId(
        final @Nullable ContainerLayoutId containerLayoutId )
    {
        return bind( ContainerLayoutExtensionFactory_create_unknownId, containerLayoutId );
    }
}
