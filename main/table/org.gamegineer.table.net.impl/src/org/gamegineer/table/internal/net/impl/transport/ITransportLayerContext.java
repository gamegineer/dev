/*
 * ITransportLayerContext.java
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
 * Created on Apr 1, 2011 at 10:45:38 PM.
 */

package org.gamegineer.table.internal.net.impl.transport;

import org.eclipse.jdt.annotation.Nullable;

/**
 * The execution context for a network transport layer.
 * 
 * <p>
 * Provides operations that allow a transport layer to interact with the
 * application.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITransportLayerContext
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new network service.
     * 
     * @return A new network service.
     */
    public IService createService();

    /**
     * Invoked by the transport layer when it has been disconnected.
     * 
     * <p>
     * The application must still explicitly close the transport layer to clean
     * up resources even after it has been disconnected.
     * </p>
     * 
     * @param exception
     *        The exception that caused the transport layer to be disconnected
     *        or {@code null} if the transport layer was disconnected normally.
     */
    public void transportLayerDisconnected(
        @Nullable Exception exception );
}
