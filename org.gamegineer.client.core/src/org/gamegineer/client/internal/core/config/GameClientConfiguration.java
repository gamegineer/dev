/*
 * GameClientConfiguration.java
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
 * Created on Mar 6, 2009 at 11:43:34 PM.
 */

package org.gamegineer.client.internal.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.client.core.config.IGameClientConfiguration;
import org.gamegineer.client.core.system.IGameSystemUiSource;

/**
 * Implementation of
 * {@link org.gamegineer.client.core.config.IGameClientConfiguration}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class GameClientConfiguration
    implements IGameClientConfiguration
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system user interface source. */
    private final IGameSystemUiSource m_gameSystemUiSource;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameClientConfiguration} class.
     * 
     * @param gameSystemUiSource
     *        The game system user interface source; must not be {@code null}.
     */
    private GameClientConfiguration(
        /* @NonNull */
        final IGameSystemUiSource gameSystemUiSource )
    {
        assert gameSystemUiSource != null;

        m_gameSystemUiSource = gameSystemUiSource;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code GameClientConfiguration} class.
     * 
     * @param gameSystemUiSource
     *        The game system user interface source; must not be {@code null}.
     * 
     * @return A new game client configuration; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any argument will result in an illegal game client
     *         configuration.
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemUiSource} is {@code null}.
     */
    /* @NonNull */
    public static GameClientConfiguration createGameClientConfiguration(
        /* @NonNull */
        final IGameSystemUiSource gameSystemUiSource )
    {
        assertArgumentNotNull( gameSystemUiSource, "gameSystemUiSource" ); //$NON-NLS-1$

        final GameClientConfiguration gameClientConfig = new GameClientConfiguration( gameSystemUiSource );
        ConfigurationUtils.assertGameClientConfigurationLegal( gameClientConfig );
        return gameClientConfig;
    }

    /*
     * @see org.gamegineer.client.core.config.IGameClientConfiguration#getGameSystemUiSource()
     */
    public IGameSystemUiSource getGameSystemUiSource()
    {
        return m_gameSystemUiSource;
    }
}
