/*
 * INetworkTableStrategyContext.java
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
 * Created on Apr 18, 2011 at 7:49:15 PM.
 */

package org.gamegineer.table.internal.net;

import org.gamegineer.table.internal.net.transport.ITransportLayerFactory;

/**
 * The execution context for a network table strategy.
 * 
 * <p>
 * Provides operations that allow a network table strategy to interact with the
 * network table.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface INetworkTableStrategyContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Disconnects the network table.
     */
    public void disconnectNetworkTable();

    /**
     * Gets the network table transport layer factory
     * 
     * @return The network table transport layer factory; never {@code null}.
     */
    /* @NonNull */
    public ITransportLayerFactory getTransportLayerFactory();
}
