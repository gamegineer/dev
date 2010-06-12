/*
 * Debug.java
 * Copyright 2008-2010 Gamegineer.org
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

    /** The name of the top-level debug option. */
    public static final String OPTION_DEFAULT = Activator.SYMBOLIC_NAME + "/debug"; //$NON-NLS-1$

    /** The name of the logging service package debug option. */
    public static final String OPTION_SERVICES_LOGGING = OPTION_DEFAULT + "/services/logging"; //$NON-NLS-1$

    /** The name of the logging package debug option. */
    public static final String OPTION_UTILITY_LOGGING = OPTION_DEFAULT + "/util/logging"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Debug} class.
     */
    private Debug()
    {
        super();
    }
}
