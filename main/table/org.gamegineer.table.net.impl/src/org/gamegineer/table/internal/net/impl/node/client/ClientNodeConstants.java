/*
 * ClientNodeConstants.java
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
 * Created on Sep 2, 2011 at 8:01:03 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client;

import net.jcip.annotations.ThreadSafe;

/**
 * A collection of useful constants for working with client nodes.
 */
@ThreadSafe
public final class ClientNodeConstants
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The name of the virtual player associated with the remote server node.
     */
    public static final String SERVER_PLAYER_NAME = "<<server>>"; //$NON-NLS-1$


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ClientNodeConstants} class.
     */
    private ClientNodeConstants()
    {
    }
}
