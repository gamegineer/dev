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
 * Created on Sep 16, 2009 at 10:43:03 PM.
 */

package org.gamegineer.table.internal.ui;

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

    /** The singleton instance of the bundle debug utility. */
    private static final Debug INSTANCE = new Debug();

    /** The name of the top-level debug option. */
    public static final String OPTION_DEFAULT = "/debug"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Debug} class.
     */
    private Debug()
    {
        super( BundleConstants.SYMBOLIC_NAME );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the default instance of the bundle debug utility.
     * 
     * @return The default instance of the bundle debug utility; never {@code
     *         null}.
     */
    /* @NonNull */
    public static Debug getDefault()
    {
        return INSTANCE;
    }
}
