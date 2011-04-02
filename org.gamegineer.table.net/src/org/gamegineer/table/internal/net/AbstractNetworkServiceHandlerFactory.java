/*
 * AbstractNetworkServiceHandlerFactory.java
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
 * Created on Mar 29, 2011 at 8:10:35 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.Immutable;

/**
 * Superclass for all implementations of {@link INetworkServiceHandlerFactory}.
 */
@Immutable
abstract class AbstractNetworkServiceHandlerFactory
    implements INetworkServiceHandlerFactory
{
    // ======================================================================
    // Fields
    // ======================================================================

    /**
     * The network table associated with the service handlers created by the
     * factory.
     */
    private final NetworkTable networkTable_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractNetworkServiceHandlerFactory} class.
     * 
     * @param networkTable
     *        The network table associated with the service handlers created by
     *        the factory; must not be {@code null}.
     */
    AbstractNetworkServiceHandlerFactory(
        /* @NonNull */
        final NetworkTable networkTable )
    {
        assert networkTable != null;

        networkTable_ = networkTable;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.INetworkServiceHandlerFactory#createNetworkServiceHandler()
     */
    @Override
    public final INetworkServiceHandler createNetworkServiceHandler()
    {
        return createNetworkServiceHandler( networkTable_ );
    }

    /**
     * Creates a new network service handler associated with the specified
     * network table.
     * 
     * @param networkTable
     *        The network table associated with the service handler; must not be
     *        {@code null}.
     * 
     * @return A new network service handler associated with the specified
     *         network table; never {@code null}.
     */
    /* @NonNull */
    abstract INetworkServiceHandler createNetworkServiceHandler(
        /* @NonNull */
        NetworkTable networkTable );
}
