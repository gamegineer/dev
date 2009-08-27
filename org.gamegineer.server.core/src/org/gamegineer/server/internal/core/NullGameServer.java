/*
 * NullGameServer.java
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
 * Created on Dec 23, 2008 at 10:13:17 PM.
 */

package org.gamegineer.server.internal.core;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.core.GameConfigurationException;
import org.gamegineer.game.core.IGame;
import org.gamegineer.game.core.config.IGameConfiguration;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.server.core.IGameServer;

/**
 * Null object implementation of {@link org.gamegineer.server.core.IGameServer}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class NullGameServer
    implements IGameServer
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NullGameServer} class.
     */
    public NullGameServer()
    {
        super();
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

        throw new GameConfigurationException( Messages.NullGameServer_createGame_unsupported );
    }

    /*
     * @see org.gamegineer.server.core.IGameServer#getGame(java.lang.String)
     */
    public IGame getGame(
        final String gameId )
    {
        assertArgumentNotNull( gameId, "gameId" ); //$NON-NLS-1$

        return null;
    }

    /*
     * @see org.gamegineer.server.core.IGameServer#getGameSystem(java.lang.String)
     */
    public IGameSystem getGameSystem(
        final String gameSystemId )
    {
        assertArgumentNotNull( gameSystemId, "gameSystemId" ); //$NON-NLS-1$

        return null;
    }

    /*
     * @see org.gamegineer.server.core.IGameServer#getGameSystems()
     */
    public Collection<IGameSystem> getGameSystems()
    {
        return new ArrayList<IGameSystem>( 0 );
    }

    /*
     * @see org.gamegineer.server.core.IGameServer#getGames()
     */
    public Collection<IGame> getGames()
    {
        return new ArrayList<IGame>( 0 );
    }

    /*
     * @see org.gamegineer.server.core.IGameServer#getName()
     */
    public String getName()
    {
        return Messages.NullGameServer_name;
    }
}
