/*
 * PlayerConfigurationAsPlayerConfigurationTest.java
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
 * Created on Jan 17, 2009 at 10:55:16 PM.
 */

package org.gamegineer.game.internal.core.config;

import org.gamegineer.game.core.config.AbstractPlayerConfigurationTestCase;
import org.gamegineer.game.core.config.IPlayerConfiguration;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.config.PlayerConfiguration} class to
 * ensure it does not violate the contract of the
 * {@link org.gamegineer.game.core.config.IPlayerConfiguration} interface.
 */
public final class PlayerConfigurationAsPlayerConfigurationTest
    extends AbstractPlayerConfigurationTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code PlayerConfigurationAsPlayerConfigurationTest} class.
     */
    public PlayerConfigurationAsPlayerConfigurationTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.core.config.AbstractPlayerConfigurationTestCase#createPlayerConfiguration()
     */
    @Override
    protected IPlayerConfiguration createPlayerConfiguration()
    {
        return PlayerConfiguration.createPlayerConfiguration( "role-id", "user-id" ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
