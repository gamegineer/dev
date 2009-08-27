/*
 * GameServerConfiguration.java
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
 * Created on Nov 30, 2008 at 10:29:00 PM.
 */

package org.gamegineer.server.internal.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.server.core.config.IGameServerConfiguration;
import org.gamegineer.server.core.system.IGameSystemSource;

/**
 * Implementation of
 * {@link org.gamegineer.server.core.config.IGameServerConfiguration}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class GameServerConfiguration
    implements IGameServerConfiguration
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system source. */
    private final IGameSystemSource m_gameSystemSource;

    /** The server name. */
    private final String m_name;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameServerConfiguration} class.
     * 
     * @param name
     *        The server name; must not be {@code null}.
     * @param gameSystemSource
     *        The game system source; must not be {@code null}.
     */
    private GameServerConfiguration(
        /* @NonNull */
        final String name,
        /* @NonNull */
        final IGameSystemSource gameSystemSource )
    {
        assert name != null;
        assert gameSystemSource != null;

        m_name = name;
        m_gameSystemSource = gameSystemSource;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code GameServerConfiguration} class.
     * 
     * @param name
     *        The server name; must not be {@code null}.
     * @param gameSystemSource
     *        The game system source; must not be {@code null}.
     * 
     * @return A new game server configuration; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any argument will result in an illegal game server
     *         configuration.
     * @throws java.lang.NullPointerException
     *         If {@code name} or {@code gameSystemSource} is {@code null}.
     */
    /* @NonNull */
    public static GameServerConfiguration createGameServerConfiguration(
        /* @NonNull */
        final String name,
        /* @NonNull */
        final IGameSystemSource gameSystemSource )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentNotNull( gameSystemSource, "gameSystemSource" ); //$NON-NLS-1$

        final GameServerConfiguration gameServerConfig = new GameServerConfiguration( name, gameSystemSource );
        ConfigurationUtils.assertGameServerConfigurationLegal( gameServerConfig );
        return gameServerConfig;
    }

    /*
     * @see org.gamegineer.server.core.config.IGameServerConfiguration#getGameSystemSource()
     */
    public IGameSystemSource getGameSystemSource()
    {
        return m_gameSystemSource;
    }

    /*
     * @see org.gamegineer.server.core.config.IGameServerConfiguration#getName()
     */
    public String getName()
    {
        return m_name;
    }
}
