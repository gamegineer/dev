/*
 * GameSystemUtilsTest.java
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
 * Created on Nov 15, 2008 at 10:26:36 PM.
 */

package org.gamegineer.game.internal.core.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.gamegineer.game.core.system.FakeStageStrategy;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.gamegineer.game.core.system.IRole;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.core.system.IStageStrategy;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.system.GameSystemUtils} class.
 */
public final class GameSystemUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemUtilsTest} class.
     */
    public GameSystemUtilsTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a game system.
     * 
     * <p>
     * This methods does no invariant checking, thus allowing you to create an
     * illegal game system.
     * </p>
     * 
     * @param id
     *        The game system identifier; must not be {@code null}.
     * @param roles
     *        The role list; must not be {@code null}.
     * @param stages
     *        The stage list; must not be {@code null}.
     * 
     * @return A new game system; never {@code null}.
     */
    /* @NonNull */
    private static IGameSystem createGameSystem(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final List<IRole> roles,
        /* @NonNull */
        final List<IStage> stages )
    {
        assert id != null;
        assert roles != null;
        assert stages != null;

        return new IGameSystem()
        {
            public String getId()
            {
                return id;
            }

            public List<IRole> getRoles()
            {
                return new ArrayList<IRole>( roles );
            }

            public List<IStage> getStages()
            {
                return new ArrayList<IStage>( stages );
            }
        };
    }

    /**
     * Creates a role.
     * 
     * <p>
     * This methods does no invariant checking, thus allowing you to create an
     * illegal role.
     * </p>
     * 
     * @param id
     *        The role identifier; must not be {@code null}.
     * 
     * @return A new role; never {@code null}.
     */
    /* @NonNull */
    private static IRole createRole(
        /* @NonNull */
        final String id )
    {
        assert id != null;

        return new IRole()
        {
            public String getId()
            {
                return id;
            }
        };
    }

    /**
     * Creates a stage.
     * 
     * <p>
     * This methods does no invariant checking, thus allowing you to create an
     * illegal stage.
     * </p>
     * 
     * @param id
     *        The stage identifier; must not be {@code null}.
     * @param strategy
     *        The stage strategy; must not be {@code null}.
     * @param cardinality
     *        The stage cardinality.
     * @param stages
     *        The child stage list; must not be {@code null}.
     * 
     * @return A new stage; never {@code null}.
     */
    /* @NonNull */
    private static IStage createStage(
        /* @NonNull */
        final String id,
        /* @NonNull */
        final IStageStrategy strategy,
        final int cardinality,
        /* @NonNull */
        final List<IStage> stages )
    {
        assert id != null;
        assert strategy != null;
        assert stages != null;

        return new IStage()
        {
            public int getCardinality()
            {
                return cardinality;
            }

            public String getId()
            {
                return id;
            }

            public List<IStage> getStages()
            {
                return stages;
            }

            public IStageStrategy getStrategy()
            {
                return strategy;
            }
        };
    }

    /**
     * Ensures the {@code assertGameSystemLegal} method throws an exception when
     * passed an illegal game system which has an empty role list.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertGameSystemLegal_GameSystem_Illegal_Roles_Empty()
    {
        final String id = "id"; //$NON-NLS-1$
        final List<IRole> roles = Collections.emptyList();
        final List<IStage> stages = GameSystems.createUniqueStageList( 0 );

        GameSystemUtils.assertGameSystemLegal( createGameSystem( id, roles, stages ) );
    }

    /**
     * Ensures the {@code assertGameSystemLegal} method throws an exception when
     * passed an illegal game system which has a role list that contains
     * non-unique role identifiers.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertGameSystemLegal_GameSystem_Illegal_Roles_NonUniqueRoleIds()
    {
        final String id = "id"; //$NON-NLS-1$
        final List<IRole> roles = GameSystems.createNonUniqueRoleList();
        final List<IStage> stages = GameSystems.createUniqueStageList( 0 );

        GameSystemUtils.assertGameSystemLegal( createGameSystem( id, roles, stages ) );
    }

    /**
     * Ensures the {@code assertGameSystemLegal} method throws an exception when
     * passed an illegal game system which has an empty stage list.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertGameSystemLegal_GameSystem_Illegal_Stages_Empty()
    {
        final String id = "id"; //$NON-NLS-1$
        final List<IRole> roles = GameSystems.createUniqueRoleList();
        final List<IStage> stages = Collections.emptyList();

        GameSystemUtils.assertGameSystemLegal( createGameSystem( id, roles, stages ) );
    }

    /**
     * Ensures the {@code assertGameSystemLegal} method throws an exception when
     * passed an illegal game system which has a stage list that contains
     * non-unique stage identifiers.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertGameSystemLegal_GameSystem_Illegal_Stages_NonUniqueStageIds()
    {
        final String id = "id"; //$NON-NLS-1$
        final List<IRole> roles = GameSystems.createUniqueRoleList();
        final List<IStage> stages = GameSystems.createNonUniqueStageList( 0 );

        GameSystemUtils.assertGameSystemLegal( createGameSystem( id, roles, stages ) );
    }

    /**
     * Ensures the {@code assertGameSystemLegal} method does not throw an
     * exception when passed a legal game system.
     */
    @Test
    public void testAssertGameSystemLegal_GameSystem_Legal()
    {
        final String id = "id"; //$NON-NLS-1$
        final List<IRole> roles = GameSystems.createUniqueRoleList();
        final List<IStage> stages = GameSystems.createUniqueStageList( 0 );

        GameSystemUtils.assertGameSystemLegal( createGameSystem( id, roles, stages ) );
    }

    /**
     * Ensures the {@code assertGameSystemLegal} method throws an exception when
     * passed a {@code null} game system.
     */
    @Test( expected = NullPointerException.class )
    public void testAssertGameSystemLegal_GameSystem_Null()
    {
        GameSystemUtils.assertGameSystemLegal( null );
    }

    /**
     * Ensures the {@code assertRoleLegal} method does not throw an exception
     * when passed a legal role.
     */
    @Test
    public void testAssertRoleLegal_Role_Legal()
    {
        final String id = "id"; //$NON-NLS-1$

        GameSystemUtils.assertRoleLegal( createRole( id ) );
    }

    /**
     * Ensures the {@code assertRoleLegal} method throws an exception when
     * passed a {@code null} role.
     */
    @Test( expected = NullPointerException.class )
    public void testAssertRoleLegal_Role_Null()
    {
        GameSystemUtils.assertRoleLegal( null );
    }

    /**
     * Ensures the {@code assertStageLegal} method throws an exception when
     * passed a negative cardinality.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertStageLegal_Stage_Illegal_Cardinality_Negative()
    {
        final String id = "id"; //$NON-NLS-1$
        final IStageStrategy strategy = new FakeStageStrategy();
        final int cardinality = -1;
        final List<IStage> stages = GameSystems.createUniqueStageList( 0 );

        GameSystemUtils.assertStageLegal( createStage( id, strategy, cardinality, stages ) );
    }

    /**
     * Ensures the {@code assertStageLegal} method throws an exception when the
     * stage list contains at least one stage that has a duplicate stage
     * identifier.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertStageLegal_Stage_Illegal_Stages_ContainsDuplicateIdentifiers()
    {
        final String id = "id"; //$NON-NLS-1$
        final IStageStrategy strategy = new FakeStageStrategy();
        final int cardinality = 0;
        final List<IStage> stages = GameSystems.createNonUniqueStageList( 2 );

        GameSystemUtils.assertStageLegal( createStage( id, strategy, cardinality, stages ) );
    }

    /**
     * Ensures the {@code assertStageLegal} method throws an exception when the
     * stage list contains a stage that has the same stage identifier as the
     * parent stage.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testAssertStageLegal_Stage_Illegal_Stages_ContainsParentIdentifiers()
    {
        final IStageStrategy strategy = new FakeStageStrategy();
        final int cardinality = 0;
        final List<IStage> stages = GameSystems.createUniqueStageList( 2 );
        final String id = stages.get( 0 ).getStages().get( 0 ).getStages().get( 0 ).getId();

        GameSystemUtils.assertStageLegal( createStage( id, strategy, cardinality, stages ) );
    }

    /**
     * Ensures the {@code assertStageLegal} method does not throw an exception
     * when passed a legal stage.
     */
    @Test
    public void testAssertStageLegal_Stage_Legal()
    {
        final String id = "id"; //$NON-NLS-1$
        final IStageStrategy strategy = new FakeStageStrategy();
        final int cardinality = 0;
        final List<IStage> stages = GameSystems.createUniqueStageList( 0 );

        GameSystemUtils.assertStageLegal( createStage( id, strategy, cardinality, stages ) );
    }

    /**
     * Ensures the {@code assertStageLegal} method throws an exception when
     * passed a {@code null} stage.
     */
    @Test( expected = NullPointerException.class )
    public void testAssertStageLegal_Stage_Null()
    {
        GameSystemUtils.assertStageLegal( null );
    }
}
