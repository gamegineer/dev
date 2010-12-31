/*
 * AbstractNetworkTableStrategy.java
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
 * Created on Dec 27, 2010 at 8:07:46 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableException;

/**
 * Superclass for all network table strategies.
 */
@Immutable
abstract class AbstractNetworkTableStrategy
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The network table that hosts the strategy. */
    private final NetworkTable networkTable_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractNetworkTableStrategy}
     * class.
     * 
     * @param networkTable
     *        The network table that hosts the strategy; must not be {@code
     *        null}.
     */
    AbstractNetworkTableStrategy(
        /* @NonNull */
        final NetworkTable networkTable )
    {
        assert networkTable != null;

        networkTable_ = networkTable;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Connects to the network.
     * 
     * @param configuration
     *        The network table configuration; must not be {@code null}.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    abstract void connect(
        /* @NonNull */
        INetworkTableConfiguration configuration )
        throws NetworkTableException;

    /**
     * Disconnects from the network.
     * 
     * @throws org.gamegineer.table.net.NetworkTableException
     *         If an error occurs.
     */
    abstract void disconnect()
        throws NetworkTableException;

    /**
     * Gets the network table that hosts the strategy.
     * 
     * @return The network table that hosts the strategy; never {@code null}.
     */
    /* @NonNull */
    final NetworkTable getNetworkTable()
    {
        return networkTable_;
    }
}
