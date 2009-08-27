/*
 * GameSystemUiBuilderTest.java
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
 * Created on Feb 27, 2009 at 11:07:33 PM.
 */

package org.gamegineer.game.ui.system;

import static org.gamegineer.game.ui.system.Assert.assertRoleUiEquals;
import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import java.util.Collections;
import java.util.List;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.ui.system.GameSystemUiBuilder} class.
 */
public final class GameSystemUiBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game system user interface builder under test in the fixture. */
    private GameSystemUiBuilder m_builder;

    /** The game system on which the user interface is based. */
    private IGameSystem m_gameSystem;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemUiBuilderTest} class.
     */
    public GameSystemUiBuilderTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a legal role user interface list for the specified game system
     * user interface builder.
     * 
     * <p>
     * It is assumed the specified game system builder is complete except for
     * the role user interface list attribute.
     * </p>
     * 
     * @param gameSystem
     *        The game system on which the user interface is based; must not be
     *        {@code null}.
     * @param builder
     *        The game system user interface builder; must not be {@code null}.
     * 
     * @return A legal role user interface list for the specified game system
     *         user interface builder; never {@code null}.
     */
    /* @NonNull */
    private static List<IRoleUi> createLegalRoleUiList(
        /* @NonNull */
        final IGameSystem gameSystem,
        /* @NonNull */
        final GameSystemUiBuilder builder )
    {
        assert gameSystem != null;
        assert builder != null;

        return GameSystemUis.createRoleUiList( gameSystem.getRoles() );
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
        m_gameSystem = GameSystems.createUniqueGameSystem();
        m_builder = new GameSystemUiBuilder();
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
        m_gameSystem = null;
    }

    /**
     * Ensures the {@code addRole} method adds a role to the resulting game
     * system user interface.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddRole_AddsRole()
    {
        final GameSystemUiBuilder builder = GameSystemUis.createIncompleteGameSystemUiBuilder( m_gameSystem, GameSystemUis.GameSystemUiAttribute.ROLES );
        final List<IRoleUi> expectedRoleUis = createLegalRoleUiList( m_gameSystem, builder );

        for( final IRoleUi roleUi : expectedRoleUis )
        {
            builder.addRole( roleUi );
        }

        final IGameSystemUi gameSystemUi = builder.toGameSystemUi();
        final List<IRoleUi> actualRoleUis = gameSystemUi.getRoles();
        assertEquals( expectedRoleUis.size(), actualRoleUis.size() );
        for( int index = 0; index < expectedRoleUis.size(); ++index )
        {
            assertRoleUiEquals( expectedRoleUis.get( index ), actualRoleUis.get( index ) );
        }
    }

    /**
     * Ensures the {@code addRole} method throws an exception when passed a
     * {@code null} role user interface.
     */
    @Test( expected = NullPointerException.class )
    public void testAddRole_RoleUi_Null()
    {
        m_builder.addRole( null );
    }

    /**
     * Ensures the {@code addRole} method returns the same builder instance.
     */
    @Test
    public void testAddRole_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.addRole( createDummy( IRoleUi.class ) ) );
    }

    /**
     * Ensures the {@code addRoles} method adds a collection of roles to the
     * resulting game system user interface.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testAddRoles_AddsRoles()
    {
        final GameSystemUiBuilder builder = GameSystemUis.createIncompleteGameSystemUiBuilder( m_gameSystem, GameSystemUis.GameSystemUiAttribute.ROLES );
        final List<IRoleUi> expectedRoleUis = createLegalRoleUiList( m_gameSystem, builder );

        builder.addRoles( expectedRoleUis );

        final IGameSystemUi gameSystemUi = builder.toGameSystemUi();
        final List<IRoleUi> actualRoleUis = gameSystemUi.getRoles();
        assertEquals( expectedRoleUis.size(), actualRoleUis.size() );
        for( int index = 0; index < expectedRoleUis.size(); ++index )
        {
            assertRoleUiEquals( expectedRoleUis.get( index ), actualRoleUis.get( index ) );
        }
    }

    /**
     * Ensures the {@code addRoles} method throws an exception when passed a
     * {@code null} role user interface list.
     */
    @Test( expected = NullPointerException.class )
    public void testAddRoles_RoleUis_Null()
    {
        m_builder.addRoles( null );
    }

    /**
     * Ensures the {@code addRoles} method returns the same builder instance.
     */
    @Test
    public void testAddRoles_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.addRoles( Collections.<IRoleUi>emptyList() ) );
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
     * user interface.
     */
    @Test
    public void testSetId_SetsId()
    {
        final GameSystemUiBuilder builder = GameSystemUis.createIncompleteGameSystemUiBuilder( m_gameSystem, GameSystemUis.GameSystemUiAttribute.ID );
        final String expectedId = "id"; //$NON-NLS-1$

        builder.setId( expectedId );

        final IGameSystemUi gameSystemUi = builder.toGameSystemUi();
        assertEquals( expectedId, gameSystemUi.getId() );
    }

    /**
     * Ensures the {@code setName} method throws an exception when passed a
     * {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testSetName_Name_Null()
    {
        m_builder.setName( null );
    }

    /**
     * Ensures the {@code setName} method returns the same builder instance.
     */
    @Test
    public void testSetName_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.setName( "name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code setName} method sets the name on the resulting game
     * system user interface.
     */
    @Test
    public void testSetName_SetsName()
    {
        final GameSystemUiBuilder builder = GameSystemUis.createIncompleteGameSystemUiBuilder( m_gameSystem, GameSystemUis.GameSystemUiAttribute.NAME );
        final String expectedName = "name"; //$NON-NLS-1$

        builder.setName( expectedName );

        final IGameSystemUi gameSystemUi = builder.toGameSystemUi();
        assertEquals( expectedName, gameSystemUi.getName() );
    }

    /**
     * Ensures the {@code toGameSystemUi} method throws an exception when the
     * state of the builder results in an illegal game system user interface.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal
     * attribute will cause an exception to be thrown. The primary collection of
     * tests for all possible permutations of illegal game system user interface
     * attributes is located in the {@code GameSystemUiUtilsTest} class.
     * </p>
     */
    @Test( expected = IllegalStateException.class )
    public void testToGameSystemUi_GameSystemUi_Illegal()
    {
        // TODO: See comment in GameConfigurationBuilderTest.testToGameConfiguration_GameConfig_Illegal().

        final GameSystemUiBuilder builder = GameSystemUis.createIncompleteGameSystemUiBuilder( m_gameSystem, GameSystemUis.GameSystemUiAttribute.ROLES );
        builder.addRoles( GameSystemUis.createRoleUiList( GameSystems.createNonUniqueRoleList() ) );

        builder.toGameSystemUi();
    }

    /**
     * Ensures the {@code toGameSystemUi} method throws an exception when the
     * identifier is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToGameSystemUi_Id_NotSet()
    {
        final GameSystemUiBuilder builder = GameSystemUis.createIncompleteGameSystemUiBuilder( m_gameSystem, GameSystemUis.GameSystemUiAttribute.ID );

        builder.toGameSystemUi();
    }

    /**
     * Ensures the {@code toGameSystemUi} method throws an exception when the
     * name is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToGameSystemUi_Name_NotSet()
    {
        final GameSystemUiBuilder builder = GameSystemUis.createIncompleteGameSystemUiBuilder( m_gameSystem, GameSystemUis.GameSystemUiAttribute.NAME );

        builder.toGameSystemUi();
    }
}
