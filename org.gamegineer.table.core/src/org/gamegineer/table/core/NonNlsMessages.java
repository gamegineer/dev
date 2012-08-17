/*
 * NonNlsMessages.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jul 13, 2011 at 8:30:21 PM.
 */

package org.gamegineer.table.core;

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

    // --- ComponentPath ----------------------------------------------------

    /** The component path index must not be negative. */
    public static String ComponentPath_ctor_index_negative;

    // --- ComponentStrategyRegistryFacade ----------------------------------

    /** The component strategy registry is not available. */
    public static String ComponentStrategyRegistryFacade_getComponentStrategy_componentStrategyRegistryNotAvailable;

    /** The component strategy identifier is not registered. */
    public static String ComponentStrategyRegistryFacade_getComponentStrategy_unknownComponentStrategyId;

    /**
     * The component strategy identifier is not associated with a container
     * strategy.
     */
    public static String ComponentStrategyRegistryFacade_getContainerStrategy_notContainerStrategy;

    // --- ComponentSurfaceDesign -------------------------------------------

    /** The component surface design height must not be negative. */
    public static String ComponentSurfaceDesign_ctor_height_negative;

    /** The component surface design width must not be negative. */
    public static String ComponentSurfaceDesign_ctor_width_negative;

    // --- ComponentSurfaceDesignRegistryFacade -----------------------------

    /** The component surface design registry is not available. */
    public static String ComponentSurfaceDesignRegistryFacade_getComponentSurfaceDesign_componentSurfaceDesignRegistryNotAvailable;

    /** The component surface design identifier is not registered. */
    public static String ComponentSurfaceDesignRegistryFacade_getComponentSurfaceDesign_unknownComponentSurfaceDesignId;

    // --- ContainerContentChangedEvent -------------------------------------

    /** The component index is negative. */
    public static String ContainerContentChangedEvent_ctor_componentIndex_negative;

    // --- ContainerLayoutRegistryFacade ------------------------------------

    /** The container layout registry is not available. */
    public static String ContainerLayoutRegistryFacade_getContainerLayout_containerLayoutRegistryNotAvailable;

    /** The container layout identifier is not registered. */
    public static String ContainerLayoutRegistryFacade_getContainerLayout_unknownContainerLayoutId;


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

    // --- ComponentStrategyRegistryFacade ----------------------------------

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
    /* @NonNull */
    static String ComponentStrategyRegistryFacade_getComponentStrategy_unknownComponentStrategyId(
        /* @NonNull */
        final ComponentStrategyId componentStrategyId )
    {
        return bind( ComponentStrategyRegistryFacade_getComponentStrategy_unknownComponentStrategyId, componentStrategyId );
    }

    /**
     * Gets the formatted message indicating the component strategy identifier
     * is not associated with a container strategy.
     * 
     * @param componentStrategyId
     *        The component strategy identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the component strategy
     *         identifier is not associated with a container strategy; never
     *         {@code null}.
     */
    /* @NonNull */
    static String ComponentStrategyRegistryFacade_getContainerStrategy_notContainerStrategy(
        /* @NonNull */
        final ComponentStrategyId componentStrategyId )
    {
        return bind( ComponentStrategyRegistryFacade_getContainerStrategy_notContainerStrategy, componentStrategyId );
    }

    // --- ComponentSurfaceDesignRegistryFacade -----------------------------

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
    /* @NonNull */
    static String ComponentSurfaceDesignRegistryFacade_getComponentSurfaceDesign_unknownComponentSurfaceDesignId(
        /* @NonNull */
        final ComponentSurfaceDesignId componentSurfaceDesignId )
    {
        return bind( ComponentSurfaceDesignRegistryFacade_getComponentSurfaceDesign_unknownComponentSurfaceDesignId, componentSurfaceDesignId );
    }

    // --- ContainerLayoutRegistryFacade ------------------------------------

    /**
     * Gets the formatted message indicating the container layout identifier is
     * not registered.
     * 
     * @param containerLayoutId
     *        The container layout identifier; must not be {@code null}.
     * 
     * @return The formatted message indicating the container layout identifier
     *         is not registered; never {@code null}.
     */
    /* @NonNull */
    static String ContainerLayoutRegistryFacade_getContainerLayout_unknownContainerLayoutId(
        /* @NonNull */
        final ContainerLayoutId containerLayoutId )
    {
        return bind( ContainerLayoutRegistryFacade_getContainerLayout_unknownContainerLayoutId, containerLayoutId );
    }
}
