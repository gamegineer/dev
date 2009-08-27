/*
 * RoleBuilderTest.java
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
 * Created on Jan 17, 2009 at 8:59:58 PM.
 */

package org.gamegineer.game.core.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.game.core.system.RoleBuilder}
 * class.
 */
public final class RoleBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The role builder under test in the fixture. */
    private RoleBuilder m_builder;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code RoleBuilderTest} class.
     */
    public RoleBuilderTest()
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
        m_builder = new RoleBuilder();
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
     * role.
     */
    @Test
    public void testSetId_SetsId()
    {
        final RoleBuilder builder = GameSystems.createIncompleteRoleBuilder( GameSystems.RoleAttribute.ID );
        final String expectedId = "id"; //$NON-NLS-1$

        builder.setId( expectedId );

        final IRole role = builder.toRole();
        assertEquals( expectedId, role.getId() );
    }

    /**
     * Ensures the {@code toRole} method throws an exception when the role
     * identifier is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToRole_Id_NotSet()
    {
        final RoleBuilder builder = GameSystems.createIncompleteRoleBuilder( GameSystems.RoleAttribute.ID );

        builder.toRole();
    }

    /**
     * Ensures the {@code toRole} method throws an exception when the state of
     * the builder results in an illegal role.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal
     * attribute will cause an exception to be thrown. The primary collection of
     * tests for all possible permutations of illegal role attributes is located
     * in the {@code GameSystemUtilsTest} class.
     * </p>
     */
    @Ignore( "Currently, there is no way to create an illegal role." )
    @Test( expected = IllegalStateException.class )
    public void testToRole_Role_Illegal()
    {
        fail( "Test not implemented." ); //$NON-NLS-1$
    }
}
