/*
 * GameClientConfigurationBuilderTest.java
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
 * Created on Mar 7, 2009 at 8:28:36 PM.
 */

package org.gamegineer.client.core.config;

import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.gamegineer.client.core.system.FakeGameSystemUiSource;
import org.gamegineer.client.core.system.IGameSystemUiSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.core.config.GameClientConfigurationBuilder}
 * class.
 */
public final class GameClientConfigurationBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game client configuration builder under test in the fixture. */
    private GameClientConfigurationBuilder builder_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * GameClientConfigurationBuilderTest} class.
     */
    public GameClientConfigurationBuilderTest()
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
        builder_ = new GameClientConfigurationBuilder();
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
     * Ensures the {@code setGameSystemUiSource} method throws an exception when
     * passed a {@code null} game system user interface source.
     */
    @Test( expected = NullPointerException.class )
    public void testSetGameSystemUiSource_GameSystemUiSource_Null()
    {
        builder_.setGameSystemUiSource( null );
    }

    /**
     * Ensures the {@code setGameSystemUiSource} method returns the same builder
     * instance.
     */
    @Test
    public void testSetGameSystemUiSource_ReturnValue_SameBuilder()
    {
        assertSame( builder_, builder_.setGameSystemUiSource( createDummy( IGameSystemUiSource.class ) ) );
    }

    /**
     * Ensures the {@code setGameSystemUiSource} method sets the game system
     * user interface source on the resulting configuration.
     */
    @Test
    public void testSetGameSystemUiSource_SetsGameSystemUiSource()
    {
        final GameClientConfigurationBuilder builder = Configurations.createIncompleteGameClientConfigurationBuilder( Configurations.GameClientConfigurationAttribute.GAME_SYSTEM_UI_SOURCE );
        final IGameSystemUiSource expectedGameSystemUiSource = new FakeGameSystemUiSource();

        builder.setGameSystemUiSource( expectedGameSystemUiSource );

        final IGameClientConfiguration gameClientConfig = builder.toGameClientConfiguration();
        assertSame( expectedGameSystemUiSource, gameClientConfig.getGameSystemUiSource() );
    }

    /**
     * Ensures the {@code toGameClientConfiguration} method throws an exception
     * when the state of the builder results in an illegal game client
     * configuration.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal attribute
     * will cause an exception to be thrown. The primary collection of tests for
     * all possible permutations of illegal game client configuration attributes
     * is located in the {@code ConfigurationUtilsTest} class.
     * </p>
     */
    @Ignore( "Currently, there is no way to create an illegal game client configuration." )
    @Test( expected = IllegalStateException.class )
    public void testToGameClientConfiguration_GameClientConfig_Illegal()
    {
        fail( "Test not implemented." ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code toGameClientConfiguration} method throws an exception
     * when the game system user interface source is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToGameClientConfiguration_GameSystemUiSource_NotSet()
    {
        final GameClientConfigurationBuilder builder = Configurations.createIncompleteGameClientConfigurationBuilder( Configurations.GameClientConfigurationAttribute.GAME_SYSTEM_UI_SOURCE );

        builder.toGameClientConfiguration();
    }
}
