/*
 * MoveStageStrategyAsStageStrategyTest.java
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
 * Created on Aug 25, 2009 at 11:21:32 PM.
 */

package org.gamegineer.tictactoe.internal.core.system.stages;

import org.gamegineer.game.core.system.AbstractStageStrategyTestCase;
import org.gamegineer.game.core.system.IStageStrategy;

/**
 * A fixture for testing the
 * {@link org.gamegineer.tictactoe.internal.core.system.stages.MoveStageStrategy}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.game.core.system.IStageStrategy} interface.
 */
public final class MoveStageStrategyAsStageStrategyTest
    extends AbstractStageStrategyTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code MoveStageStrategyAsStageStrategyTest} class.
     */
    public MoveStageStrategyAsStageStrategyTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.core.system.AbstractStageStrategyTestCase#createStageStrategy()
     */
    @Override
    protected IStageStrategy createStageStrategy()
    {
        return new MoveStageStrategy();
    }
}