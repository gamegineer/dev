/*
 * GameConfigurationBuilderTest.java
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
 * Created on Jul 12, 2008 at 11:43:50 PM.
 */

package org.gamegineer.game.core.config;

import static org.gamegineer.game.core.config.Assert.assertPlayerConfigurationEquals;
import static org.gamegineer.test.core.Assert.assertImmutableCollection;
import static org.gamegineer.test.core.DummyFactory.createDummy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import java.util.Collections;
import java.util.List;
import org.gamegineer.game.core.system.GameSystemBuilder;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.core.config.GameConfigurationBuilder} class.
 */
public final class GameConfigurationBuilderTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The game configuration builder under test in the fixture. */
    private GameConfigurationBuilder m_builder;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameConfigurationBuilderTest}
     * class.
     */
    public GameConfigurationBuilderTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a legal game system for the specified game configuration builder.
     * 
     * <p>
     * It is assumed the specified game configuration builder is complete except
     * for the game system attribute.
     * </p>
     * 
     * @param builder
     *        The game configuration builder; must not be {@code null}.
     * 
     * @return A legal game system for the specified game configuration builder;
     *         never {@code null}.
     */
    /* @NonNull */
    private static IGameSystem createLegalGameSystem(
        /* @NonNull */
        final GameConfigurationBuilder builder )
    {
        assert builder != null;

        final GameSystemBuilder gameSystemBuilder = GameSystems.createIncompleteGameSystemBuilder( GameSystems.GameSystemAttribute.ROLES );
        for( final IPlayerConfiguration playerConfig : builder.getPlayers() )
        {
            gameSystemBuilder.addRole( GameSystems.createRole( playerConfig.getRoleId() ) );
        }
        return gameSystemBuilder.toGameSystem();
    }

    /**
     * Creates a legal player configuration list for the specified game
     * configuration builder.
     * 
     * <p>
     * It is assumed the specified game configuration builder is complete except
     * for the player configuration list attribute.
     * </p>
     * 
     * @param builder
     *        The game configuration builder; must not be {@code null}.
     * 
     * @return A legal player configuration list for the specified game
     *         configuration builder; never {@code null}.
     */
    /* @NonNull */
    private static List<IPlayerConfiguration> createLegalPlayerConfigurationList(
        /* @NonNull */
        final GameConfigurationBuilder builder )
    {
        assert builder != null;

        return Configurations.createPlayerConfigurationList( builder.getSystem() );
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
        m_builder = new GameConfigurationBuilder();
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
     * Ensures the {@code addPlayer} method adds a player to the resulting
     * configuration.
     */
    @Test
    public void testAddPlayer_AddsPlayer()
    {
        final GameConfigurationBuilder builder = Configurations.createIncompleteGameConfigurationBuilder( Configurations.GameConfigurationAttribute.PLAYERS );
        final List<IPlayerConfiguration> expectedPlayerConfigs = createLegalPlayerConfigurationList( builder );

        for( final IPlayerConfiguration playerConfig : expectedPlayerConfigs )
        {
            builder.addPlayer( playerConfig );
        }

        final IGameConfiguration gameConfig = builder.toGameConfiguration();
        final List<IPlayerConfiguration> actualPlayerConfigs = gameConfig.getPlayers();
        assertEquals( expectedPlayerConfigs.size(), actualPlayerConfigs.size() );
        for( int index = 0; index < expectedPlayerConfigs.size(); ++index )
        {
            assertPlayerConfigurationEquals( expectedPlayerConfigs.get( index ), actualPlayerConfigs.get( index ) );
        }
    }

    /**
     * Ensures the {@code addPlayer} method throws an exception when passed a
     * {@code null} player configuration.
     */
    @Test( expected = NullPointerException.class )
    public void testAddPlayer_PlayerConfig_Null()
    {
        m_builder.addPlayer( null );
    }

    /**
     * Ensures the {@code addPlayer} method returns the same builder instance.
     */
    @Test
    public void testAddPlayer_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.addPlayer( createDummy( IPlayerConfiguration.class ) ) );
    }

    /**
     * Ensures the {@code addPlayers} method adds a collection of players to the
     * resulting configuration.
     */
    @Test
    public void testAddPlayers_AddsPlayers()
    {
        final GameConfigurationBuilder builder = Configurations.createIncompleteGameConfigurationBuilder( Configurations.GameConfigurationAttribute.PLAYERS );
        final List<IPlayerConfiguration> expectedPlayerConfigs = createLegalPlayerConfigurationList( builder );

        builder.addPlayers( expectedPlayerConfigs );

        final IGameConfiguration gameConfig = builder.toGameConfiguration();
        final List<IPlayerConfiguration> actualPlayerConfigs = gameConfig.getPlayers();
        assertEquals( expectedPlayerConfigs.size(), actualPlayerConfigs.size() );
        for( int index = 0; index < expectedPlayerConfigs.size(); ++index )
        {
            assertPlayerConfigurationEquals( expectedPlayerConfigs.get( index ), actualPlayerConfigs.get( index ) );
        }
    }

    /**
     * Ensures the {@code addPlayers} method throws an exception when passed a
     * {@code null} player configuration list.
     */
    @Test( expected = NullPointerException.class )
    public void testAddPlayers_PlayerConfigs_Null()
    {
        m_builder.addPlayers( null );
    }

    /**
     * Ensures the {@code addPlayers} method returns the same builder instance.
     */
    @Test
    public void testAddPlayers_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.addPlayers( Collections.<IPlayerConfiguration>emptyList() ) );
    }

    /**
     * Ensures the {@code getPlayers} method returns an immutable collection.
     */
    @Test
    public void testGetPlayers_ReturnValue_Immutable()
    {
        assertImmutableCollection( m_builder.getPlayers() );
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
     * configuration.
     */
    @Test
    public void testSetId_SetsId()
    {
        final GameConfigurationBuilder builder = Configurations.createIncompleteGameConfigurationBuilder( Configurations.GameConfigurationAttribute.ID );
        final String expectedId = "id"; //$NON-NLS-1$

        builder.setId( expectedId );

        final IGameConfiguration gameConfig = builder.toGameConfiguration();
        assertEquals( expectedId, gameConfig.getId() );
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
        final GameConfigurationBuilder builder = Configurations.createIncompleteGameConfigurationBuilder( Configurations.GameConfigurationAttribute.NAME );
        final String expectedName = "name"; //$NON-NLS-1$

        builder.setName( expectedName );

        final IGameConfiguration gameConfig = builder.toGameConfiguration();
        assertEquals( expectedName, gameConfig.getName() );
    }

    /**
     * Ensures the {@code setSystem} method throws an exception when passed a
     * {@code null} game system.
     */
    @Test( expected = NullPointerException.class )
    public void testSetSystem_System_Null()
    {
        m_builder.setSystem( null );
    }

    /**
     * Ensures the {@code setSystem} method returns the same builder instance.
     */
    @Test
    public void testSetSystem_ReturnValue_SameBuilder()
    {
        assertSame( m_builder, m_builder.setSystem( createDummy( IGameSystem.class ) ) );
    }

    /**
     * Ensures the {@code setSystem} method sets the game system on the
     * resulting configuration.
     */
    @Test
    public void testSetSystem_SetsSystem()
    {
        final GameConfigurationBuilder builder = Configurations.createIncompleteGameConfigurationBuilder( Configurations.GameConfigurationAttribute.SYSTEM );
        final IGameSystem expectedSystem = createLegalGameSystem( builder );

        builder.setSystem( expectedSystem );

        final IGameConfiguration gameConfig = builder.toGameConfiguration();
        assertSame( expectedSystem, gameConfig.getSystem() );
    }

    /**
     * Ensures the {@code toGameConfiguration} method throws an exception when
     * the state of the builder results in an illegal game configuration.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal attribute
     * will cause an exception to be thrown. The primary collection of tests for
     * all possible permutations of illegal game configuration attributes is
     * located in the {@code ConfigurationUtilsTest} class.
     * </p>
     */
    @Test( expected = IllegalStateException.class )
    public void testToGameConfiguration_GameConfig_Illegal()
    {
        // TODO: It would be more appropriate if this method used
        // Configurations.createIllegalGameConfiguration(), but then we have to
        // manually copy over each attribute to the builder.  If we add a new
        // attribute to the GameConfiguration class in the future and forget to
        // update this method, it will end up failing for the wrong reason.
        // This can be corrected if we switch to throwing an exception other
        // than IllegalStateException for this case.

        final GameConfigurationBuilder builder = Configurations.createIncompleteGameConfigurationBuilder( Configurations.GameConfigurationAttribute.PLAYERS );

        builder.toGameConfiguration();
    }

    /**
     * Ensures the {@code toGameConfiguration} method throws an exception when
     * the game identifier is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToGameConfiguration_Id_NotSet()
    {
        final GameConfigurationBuilder builder = Configurations.createIncompleteGameConfigurationBuilder( Configurations.GameConfigurationAttribute.ID );

        builder.toGameConfiguration();
    }

    /**
     * Ensures the {@code toGameConfiguration} method throws an exception when
     * the game name is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToGameConfiguration_Name_NotSet()
    {
        final GameConfigurationBuilder builder = Configurations.createIncompleteGameConfigurationBuilder( Configurations.GameConfigurationAttribute.NAME );

        builder.toGameConfiguration();
    }

    /**
     * Ensures the {@code toGameConfiguration} method throws an exception when
     * the game system is not set.
     */
    @Test( expected = IllegalStateException.class )
    public void testToGameConfiguration_System_NotSet()
    {
        final GameConfigurationBuilder builder = Configurations.createIncompleteGameConfigurationBuilder( Configurations.GameConfigurationAttribute.SYSTEM );

        builder.toGameConfiguration();
    }
}
