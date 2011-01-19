/*
 * NetworkInterface.java
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
 * Created on Jan 16, 2011 at 5:14:58 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.INetworkInterface;
import org.gamegineer.table.internal.net.connection.IAcceptor;
import org.gamegineer.table.internal.net.connection.IConnector;
import org.gamegineer.table.internal.net.connection.IDispatcher;

/**
 * Implementation of {@link org.gamegineer.table.internal.net.INetworkInterface}
 * for TCP connections.
 */
@Immutable
final class NetworkInterface
    implements INetworkInterface
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dispatcher associated with the network interface. */
    private final Dispatcher dispatcher_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkInterface} class.
     */
    NetworkInterface()
    {
        dispatcher_ = new Dispatcher();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.connection.INetworkInterface#createAcceptor()
     */
    @Override
    public IAcceptor<?, ?> createAcceptor()
    {
        return new Acceptor( dispatcher_ );
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.INetworkInterface#createConnector()
     */
    @Override
    public IConnector<?, ?> createConnector()
    {
        return new Connector( dispatcher_ );
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.INetworkInterface#getDispatcher()
     */
    @Override
    public IDispatcher<?, ?> getDispatcher()
    {
        return dispatcher_;
    }
}
