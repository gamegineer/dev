/*
 * GameServerConfigurationBuilder.java
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
 * Created on Nov 30, 2008 at 10:06:25 PM.
 */

package org.gamegineer.server.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import org.gamegineer.server.core.system.IGameSystemSource;
import org.gamegineer.server.internal.core.config.GameServerConfiguration;

/**
 * A factory for building instances of
 * {@link org.gamegineer.server.core.config.IGameServerConfiguration}.
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
public final class GameServerConfigurationBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system source. */
    private IGameSystemSource gameSystemSource_;

    /** The server name. */
    private String name_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameServerConfigurationBuilder}
     * class with an empty game server configuration.
     */
    public GameServerConfigurationBuilder()
    {
        name_ = null;
        gameSystemSource_ = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets the game system source.
     * 
     * @param gameSystemSource
     *        The game system source; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystemSource} is {@code null}.
     */
    /* @NonNull */
    public GameServerConfigurationBuilder setGameSystemSource(
        /* @NonNull */
        final IGameSystemSource gameSystemSource )
    {
        assertArgumentNotNull( gameSystemSource, "gameSystemSource" ); //$NON-NLS-1$

        gameSystemSource_ = gameSystemSource;

        return this;
    }

    /**
     * Sets the server name.
     * 
     * @param name
     *        The server name; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @NonNull */
    public GameServerConfigurationBuilder setName(
        /* @NonNull */
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        name_ = name;

        return this;
    }

    /**
     * Creates a new game server configuration based on the current state of
     * this builder.
     * 
     * @return A new game server configuration; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the current state of this builder does not represent a legal
     *         game server configuration.
     */
    /* @NonNull */
    public IGameServerConfiguration toGameServerConfiguration()
    {
        assertStateLegal( name_ != null, Messages.GameServerConfigurationBuilder_name_notSet );
        assertStateLegal( gameSystemSource_ != null, Messages.GameServerConfigurationBuilder_gameSystemSource_notSet );

        try
        {
            return GameServerConfiguration.createGameServerConfiguration( name_, gameSystemSource_ );
        }
        catch( final IllegalArgumentException e )
        {
            throw new IllegalStateException( Messages.GameServerConfigurationBuilder_state_illegal, e );
        }
    }
}
