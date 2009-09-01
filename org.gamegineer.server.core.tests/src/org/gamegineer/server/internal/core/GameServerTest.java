/*
 * GameServerTest.java
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
 * Created on Dec 10, 2008 at 11:40:18 PM.
 */

package org.gamegineer.server.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Collection;
import org.gamegineer.game.core.IGame;
import org.gamegineer.game.core.config.Configurations;
import org.gamegineer.game.core.config.IGameConfiguration;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.server.core.GameServerConfigurationException;
import org.gamegineer.server.core.config.GameServerConfigurationBuilder;
import org.gamegineer.server.core.config.IGameServerConfiguration;
import org.gamegineer.server.core.system.FakeGameSystemSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.server.internal.core.GameServer} class.
 */
public final class GameServerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game server under test in the fixture. */
    private GameServer m_gameServer;

    /** The game server configuration. */
    private IGameServerConfiguration m_gameServerConfig;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameServerTest} class.
     */
    public GameServerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a game server configuration with a non-empty game system
     * collection.
     * 
     * @return The game server configuration; never {@code null}.
     */
    /* @NonNull */
    private static IGameServerConfiguration createGameServerConfiguration()
    {
        final GameServerConfigurationBuilder builder = new GameServerConfigurationBuilder();
        builder.setName( "name" ); //$NON-NLS-1$

        final Collection<IGameSystem> gameSystems = new ArrayList<IGameSystem>();
        gameSystems.add( GameSystems.createUniqueGameSystem() );
        gameSystems.add( GameSystems.createUniqueGameSystem() );
        gameSystems.add( GameSystems.createUniqueGameSystem() );
        builder.setGameSystemSource( new FakeGameSystemSource( gameSystems ) );

        return builder.toGameServerConfiguration();
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
        m_gameServerConfig = createGameServerConfiguration();
        m_gameServer = GameServer.createGameServer( m_gameServerConfig );
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
        m_gameServerConfig = null;
    }

    /**
     * Ensures the {@code createGameServer} method throws an exception when the
     * game server configuration is illegal.
     */
    @Ignore( "Currently, there is no way to create an illegal game server configuration." )
    @Test( expected = GameServerConfigurationException.class )
    public void testCreateGameServer_GameServerConfig_Illegal()
    {
        fail( "Test not implemented." ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createGameServer} method throws an exception when
     * passed a {@code null} game server configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = AssertionError.class )
    public void testCreateGameServer_GameServerConfig_Null()
        throws Exception
    {
        GameServer.createGameServer( null );
    }

    /**
     * Ensures the {@code getGame} method returns the expected value.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetGame()
        throws Exception
    {
        final IGameConfiguration gameConfig = Configurations.createUniqueGameConfiguration();
        final IGame expectedGame = m_gameServer.createGame( gameConfig );

        final IGame actualGame = m_gameServer.getGame( gameConfig.getId() );

        assertEquals( expectedGame.getId(), actualGame.getId() );
    }

    /**
     * Ensures the {@code getGameSystem} method returns the expected value.
     */
    @Test
    public void testGetGameSystem()
    {
        final String expectedGameSystemId = m_gameServerConfig.getGameSystemSource().getGameSystems().iterator().next().getId();

        final IGameSystem actualGameSystem = m_gameServer.getGameSystem( expectedGameSystemId );

        assertNotNull( actualGameSystem );
        assertEquals( expectedGameSystemId, actualGameSystem.getId() );
    }

    /**
     * Ensures the {@code getGameSystems} method returns the expected value.
     */
    @Test
    public void testGetGameSystems()
    {
        final Collection<IGameSystem> actualGameSystems = m_gameServer.getGameSystems();

        assertNotNull( actualGameSystems );
        assertEquals( m_gameServerConfig.getGameSystemSource().getGameSystems(), actualGameSystems );
    }

    /**
     * Ensures the {@code getGames} method returns the expected value.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testGetGames()
        throws Exception
    {
        m_gameServer.createGame( Configurations.createUniqueGameConfiguration() );
        m_gameServer.createGame( Configurations.createUniqueGameConfiguration() );

        final Collection<IGame> actualGames = m_gameServer.getGames();

        assertEquals( 2, actualGames.size() );
    }
}
