/*
 * INetworkServiceHandlerFactory.java
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
 * Created on Mar 29, 2011 at 8:04:42 PM.
 */

package org.gamegineer.table.internal.net;

// XXX: may replace both INetworkInterfaceListener and INetworkServiceHandlerFactory
// with INetworkInterfaceContext, which will have several methods including...
//     createClientNetworkServiceHandler,
//     createServerNetworkServiceHandler,
//     networkInterfaceDisconnected    

/**
 * A network service handler factory.
 */
public interface INetworkServiceHandlerFactory
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new network service handler.
     * 
     * @return A new network service handler; never {@code null}.
     */
    /* @NonNull */
    public INetworkServiceHandler createNetworkServiceHandler();
}
