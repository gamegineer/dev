/*
 * AbstractGameClientTestCase.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Dec 22, 2008 at 11:23:58 PM.
 */

package org.gamegineer.client.core;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.io.IOException;
import java.util.Collection;
import org.gamegineer.client.core.config.Configurations;
import org.gamegineer.client.core.config.IGameClientConfiguration;
import org.gamegineer.client.core.connection.IGameServerConnection;
import org.gamegineer.client.core.connection.MockGameServerConnection;
import org.gamegineer.game.ui.system.IGameSystemUi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.client.core.IGameClient} interface.
 */
public abstract class AbstractGameClientTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game client under test in the fixture. */
    private IGameClient gameClient_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractGameClientTestCase}
     * class.
     */
    protected AbstractGameClientTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the game client to be tested.
     * 
     * @param gameClientConfig
     *        The game client configuration; must not be {@code null}.
     * 
     * @return The game client to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code gameServerConfig} is {@code null}.
     */
    /* @NonNull */
    protected abstract IGameClient createGameClient(
        IGameClientConfiguration gameClientConfig )
        throws Exception;

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
        gameClient_ = createGameClient( Configurations.createGameClientConfiguration() );
        assertNotNull( gameClient_ );
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
        gameClient_ = null;
    }

    /**
     * Ensures the {@code connect} method opens the specified connection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConnect_Connection_Opened()
        throws Exception
    {
        final MockGameServerConnection mockConnection = new MockGameServerConnection();

        gameClient_.connect( mockConnection );

        assertEquals( 1, mockConnection.getOpenCallCount() );
    }

    /**
     * Ensures the {@code connect} method propagates any exception thrown by the
     * connection when it is opened.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IOException.class )
    public void testConnect_Connection_ThrowsException()
        throws Exception
    {
        final MockGameServerConnection mockConnection = new MockGameServerConnection();
        mockConnection.setThrowExceptionWhenOpened( true );

        gameClient_.connect( mockConnection );
    }

    /**
     * Ensures the {@code connect} method throws an exception when passed a
     * {@code null} game server connection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testConnect_Connection_Null()
        throws Exception
    {
        gameClient_.connect( null );
    }

    /**
     * Ensures the {@code connect} method closes the previous connection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testConnect_PreviousConnection_Closed()
        throws Exception
    {
        final MockGameServerConnection mockConnection = new MockGameServerConnection();
        gameClient_.connect( mockConnection );

        gameClient_.connect( new MockGameServerConnection() );

        assertEquals( 1, mockConnection.getCloseCallCount() );
    }

    /**
     * Ensures the {@code disconnect} method closes the connection.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_Connection_Closed()
        throws Exception
    {
        final MockGameServerConnection mockConnection = new MockGameServerConnection();
        gameClient_.connect( mockConnection );

        gameClient_.disconnect();

        assertEquals( 1, mockConnection.getCloseCallCount() );
    }

    /**
     * Ensures the {@code disconnect} method does not propagate any exception
     * thrown by the connection when it is closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testDisconnect_Connection_ThrowsException()
        throws Exception
    {
        final MockGameServerConnection mockConnection = new MockGameServerConnection();
        mockConnection.setThrowExceptionWhenClosed( true );
        gameClient_.connect( mockConnection );

        gameClient_.disconnect();
    }

    /**
     * Ensures the {@code disconnect} method does not throw an exception when
     * invoked while the client is disconnected.
     */
    @Test
    public void testDisconnect_Disconnected()
    {
        gameClient_.disconnect();

        gameClient_.disconnect();
    }

    /**
     * Ensures the {@code getGameServerConnection} method does not return
     * {@code null} when the client is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetGameServerConnection_Connected_ReturnValue_NonNull()
        throws Exception
    {
        gameClient_.connect( new MockGameServerConnection() );

        assertNotNull( gameClient_.getGameServerConnection() );
    }

    /**
     * Ensures the {@code getGameServerConnection} method returns a game server
     * connection whose {@code getGameServer} method does not throw an exception
     * when the client is disconnected.
     * 
     * <p>
     * The purpose of this test is to show that the client uses some sort of
     * null game server connection object when the client is disconnected.
     * </p>
     */
    @Test
    public void testGetGameServerConnection_Disconnected_GetGameServer_ReturnValue_NonNull()
    {
        gameClient_.disconnect();
        final IGameServerConnection connection = gameClient_.getGameServerConnection();

        assertNotNull( connection.getGameServer() );
    }

    /**
     * Ensures the {@code getGameServerConnection} method does not return
     * {@code null} when the client is disconnected.
     */
    @Test
    public void testGetGameServerConnection_Disconnected_ReturnValue_NonNull()
    {
        gameClient_.disconnect();

        assertNotNull( gameClient_.getGameServerConnection() );
    }

    /**
     * Ensures the {@code getGameSystemUi} method returns {@code null} when the
     * game system identifier is absent.
     */
    @Test
    public void testGetGameSystemUi_GameSystemId_Absent()
    {
        assertNull( gameClient_.getGameSystemUi( "unknownId" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getGameSystemUi} method throws an exception when
     * passed a {@code null} game system identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetGameSystemUi_GameSystemId_Null()
    {
        gameClient_.getGameSystemUi( null );
    }

    /**
     * Ensures the {@code getGameSystemUis} method returns a copy of the game
     * system user interface collection.
     */
    @Test
    public void testGetGameSystemUis_ReturnValue_Copy()
    {
        final Collection<IGameSystemUi> gameSystemUis = gameClient_.getGameSystemUis();
        final int expectedGameSystemUisSize = gameSystemUis.size();

        gameSystemUis.add( createDummy( IGameSystemUi.class ) );

        assertEquals( expectedGameSystemUisSize, gameClient_.getGameSystemUis().size() );
    }

    /**
     * Ensures the {@code getGameSystemUis} method does not return {@code null}.
     */
    @Test
    public void testGetGameSystemUis_ReturnValue_NonNull()
    {
        assertNotNull( gameClient_.getGameSystemUis() );
    }
}
