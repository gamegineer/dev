/*
 * ClientServiceHandlerAsServiceHandlerTest.java
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
 * Created on Jan 13, 2011 at 11:27:59 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import org.gamegineer.table.internal.net.connection.AbstractServiceHandlerTestCase;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.tcp.ClientServiceHandler} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.table.internal.net.connection.IServiceHandler}
 * interface.
 */
public final class ClientServiceHandlerAsServiceHandlerTest
    extends AbstractServiceHandlerTestCase<SelectableChannel, SelectionKey, ClientServiceHandler>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * ClientServiceHandlerAsServiceHandlerTest} class.
     */
    public ClientServiceHandlerAsServiceHandlerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.internal.net.connection.AbstractEventHandlerTestCase#createEventHandler()
     */
    @Override
    protected ClientServiceHandler createEventHandler()
    {
        return new ClientServiceHandler( new Dispatcher() );
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.AbstractEventHandlerTestCase#createMockEvent()
     */
    @Override
    protected SelectionKey createMockEvent()
    {
        return Mocks.createMockEvent();
    }

    /*
     * @see org.gamegineer.table.internal.net.connection.AbstractEventHandlerTestCase#createMockTransportHandle()
     */
    @Override
    protected SelectableChannel createMockTransportHandle()
    {
        return Mocks.createMockTransportHandle();
    }
}
