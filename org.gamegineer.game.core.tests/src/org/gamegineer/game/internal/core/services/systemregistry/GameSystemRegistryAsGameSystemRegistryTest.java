/*
 * GameSystemRegistryAsGameSystemRegistryTest.java
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
 * Created on Feb 16, 2009 at 10:38:14 PM.
 */

package org.gamegineer.game.internal.core.services.systemregistry;

import org.gamegineer.game.core.services.systemregistry.AbstractGameSystemRegistryTestCase;
import org.gamegineer.game.core.services.systemregistry.IGameSystemRegistry;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.services.systemregistry.GameSystemRegistry}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.game.core.services.systemregistry.IGameSystemRegistry}
 * interface.
 */
public final class GameSystemRegistryAsGameSystemRegistryTest
    extends AbstractGameSystemRegistryTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code GameSystemRegistryAsGameSystemRegistryTest} class.
     */
    public GameSystemRegistryAsGameSystemRegistryTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.core.services.systemregistry.AbstractGameSystemRegistryTestCase#createGameSystemRegistry()
     */
    @Override
    protected IGameSystemRegistry createGameSystemRegistry()
    {
        return new GameSystemRegistry();
    }
}
