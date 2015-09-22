/*
 * WindowConstants.java
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
 * Created on Sep 12, 2010 at 9:01:46 PM.
 */

package org.gamegineer.common.ui.window;

import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful constants for windows.
 */
@ThreadSafe
public final class WindowConstants
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The standard window return code that indicates the window was opened. */
    public static final int RETURN_CODE_OK = 0;

    /**
     * The standard window return code that indicates the window was cancelled.
     */
    public static final int RETURN_CODE_CANCEL = 1;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code WindowConstants} class.
     */
    private WindowConstants()
    {
    }
}
