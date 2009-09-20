/*
 * Debug.java
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
 * Created on Feb 29, 2008 at 12:16:41 AM.
 */

package org.gamegineer.common.internal.core;

import net.jcip.annotations.ThreadSafe;
import org.eclipse.osgi.service.debug.DebugOptions;

/**
 * Debugging utilities for the bundle.
 */
@ThreadSafe
public final class Debug
    extends org.gamegineer.common.core.runtime.Debug
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the top-level debug option key. */
    private static final String OPTION_DEBUG = Activator.SYMBOLIC_NAME + "/debug"; //$NON-NLS-1$

    /** The name of the services package debug option key. */
    private static final String OPTION_DEBUG_SERVICES = OPTION_DEBUG + "/services"; //$NON-NLS-1$

    /** The name of the component service package debug option key. */
    private static final String OPTION_DEBUG_SERVICES_COMPONENT = OPTION_DEBUG_SERVICES + "/component"; //$NON-NLS-1$

    /** The name of the logging service package debug option key. */
    private static final String OPTION_DEBUG_SERVICES_LOGGING = OPTION_DEBUG_SERVICES + "/logging"; //$NON-NLS-1$

    /** The name of the utility package debug option key. */
    private static final String OPTION_DEBUG_UTILITY = OPTION_DEBUG + "/util"; //$NON-NLS-1$

    /** The name of the logging package debug option key. */
    private static final String OPTION_DEBUG_UTILITY_LOGGING = OPTION_DEBUG_UTILITY + "/logging"; //$NON-NLS-1$

    /** Indicates the default debug option for the bundle is enabled. */
    public static final boolean DEFAULT;

    /** Indicates the debug option for the services package is enabled. */
    public static final boolean SERVICES;

    /** Indicates the debug option for the component service package is enabled. */
    public static final boolean SERVICES_COMPONENT;

    /** Indicates the debug option for the logging service package is enabled. */
    public static final boolean SERVICES_LOGGING;

    /** Indicates the debug option for the utility package is enabled. */
    public static final boolean UTILITY;

    /** Indicates the debug option for the logging package is enabled. */
    public static final boolean UTILITY_LOGGING;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code Debug} class.
     */
    static
    {
        final DebugOptions debugOptions = getDebugOptions();
        DEFAULT = debugOptions.getBooleanOption( OPTION_DEBUG, false );
        SERVICES = DEFAULT && debugOptions.getBooleanOption( OPTION_DEBUG_SERVICES, false );
        SERVICES_COMPONENT = DEFAULT && SERVICES && debugOptions.getBooleanOption( OPTION_DEBUG_SERVICES_COMPONENT, false );
        SERVICES_LOGGING = DEFAULT && SERVICES && debugOptions.getBooleanOption( OPTION_DEBUG_SERVICES_LOGGING, false );
        UTILITY = DEFAULT && debugOptions.getBooleanOption( OPTION_DEBUG_UTILITY, false );
        UTILITY_LOGGING = DEFAULT && UTILITY && debugOptions.getBooleanOption( OPTION_DEBUG_UTILITY_LOGGING, false );
    }

    /**
     * Initializes a new instance of the {@code Debug} class.
     */
    private Debug()
    {
        super();
    }
}
