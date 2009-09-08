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
    private IGameServer gameServer_;


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
        assertNotNull( gameServer_ );
        return gameServer_;
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
        gameServer_ = createGameServer( Configurations.createGameServerConfiguration() );
        assertNotNull( gameServer_ );
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
        gameServer_ = null;
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
        gameServer_.createGame( org.gamegineer.game.core.config.Configurations.createIllegalGameConfiguration() );
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
        gameServer_.createGame( gameConfig1 );
        final IGameConfiguration gameConfig2 = org.gamegineer.game.core.config.Configurations.createGameConfiguration( gameId );

        gameServer_.createGame( gameConfig2 );
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
        assertNotNull( gameServer_.createGame( org.gamegineer.game.core.config.Configurations.createMinimalGameConfiguration() ) );
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
        gameServer_.createGame( null );
    }

    /**
     * Ensures the {@code getGame} method returns {@code null} when the game
     * identifier is absent.
     */
    @Test
    public void testGetGame_GameId_Absent()
    {
        assertNull( gameServer_.getGame( "unknownId" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getGame} method throws an exception when passed a
     * {@code null} game identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetGame_GameId_Null()
    {
        gameServer_.getGame( null );
    }

    /**
     * Ensures the {@code getGameSystem} method returns {@code null} when the
     * game system identifier is absent.
     */
    @Test
    public void testGetGameSystem_GameSystemId_Absent()
    {
        assertNull( gameServer_.getGameSystem( "unknownId" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code getGameSystem} method throws an exception when passed
     * a {@code null} game system identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testGetGameSystem_GameSystemId_Null()
    {
        gameServer_.getGameSystem( null );
    }

    /**
     * Ensures the {@code getGameSystems} method returns a copy of the game
     * system collection.
     */
    @Test
    public void testGetGameSystems_ReturnValue_Copy()
    {
        final Collection<IGameSystem> gameSystems = gameServer_.getGameSystems();
        final int expectedGameSystemsSize = gameSystems.size();

        gameSystems.add( createDummy( IGameSystem.class ) );

        assertEquals( expectedGameSystemsSize, gameServer_.getGameSystems().size() );
    }

    /**
     * Ensures the {@code getGameSystems} method does not return {@code null}.
     */
    @Test
    public void testGetGameSystems_ReturnValue_NonNull()
    {
        assertNotNull( gameServer_.getGameSystems() );
    }

    /**
     * Ensures the {@code getGames} method returns a copy of the game
     * collection.
     */
    @Test
    public void testGetGames_ReturnValue_Copy()
    {
        final Collection<IGame> games = gameServer_.getGames();
        final int expectedGamesSize = games.size();

        games.add( createDummy( IGame.class ) );

        assertEquals( expectedGamesSize, gameServer_.getGames().size() );
    }

    /**
     * Ensures the {@code getGames} method does not return {@code null}.
     */
    @Test
    public void testGetGames_ReturnValue_NonNull()
    {
        assertNotNull( gameServer_.getGames() );
    }

    /**
     * Ensures the {@code getName} method does not return {@code null}.
     */
    @Test
    public void testGetName_ReturnValue_NonNull()
    {
        assertNotNull( gameServer_.getName() );
    }
}
