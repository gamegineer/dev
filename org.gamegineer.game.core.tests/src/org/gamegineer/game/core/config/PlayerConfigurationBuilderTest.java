/*
 * PlayerConfigurationBuilderTest.java
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
 * Created on Jan 17, 2009 at 10:48:12 PM.
 */

package org.gamegineer.game.core.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.core.config.PlayerConfigurationBuilder} class.
 */
public final class PlayerConfigurationBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The player configuration builder under test in the fixture. */
    private PlayerConfigurationBuilder builder_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayerConfigurationBuilderTest}
     * class.
     */
    public PlayerConfigurationBuilderTest()
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
        builder_ = new PlayerConfigurationBuilder();
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
        builder_ = null;
    }

    /**
     * Ensures the {@code setRoleId} method throws an exception when passed a
     * {@code null} role identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testSetRoleId_RoleId_Null()
    {
        builder_.setRoleId( null );
    }

    /**
     * Ensures the {@code setRoleId} method returns the same builder instance.
     */
    @Test
    public void testSetRoleId_ReturnValue_SameBuilder()
    {
        assertSame( builder_, builder_.setRoleId( "role-id" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code setRoleId} method sets the role identifier on the
     * resulting configuration.
     */
    @Test
    public void testSetRoleId_SetsRoleId()
    {
        final PlayerConfigurationBuilder builder = Configurations.createIncompletePlayerConfigurationBuilder( Configurations.PlayerConfigurationAttribute.ROLE_ID );
        final String expectedRoleId = "role-id"; //$NON-NLS-1$

        builder.setRoleId( expectedRoleId );

        final IPlayerConfiguration playerConfig = builder.toPlayerConfiguration();
        assertEquals( expectedRoleId, playerConfig.getRoleId() );
    }

    /**
     * Ensures the {@code setUserId} method throws an exception when passed a
     * {@code null} user identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testSetUserId_UserId_Null()
    {
        builder_.setUserId( null );
    }

    /**
     * Ensures the {@code setUserId} method returns the same builder instance.
     */
    @Test
    public void testSetUserId_ReturnValue_SameBuilder()
    {
        assertSame( builder_, builder_.setUserId( "user-id" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code setUserId} method sets the user identifier on the
     * resulting configuration.
     */
    @Test
    public void testSetUserId_SetsUserId()
    {
        final PlayerConfigurationBuilder builder = Configurations.createIncompletePlayerConfigurationBuilder( Configurations.PlayerConfigurationAttribute.USER_ID );
        final String expectedUserId = "user-id"; //$NON-NLS-1$

        builder.setUserId( expectedUserId );

        final IPlayerConfiguration playerConfig = builder.toPlayerConfiguration();
        assertEquals( expectedUserId, playerConfig.getUserId() );
    }

    /**
     * Ensures the {@code toPlayerConfiguration} method throws an exception when
     * the state of the builder results in an illegal player configuration.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal attribute
     * will cause an exception to be thrown. The primary collection of tests for
     * all possible permutations of illegal player configuration attributes is
     * located in the {@code ConfigurationUtilsTest} class.
     * </p>
     */
    @Ignore( "Currently, there is no way to create an illegal player configuration." )
    @Test( expected = IllegalStateException.class )
    public void testToPlayerConfiguration_PlayerConfig_Illegal()
    {
        fail( "Test not implemented." ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code toPlayerConfiguration} method throws an exception when
     * the role identifier is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToPlayerConfiguration_RoleId_NotSet()
    {
        final PlayerConfigurationBuilder builder = Configurations.createIncompletePlayerConfigurationBuilder( Configurations.PlayerConfigurationAttribute.ROLE_ID );

        builder.toPlayerConfiguration();
    }

    /**
     * Ensures the {@code toPlayerConfiguration} method throws an exception when
     * the user identifier is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToPlayerConfiguration_UserId_NotSet()
    {
        final PlayerConfigurationBuilder builder = Configurations.createIncompletePlayerConfigurationBuilder( Configurations.PlayerConfigurationAttribute.USER_ID );

        builder.toPlayerConfiguration();
    }
}
