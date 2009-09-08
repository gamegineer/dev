/*
 * GameConfiguration.java
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
 * Created on Jul 10, 2008 at 9:36:15 PM.
 */

package org.gamegineer.game.internal.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.List;
import net.jcip.annotations.Immutable;
import org.gamegineer.game.core.config.IGameConfiguration;
import org.gamegineer.game.core.config.IPlayerConfiguration;
import org.gamegineer.game.core.system.IGameSystem;

/**
 * Implementation of {@link org.gamegineer.game.core.config.IGameConfiguration}.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class GameConfiguration
    implements IGameConfiguration
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game identifier. */
    private final String id_;

    /** The game name. */
    private final String name_;

    /** The list of player configurations. */
    private final List<IPlayerConfiguration> playerConfigs_;

    /** The game system. */
    private final IGameSystem system_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameConfiguration} class.
     * 
     * @param id
     *        The game identifier; must not be {@code null}.
     * @param name
     *        The game name; must not be {@code null}.
     * @param system
     *        The game system; must not be {@code null}.
     * @param playerConfigs
     *        The list of player configurations; must not be {@code null}.
     */
    private GameConfiguration(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final String name,
        /* @NonNull */
        final IGameSystem system,
        /* @NonNull */
        final List<IPlayerConfiguration> playerConfigs )
    {
        assert id != null;
        assert name != null;
        assert system != null;
        assert playerConfigs != null;

        id_ = id;
        name_ = name;
        system_ = system;
        playerConfigs_ = new ArrayList<IPlayerConfiguration>( playerConfigs );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new instance of the {@code GameConfiguration} class.
     * 
     * @param id
     *        The game identifier; must not be {@code null}.
     * @param name
     *        The game name; must not be {@code null}.
     * @param system
     *        The game system; must not be {@code null}.
     * @param playerConfigs
     *        The player configuration list; must not be {@code null}.
     * 
     * @return A new game configuration; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If any argument will result in an illegal game configuration.
     * @throws java.lang.NullPointerException
     *         If {@code id}, {@code name}, {@code system}, or {@code
     *         playerConfigs} is {@code null}.
     */
    /* @NonNull */
    public static GameConfiguration createGameConfiguration(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final String name,
        /* @NonNull */
        final IGameSystem system,
        /* @NonNull */
        final List<IPlayerConfiguration> playerConfigs )
    {
        // TODO: Determine a pattern whereby we can correctly determine which argument
        // caused the resulting game configuration to be illegal so we can indicate that
        // to the caller.  One possibility is that the exception thrown by the
        // assertGameConfigurationLegal() method carry a field that indicates
        // which attribute of the game configuration was illegal and we can map that
        // back to an argument of this method.

        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$
        assertArgumentNotNull( name, "name" ); //$NON-NLS-1$
        assertArgumentNotNull( system, "system" ); //$NON-NLS-1$
        assertArgumentNotNull( playerConfigs, "playerConfigs" ); //$NON-NLS-1$

        final GameConfiguration gameConfig = new GameConfiguration( id, name, system, playerConfigs );
        ConfigurationUtils.assertGameConfigurationLegal( gameConfig );
        return gameConfig;
    }

    /*
     * @see org.gamegineer.game.core.config.IGameConfiguration#getId()
     */
    public String getId()
    {
        return id_;
    }

    /*
     * @see org.gamegineer.game.core.config.IGameConfiguration#getName()
     */
    public String getName()
    {
        return name_;
    }

    /*
     * @see org.gamegineer.game.core.config.IGameConfiguration#getPlayers()
     */
    public List<IPlayerConfiguration> getPlayers()
    {
        return new ArrayList<IPlayerConfiguration>( playerConfigs_ );
    }

    /*
     * @see org.gamegineer.game.core.config.IGameConfiguration#getSystem()
     */
    public IGameSystem getSystem()
    {
        return system_;
    }
}
