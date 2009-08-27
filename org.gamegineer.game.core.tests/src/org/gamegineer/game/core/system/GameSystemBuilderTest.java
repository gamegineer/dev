/*
 * GameSystemBuilderTest.java
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
 * Created on Nov 15, 2008 at 11:23:43 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.game.core.system.Assert.assertRoleEquals;
import static org.gamegineer.game.core.system.Assert.assertStageEquals;
import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.core.system.GameSystemBuilder} class.
 */
public final class GameSystemBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system builder under test in the fixture. */
    private GameSystemBuilder m_builder;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemBuilderTest} class.
     */
    public GameSystemBuilderTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a legal role list for the specified game system builder.
     * 
     * <p>
     * It is assumed the specified game system builder is complete except for
     * the role list attribute.
     * </p>
     * 
     * @param builder
     *        The game system builder; must not be {@code null}.
     * 
     * @return A legal role list for the specified game system builder; never
     *         {@code null}.
     */
    /* @NonNull */
    private static List<IRole> createLegalRoleList(
        /* @NonNull */
        final GameSystemBuilder builder )
    {
        assert builder != null;

        return GameSystems.createUniqueRoleList();
    }

    /**
     * Creates a legal stage list for the specified game system builder.
     * 
     * <p>
     * It is assumed the specified game system builder is complete except for
     * the stage list attribute.
     * </p>
     * 
     * @param builder
     *        The game system builder; must not be {@code null}.
     * 
     * @return A legal stage list for the specified game system builder; never
     *         {@code null}.
     */
    /* @NonNull */
    private static List<IStage> createLegalStageList(
        /* @NonNull */
        final GameSystemBuilder builder )
    {
        assert builder != null;

        return GameSystems.createUniqueStageList( 0 );
    }

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        m_builder = new GameSystemBuilder();
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        m_builder = null;
    }

    /**
     * Ensures the {@code addRole} method adds a role to the resulting game
     * system.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddRole_AddsRole()
    {
        final GameSystemBuilder builder = GameSystems.createIncompleteGameSystemBuilder( GameSystems.GameSystemAttribute.ROLES );
        final List<IRole> expectedRoles = createLegalRoleList( builder );

        for( final IRole role : expectedRoles )
        {
            builder.addRole( role );
        }

        final IGameSystem gameSystem = builder.toGameSystem();
        final List<IRole> actualRoles = gameSystem.getRoles();
        assertEquals( expectedRoles.size(), actualRoles.size() );
        for( int index = 0; index < expectedRoles.size(); ++index )
        {
            assertRoleEquals( expectedRoles.get( index ), actualRoles.get( index ) );
        }
    }

    /**
     * Ensures the {@code addRole} method throws an exception when passed a
     * {@code null} role.
     */
    @Test( expected = NullPointerException.class )
    public void testAddRole_Role_Null()
    {
        m_builder.addRole( null );
    }

    /**
     * Ensures the {@code addRole} method returns the same builder instance.
     */
    @Test
    public void testAddRole_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.addRole( createDummy( IRole.class ) ) );
    }

    /**
     * Ensures the {@code addRoles} method adds a collection of roles to the
     * resulting game system.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddRoles_AddsRoles()
    {
        final GameSystemBuilder builder = GameSystems.createIncompleteGameSystemBuilder( GameSystems.GameSystemAttribute.ROLES );
        final List<IRole> expectedRoles = createLegalRoleList( builder );

        builder.addRoles( expectedRoles );

        final IGameSystem gameSystem = builder.toGameSystem();
        final List<IRole> actualRoles = gameSystem.getRoles();
        assertEquals( expectedRoles.size(), actualRoles.size() );
        for( int index = 0; index < expectedRoles.size(); ++index )
        {
            assertRoleEquals( expectedRoles.get( index ), actualRoles.get( index ) );
        }
    }

    /**
     * Ensures the {@code addRoles} method throws an exception when passed a
     * {@code null} role list.
     */
    @Test( expected = NullPointerException.class )
    public void testAddRoles_Roles_Null()
    {
        m_builder.addRoles( null );
    }

    /**
     * Ensures the {@code addRoles} method returns the same builder instance.
     */
    @Test
    public void testAddRoles_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.addRoles( Collections.<IRole>emptyList() ) );
    }

    /**
     * Ensures the {@code addStage} method adds a stage to the resulting game
     * system.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddStage_AddsStage()
    {
        final GameSystemBuilder builder = GameSystems.createIncompleteGameSystemBuilder( GameSystems.GameSystemAttribute.STAGES );
        final List<IStage> expectedStages = createLegalStageList( builder );

        for( final IStage stage : expectedStages )
        {
            builder.addStage( stage );
        }

        final IGameSystem gameSystem = builder.toGameSystem();
        final List<IStage> actualStages = gameSystem.getStages();
        assertEquals( expectedStages.size(), actualStages.size() );
        for( int index = 0; index < expectedStages.size(); ++index )
        {
            assertStageEquals( expectedStages.get( index ), actualStages.get( index ) );
        }
    }

    /**
     * Ensures the {@code addStage} method throws an exception when passed a
     * {@code null} stage.
     */
    @Test( expected = NullPointerException.class )
    public void testAddStage_Stage_Null()
    {
        m_builder.addStage( null );
    }

    /**
     * Ensures the {@code addStage} method returns the same builder instance.
     */
    @Test
    public void testAddStage_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.addStage( createDummy( IStage.class ) ) );
    }

    /**
     * Ensures the {@code addStages} method adds a collection of stages to the
     * resulting game system.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddStages_AddsStages()
    {
        final GameSystemBuilder builder = GameSystems.createIncompleteGameSystemBuilder( GameSystems.GameSystemAttribute.STAGES );
        final List<IStage> expectedStages = createLegalStageList( builder );

        builder.addStages( expectedStages );

        final IGameSystem gameSystem = builder.toGameSystem();
        final List<IStage> actualStages = gameSystem.getStages();
        assertEquals( expectedStages.size(), actualStages.size() );
        for( int index = 0; index < expectedStages.size(); ++index )
        {
            assertStageEquals( expectedStages.get( index ), actualStages.get( index ) );
        }
    }

    /**
     * Ensures the {@code addStages} method throws an exception when passed a
     * {@code null} stage list.
     */
    @Test( expected = NullPointerException.class )
    public void testAddStages_Stages_Null()
    {
        m_builder.addStages( null );
    }

    /**
     * Ensures the {@code addStages} method returns the same builder instance.
     */
    @Test
    public void testAddStages_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.addStages( Collections.<IStage>emptyList() ) );
    }

    /**
     * Ensures the {@code setId} method throws an exception when passed a
     * {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testSetId_Id_Null()
    {
        m_builder.setId( null );
    }

    /**
     * Ensures the {@code setId} method returns the same builder instance.
     */
    @Test
    public void testSetId_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.setId( "id" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code setId} method sets the identifier on the resulting
     * game system.
     */
    @Test
    public void testSetId_SetsId()
    {
        final GameSystemBuilder builder = GameSystems.createIncompleteGameSystemBuilder( GameSystems.GameSystemAttribute.ID );
        final String expectedId = "id"; //$NON-NLS-1$

        builder.setId( expectedId );

        final IGameSystem gameSystem = builder.toGameSystem();
        assertEquals( expectedId, gameSystem.getId() );
    }

    /**
     * Ensures the {@code toGameSystem} method throws an exception when the
     * state of the builder results in an illegal game system.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal
     * attribute will cause an exception to be thrown. The primary collection of
     * tests for all possible permutations of illegal game system attributes is
     * located in the {@code GameSystemUtilsTest} class.
     * </p>
     */
    @Test( expected = IllegalStateException.class )
    public void testToGameSystem_GameSystem_Illegal()
    {
        // TODO: See comment in GameConfigurationBuilderTest.testToGameConfiguration_GameConfig_Illegal().

        final GameSystemBuilder builder = GameSystems.createIncompleteGameSystemBuilder( GameSystems.GameSystemAttribute.ROLES );
        builder.addRoles( GameSystems.createNonUniqueRoleList() );

        builder.toGameSystem();
    }

    /**
     * Ensures the {@code toGameSystem} method throws an exception when the
     * identifier is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToGameSystem_Id_NotSet()
    {
        final GameSystemBuilder builder = GameSystems.createIncompleteGameSystemBuilder( GameSystems.GameSystemAttribute.ID );

        builder.toGameSystem();
    }
}
