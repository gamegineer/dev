/*
 * ConfigurationUtils.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Dec 20, 2008 at 7:59:54 PM.
 */

package org.gamegineer.server.internal.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.server.core.config.IGameServerConfiguration;

/**
 * A collection of useful methods for working with configurations.
 */
public final class ConfigurationUtils
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ConfigurationUtils} class.
     */
    private ConfigurationUtils()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Asserts a game server configuration is legal.
     * 
     * @param gameServerConfig
     *        The game server configuration; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code gameServerConfig} is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code gameServerConfig} is {@code null}.
     */
    public static void assertGameServerConfigurationLegal(
        /* @NonNull */
        final IGameServerConfiguration gameServerConfig )
    {
        assertArgumentNotNull( gameServerConfig, "gameServerConfig" ); //$NON-NLS-1$
    }
}
