/*
 * GiveControlMessageHandlerTest.java
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
 * Created on Sep 4, 2011 at 10:02:04 PM.
 */

package org.gamegineer.table.internal.net.node.server.handlers;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.node.IMessageHandler;
import org.gamegineer.table.internal.net.node.common.messages.GiveControlMessage;
import org.gamegineer.table.internal.net.node.server.IRemoteClientNodeController;
import org.gamegineer.table.internal.net.node.server.IServerNode;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.server.handlers.GiveControlMessageHandler}
 * class.
 */
public final class GiveControlMessageHandlerTest
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
     * Initializes a new instance of the {@code GiveControlMessageHandlerTest}
     * class.
     */
    public GiveControlMessageHandlerTest()
    {
        super();
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
        messageHandler_ = GiveControlMessageHandler.INSTANCE;
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles a give control
     * message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHandleMessage_GiveControlMessage()
        throws Exception
    {
        final String remotePlayerName = "name"; //$NON-NLS-1$
        final String newEditorPlayerName = "otherName"; //$NON-NLS-1$
        final IServerNode localNode = mocksControl_.createMock( IServerNode.class );
        localNode.giveControl( remotePlayerName, newEditorPlayerName );
        final IRemoteClientNodeController remoteNodeController = mocksControl_.createMock( IRemoteClientNodeController.class );
        EasyMock.expect( remoteNodeController.getPlayerName() ).andReturn( remotePlayerName ).anyTimes();
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        mocksControl_.replay();

        final GiveControlMessage message = new GiveControlMessage();
        message.setPlayerName( newEditorPlayerName );
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
    }
}
