/*
 * GameSystems.java
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
 * Created on Nov 15, 2008 at 9:43:57 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentLegal;
import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.game.internal.core.Services;

/**
 * A factory for creating various types of game system types suitable for
 * testing.
 * 
 * <p>
 * This class is thread-safe
 * </p>
 */
@ThreadSafe
public final class GameSystems
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The next unique game system identifier. */
    private static final AtomicLong c_nextGameSystemId = new AtomicLong();

    /** The next unique role identifier. */
    private static final AtomicLong c_nextRoleId = new AtomicLong();

    /** The next unique stage identifier. */
    private static final AtomicLong c_nextStageId = new AtomicLong();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystems} class.
     */
    private GameSystems()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a new game system with the specified game system identifier.
     * 
     * <p>
     * The returned game system contains legal values for all fields.
     * </p>
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * 
     * @return A new game system; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static IGameSystem createGameSystem(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return createGameSystemBuilder( id ).toGameSystem();
    }

    /**
     * Creates a new game system builder with the specified game system
     * identifier.
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * 
     * @return A new game system builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static GameSystemBuilder createGameSystemBuilder(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        final GameSystemBuilder builder = createIncompleteGameSystemBuilder( GameSystemAttribute.ID );
        builder.setId( id );
        return builder;
    }

    /**
     * Creates a new illegal game system.
     * 
     * <p>
     * The returned game system has an illegal value in one or more fields.
     * </p>
     * 
     * @return A new illegal game system; never {@code null}.
     */
    /* @NonNull */
    public static IGameSystem createIllegalGameSystem()
    {
        // NB: Our choice of illegal game system is simply to not allocate any roles.
        final IGameSystem gameSystem = createUniqueGameSystem();
        return new IGameSystem()
        {
            public String getId()
            {
                return gameSystem.getId();
            }

            public List<IRole> getRoles()
            {
                return new ArrayList<IRole>();
            }

            public List<IStage> getStages()
            {
                return gameSystem.getStages();
            }
        };
    }

    /**
     * Creates a new illegal stage.
     * 
     * <p>
     * The returned stage has an illegal value in one or more fields.
     * </p>
     * 
     * @return A new illegal stage; never {@code null}.
     */
    /* @NonNull */
    public static IStage createIllegalStage()
    {
        // NB: Our choice of illegal stage is to return an illegal cardinality.
        final IStage stage = createUniqueStage();
        return new IStage()
        {
            public int getCardinality()
            {
                return -1;
            }

            public String getId()
            {
                return stage.getId();
            }

            public List<IStage> getStages()
            {
                return stage.getStages();
            }

            public IStageStrategy getStrategy()
            {
                return stage.getStrategy();
            }
        };
    }

    /**
     * Creates an incomplete game system builder that does not have a value set
     * for the specified attribute.
     * 
     * @param missingAttribute
     *        The attribute for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A game system builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttribute} is {@code null}.
     */
    /* @NonNull */
    public static GameSystemBuilder createIncompleteGameSystemBuilder(
        /* @NonNull */
        final GameSystemAttribute missingAttribute )
    {
        assertArgumentNotNull( missingAttribute, "missingAttribute" ); //$NON-NLS-1$

        return createIncompleteGameSystemBuilder( EnumSet.of( missingAttribute ) );
    }

    /**
     * Creates an incomplete game system builder that does not have a value set
     * for the specified attributes.
     * 
     * <p>
     * Note that calling this method with an empty set of attributes will result
     * in a complete game system builder.
     * </p>
     * 
     * @param missingAttributes
     *        The set of attributes for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A game system builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttributes} is {@code null}.
     */
    /* @NonNull */
    public static GameSystemBuilder createIncompleteGameSystemBuilder(
        /* @NonNull */
        final EnumSet<GameSystemAttribute> missingAttributes )
    {
        assertArgumentNotNull( missingAttributes, "missingAttributes" ); //$NON-NLS-1$

        final GameSystemBuilder builder = new GameSystemBuilder();

        if( !missingAttributes.contains( GameSystemAttribute.ID ) )
        {
            builder.setId( getUniqueGameSystemIdentifier() );
        }
        if( !missingAttributes.contains( GameSystemAttribute.ROLES ) )
        {
            builder.addRoles( createUniqueRoleList() );
        }
        if( !missingAttributes.contains( GameSystemAttribute.STAGES ) )
        {
            builder.addStages( createUniqueStageList( 2 ) );
        }

        return builder;
    }

    /**
     * Creates an incomplete role builder that does not have a value set for the
     * specified attribute.
     * 
     * @param missingAttribute
     *        The attribute for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A role builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttribute} is {@code null}.
     */
    /* @NonNull */
    public static RoleBuilder createIncompleteRoleBuilder(
        /* @NonNull */
        final RoleAttribute missingAttribute )
    {
        assertArgumentNotNull( missingAttribute, "missingAttribute" ); //$NON-NLS-1$

        return createIncompleteRoleBuilder( EnumSet.of( missingAttribute ) );
    }

    /**
     * Creates an incomplete role builder that does not have a value set for the
     * specified attributes.
     * 
     * <p>
     * Note that calling this method with an empty set of attributes will result
     * in a complete role builder.
     * </p>
     * 
     * @param missingAttributes
     *        The set of attributes for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A role builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttributes} is {@code null}.
     */
    /* @NonNull */
    public static RoleBuilder createIncompleteRoleBuilder(
        /* @NonNull */
        final EnumSet<RoleAttribute> missingAttributes )
    {
        assertArgumentNotNull( missingAttributes, "missingAttributes" ); //$NON-NLS-1$

        final RoleBuilder builder = new RoleBuilder();

        if( !missingAttributes.contains( RoleAttribute.ID ) )
        {
            builder.setId( getUniqueRoleIdentifier() );
        }

        return builder;
    }

    /**
     * Creates an incomplete stage builder that does not have a value set for
     * the specified attribute.
     * 
     * @param missingAttribute
     *        The attribute for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A stage builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttribute} is {@code null}.
     */
    /* @NonNull */
    public static StageBuilder createIncompleteStageBuilder(
        /* @NonNull */
        final StageAttribute missingAttribute )
    {
        assertArgumentNotNull( missingAttribute, "missingAttribute" ); //$NON-NLS-1$

        return createIncompleteStageBuilder( EnumSet.of( missingAttribute ) );
    }

    /**
     * Creates an incomplete stage builder that does not have a value set for
     * the specified attributes.
     * 
     * <p>
     * Note that calling this method with an empty set of attributes will result
     * in a complete stage builder.
     * </p>
     * 
     * @param missingAttributes
     *        The set of attributes for which a value is not set in the returned
     *        builder; must not be {@code null}.
     * 
     * @return A stage builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code missingAttributes} is {@code null}.
     */
    /* @NonNull */
    public static StageBuilder createIncompleteStageBuilder(
        /* @NonNull */
        final EnumSet<StageAttribute> missingAttributes )
    {
        assertArgumentNotNull( missingAttributes, "missingAttributes" ); //$NON-NLS-1$

        final StageBuilder builder = new StageBuilder();

        if( !missingAttributes.contains( StageAttribute.CARDINALITY ) )
        {
            builder.setCardinality( 0 );
        }
        if( !missingAttributes.contains( StageAttribute.ID ) )
        {
            builder.setId( getUniqueStageIdentifier() );
        }
        if( !missingAttributes.contains( StageAttribute.STAGES ) )
        {
            // Do not bother adding child stages.  If you decide to change
            // this in the future, ensure you do not call a method of this
            // class that ends up recursively calling this method
            // (e.g. createUniqueStageList).
        }
        if( !missingAttributes.contains( StageAttribute.STRATEGY ) )
        {
            builder.setStrategy( new FakeStageStrategy() );
        }

        return builder;
    }

    /**
     * Creates a role list in which at least one role identifier is not unique.
     * 
     * @return A role list; never {@code null}.
     */
    /* @NonNull */
    public static List<IRole> createNonUniqueRoleList()
    {
        final List<IRole> roles = createUniqueRoleList();
        roles.add( createRole( roles.get( 0 ).getId() ) );
        return roles;
    }

    /**
     * Creates a stage list in which at least one stage identifier is not
     * unique.
     * 
     * <p>
     * The stage with the non-unique identifier will be located at the deepest
     * generation in the list.
     * </p>
     * 
     * @param generations
     *        The number of generations to create for each stage in the list;
     *        must be non-negative. For example, {@code 0} will create no child
     *        stages; {@code 1} will create child stages; {@code 2} will create
     *        child and grandchild stages; and so on.
     * 
     * @return A stage list; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code generations} is negative.
     */
    /* @NonNull */
    public static List<IStage> createNonUniqueStageList(
        final int generations )
    {
        assertArgumentLegal( generations >= 0, "generations" ); //$NON-NLS-1$

        final List<IStage> stages = createUniqueStageList( generations );

        int generation = generations;
        StageBuilder builder = createStageBuilder( stages.get( 0 ).getId() );
        for( ; generation >= 0; --generation )
        {
            final IStage stage = builder.toStage();
            builder = createUniqueStageBuilder();
            builder.addStage( stage );
        }

        stages.add( builder.toStage() );
        return stages;
    }

    /**
     * Creates a new role with the specified role identifier.
     * 
     * @param id
     *        The role identifier; must not be {@code null}.
     * 
     * @return A new role; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static IRole createRole(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return createRoleBuilder( id ).toRole();
    }

    /**
     * Creates a new role builder with the specified role identifier.
     * 
     * @param id
     *        The role identifier; must not be {@code null}.
     * 
     * @return A new role builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static RoleBuilder createRoleBuilder(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        final RoleBuilder builder = createIncompleteRoleBuilder( RoleAttribute.ID );
        builder.setId( id );
        return builder;
    }

    /**
     * Creates a new stage with the specified stage identifier.
     * 
     * @param id
     *        The stage identifier; must not be {@code null}.
     * 
     * @return A new stage; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static IStage createStage(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return createStageBuilder( id ).toStage();
    }

    /**
     * Creates a new stage builder with the specified stage identifier.
     * 
     * @param id
     *        The stage identifier; must not be {@code null}.
     * 
     * @return A new stage builder; never {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @NonNull */
    public static StageBuilder createStageBuilder(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        final StageBuilder builder = createIncompleteStageBuilder( StageAttribute.ID );
        builder.setId( id );
        return builder;
    }

    /**
     * Creates a new game system with a unique game system identifier.
     * 
     * @return A new game system; never {@code null}.
     */
    /* @NonNull */
    public static IGameSystem createUniqueGameSystem()
    {
        return createGameSystem( getUniqueGameSystemIdentifier() );
    }

    /**
     * Creates a new game system builder with a unique game system identifier.
     * 
     * @return A new game system builder; never {@code null}.
     */
    /* @NonNull */
    public static GameSystemBuilder createUniqueGameSystemBuilder()
    {
        return createGameSystemBuilder( getUniqueGameSystemIdentifier() );
    }

    /**
     * Creates a new role with a unique role identifier.
     * 
     * @return A new role; never {@code null}.
     */
    /* @NonNull */
    public static IRole createUniqueRole()
    {
        return createRole( getUniqueRoleIdentifier() );
    }

    /**
     * Creates a new role builder with a unique role identifier.
     * 
     * @return A new role builder; never {@code null}.
     */
    /* @NonNull */
    public static RoleBuilder createUniqueRoleBuilder()
    {
        return createRoleBuilder( getUniqueRoleIdentifier() );
    }

    /**
     * Creates a role list in which all role identifiers are unique.
     * 
     * @return A role list; never {@code null}.
     */
    /* @NonNull */
    public static List<IRole> createUniqueRoleList()
    {
        final List<IRole> roles = new ArrayList<IRole>();
        roles.add( createUniqueRole() );
        roles.add( createUniqueRole() );
        roles.add( createUniqueRole() );
        return roles;
    }

    /**
     * Creates a new stage with a unique stage identifier.
     * 
     * @return A new stage; never {@code null}.
     */
    /* @NonNull */
    public static IStage createUniqueStage()
    {
        return createStage( getUniqueStageIdentifier() );
    }

    /**
     * Creates a new stage builder with a unique stage identifier.
     * 
     * @return A new stage builder; never {@code null}.
     */
    /* @NonNull */
    public static StageBuilder createUniqueStageBuilder()
    {
        return createStageBuilder( getUniqueStageIdentifier() );
    }

    /**
     * Creates a stage list in which all stage identifiers are unique.
     * 
     * @param generations
     *        The number of generations to create for each stage in the list;
     *        must be non-negative. For example, {@code 0} will create no child
     *        stages; {@code 1} will create child stages; {@code 2} will create
     *        child and grandchild stages; and so on.
     * 
     * @return A stage list; never {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code generations} is negative.
     */
    /* @NonNull */
    public static List<IStage> createUniqueStageList(
        final int generations )
    {
        assertArgumentLegal( generations >= 0, "generations" ); //$NON-NLS-1$

        final int ELEMENT_COUNT = 3;
        final List<IStage> stages = new ArrayList<IStage>();
        for( int index = 0; index < ELEMENT_COUNT; ++index )
        {
            final StageBuilder builder = createUniqueStageBuilder();
            if( generations > 0 )
            {
                for( final IStage stage : createUniqueStageList( generations - 1 ) )
                {
                    builder.addStage( stage );
                }
            }
            stages.add( builder.toStage() );
        }
        return stages;
    }

    /**
     * Gets the game system that has been registered with the specified
     * identifier.
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * 
     * @return The game system that has been registered with the specified
     *         identifier or {@code null} if no such identifier exists.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code id} is {@code null}.
     */
    /* @Nullable */
    public static IGameSystem getGameSystem(
        /* @NonNull */
        final String id )
    {
        assertArgumentNotNull( id, "id" ); //$NON-NLS-1$

        return Services.getDefault().getGameSystemRegistry().getGameSystem( id );
    }

    /**
     * Gets a unique game system identifier.
     * 
     * @return A unique game system identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static String getUniqueGameSystemIdentifier()
    {
        return String.format( "game-system-%1$d", c_nextGameSystemId.incrementAndGet() ); //$NON-NLS-1$
    }

    /**
     * Gets a unique role identifier.
     * 
     * @return A unique role identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static String getUniqueRoleIdentifier()
    {
        return String.format( "role-%1$d", c_nextRoleId.incrementAndGet() ); //$NON-NLS-1$
    }

    /**
     * Gets a unique stage identifier.
     * 
     * @return A unique stage identifier; never {@code null}.
     */
    /* @NonNull */
    @SuppressWarnings( "boxing" )
    private static String getUniqueStageIdentifier()
    {
        return String.format( "stage-%1$d", c_nextStageId.incrementAndGet() ); //$NON-NLS-1$
    }


    // ======================================================================
    // Nested Types
    // ======================================================================

    /**
     * Identifies the attributes of a game system.
     */
    public enum GameSystemAttribute
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The game system identifier attribute. */
        ID,

        /** The game system roles attribute. */
        ROLES,

        /** The game system stages attribute. */
        STAGES,
    }

    /**
     * Identifies the attributes of a role.
     */
    public enum RoleAttribute
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The role identifier attribute. */
        ID,
    }

    /**
     * Identifies the attributes of a stage.
     */
    public enum StageAttribute
    {
        // ==================================================================
        // Enum Constants
        // ==================================================================

        /** The stage cardinality attribute. */
        CARDINALITY,

        /** The stage identifier attribute. */
        ID,

        /** The stage child stages attribute. */
        STAGES,

        /** The stage strategy attribute. */
        STRATEGY,
    }
}
