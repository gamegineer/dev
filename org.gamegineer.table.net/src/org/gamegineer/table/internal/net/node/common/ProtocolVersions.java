/*
 * ProtocolVersions.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Mar 11, 2011 at 11:44:03 PM.
 */

package org.gamegineer.table.internal.net.node.common;

import net.jcip.annotations.ThreadSafe;

/**
 * A collection of constants that define the known table network protocol
 * versions.
 */
@ThreadSafe
public final class ProtocolVersions
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The version 1.0.0 protocol. */
    public static final int VERSION_1 = 100;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ProtocolVersions} class.
     */
    private ProtocolVersions()
    {
        super();
    }
}
