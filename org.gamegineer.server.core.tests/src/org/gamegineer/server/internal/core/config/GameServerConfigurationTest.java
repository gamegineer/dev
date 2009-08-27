/*
 * GameServerConfigurationTest.java
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
 * Created on Nov 30, 2008 at 10:35:38 PM.
 */

package org.gamegineer.server.internal.core.config;

import static org.junit.Assert.fail;
import org.gamegineer.server.core.system.FakeGameSystemSource;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.server.internal.core.config.GameServerConfiguration}
 * class.
 */
public final class GameServerConfigurationTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameServerConfigurationTest}
     * class.
     */
    public GameServerConfigurationTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createGameServerConfiguration} method throws an
     * exception when passed a {@code null} game system source.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameServerConfiguration_GameSystemSource_Null()
    {
        GameServerConfiguration.createGameServerConfiguration( "name", null ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createGameServerConfiguration} method throws an
     * exception when one or more of its arguments results in an illegal game
     * server configuration.
     * 
     * <p>
     * The purpose of this test is simply to ensure <i>any</i> illegal argument
     * will cause an exception to be thrown. The primary collection of tests for
     * all possible permutations of illegal game server configuration attributes
     * is located in the {@code ConfigurationUtilsTest} class.
     * </p>
     */
    @Ignore( "Currently, there is no way to create an illegal game server configuration." )
    @Test( expected = IllegalArgumentException.class )
    public void testCreateGameServerConfiguration_GameServerConfig_Illegal()
    {
        fail( "Test not implemented." ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code createGameServerConfiguration} method throws an
     * exception when passed a {@code null} server name.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateGameServerConfiguration_Name_Null()
    {
        GameServerConfiguration.createGameServerConfiguration( null, new FakeGameSystemSource() );
    }
}
