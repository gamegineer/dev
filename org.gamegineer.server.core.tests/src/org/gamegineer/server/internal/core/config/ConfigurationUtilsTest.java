/*
 * ConfigurationUtilsTest.java
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
 * Created on Dec 20, 2008 at 8:02:07 PM.
 */

package org.gamegineer.server.internal.core.config;

import org.gamegineer.server.core.config.IGameServerConfiguration;
import org.gamegineer.server.core.system.FakeGameSystemSource;
import org.gamegineer.server.core.system.IGameSystemSource;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.server.internal.core.config.ConfigurationUtils} class.
 */
public final class ConfigurationUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConfigurationUtilsTest} class.
     */
    public ConfigurationUtilsTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a game server configuration.
     * 
     * <p>
     * This methods does no invariant checking, thus allowing you to create an
     * illegal game server configuration.
     * </p>
     * 
     * @param name
     *        The server name; must not be {@code null}.
     * @param gameSystemSource
     *        The game system source; must not be {@code null}.
     * 
     * @return A new game server configuration; never {@code null}.
     */
    /* @NonNull */
    private static IGameServerConfiguration createGameServerConfiguration(
        /* @NonNull */
        final String name,
        /* @NonNull */
        final IGameSystemSource gameSystemSource )
    {
        assert name != null;
        assert gameSystemSource != null;

        return new IGameServerConfiguration()
        {
            public IGameSystemSource getGameSystemSource()
            {
                return gameSystemSource;
            }

            public String getName()
            {
                return name;
            }
        };
    }

    /**
     * Ensures the {@code assertGameServerConfigurationLegal} method does
     * nothing when passed a legal game server configuration.
     */
    @Test
    public void testAssertGameServerConfigurationLegal_GameServerConfig_Legal()
    {
        final String name = "name"; //$NON-NLS-1$
        final IGameSystemSource gameSystemSource = new FakeGameSystemSource();

        ConfigurationUtils.assertGameServerConfigurationLegal( createGameServerConfiguration( name, gameSystemSource ) );
    }

    /**
     * Ensures the {@code assertGameServerConfigurationLegal} method throws an
     * exception when passed a {@code null} game server configuration.
     */
    @Test( expected = NullPointerException.class )
    public void testAssertGameServerConfigurationLegal_GameServerConfig_Null()
    {
        ConfigurationUtils.assertGameServerConfigurationLegal( null );
    }
}
