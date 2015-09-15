/*
 * PlayersMessageHandlerTest.java
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
 * Created on May 20, 2011 at 9:45:12 PM.
 */

package org.gamegineer.table.internal.net.impl.node.client.handlers;

import static org.gamegineer.common.core.runtime.NullAnalysis.assumeNonNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.gamegineer.table.internal.net.impl.node.IMessageHandler;
import org.gamegineer.table.internal.net.impl.node.client.IClientNode;
import org.gamegineer.table.internal.net.impl.node.client.IRemoteServerNodeController;
import org.gamegineer.table.internal.net.impl.node.common.messages.PlayersMessage;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.PlayerRole;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link PlayersMessageHandler} class.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
public final class PlayersMessageHandlerTest
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
     * Initializes a new instance of the {@code PlayersMessageHandlerTest}
     * class.
     */
    public PlayersMessageHandlerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Create a new mock player.
     * 
     * @param name
     *        The player name; must not be {@code null}.
     * 
     * @return The new mock player; never {@code null}.
     */
    private IPlayer createMockPlayer(
        final String name )
    {
        final IPlayer player = mocksControl_.createMock( IPlayer.class );
        EasyMock.expect( player.getName() ).andReturn( name ).anyTimes();
        EasyMock.expect( player.getRoles() ).andReturn( EnumSet.noneOf( PlayerRole.class ) ).anyTimes();
        return player;
    }

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
        messageHandler_ = PlayersMessageHandler.INSTANCE;
    }

    /**
     * Ensures the {@link PlayersMessageHandler#handleMessage} method correctly
     * handles a players message.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testHandleMessage_PlayersMessage()
        throws Exception
    {
        final Collection<IPlayer> players = Arrays.asList( //
            createMockPlayer( "player1" ), //$NON-NLS-1$
            createMockPlayer( "player2" ), //$NON-NLS-1$
            createMockPlayer( "player3" ) ); //$NON-NLS-1$
        final IClientNode localNode = mocksControl_.createMock( IClientNode.class );
        EasyMock.expect( localNode.getPlayerName() ).andReturn( "player1" ).anyTimes(); //$NON-NLS-1$
        localNode.setPlayers( assumeNonNull( EasyMock.<Collection<IPlayer>>notNull() ) );
        final IRemoteServerNodeController remoteNodeController = mocksControl_.createMock( IRemoteServerNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        mocksControl_.replay();

        final PlayersMessage message = new PlayersMessage();
        message.setPlayers( players );
        messageHandler_.handleMessage( remoteNodeController, message );

        mocksControl_.verify();
    }
}
