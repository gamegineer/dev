/*
 * GameSystemAsGameSystemTest.java
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
 * Created on Nov 15, 2008 at 11:34:45 PM.
 */

package org.gamegineer.game.internal.core.system;

import org.gamegineer.game.core.system.AbstractGameSystemTestCase;
import org.gamegineer.game.core.system.GameSystems;
import org.gamegineer.game.core.system.IGameSystem;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.system.GameSystem} class to ensure
 * it does not violate the contract of the
 * {@link org.gamegineer.game.core.system.IGameSystem} interface.
 */
public final class GameSystemAsGameSystemTest
    extends AbstractGameSystemTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameSystemAsGameSystemTest}
     * class.
     */
    public GameSystemAsGameSystemTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.core.system.AbstractGameSystemTestCase#createGameSystem()
     */
    @Override
    protected IGameSystem createGameSystem()
    {
        return GameSystem.createGameSystem( "id", GameSystems.createUniqueRoleList(), GameSystems.createUniqueStageList( 0 ) ); //$NON-NLS-1$
    }
}
