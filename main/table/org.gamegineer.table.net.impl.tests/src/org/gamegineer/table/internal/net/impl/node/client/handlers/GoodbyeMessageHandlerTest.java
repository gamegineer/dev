/*
 * GoodbyeMessageHandlerTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Jun 18, 2011 at 11:32:34 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client.handlers;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.impl.node.IMessageHandler;
import org.gamegineer.table.internal.net.impl.node.client.IClientNode;
import org.gamegineer.table.internal.net.impl.node.client.IRemoteServerNodeController;
import org.gamegineer.table.internal.net.impl.node.common.messages.GoodbyeMessage;
import org.gamegineer.table.net.TableNetworkError;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link GoodbyeMessageHandler} class.
 */
public final class GoodbyeMessageHandlerTest
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
     * Initializes a new instance of the {@code GoodbyeMessageHandlerTest}
     * class.
     */
    public GoodbyeMessageHandlerTest()
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
        messageHandler_ = GoodbyeMessageHandler.INSTANCE;
    }

    /**
     * Ensures the {@link GoodbyeMessageHandler#handleMessage} method correctly
     * handles a goodbye message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHandleMessage_GoodbyeMessage()
        throws Exception
    {
        final IClientNode localNode = mocksControl_.createMock( IClientNode.class );
        localNode.disconnect( TableNetworkError.SERVER_SHUTDOWN );
        final IRemoteServerNodeController remoteNodeController = mocksControl_.createMock( IRemoteServerNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        mocksControl_.replay();

        final GoodbyeMessage message = new GoodbyeMessage();
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
    }
}
