/*
 * GameConfigurationBuilder.java
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
 * Created on Jul 10, 2008 at 9:29:26 PM.
 */

package org.gamegineer.game.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import static org.gamegineer.common.core.runtime.Assert.assertStateLegal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.internal.core.config.GameConfiguration;

/**
 * A factory for building instances of
 * {@link org.gamegineer.game.core.config.IGameConfiguration}.
 * 
 * <p>
 * Each game configuration built by an instance of this class is immutable and
 * thus guaranteed to be thread-safe.
 * </p>
 * 
 * <p>
 * This class is not thread-safe.
 * </p>
 */
public final class GameConfigurationBuilder
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game identifier. */
    private String m_id;

    /** The game name. */
    private String m_name;

    /** The player configuration list. */
    private final List<IPlayerConfiguration> m_playerConfigs;

    /** The game system. */
    private IGameSystem m_system;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameConfigurationBuilder} class
     * with an empty game configuration.
     */
    public GameConfigurationBuilder()
    {
        m_id = null;
        m_name = null;
        m_playerConfigs = new ArrayList<IPlayerConfiguration>();
        m_system = null;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds a player.
     * 
     * @param playerConfig
     *        The player configuration; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code playerConfig} is {@code null}.
     */
    /* @NonNull */
    public GameConfigurationBuilder addPlayer(
        /* @NonNull */
        final IPlayerConfiguration playerConfig )
    {
        assertArgumentNotNull( playerConfig, "playerConfig" ); //$NON-NLS-1$

        m_playerConfigs.add( playerConfig );

        return this;
    }

    /**
     * Adds a collection of players.
     * 
     * @param playerConfigs
     *        The player configuration list; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code playerConfigs} is {@code null}.
     */
    /* @NonNull */
    public GameConfigurationBuilder addPlayers(
        /* @NonNull */
        final List<IPlayerConfiguration> playerConfigs )
    {
        assertArgumentNotNull( playerConfigs, "playerConfigs" ); //$NON-NLS-1$

        m_playerConfigs.addAll( playerConfigs );

        return this;
    }

    /**
     * Gets an immutable view of the player configuration list.
     * 
     * @return An immutable view of the player configuration list; never
     *         {@code null}.
     */
    /* @NonNull */
    List<IPlayerConfiguration> getPlayers()
    {
        return Collections.unmodifiableList( m_playerConfigs );
    }

    /**
     * Gets the game system.
     * 
     * @return The game system; may be {@code null}.
     */
    /* @Nullable */
    IGameSystem getSystem()
    {
        return m_system;
    }

    /**
     * Sets the game identifier.
     * 
     * @param id
     *        The game identifier; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public GameConfigurationBuilder setId(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        m_id = id;

        return this;
    }

    /**
     * Sets the game name.
     * 
     * @param name
     *        The game name; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code name} is {@code null}.
     */
    /* @NonNull */
    public GameConfigurationBuilder setName(
        /* @NonNull */
        final String name )
    {
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$

        m_name = name;

        return this;
    }

    /**
     * Sets the game system.
     * 
     * @param system
     *        The game system; must not be {@code null}.
     * 
     * @return A reference to this builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code system} is {@code null}.
     */
    /* @NonNull */
    public GameConfigurationBuilder setSystem(
        /* @NonNull */
        final IGameSystem system )
    {
        assertArgumentNotNull( system, "system" ); //$NON-NLS-1$

        m_system = system;

        return this;
    }

    /**
     * Creates a new game configuration based on the current state of this
     * builder.
     * 
     * @return A new game configuration; never {@code null}.
     * 
     * @throws java.lang.IllegalStateException
     *         If the current state of this builder does not represent a legal
     *         game configuration.
     */
    /* @NonNull */
    public IGameConfiguration toGameConfiguration()
    {
        assertStateLegal( m_id != null, Messages.GameConfigurationBuilder_id_notSet );
        assertStateLegal( m_name != null, Messages.GameConfigurationBuilder_name_notSet );
        assertStateLegal( m_system != null, Messages.GameConfigurationBuilder_system_notSet );

        try
        {
            return GameConfiguration.createGameConfiguration( m_id, m_name, m_system, m_playerConfigs );
        }
        catch( final IllegalArgumentException e )
        {
            throw new IllegalStateException( Messages.GameConfigurationBuilder_state_illegal, e );
        }
    }
}
