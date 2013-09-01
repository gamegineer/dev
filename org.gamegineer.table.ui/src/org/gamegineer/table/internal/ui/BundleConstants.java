/*
 * BundleConstants.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Jun 13, 2010 at 8:58:45 PM.
 */

package org.gamegineer.table.internal.ui;

import net.jcip.annotations.ThreadSafe;

/**
 * Defines useful constants for use by the bundle.
 */
@ThreadSafe
public final class BundleConstants
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The simple identifier of the component prototypes extension point. */
    public static final String COMPONENT_PROTOTYPES_EXTENSION_POINT_SIMPLE_ID = "componentPrototypes"; //$NON-NLS-1$

    /**
     * The simple identifier of the component strategy user interfaces extension
     * point.
     */
    public static final String COMPONENT_STRATEGY_UIS_EXTENSION_POINT_SIMPLE_ID = "componentStrategyUIs"; //$NON-NLS-1$

    /**
     * The simple identifier of the component surface design user interfaces
     * extension point.
     */
    public static final String COMPONENT_SURFACE_DESIGN_UIS_EXTENSION_POINT_SIMPLE_ID = "componentSurfaceDesignUIs"; //$NON-NLS-1$

    /** The symbolic name of the bundle. */
    public static final String SYMBOLIC_NAME = "org.gamegineer.table.ui"; //$NON-NLS-1$

    /** The unique identifier of the component prototypes extension point. */
    public static final String COMPONENT_PROTOTYPES_EXTENSION_POINT_UNIQUE_ID = SYMBOLIC_NAME + "." + COMPONENT_PROTOTYPES_EXTENSION_POINT_SIMPLE_ID; //$NON-NLS-1$

    /**
     * The unique identifier of the component strategy user interfaces extension
     * point.
     */
    public static final String COMPONENT_STRATEGY_UIS_EXTENSION_POINT_UNIQUE_ID = SYMBOLIC_NAME + "." + COMPONENT_STRATEGY_UIS_EXTENSION_POINT_SIMPLE_ID; //$NON-NLS-1$

    /**
     * The unique identifier of the component surface design user interfaces
     * extension point.
     */
    public static final String COMPONENT_SURFACE_DESIGN_UIS_EXTENSION_POINT_UNIQUE_ID = SYMBOLIC_NAME + "." + COMPONENT_SURFACE_DESIGN_UIS_EXTENSION_POINT_SIMPLE_ID; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code BundleConstants} class.
     */
    private BundleConstants()
    {
    }
}
