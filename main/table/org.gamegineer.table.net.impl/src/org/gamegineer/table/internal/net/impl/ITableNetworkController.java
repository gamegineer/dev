/*
 * ITableNetworkController.java
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
 * Created on Apr 18, 2011 at 7:49:15 PM.
 */

package org.gamegineer.table.internal.net.impl;

import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.net.impl.node.INodeController;
import org.gamegineer.table.internal.net.impl.transport.ITransportLayerFactory;
import org.gamegineer.table.net.TableNetworkError;

/**
 * The control interface for a table network.
 * 
 * <p>
 * This interface provides operations that allow a table network node controller
 * to control the local table network. It is only intended for use by an
 * implementation of {@link INodeController}.
 * </p>
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ITableNetworkController
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Disconnects the table network for the specified cause.
     * 
     * <p>
     * This method blocks until the table network is disconnected.
     * </p>
     * 
     * @param error
     *        The error that caused the table network to be disconnected or
     *        {@code null} if the table network was disconnected normally.
     * 
     * @throws java.lang.InterruptedException
     *         If this thread is interrupted while waiting for the table network
     *         to disconnect.
     */
    public void disconnect(
        @Nullable TableNetworkError error )
        throws InterruptedException;

    /**
     * Gets the table network transport layer factory.
     * 
     * @return The table network transport layer factory.
     */
    public ITransportLayerFactory getTransportLayerFactory();

    /**
     * Invoked by the local table network node when the collection of players
     * connected to the table network has been updated.
     */
    public void playersUpdated();
}
