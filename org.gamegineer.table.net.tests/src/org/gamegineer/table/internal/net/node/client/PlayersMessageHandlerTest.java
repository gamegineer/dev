/*
 * PlayersMessageHandlerTest.java
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
 * Created on May 20, 2011 at 9:45:12 PM.
 */

package org.gamegineer.table.internal.net.node.client;

import java.util.Arrays;
import java.util.Collection;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.gamegineer.table.internal.net.node.common.messages.PlayersMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.client.PlayersMessageHandler}
 * class.
 */
public final class PlayersMessageHandlerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private IMocksControl mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayersMessageHandlerTest}
     * class.
     */
    public PlayersMessageHandlerTest()
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
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        mocksControl_ = null;
    }

    /**
     * Ensures the {@code handleMessage} method correctly handles a players
     * message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHandleMessage_PlayersMessage()
        throws Exception
    {
        final Collection<String> players = Arrays.asList( "player1", "player2", "player3" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        final IClientNode localNode = mocksControl_.createMock( IClientNode.class );
        localNode.setPlayers( players );
        final IRemoteServerNodeController controller = mocksControl_.createMock( IRemoteServerNodeController.class );
        EasyMock.expect( controller.getLocalNode() ).andReturn( localNode ).anyTimes();
        mocksControl_.replay();

        final PlayersMessage message = new PlayersMessage();
        message.setPlayers( players );
        final PlayersMessageHandler messageHandler = new PlayersMessageHandler( controller );
        messageHandler.handleMessage( message );

        mocksControl_.verify();
    }
}
