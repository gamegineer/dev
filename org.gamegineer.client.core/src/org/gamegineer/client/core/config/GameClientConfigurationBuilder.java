/*
 * GameClientConfigurationBuilder.java
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
 * Created on Mar 7, 2009 at 12:24:12 AM.
 */

package org.gamegineer.client.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import org.gamegineer.client.core.system.IGameSystemUiSource;
import org.gamegineer.client.internal.core.config.GameClientConfiguration;

/**
 * A factory for building instances of
 * {@link org.gamegineer.client.core.config.IGameClientConfiguration}.
 * 
 * <p>
 * Each game server configuration built by an instance of this class is
 * immutable and thus guaranteed to be thread-safe.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class GameClientConfigurationBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system user interface source. */
    private IGameSystemUiSource m_gameSystemUiSource;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameClientConfigurationBuilder}
     * class with an empty game server configuration.
     */
    public GameClientConfigurationBuilder()
    {
        m_gameSystemUiSource = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets the game system user interface source.
     * 
     * @param gameSystemUiSource
     *        The game system user interface source; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemUiSource} is {@code null}.
     */
    /* @NonNull */
    public GameClientConfigurationBuilder setGameSystemUiSource(
        /* @NonNull */
        final IGameSystemUiSource gameSystemUiSource )
    {
        assertArgumentNotNull( gameSystemUiSource, "gameSystemUiSource" ); //$NON-NLS-1$

        m_gameSystemUiSource = gameSystemUiSource;

        return this;
    }

    /**
     * Creates a new game client configuration based on the current state of
     * this builder.
     * 
     * @return A new game client configuration; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the current state of this builder does not represent a legal
     *         game client configuration.
     */
    /* @NonNull */
    public IGameClientConfiguration toGameClientConfiguration()
    {
        assertStateLegal( m_gameSystemUiSource != null, Messages.GameClientConfigurationBuilder_gameSystemUiSource_notSet );

        try
        {
            return GameClientConfiguration.createGameClientConfiguration( m_gameSystemUiSource );
        }
        catch( final IllegalArgumentException e )
        {
            throw new IllegalStateException( Messages.GameClientConfigurationBuilder_state_illegal, e );
        }
    }
}
