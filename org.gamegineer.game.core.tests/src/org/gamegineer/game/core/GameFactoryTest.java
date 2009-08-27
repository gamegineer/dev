/*
 * GameFactoryTest.java
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
 * Created on Jul 17, 2008 at 10:53:52 PM.
 */

package org.gamegineer.game.core;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.game.core.config.Configurations;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.game.core.GameFactory} class.
 */
public final class GameFactoryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameFactoryTest} class.
     */
    public GameFactoryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createGame} method throws an exception when passed an
     * illegal game configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = GameConfigurationException.class )
    public void testCreateGame_GameConfig_Illegal()
        throws Exception
    {
        GameFactory.createGame( Configurations.createIllegalGameConfiguration() );
    }

    /**
     * Ensures the {@code createGame} method does not return {@code null} when
     * passed a legal game configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test
    public void testCreateGame_GameConfig_Legal()
        throws Exception
    {
        assertNotNull( GameFactory.createGame( Configurations.createMinimalGameConfiguration() ) );
    }

    /**
     * Ensures the {@code createGame} method throws an exception if passed a
     * {@code null} game configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGame_GameConfig_Null()
        throws Exception
    {
        GameFactory.createGame( null );
    }
}
