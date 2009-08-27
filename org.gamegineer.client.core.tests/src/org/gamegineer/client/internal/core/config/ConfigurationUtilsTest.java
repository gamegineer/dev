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
 * Created on Mar 7, 2009 at 12:17:17 AM.
 */

package org.gamegineer.client.internal.core.config;

import org.gamegineer.client.core.config.IGameClientConfiguration;
import org.gamegineer.client.core.system.FakeGameSystemUiSource;
import org.gamegineer.client.core.system.IGameSystemUiSource;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.core.config.ConfigurationUtils} class.
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
     * Creates a game client configuration.
     * 
     * <p>
     * This methods does no invariant checking, thus allowing you to create an
     * illegal game client configuration.
     * </p>
     * 
     * @param gameSystemUiSource
     *        The game system user interface source; must not be {@code null}.
     * 
     * @return A new game client configuration; never {@code null}.
     */
    /* @NonNull */
    private static IGameClientConfiguration createGameClientConfiguration(
        /* @NonNull */
        final IGameSystemUiSource gameSystemUiSource )
    {
        assert gameSystemUiSource != null;

        return new IGameClientConfiguration()
        {
            public IGameSystemUiSource getGameSystemUiSource()
            {
                return gameSystemUiSource;
            }
        };
    }

    /**
     * Ensures the {@code assertGameClientConfigurationLegal} method does
     * nothing when passed a legal game client configuration.
     */
    @Test
    public void testAssertGameClientConfigurationLegal_GameClientConfig_Legal()
    {
        final IGameSystemUiSource gameSystemUiSource = new FakeGameSystemUiSource();

        ConfigurationUtils.assertGameClientConfigurationLegal( createGameClientConfiguration( gameSystemUiSource ) );
    }

    /**
     * Ensures the {@code assertGameClientConfigurationLegal} method throws an
     * exception when passed a {@code null} game client configuration.
     */
    @Test( expected = NullPointerException.class )
    public void testAssertGameClientConfigurationLegal_GameClientConfig_Null()
    {
        ConfigurationUtils.assertGameClientConfigurationLegal( null );
    }
}
