/*
 * RoleUiBuilderTest.java
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
 * Created on Feb 27, 2009 at 10:04:02 PM.
 */

package org.gamegineer.game.ui.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IRole;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.game.ui.system.RoleUiBuilder}
 * class.
 */
public final class RoleUiBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The role user interface builder under test in the fixture. */
    private RoleUiBuilder m_builder;

    /** The role on which the user interface is based. */
    private IRole m_role;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RoleUiBuilderTest} class.
     */
    public RoleUiBuilderTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        m_role = GameSystems.createUniqueRole();
        m_builder = new RoleUiBuilder();
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
        m_role = null;
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
        final RoleUiBuilder builder = GameSystemUis.createIncompleteRoleUiBuilder( m_role, GameSystemUis.RoleUiAttribute.ID );
        final String expectedId = "id"; //$NON-NLS-1$

        builder.setId( expectedId );

        final IRoleUi roleUi = builder.toRoleUi();
        assertEquals( expectedId, roleUi.getId() );
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
     * Ensures the {@code setName} method sets the name on the resulting user
     * interface.
     */
    @Test
    public void testSetName_SetsName()
    {
        final RoleUiBuilder builder = GameSystemUis.createIncompleteRoleUiBuilder( m_role, GameSystemUis.RoleUiAttribute.NAME );
        final String expectedName = "name"; //$NON-NLS-1$

        builder.setName( expectedName );

        final IRoleUi roleUi = builder.toRoleUi();
        assertEquals( expectedName, roleUi.getName() );
    }

    /**
     * Ensures the {@code toRoleUi} method throws an exception when the
     * identifier is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToRoleUi_Id_NotSet()
    {
        final RoleUiBuilder builder = GameSystemUis.createIncompleteRoleUiBuilder( m_role, GameSystemUis.RoleUiAttribute.ID );

        builder.toRoleUi();
    }

    /**
     * Ensures the {@code toRoleUi} method throws an exception when the name is
     * not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToRoleUi_Name_NotSet()
    {
        final RoleUiBuilder builder = GameSystemUis.createIncompleteRoleUiBuilder( m_role, GameSystemUis.RoleUiAttribute.NAME );

        builder.toRoleUi();
    }

    /**
     * Ensures the {@code toRoleUi} method throws an exception when the state of
     * the builder results in an illegal role user interface.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal
     * attribute will cause an exception to be thrown. The primary collection of
     * tests for all possible permutations of illegal role user interface
     * attributes is located in the {@code GameSystemUiUtilsTest} class.
     * </p>
     */
    @Ignore( "Currently, there is no way to create an illegal role user interface." )
    @Test( expected = IllegalStateException.class )
    public void testToRoleUi_RoleUi_Illegal()
    {
        fail( "Test not implemented." ); //$NON-NLS-1$
    }
}
