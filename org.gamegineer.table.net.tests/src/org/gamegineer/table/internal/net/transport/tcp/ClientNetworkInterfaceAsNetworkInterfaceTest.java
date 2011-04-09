/*
 * ClientNetworkInterfaceAsNetworkInterfaceTest.java
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
 * Created on Jan 18, 2011 at 9:00:54 PM.
 */

package org.gamegineer.table.internal.net.transport.tcp;

import org.easymock.EasyMock;
import org.gamegineer.table.internal.net.transport.AbstractNetworkInterfaceTestCase;
import org.gamegineer.table.internal.net.transport.INetworkInterface;
import org.gamegineer.table.internal.net.transport.INetworkInterfaceContext;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.transport.tcp.ClientNetworkInterface}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.transport.INetworkInterface}
 * interface.
 */
public final class ClientNetworkInterfaceAsNetworkInterfaceTest
    extends AbstractNetworkInterfaceTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * ClientNetworkInterfaceAsNetworkInterfaceTest} class.
     */
    public ClientNetworkInterfaceAsNetworkInterfaceTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.transport.AbstractNetworkInterfaceTestCase#createNetworkInterface()
     */
    @Override
    protected INetworkInterface createNetworkInterface()
    {
        return new ClientNetworkInterface( EasyMock.createMock( INetworkInterfaceContext.class ) );
    }
}
