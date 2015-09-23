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

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Optional;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.eclipse.jdt.annotation.NonNull;
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
public final class PlayersMessageHandlerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The mocks control for use in the fixture. */
    private Optional<IMocksControl> mocksControl_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayersMessageHandlerTest}
     * class.
     */
    public PlayersMessageHandlerTest()
    {
        mocksControl_ = Optional.empty();
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
        final IPlayer player = getMocksControl().createMock( IPlayer.class );
        EasyMock.expect( player.getName() ).andReturn( name ).anyTimes();
        EasyMock.expect( player.getRoles() ).andReturn( EnumSet.noneOf( PlayerRole.class ) ).anyTimes();
        return player;
    }

    /**
     * Gets the message handler under test in the fixture.
     * 
     * @return The message handler under test in the fixture; never {@code null}
     *         .
     */
    private IMessageHandler getMessageHandler()
    {
        return PlayersMessageHandler.INSTANCE;
    }

    /**
     * Gets the fixture mocks control.
     * 
     * @return The fixture mocks control; never {@code null}.
     */
    private IMocksControl getMocksControl()
    {
        return mocksControl_.get();
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
        mocksControl_ = Optional.of( EasyMock.createControl() );
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
        final IMocksControl mocksControl = getMocksControl();
        final Collection<IPlayer> players = Arrays.asList( //
            createMockPlayer( "player1" ), //$NON-NLS-1$
            createMockPlayer( "player2" ), //$NON-NLS-1$
            createMockPlayer( "player3" ) ); //$NON-NLS-1$
        final IClientNode localNode = mocksControl.createMock( IClientNode.class );
        EasyMock.expect( localNode.getPlayerName() ).andReturn( "player1" ).anyTimes(); //$NON-NLS-1$
        localNode.setPlayers( EasyMock.<@NonNull Collection<IPlayer>>notNull() );
        final IRemoteServerNodeController remoteNodeController = mocksControl.createMock( IRemoteServerNodeController.class );
        EasyMock.expect( remoteNodeController.getLocalNode() ).andReturn( localNode ).anyTimes();
        mocksControl.replay();

        final PlayersMessage message = new PlayersMessage();
        message.setPlayers( players );
        getMessageHandler().handleMessage( remoteNodeController, message );

        mocksControl.verify();
    }
}
