/*
 * AbstractNetworkTableStrategyFactory.java
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
 * Created on Dec 27, 2010 at 8:11:30 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.Immutable;

/**
 * Superclass for all network table strategy factories.
 */
@Immutable
abstract class AbstractNetworkTableStrategyFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractNetworkTableStrategyFactory} class.
     */
    AbstractNetworkTableStrategyFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new client network table strategy for the specified network
     * table.
     * 
     * @param networkTable
     *        The network table; must not be {@code null}.
     * 
     * @return The client network table strategy; never {@code null}.
     */
    /* @NonNull */
    abstract AbstractNetworkTableStrategy createClientNetworkTableStrategy(
        /* @NonNull */
        NetworkTable networkTable );

    /**
     * Creates a new server network table strategy for the specified network
     * table.
     * 
     * @param networkTable
     *        The network table; must not be {@code null}.
     * 
     * @return The server network table strategy; never {@code null}.
     */
    /* @NonNull */
    abstract AbstractNetworkTableStrategy createServerNetworkTableStrategy(
        /* @NonNull */
        NetworkTable networkTable );
}
