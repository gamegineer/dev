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
 * Created on Nov 16, 2011 at 7:46:56 PM.
 */

package org.gamegineer.table.ui;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.util.NLS;
import org.gamegineer.table.core.ComponentStrategyId;
import org.gamegineer.table.core.ComponentSurfaceDesignId;

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

    // --- ComponentStrategyUIRegistry --------------------------------------

    /** The component strategy user interface registry is not available. */
    public static String ComponentStrategyUIRegistry_getComponentStrategyUI_componentStrategyUIRegistryNotAvailable = ""; //$NON-NLS-1$

    /** The component strategy identifier is not registered. */
    public static String ComponentStrategyUIRegistry_getComponentStrategyUI_unknownComponentStrategyId = ""; //$NON-NLS-1$

    // --- ComponentSurfaceDesignUIRegistry ---------------------------------

    /**
     * The component surface design user interface registry is not available.
     */
    public static String ComponentSurfaceDesignUIRegistry_getComponentSurfaceDesignUI_componentSurfaceDesignUIRegistryNotAvailable = ""; //$NON-NLS-1$

    /** The component surface design identifier is not registered. */
    public static String ComponentSurfaceDesignUIRegistry_getComponentSurfaceDesignUI_unknownComponentSurfaceDesignId = ""; //$NON-NLS-1$


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

    // --- ComponentStrategyUIRegistry --------------------------------------

    /**
     * Gets the formatted message indicating the component strategy identifier
     * is not registered.
     * 
     * @param componentStrategyId
     *        The component strategy identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the component strategy
     *         identifier is not registered; never {@code null}.
     */
    static String ComponentStrategyUIRegistry_getComponentStrategyUI_unknownComponentStrategyId(
        final ComponentStrategyId componentStrategyId )
    {
        return bind( ComponentStrategyUIRegistry_getComponentStrategyUI_unknownComponentStrategyId, componentStrategyId );
    }

    // --- ComponentSurfaceDesignUIRegistry ---------------------------------

    /**
     * Gets the formatted message indicating the component surface design
     * identifier is not registered.
     * 
     * @param componentSurfaceDesignId
     *        The component surface design identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the component surface design
     *         identifier is not registered; never {@code null}.
     */
    static String ComponentSurfaceDesignUIRegistry_getComponentSurfaceDesignUI_unknownComponentSurfaceDesignId(
        final ComponentSurfaceDesignId componentSurfaceDesignId )
    {
        return bind( ComponentSurfaceDesignUIRegistry_getComponentSurfaceDesignUI_unknownComponentSurfaceDesignId, componentSurfaceDesignId );
    }
}
