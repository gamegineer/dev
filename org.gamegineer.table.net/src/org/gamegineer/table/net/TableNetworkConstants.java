/*
 * TableNetworkConstants.java
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
 * Created on Oct 26, 2010 at 10:24:57 PM.
 */

package org.gamegineer.table.net;

import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful constants for working with table networks.
 */
@ThreadSafe
public final class TableNetworkConstants
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default table network port. */
    public static final int DEFAULT_PORT = 52112;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkConstants} class.
     */
    private TableNetworkConstants()
    {
    }
}
