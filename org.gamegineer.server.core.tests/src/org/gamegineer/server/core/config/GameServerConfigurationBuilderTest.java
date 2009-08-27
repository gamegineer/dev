/*
 * GameServerConfigurationBuilderTest.java
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
 * Created on Nov 30, 2008 at 10:09:27 PM.
 */

package org.gamegineer.server.core.config;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.gamegineer.server.core.system.FakeGameSystemSource;
import org.gamegineer.server.core.system.IGameSystemSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.server.core.config.GameServerConfigurationBuilder}
 * class.
 */
public final class GameServerConfigurationBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game server configuration builder under test in the fixture. */
    private GameServerConfigurationBuilder m_builder;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code GameServerConfigurationBuilderTest} class.
     */
    public GameServerConfigurationBuilderTest()
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
        m_builder = new GameServerConfigurationBuilder();
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
     * Ensures the {@code setGameSystemSource} method throws an exception when
     * passed a {@code null} game system source.
     */
    @Test( expected = NullPointerException.class )
    public void testSetGameSystemSource_GameSystemSource_Null()
    {
        m_builder.setGameSystemSource( null );
    }

    /**
     * Ensures the {@code setGameSystemSource} method returns the same builder
     * instance.
     */
    @Test
    public void testSetGameSystemSource_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.setGameSystemSource( createDummy( IGameSystemSource.class ) ) );
    }

    /**
     * Ensures the {@code setGameSystemSource} method sets the game system
     * source on the resulting configuration.
     */
    @Test
    public void testSetGameSystemSource_SetsGameSystemSource()
    {
        final GameServerConfigurationBuilder builder = Configurations.createIncompleteGameServerConfigurationBuilder( Configurations.GameServerConfigurationAttribute.GAME_SYSTEM_SOURCE );
        final IGameSystemSource expectedGameSystemSource = new FakeGameSystemSource();

        builder.setGameSystemSource( expectedGameSystemSource );

        final IGameServerConfiguration gameServerConfig = builder.toGameServerConfiguration();
        assertSame( expectedGameSystemSource, gameServerConfig.getGameSystemSource() );
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
     * Ensures the {@code setName} method sets the name on the resulting
     * configuration.
     */
    @Test
    public void testSetName_SetsName()
    {
        final GameServerConfigurationBuilder builder = Configurations.createIncompleteGameServerConfigurationBuilder( Configurations.GameServerConfigurationAttribute.NAME );
        final String expectedName = "name"; //$NON-NLS-1$

        builder.setName( expectedName );

        final IGameServerConfiguration gameServerConfig = builder.toGameServerConfiguration();
        assertEquals( expectedName, gameServerConfig.getName() );
    }

    /**
     * Ensures the {@code toGameServerConfiguration} method throws an exception
     * when the game system source is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToGameServerConfiguration_GameSystemSource_NotSet()
    {
        final GameServerConfigurationBuilder builder = Configurations.createIncompleteGameServerConfigurationBuilder( Configurations.GameServerConfigurationAttribute.GAME_SYSTEM_SOURCE );

        builder.toGameServerConfiguration();
    }

    /**
     * Ensures the {@code toGameServerConfiguration} method throws an exception
     * when the state of the builder results in an illegal game server
     * configuration.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal
     * attribute will cause an exception to be thrown. The primary collection of
     * tests for all possible permutations of illegal game server configuration
     * attributes is located in the {@code ConfigurationUtilsTest} class.
     * </p>
     */
    @Ignore( "Currently, there is no way to create an illegal game server configuration." )
    @Test( expected = IllegalStateException.class )
    public void testToGameServerConfiguration_GameServerConfig_Illegal()
    {
        fail( "Test not implemented." ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code toGameServerConfiguration} method throws an exception
     * when the name is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToGameServerConfiguration_Name_NotSet()
    {
        final GameServerConfigurationBuilder builder = Configurations.createIncompleteGameServerConfigurationBuilder( Configurations.GameServerConfigurationAttribute.NAME );

        builder.toGameServerConfiguration();
    }
}
