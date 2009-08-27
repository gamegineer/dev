/*
 * ConfigurationUtilsTest.java
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
 * Created on Jul 12, 2008 at 9:44:42 PM.
 */

package org.gamegineer.game.internal.core.config;

import java.util.ArrayList;
import java.util.List;
import org.gamegineer.game.core.config.Configurations;
import org.gamegineer.game.core.config.IGameConfiguration;
import org.gamegineer.game.core.config.IPlayerConfiguration;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.config.ConfigurationUtils} class.
 */
public final class ConfigurationUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConfigurationUtilsTest} class.
     */
    public ConfigurationUtilsTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a game configuration.
     * 
     * <p>
     * This methods does no invariant checking, thus allowing you to create an
     * illegal game configuration.
     * </p>
     * 
     * @param id
     *        The game identifier; must not be {@code null}.
     * @param name
     *        The game name; must not be {@code null}.
     * @param gameSystem
     *        The game system; must not be {@code null}.
     * @param playerConfigs
     *        The player configuration list; must not be {@code null}.
     * 
     * @return A new game configuration; never {@code null}.
     */
    /* @NonNull */
    private static IGameConfiguration createGameConfiguration(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final String name,
        /* @NonNull */
        final IGameSystem gameSystem,
        /* @NonNull */
        final List<IPlayerConfiguration> playerConfigs )
    {
        assert id != null;
        assert name != null;
        assert gameSystem != null;
        assert playerConfigs != null;

        return new IGameConfiguration()
        {
            public String getId()
            {
                return id;
            }

            public String getName()
            {
                return name;
            }

            public List<IPlayerConfiguration> getPlayers()
            {
                return new ArrayList<IPlayerConfiguration>( playerConfigs );
            }

            public IGameSystem getSystem()
            {
                return gameSystem;
            }
        };
    }

    /**
     * Creates a player configuration.
     * 
     * <p>
     * This methods does no invariant checking, thus allowing you to create an
     * illegal player configuration.
     * </p>
     * 
     * @param roleId
     *        The player role identifier; must not be {@code null}.
     * @param userId
     *        The player user identifier; must not be {@code null}.
     * 
     * @return A new player configuration; never {@code null}.
     */
    /* @NonNull */
    private static IPlayerConfiguration createPlayerConfiguration(
        /* @NonNull */
        final String roleId,
        /* @NonNull */
        final String userId )
    {
        assert roleId != null;
        assert userId != null;

        return new IPlayerConfiguration()
        {
            public String getRoleId()
            {
                return roleId;
            }

            public String getUserId()
            {
                return userId;
            }
        };
    }

    /**
     * Ensures the {@code assertGameConfigurationLegal} method throws an
     * exception when passed an illegal game configuration due to a player
     * configuration list that contains a duplicate role.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertGameConfigurationLegal_GameConfig_Illegal_PlayerConfigs_DuplicateRole()
    {
        final String id = "id"; //$NON-NLS-1$
        final String name = "name"; //$NON-NLS-1$
        final IGameSystem gameSystem = GameSystems.createUniqueGameSystem();
        final List<IPlayerConfiguration> playerConfigs = Configurations.createPlayerConfigurationList( gameSystem );
        playerConfigs.set( 0, Configurations.createPlayerConfiguration( gameSystem.getRoles().get( 1 ).getId() ) );

        ConfigurationUtils.assertGameConfigurationLegal( createGameConfiguration( id, name, gameSystem, playerConfigs ) );
    }

    /**
     * Ensures the {@code assertGameConfigurationLegal} method throws an
     * exception when passed an illegal game configuration due to a player
     * configuration list that is missing at least one role.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertGameConfigurationLegal_GameConfig_Illegal_PlayerConfigs_MissingRole()
    {
        final String id = "id"; //$NON-NLS-1$
        final String name = "name"; //$NON-NLS-1$
        final IGameSystem gameSystem = GameSystems.createUniqueGameSystem();
        final List<IPlayerConfiguration> playerConfigs = Configurations.createPlayerConfigurationList( gameSystem );
        playerConfigs.remove( 0 );

        ConfigurationUtils.assertGameConfigurationLegal( createGameConfiguration( id, name, gameSystem, playerConfigs ) );
    }

    /**
     * Ensures the {@code assertGameConfigurationLegal} method throws an
     * exception when passed an illegal game configuration due to a player
     * configuration list that contains at least one unknown role.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertGameConfigurationLegal_GameConfig_Illegal_PlayerConfigs_UnknownRole()
    {
        final String id = "id"; //$NON-NLS-1$
        final String name = "name"; //$NON-NLS-1$
        final IGameSystem gameSystem = GameSystems.createUniqueGameSystem();
        final List<IPlayerConfiguration> playerConfigs = Configurations.createPlayerConfigurationList( gameSystem );
        playerConfigs.set( 0, Configurations.createUniquePlayerConfiguration() );

        ConfigurationUtils.assertGameConfigurationLegal( createGameConfiguration( id, name, gameSystem, playerConfigs ) );
    }

    /**
     * Ensures the {@code assertGameConfigurationLegal} method does nothing when
     * passed a legal game configuration.
     */
    @Test
    public void testAssertGameConfigurationLegal_GameConfig_Legal()
    {
        final String id = "id"; //$NON-NLS-1$
        final String name = "name"; //$NON-NLS-1$
        final IGameSystem gameSystem = GameSystems.createUniqueGameSystem();
        final List<IPlayerConfiguration> playerConfigs = Configurations.createPlayerConfigurationList( gameSystem );

        ConfigurationUtils.assertGameConfigurationLegal( createGameConfiguration( id, name, gameSystem, playerConfigs ) );
    }

    /**
     * Ensures the {@code assertGameConfigurationLegal} method throws an
     * exception when passed a {@code null} game configuration.
     */
    @Test( expected = NullPointerException.class )
    public void testAssertGameConfigurationLegal_GameConfig_Null()
    {
        ConfigurationUtils.assertGameConfigurationLegal( null );
    }

    /**
     * Ensures the {@code assertPlayerConfigurationLegal} method does nothing
     * when passed a legal player configuration.
     */
    @Test
    public void testAssertPlayerConfigurationLegal_PlayerConfig_Legal()
    {
        final String roleId = "role-id"; //$NON-NLS-1$
        final String userId = "user-id"; //$NON-NLS-1$

        ConfigurationUtils.assertPlayerConfigurationLegal( createPlayerConfiguration( roleId, userId ) );
    }

    /**
     * Ensures the {@code assertPlayerConfigurationLegal} method throws an
     * exception when passed a {@code null} player configuration.
     */
    @Test( expected = NullPointerException.class )
    public void testAssertPlayerConfigurationLegal_PlayerConfig_Null()
    {
        ConfigurationUtils.assertPlayerConfigurationLegal( null );
    }
}
