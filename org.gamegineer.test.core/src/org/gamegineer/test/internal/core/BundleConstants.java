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
 * Created on Jun 13, 2010 at 9:01:14 PM.
 */

package org.gamegineer.test.internal.core;

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

    /** The plugin identifier. */
    public static final String SYMBOLIC_NAME = "org.gamegineer.test.core"; //$NON-NLS-1$


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
