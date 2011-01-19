/*
 * INetworkInterface.java
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
 * Created on Jan 16, 2011 at 5:05:40 PM.
 */

package org.gamegineer.table.internal.net;

import org.gamegineer.table.internal.net.connection.IAcceptor;
import org.gamegineer.table.internal.net.connection.IConnector;
import org.gamegineer.table.internal.net.connection.IDispatcher;

/**
 * Encapsulates the participants of the network table Acceptor-Connector pattern
 * for a specific transport implementation.
 */
public interface INetworkInterface
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new acceptor for the network interface.
     * 
     * @return A new acceptor; never {@code null}.
     */
    /* @NonNull */
    public IAcceptor<?, ?> createAcceptor();

    /**
     * Creates a new connector for the network interface.
     * 
     * @return A new connector; never {@code null}.
     */
    /* @NonNull */
    public IConnector<?, ?> createConnector();

    /**
     * Gets the dispatcher associated with the network interface.
     * 
     * @return The dispatcher associated with the network interface; never
     *         {@code null}.
     */
    /* @NonNull */
    public IDispatcher<?, ?> getDispatcher();
}
