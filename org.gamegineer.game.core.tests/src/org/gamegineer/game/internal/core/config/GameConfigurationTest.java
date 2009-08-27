/*
 * GameConfigurationTest.java
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
 * Created on Jul 12, 2008 at 8:49:22 PM.
 */

package org.gamegineer.game.internal.core.config;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.gamegineer.game.core.config.Configurations;
import org.gamegineer.game.core.config.IGameConfiguration;
import org.gamegineer.game.core.config.IPlayerConfiguration;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.config.GameConfiguration} class.
 */
public final class GameConfigurationTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default game identifier. */
    private static final String DEFAULT_ID;

    /** The default game name. */
    private static final String DEFAULT_NAME;

    /** The default player configuration list. */
    private static final List<IPlayerConfiguration> DEFAULT_PLAYER_CONFIGS;

    /** The default game system. */
    private static final IGameSystem DEFAULT_SYSTEM;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes the {@code GameConfigurationTest} class.
     */
    static
    {
        DEFAULT_ID = "id"; //$NON-NLS-1$
        DEFAULT_NAME = "name"; //$NON-NLS-1$
        DEFAULT_SYSTEM = GameSystems.createUniqueGameSystem();
        DEFAULT_PLAYER_CONFIGS = Configurations.createPlayerConfigurationList( DEFAULT_SYSTEM );
    }

    /**
     * Initializes a new instance of the {@code GameConfigurationTest} class.
     */
    public GameConfigurationTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createGameConfiguration} method throws an exception
     * when one or more of its arguments results in an illegal game
     * configuration.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal argument
     * will cause an exception to be thrown. The primary collection of tests for
     * all possible permutations of illegal game configuration attributes is
     * located in the {@code ConfigurationUtilsTest} class.
     * </p>
     */
    @Test( expected = IllegalArgumentException.class )
    public void testCreateGameConfiguration_GameConfig_Illegal()
    {
        final IGameConfiguration illegalGameConfig = Configurations.createIllegalGameConfiguration();
        GameConfiguration.createGameConfiguration( illegalGameConfig.getId(), illegalGameConfig.getName(), illegalGameConfig.getSystem(), illegalGameConfig.getPlayers() );
    }

    /**
     * Ensures the {@code createGameConfiguration} method throws an exception
     * when passed a {@code null} identifier.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameConfiguration_Id_Null()
    {
        GameConfiguration.createGameConfiguration( null, DEFAULT_NAME, DEFAULT_SYSTEM, DEFAULT_PLAYER_CONFIGS );
    }

    /**
     * Ensures the {@code createGameConfiguration} method throws an exception
     * when passed a {@code null} name.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameConfiguration_Name_Null()
    {
        GameConfiguration.createGameConfiguration( DEFAULT_ID, null, DEFAULT_SYSTEM, DEFAULT_PLAYER_CONFIGS );
    }

    /**
     * Ensures the {@code createGameConfiguration} method makes a copy of the
     * player configuration list.
     */
    @SuppressWarnings( "boxing" )
    @Test
    public void testCreateGameConfiguration_PlayerConfigs_Copy()
    {
        final List<IPlayerConfiguration> playerConfigs = new ArrayList<IPlayerConfiguration>( DEFAULT_PLAYER_CONFIGS );
        final GameConfiguration config = GameConfiguration.createGameConfiguration( DEFAULT_ID, DEFAULT_NAME, DEFAULT_SYSTEM, playerConfigs );
        final int originalListSize = playerConfigs.size();

        playerConfigs.add( null );

        assertEquals( originalListSize, config.getPlayers().size() );
    }

    /**
     * Ensures the {@code createGameConfiguration} method throws an exception
     * when passed a {@code null} player configuration list.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameConfiguration_PlayerConfigs_Null()
    {
        GameConfiguration.createGameConfiguration( DEFAULT_ID, DEFAULT_NAME, DEFAULT_SYSTEM, null );
    }

    /**
     * Ensures the {@code createGameConfiguration} method throws an exception
     * when passed a {@code null} game system.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameConfiguration_System_Null()
    {
        GameConfiguration.createGameConfiguration( DEFAULT_ID, DEFAULT_NAME, null, DEFAULT_PLAYER_CONFIGS );
    }
}
