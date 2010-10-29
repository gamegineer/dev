/*
 * NetworkTableConstants.java
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
 * Created on Oct 26, 2010 at 10:24:57 PM.
 */

package org.gamegineer.table.net;

import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful constants for working with network tables.
 */
@ThreadSafe
public final class NetworkTableConstants
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default network table port. */
    public static final int DEFAULT_PORT = 21112;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableConstants} class.
     */
    private NetworkTableConstants()
    {
        super();
    }
}
