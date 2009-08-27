/*
 * GameClientConfigurationAsGameClientConfigurationTest.java
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
 * Created on Mar 6, 2009 at 11:44:54 PM.
 */

package org.gamegineer.client.internal.core.config;

import org.gamegineer.client.core.config.AbstractGameClientConfigurationTestCase;
import org.gamegineer.client.core.config.IGameClientConfiguration;
import org.gamegineer.client.core.system.FakeGameSystemUiSource;

/**
 * A fixture for testing the
 * {@link org.gamegineer.client.internal.core.config.GameClientConfiguration}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.client.core.config.IGameClientConfiguration} interface.
 */
public final class GameClientConfigurationAsGameClientConfigurationTest
    extends AbstractGameClientConfigurationTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code GameClientConfigurationAsGameClientConfigurationTest} class.
     */
    public GameClientConfigurationAsGameClientConfigurationTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.client.core.config.AbstractGameClientConfigurationTestCase#createGameClientConfiguration()
     */
    @Override
    protected IGameClientConfiguration createGameClientConfiguration()
    {
        return GameClientConfiguration.createGameClientConfiguration( new FakeGameSystemUiSource() );
    }
}
