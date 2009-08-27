/*
 * ConfigurationUtils.java
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
 * Created on Mar 7, 2009 at 12:17:08 AM.
 */

package org.gamegineer.client.internal.core.config;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.client.core.config.IGameClientConfiguration;

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
     * Asserts a game client configuration is legal.
     * 
     * @param gameClientConfig
     *        The game client configuration; must not be {@code null}.
     * 
     * @throws java.lang.IllegalArgumentException
     *         If {@code gameClientConfig} is illegal.
     * @throws java.lang.NullPointerException
     *         If {@code gameClientConfig} is {@code null}.
     */
    public static void assertGameClientConfigurationLegal(
        /* @NonNull */
        final IGameClientConfiguration gameClientConfig )
    {
        assertArgumentNotNull( gameClientConfig, "gameClientConfig" ); //$NON-NLS-1$
    }
}
