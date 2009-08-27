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
 * Created on Feb 14, 2009 at 11:17:59 PM.
 */

package org.gamegineer.game.internal.ui;

import org.eclipse.osgi.service.debug.DebugOptions;

/**
 * Debugging utilities for the bundle.
 */
public final class Debug
    extends org.gamegineer.common.core.runtime.Debug
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the top-level debug option key. */
    private static final String OPTION_DEBUG = Activator.SYMBOLIC_NAME + "/debug"; //$NON-NLS-1$

    /** The name of the game system user interface package debug option key. */
    private static final String OPTION_DEBUG_SYSTEM = OPTION_DEBUG + "/system"; //$NON-NLS-1$

    /** Indicates the default debug option for the bundle is enabled. */
    public static final boolean DEFAULT;

    /**
     * Indicates the debug option for the game system user interface package is
     * enabled.
     */
    public static final boolean SYSTEM;


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
        SYSTEM = DEFAULT && debugOptions.getBooleanOption( OPTION_DEBUG_SYSTEM, false );
    }

    /**
     * Initializes a new instance of the {@code Debug} class.
     */
    private Debug()
    {
        super();
    }
}
