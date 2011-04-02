/*
 * ServerNetworkServiceHandlerFactory.java
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
 * Created on Mar 29, 2011 at 8:09:27 PM.
 */

package org.gamegineer.table.internal.net;

import net.jcip.annotations.Immutable;

/**
 * A factory for creating service handlers that represent the server half of the
 * network table protocol.
 */
@Immutable
final class ServerNetworkServiceHandlerFactory
    extends AbstractNetworkServiceHandlerFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ServerNetworkServiceHandlerFactory}
     * class.
     * 
     * @param networkTable
     *        The network table associated with the service handlers created by
     *        the factory; must not be {@code null}.
     */
    ServerNetworkServiceHandlerFactory(
        /* @NonNull */
        final NetworkTable networkTable )
    {
        super( networkTable );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.AbstractNetworkServiceHandlerFactory#createNetworkServiceHandler(org.gamegineer.table.internal.net.NetworkTable)
     */
    @Override
    INetworkServiceHandler createNetworkServiceHandler(
        final NetworkTable networkTable )
    {
        assert networkTable != null;

        return new ServerNetworkServiceHandler( networkTable );
    }
}
