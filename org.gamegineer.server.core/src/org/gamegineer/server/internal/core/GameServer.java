/*
 * GameServer.java
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
 * Created on Dec 10, 2008 at 11:39:53 PM.
 */

package org.gamegineer.server.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.game.core.GameConfigurationException;
import org.gamegineer.game.core.GameFactory;
import org.gamegineer.game.core.IGame;
import org.gamegineer.game.core.config.IGameConfiguration;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.server.core.GameServerConfigurationException;
import org.gamegineer.server.core.IGameServer;
import org.gamegineer.server.core.config.IGameServerConfiguration;
import org.gamegineer.server.internal.core.config.ConfigurationUtils;

/**
 * Implementation of {@link org.gamegineer.server.core.IGameServer}.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 */
@ThreadSafe
final class GameServer
    implements IGameServer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game server configuration. */
    private final IGameServerConfiguration m_config;

    /** The collection of games managed by this server. */
    private final ConcurrentMap<String, IGame> m_games;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameServer} class.
     * 
     * @param gameServerConfig
     *        The game server configuration; must not be {@code null}.
     */
    private GameServer(
        /* @NonNull */
        final IGameServerConfiguration gameServerConfig )
    {
        assert gameServerConfig != null;

        m_config = gameServerConfig;
        m_games = new ConcurrentHashMap<String, IGame>();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.server.core.IGameServer#createGame(org.gamegineer.game.core.config.IGameConfiguration)
     */
    public IGame createGame(
        final IGameConfiguration gameConfig )
        throws GameConfigurationException
    {
        assertArgumentNotNull( gameConfig, "gameConfig" ); //$NON-NLS-1$

        final IGame game = GameFactory.createGame( gameConfig );
        if( m_games.putIfAbsent( game.getId(), game ) != null )
        {
            throw new GameConfigurationException( Messages.GameServer_createGame_duplicateGameId( game.getId() ) );
        }
        return game;
    }

    /**
     * Creates a new instance of the {@code GameServer} class.
     * 
     * @param gameServerConfig
     *        The game server configuration; must not be {@code null}.
     * 
     * @return A new instance of the {@code GameServer} class; never
     *         {@code null}.
     * 
     * @throws org.gamegineer.server.core.GameServerConfigurationException
     *         If an error occurs while creating the game server.
     */
    /* @NonNull */
    static GameServer createGameServer(
        /* @NonNull */
        final IGameServerConfiguration gameServerConfig )
        throws GameServerConfigurationException
    {
        assert gameServerConfig != null;

        try
        {
            ConfigurationUtils.assertGameServerConfigurationLegal( gameServerConfig );
        }
        catch( final IllegalArgumentException e )
        {
            throw new GameServerConfigurationException( Messages.GameServer_gameServerConfig_illegal, e );
        }

        return new GameServer( gameServerConfig );
    }

    /*
     * @see org.gamegineer.server.core.IGameServer#getGame(java.lang.String)
     */
    public IGame getGame(
        final String gameId )
    {
        assertArgumentNotNull( gameId, "gameId" ); //$NON-NLS-1$

        return m_games.get( gameId );
    }

    /*
     * @see org.gamegineer.server.core.IGameServer#getGameSystem(java.lang.String)
     */
    public IGameSystem getGameSystem(
        final String gameSystemId )
    {
        assertArgumentNotNull( gameSystemId, "gameSystemId" ); //$NON-NLS-1$

        for( final IGameSystem gameSystem : getGameSystems() )
        {
            if( gameSystemId.equals( gameSystem.getId() ) )
            {
                return gameSystem;
            }
        }

        return null;
    }

    /*
     * @see org.gamegineer.server.core.IGameServer#getGameSystems()
     */
    public Collection<IGameSystem> getGameSystems()
    {
        return m_config.getGameSystemSource().getGameSystems();
    }

    /*
     * @see org.gamegineer.server.core.IGameServer#getGames()
     */
    public Collection<IGame> getGames()
    {
        return new ArrayList<IGame>( m_games.values() );
    }

    /*
     * @see org.gamegineer.server.core.IGameServer#getName()
     */
    public String getName()
    {
        return m_config.getName();
    }
}
