/*
 * TcpNetworkInterfaceFactory.java
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
 * Created on Jan 7, 2011 at 10:32:35 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.table.internal.net.NetworkTable;
import org.gamegineer.table.internal.net.connection.INetworkInterface;
import org.gamegineer.table.internal.net.connection.INetworkInterfaceFactory;

/**
 * Implementation of
 * {@link org.gamegineer.table.internal.net.connection.INetworkInterfaceFactory}
 * for TCP connections.
 */
@Immutable
public final class TcpNetworkInterfaceFactory
    implements INetworkInterfaceFactory
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TcpNetworkInterfaceFactory}.
     */
    public TcpNetworkInterfaceFactory()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.connection.INetworkInterfaceFactory#createNetworkInterface(org.gamegineer.table.internal.net.NetworkTable)
     */
    @Override
    public INetworkInterface createNetworkInterface(
        final NetworkTable networkTable )
    {
        assertArgumentNotNull( networkTable, "networkTable" ); //$NON-NLS-1$

        return new NetworkInterface();
    }
}
