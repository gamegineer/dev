/*
 * GameAsGameTest.java
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
 * Created on Jul 17, 2008 at 11:44:41 PM.
 */

package org.gamegineer.game.internal.core;

import org.gamegineer.game.core.AbstractGameTestCase;
import org.gamegineer.game.core.config.IGameConfiguration;

/**
 * A fixture for testing the {@link org.gamegineer.game.internal.core.Game}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.game.core.IGame} interface.
 */
public final class GameAsGameTest
    extends AbstractGameTestCase<Game>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameAsGameTest} class.
     */
    public GameAsGameTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.core.AbstractGameTestCase#createGame(org.gamegineer.game.core.config.IGameConfiguration)
     */
    @Override
    protected Game createGame(
        final IGameConfiguration gameConfig )
        throws Exception
    {
        return Game.createGame( gameConfig );
    }
}
