/*
 * Configurations.java
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
 * Created on Jul 12, 2008 at 9:21:30 PM.
 */

package org.gamegineer.game.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.game.core.system.AbstractStageStrategy;
import org.gamegineer.game.core.system.GameSystemBuilder;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.core.system.IRole;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.core.system.IStageStrategy;
import org.gamegineer.game.core.system.StageBuilder;

/**
 * A factory for creating various types of game configuration objects suitable
 * for testing.
 * 
 * <p>
 * This class is thread-safe
 * </p>
 */
@ThreadSafe
public final class Configurations
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The next unique game identifier. */
    private static final AtomicLong nextGameId_ = new AtomicLong();

    /** The next unique user identifier. */
    private static final AtomicLong nextUserId_ = new AtomicLong();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Configurations} class.
     */
    private Configurations()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new game configuration with the specified game identifier.
     * 
     * @param id
     *        The game identifier; must not be {@code null}.
     * 
     * @return A new game configuration; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static IGameConfiguration createGameConfiguration(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return createGameConfigurationBuilder( id ).toGameConfiguration();
    }

    /**
     * Creates a new game configuration builder with the specified game
     * identifier.
     * 
     * @param id
     *        The game identifier; must not be {@code null}.
     * 
     * @return A new game configuration builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static GameConfigurationBuilder createGameConfigurationBuilder(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        final GameConfigurationBuilder builder = createIncompleteGameConfigurationBuilder( GameConfigurationAttribute.ID );
        builder.setId( id );
        return builder;
    }

    /**
     * Creates a new illegal game configuration.
     * 
     * <p>
     * The returned game configuration has an illegal value in one or more
     * fields.
     * </p>
     * 
     * @return A new illegal game configuration; never {@code null}.
     */
    /* @NonNull */
    public static IGameConfiguration createIllegalGameConfiguration()
    {
        // NB: Our choice of illegal game configuration is simply to not
        // allocate a player for each defined game role.
        final IGameConfiguration gameConfig = createUniqueGameConfiguration();
        return new IGameConfiguration()
        {
            public String getId()
            {
                return gameConfig.getId();
            }

            public String getName()
            {
                return gameConfig.getName();
            }

            public List<IPlayerConfiguration> getPlayers()
            {
                return new ArrayList<IPlayerConfiguration>();
            }

            public IGameSystem getSystem()
            {
                return gameConfig.getSystem();
            }
        };
    }

    /**
     * Creates an incomplete game configuration builder that does not have a
     * value set for the specified attribute.
     * 
     * @param missingAttribute
     *        The attribute for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A game configuration builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttribute} is {@code null}.
     */
    /* @NonNull */
    public static GameConfigurationBuilder createIncompleteGameConfigurationBuilder(
        /* @NonNull */
        final GameConfigurationAttribute missingAttribute )
    {
        assertArgumentNotNull( missingAttribute, "missingAttribute" ); //$NON-NLS-1$

        return createIncompleteGameConfigurationBuilder( EnumSet.of( missingAttribute ) );
    }

    /**
     * Creates an incomplete game configuration builder that does not have a
     * value set for the specified attributes.
     * 
     * <p>
     * Note that calling this method with an empty set of attributes will result
     * in a complete game configuration builder.
     * </p>
     * 
     * @param missingAttributes
     *        The set of attributes for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A game configuration builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttributes} is {@code null}.
     */
    /* @NonNull */
    public static GameConfigurationBuilder createIncompleteGameConfigurationBuilder(
        /* @NonNull */
        final EnumSet<GameConfigurationAttribute> missingAttributes )
    {
        assertArgumentNotNull( missingAttributes, "missingAttributes" ); //$NON-NLS-1$

        final GameConfigurationBuilder builder = new GameConfigurationBuilder();
        final IGameSystem gameSystem = GameSystems.createUniqueGameSystem();

        if( !missingAttributes.contains( GameConfigurationAttribute.ID ) )
        {
            builder.setId( "id" ); //$NON-NLS-1$
        }
        if( !missingAttributes.contains( GameConfigurationAttribute.NAME ) )
        {
            builder.setName( "name" ); //$NON-NLS-1$
        }
        if( !missingAttributes.contains( GameConfigurationAttribute.PLAYERS ) )
        {
            builder.addPlayers( Configurations.createPlayerConfigurationList( gameSystem ) );
        }
        if( !missingAttributes.contains( GameConfigurationAttribute.SYSTEM ) )
        {
            builder.setSystem( gameSystem );
        }

        return builder;
    }

    /**
     * Creates an incomplete player configuration builder that does not have a
     * value set for the specified attribute.
     * 
     * @param missingAttribute
     *        The attribute for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A player configuration builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttribute} is {@code null}.
     */
    /* @NonNull */
    public static PlayerConfigurationBuilder createIncompletePlayerConfigurationBuilder(
        /* @NonNull */
        final PlayerConfigurationAttribute missingAttribute )
    {
        assertArgumentNotNull( missingAttribute, "missingAttribute" ); //$NON-NLS-1$

        return createIncompletePlayerConfigurationBuilder( EnumSet.of( missingAttribute ) );
    }

    /**
     * Creates an incomplete player configuration builder that does not have a
     * value set for the specified attributes.
     * 
     * <p>
     * Note that calling this method with an empty set of attributes will result
     * in a complete player configuration builder.
     * </p>
     * 
     * @param missingAttributes
     *        The set of attributes for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A player configuration builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttributes} is {@code null}.
     */
    /* @NonNull */
    public static PlayerConfigurationBuilder createIncompletePlayerConfigurationBuilder(
        /* @NonNull */
        final EnumSet<PlayerConfigurationAttribute> missingAttributes )
    {
        assertArgumentNotNull( missingAttributes, "missingAttributes" ); //$NON-NLS-1$

        final PlayerConfigurationBuilder builder = new PlayerConfigurationBuilder();

        if( !missingAttributes.contains( PlayerConfigurationAttribute.ROLE_ID ) )
        {
            builder.setRoleId( "role-id" ); //$NON-NLS-1$
        }
        if( !missingAttributes.contains( PlayerConfigurationAttribute.USER_ID ) )
        {
            builder.setUserId( getUniqueUserIdentifier() );
        }

        return builder;
    }

    /**
     * Creates a new minimal game configuration.
     * 
     * <p>
     * The minimal game configuration consists of a single stage that never
     * completes.
     * </p>
     * 
     * @return A new game configuration; never {@code null}.
     */
    /* @NonNull */
    public static IGameConfiguration createMinimalGameConfiguration()
    {
        final GameSystemBuilder gameSystemBuilder = new GameSystemBuilder();
        gameSystemBuilder.setId( "game-system-id" ); //$NON-NLS-1$

        for( final IRole role : GameSystems.createUniqueRoleList() )
        {
            gameSystemBuilder.addRole( role );
        }

        final StageBuilder stageBuilder = GameSystems.createUniqueStageBuilder();
        stageBuilder.setStrategy( createStageStrategy( false ) );
        gameSystemBuilder.addStage( stageBuilder.toStage() );

        final IGameSystem gameSystem = gameSystemBuilder.toGameSystem();
        final GameConfigurationBuilder gameConfigBuilder = new GameConfigurationBuilder();
        gameConfigBuilder.setId( "game-id" ).setName( "game-name" ).setSystem( gameSystem ); //$NON-NLS-1$ //$NON-NLS-2$
        gameConfigBuilder.addPlayers( Configurations.createPlayerConfigurationList( gameSystem ) );
        return gameConfigBuilder.toGameConfiguration();
    }

    /**
     * Creates a player configuration list in which at least one role identifier
     * is not unique.
     * 
     * @return A player configuration list; never {@code null}.
     */
    /* @NonNull */
    public static List<IPlayerConfiguration> createNonUniquePlayerConfigurationList()
    {
        final List<IPlayerConfiguration> playerConfigs = createUniquePlayerConfigurationList();
        playerConfigs.add( createPlayerConfiguration( playerConfigs.get( 0 ).getRoleId() ) );
        return playerConfigs;
    }

    /**
     * Creates a new player configuration with the specified role identifier.
     * 
     * @param roleId
     *        The role identifier; must not be {@code null}.
     * 
     * @return A new player configuration; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code roleId} is {@code null}.
     */
    /* @NonNull */
    public static IPlayerConfiguration createPlayerConfiguration(
        /* @NonNull */
        final String roleId )
    {
        assertArgumentNotNull( roleId, "roleId" ); //$NON-NLS-1$

        return createPlayerConfigurationBuilder( roleId ).toPlayerConfiguration();
    }

    /**
     * Creates a new player configuration builder with the specified role
     * identifier.
     * 
     * @param roleId
     *        The role identifier; must not be {@code null}.
     * 
     * @return A new player configuration builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code roleId} is {@code null}.
     */
    /* @NonNull */
    public static PlayerConfigurationBuilder createPlayerConfigurationBuilder(
        /* @NonNull */
        final String roleId )
    {
        assertArgumentNotNull( roleId, "roleId" ); //$NON-NLS-1$

        final PlayerConfigurationBuilder builder = createIncompletePlayerConfigurationBuilder( PlayerConfigurationAttribute.ROLE_ID );
        builder.setRoleId( roleId );
        return builder;
    }

    /**
     * Creates a player configuration list appropriate for the specified game
     * system.
     * 
     * @param gameSystem
     *        The game system; must not be {@code null}.
     * 
     * @return A player configuration list; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code gameSystem} is {@code null}.
     */
    /* @NonNull */
    public static List<IPlayerConfiguration> createPlayerConfigurationList(
        /* @NonNull */
        final IGameSystem gameSystem )
    {
        assertArgumentNotNull( gameSystem, "gameSystem" ); //$NON-NLS-1$

        final List<IRole> roles = gameSystem.getRoles();
        final List<IPlayerConfiguration> playerConfigs = new ArrayList<IPlayerConfiguration>( roles.size() );
        for( final IRole role : roles )
        {
            playerConfigs.add( createPlayerConfiguration( role.getId() ) );
        }
        return playerConfigs;
    }

    /**
     * Creates a new stage strategy that indicates the stage is either always
     * complete or always incomplete.
     * 
     * @param isComplete
     *        {@code true} if the strategy should report the stage is always
     *        complete; {@code false} if the strategy should report the stage is
     *        always incomplete.
     * 
     * @return A new stage strategy; never {@code null}.
     */
    /* @NonNull */
    private static IStageStrategy createStageStrategy(
        final boolean isComplete )
    {
        return new AbstractStageStrategy()
        {
            @Override
            public boolean isComplete(
                final IStage stage,
                final IEngineContext context )
            {
                assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$
                assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

                return isComplete;
            }
        };
    }

    /**
     * Creates a new game configuration with a unique game identifier.
     * 
     * @return A new game configuration; never {@code null}.
     */
    /* @NonNull */
    public static IGameConfiguration createUniqueGameConfiguration()
    {
        return createGameConfiguration( getUniqueGameIdentifier() );
    }

    /**
     * Creates a new game configuration builder with a unique game identifier.
     * 
     * @return A new game configuration builder; never {@code null}.
     */
    /* @NonNull */
    public static GameConfigurationBuilder createUniqueGameConfigurationBuilder()
    {
        return createGameConfigurationBuilder( getUniqueGameIdentifier() );
    }

    /**
     * Creates a new player configuration with a unique role identifier.
     * 
     * @return A new player configuration; never {@code null}.
     */
    /* @NonNull */
    public static IPlayerConfiguration createUniquePlayerConfiguration()
    {
        return createPlayerConfiguration( GameSystems.createUniqueRole().getId() );
    }

    /**
     * Creates a new player configuration builder with a unique role identifier.
     * 
     * @return A new player configuration builder; never {@code null}.
     */
    /* @NonNull */
    public static PlayerConfigurationBuilder createUniquePlayerConfigurationBuilder()
    {
        return createPlayerConfigurationBuilder( GameSystems.createUniqueRole().getId() );
    }

    /**
     * Creates a player configuration list in which all role identifiers are
     * unique.
     * 
     * @return A player configuration list; never {@code null}.
     */
    /* @NonNull */
    public static List<IPlayerConfiguration> createUniquePlayerConfigurationList()
    {
        final List<IPlayerConfiguration> playerConfigs = new ArrayList<IPlayerConfiguration>();
        playerConfigs.add( createUniquePlayerConfiguration() );
        playerConfigs.add( createUniquePlayerConfiguration() );
        playerConfigs.add( createUniquePlayerConfiguration() );
        return playerConfigs;
    }

    /**
     * Gets a unique game identifier.
     * 
     * @return A unique game identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static String getUniqueGameIdentifier()
    {
        return String.format( "game-%1$d", nextGameId_.incrementAndGet() ); //$NON-NLS-1$
    }

    /**
     * Gets a unique user identifier.
     * 
     * @return A unique user identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static String getUniqueUserIdentifier()
    {
        return String.format( "user-%1$d", nextUserId_.incrementAndGet() ); //$NON-NLS-1$
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Identifies the attributes of a game configuration.
     */
    public enum GameConfigurationAttribute
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The game identifier attribute. */
        ID,

        /** The game name attribute. */
        NAME,

        /** The player configuration list attribute. */
        PLAYERS,

        /** The game system attribute. */
        SYSTEM,
    }

    /**
     * Identifies the attributes of a player configuration.
     */
    public enum PlayerConfigurationAttribute
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The player role identifier attribute. */
        ROLE_ID,

        /** The player user identifier attribute. */
        USER_ID,
    }
}
