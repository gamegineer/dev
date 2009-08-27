/*
 * GameServerConfigurationAsGameServerConfigurationTest.java
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
 * Created on Nov 30, 2008 at 10:35:28 PM.
 */

package org.gamegineer.server.internal.core.config;

import org.gamegineer.server.core.config.AbstractGameServerConfigurationTestCase;
import org.gamegineer.server.core.config.IGameServerConfiguration;
import org.gamegineer.server.core.system.FakeGameSystemSource;

/**
 * A fixture for testing the
 * {@link org.gamegineer.server.internal.core.config.GameServerConfiguration}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.server.core.config.IGameServerConfiguration} interface.
 */
public final class GameServerConfigurationAsGameServerConfigurationTest
    extends AbstractGameServerConfigurationTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code GameServerConfigurationAsGameServerConfigurationTest} class.
     */
    public GameServerConfigurationAsGameServerConfigurationTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.server.core.config.AbstractGameServerConfigurationTestCase#createGameServerConfiguration()
     */
    @Override
    protected IGameServerConfiguration createGameServerConfiguration()
    {
        return GameServerConfiguration.createGameServerConfiguration( "name", new FakeGameSystemSource() ); //$NON-NLS-1$
    }
}
