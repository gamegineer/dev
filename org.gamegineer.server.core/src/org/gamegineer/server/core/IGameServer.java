/*
 * IGameServer.java
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
 * Created on Sep 23, 2008 at 10:52:37 PM.
 */

package org.gamegineer.server.core;

import java.util.Collection;
import org.gamegineer.game.core.GameConfigurationException;
import org.gamegineer.game.core.IGame;
import org.gamegineer.game.core.config.IGameConfiguration;
import org.gamegineer.game.core.system.IGameSystem;

/**
 * A game server.
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IGameServer
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new game on this server.
     * 
     * @param gameConfig
     *        The game configuration; must not be {@code null}.
     * 
     * @return The new game; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameConfig} is {@code null}.
     * @throws org.gamegineer.game.core.GameConfigurationException
     *         If {@code gameConfig} represents an illegal configuration,
     *         including the case where a game with the specified identifier
     *         already exists on this server.
     */
    /* @NonNull */
    public IGame createGame(
        /* @NonNull */
        IGameConfiguration gameConfig )
        throws GameConfigurationException;

    /**
     * Gets a game on this server.
     * 
     * @param gameId
     *        The game identifier; must not be {@code null}.
     * 
     * @return The game or {@code null} if a game with the specified identifier
     *         does not exist on this server.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameId} is {@code null}.
     */
    /* @Nullable */
    public IGame getGame(
        /* @NonNull */
        String gameId );

    /**
     * Gets a game system on this server.
     * 
     * @param gameSystemId
     *        The game system identifier; must not be {@code null}.
     * 
     * @return The game system or {@code null} if a game system with the
     *         specified identifier does not exist on this server.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemId} is {@code null}.
     */
    /* @Nullable */
    public IGameSystem getGameSystem(
        /* @NonNull */
        String gameSystemId );

    /**
     * Gets the game systems available to this server.
     * 
     * @return The game systems available to this server; never {@code null}.
     */
    /* @NonNull */
    public Collection<IGameSystem> getGameSystems();

    /**
     * Gets the games on this server.
     * 
     * @return The games on this server; never {@code null}.
     */
    /* @NonNull */
    public Collection<IGame> getGames();

    /**
     * Gets the name of this server.
     * 
     * @return The name of this server; never {@code null}.
     */
    /* @NonNull */
    public String getName();
}
