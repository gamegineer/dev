/*
 * CancelControlRequestMessageHandlerTest.java
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
 * Created on Sep 4, 2011 at 9:41:40 PM.
 */

package org.gamegineer.table.internal.net.impl.node.server.handlers;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.internal.net.impl.node.IMessageHandler;
import org.gamegineer.table.internal.net.impl.node.common.messages.CancelControlRequestMessage;
import org.gamegineer.table.internal.net.impl.node.server.IRemoteClientNodeController;
import org.gamegineer.table.internal.net.impl.node.server.IServerNode;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link CancelControlRequestMessageHandler} class.
 */
@NonNullByDefault( {
    DefaultLocation.PARAMETER, //
    DefaultLocation.RETURN_TYPE, //
    DefaultLocation.TYPE_BOUND, //
    DefaultLocation.TYPE_ARGUMENT
} )
public final class CancelControlRequestMessageHandlerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The message handler under test in the fixture. */
    private IMessageHandler messageHandler_;

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code CancelControlRequestMessageHandlerTest} class.
     */
    public CancelControlRequestMessageHandlerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        mocksControl_ = EasyMock.createControl();
        messageHandler_ = CancelControlRequestMessageHandler.INSTANCE;
    }

    /**
     * Ensures the {@link CancelControlRequestMessageHandler#handleMessage}
     * method correctly handles a cancel control request message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHandleMessage_CancelControlRequestMessage()
        throws Exception
    {
        final IServerNode localNode = mocksControl_.createMock( IServerNode.class );
        localNode.cancelControlRequest();
        final IRemoteClientNodeController remoteNodeController = mocksControl_.createMock( IRemoteClientNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        mocksControl_.replay();

        final CancelControlRequestMessage message = new CancelControlRequestMessage();
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
    }
}
