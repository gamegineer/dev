/*
 * TestsFragmentConstants.java
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
 * Created on Sep 7, 2010 at 10:42:56 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.ThreadSafe;

/**
 * Defines useful constants for use by the fragment.
 */
@ThreadSafe
public final class TestsFragmentConstants
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The symbolic name of the fragment. */
    public static final String SYMBOLIC_NAME = "org.gamegineer.table.net.tests"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TestsFragmentConstants} class.
     */
    private TestsFragmentConstants()
    {
        super();
    }
}