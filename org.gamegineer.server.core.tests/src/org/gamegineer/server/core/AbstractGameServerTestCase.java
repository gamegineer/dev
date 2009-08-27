/*
 * AbstractGameServerTestCase.java
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
 * Created on Dec 10, 2008 at 11:28:44 PM.
 */

package org.gamegineer.server.core;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.Collection;
import org.gamegineer.game.core.GameConfigurationException;
import org.gamegineer.game.core.IGame;
import org.gamegineer.game.core.config.IGameConfiguration;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.server.core.config.Configurations;
import org.gamegineer.server.core.config.IGameServerConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.server.core.IGameServer} interface.
 */
public abstract class AbstractGameServerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game server under test in the fixture. */
    private IGameServer m_gameServer;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractGameServerTestCase}
     * class.
     */
    protected AbstractGameServerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the game server to be tested.
     * 
     * @param gameServerConfig
     *        The game server configuration; must not be {@code null}.
     * 
     * @return The game server to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code gameServerConfig} is {@code null}.
     */
    /* @NonNull */
    protected abstract IGameServer createGameServer(
        /* @NonNull */
        IGameServerConfiguration gameServerConfig )
        throws Exception;

    /**
     * Gets the game server under test in the fixture.
     * 
     * @return The game server under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final IGameServer getGameServer()
    {
        assertNotNull( m_gameServer );
        return m_gameServer;
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
        m_gameServer = createGameServer( Configurations.createGameServerConfiguration() );
        assertNotNull( m_gameServer );
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
        m_gameServer = null;
    }

    /**
     * Ensures the {@code createGame} method throws an exception when passed an
     * illegal game configuration, which is illegal for any reason other than it
     * contains a duplicate game identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = GameConfigurationException.class )
    public void testCreateGame_GameConfig_Illegal()
        throws Exception
    {
        m_gameServer.createGame( org.gamegineer.game.core.config.Configurations.createIllegalGameConfiguration() );
    }

    /**
     * Ensures the {@code createGame} method throws an exception when passed an
     * illegal game configuration, which is illegal because it contains a
     * duplicate game identifier.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = GameConfigurationException.class )
    public void testCreateGame_GameConfig_Illegal_DuplicateGameId()
        throws Exception
    {
        final String gameId = "game-id"; //$NON-NLS-1$
        final IGameConfiguration gameConfig1 = org.gamegineer.game.core.config.Configurations.createGameConfiguration( gameId );
        m_gameServer.createGame( gameConfig1 );
        final IGameConfiguration gameConfig2 = org.gamegineer.game.core.config.Configurations.createGameConfiguration( gameId );

        m_gameServer.createGame( gameConfig2 );
    }

    /**
     * Ensures the {@code createGame} method does not return {@code null} when
     * passed a legal game configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGame_GameConfig_Legal()
        throws Exception
    {
        assertNotNull( m_gameServer.createGame( org.gamegineer.game.core.config.Configurations.createMinimalGameConfiguration() ) );
    }

    /**
     * Ensures the {@code createGame} method throws an exception when passed a
     * {@code null} game configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGame_GameConfig_Null()
        throws Exception
    {
        m_gameServer.createGame( null );
    }

    /**
     * Ensures the {@code getGame} method returns {@code null} when the game
     * identifier is absent.
     */
    @Test
    public void testGetGame_GameId_Absent()
    {
        assertNull( m_gameServer.getGame( "unknownId" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getGame} method throws an exception when passed a
     * {@code null} game identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetGame_GameId_Null()
    {
        m_gameServer.getGame( null );
    }

    /**
     * Ensures the {@code getGameSystem} method returns {@code null} when the
     * game system identifier is absent.
     */
    @Test
    public void testGetGameSystem_GameSystemId_Absent()
    {
        assertNull( m_gameServer.getGameSystem( "unknownId" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getGameSystem} method throws an exception when passed
     * a {@code null} game system identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetGameSystem_GameSystemId_Null()
    {
        m_gameServer.getGameSystem( null );
    }

    /**
     * Ensures the {@code getGameSystems} method returns a copy of the game
     * system collection.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testGetGameSystems_ReturnValue_Copy()
    {
        final Collection<IGameSystem> gameSystems = m_gameServer.getGameSystems();
        final int expectedGameSystemsSize = gameSystems.size();

        gameSystems.add( createDummy( IGameSystem.class ) );

        assertEquals( expectedGameSystemsSize, m_gameServer.getGameSystems().size() );
    }

    /**
     * Ensures the {@code getGameSystems} method does not return {@code null}.
     */
    @Test
    public void testGetGameSystems_ReturnValue_NonNull()
    {
        assertNotNull( m_gameServer.getGameSystems() );
    }

    /**
     * Ensures the {@code getGames} method returns a copy of the game
     * collection.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testGetGames_ReturnValue_Copy()
    {
        final Collection<IGame> games = m_gameServer.getGames();
        final int expectedGamesSize = games.size();

        games.add( createDummy( IGame.class ) );

        assertEquals( expectedGamesSize, m_gameServer.getGames().size() );
    }

    /**
     * Ensures the {@code getGames} method does not return {@code null}.
     */
    @Test
    public void testGetGames_ReturnValue_NonNull()
    {
        assertNotNull( m_gameServer.getGames() );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertNotNull( m_gameServer.getName() );
    }
}
