/*
 * GameSystemUiRegistryAsGameSystemUiRegistryTest.java
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
 * Created on Mar 3, 2009 at 11:13:57 PM.
 */

package org.gamegineer.game.internal.ui.services.systemuiregistry;

import org.gamegineer.game.ui.services.systemuiregistry.AbstractGameSystemUiRegistryTestCase;
import org.gamegineer.game.ui.services.systemuiregistry.IGameSystemUiRegistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.ui.services.systemuiregistry.GameSystemUiRegistry}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.game.ui.services.systemuiregistry.IGameSystemUiRegistry}
 * interface.
 */
public final class GameSystemUiRegistryAsGameSystemUiRegistryTest
    extends AbstractGameSystemUiRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code GameSystemUiRegistryAsGameSystemUiRegistryTest} class.
     */
    public GameSystemUiRegistryAsGameSystemUiRegistryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.ui.services.systemuiregistry.AbstractGameSystemUiRegistryTestCase#createGameSystemUiRegistry()
     */
    @Override
    protected IGameSystemUiRegistry createGameSystemUiRegistry()
    {
        return new GameSystemUiRegistry();
    }
}
