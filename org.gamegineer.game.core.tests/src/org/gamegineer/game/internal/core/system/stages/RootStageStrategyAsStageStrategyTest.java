/*
 * RootStageStrategyAsStageStrategyTest.java
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
 * Created on Jul 22, 2009 at 10:36:42 PM.
 */

package org.gamegineer.game.internal.core.system.stages;

import org.gamegineer.game.core.system.AbstractStageStrategyTestCase;
import org.gamegineer.game.core.system.IStageStrategy;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.system.stages.RootStageStrategy}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.game.core.system.IStageStrategy} interface.
 */
public final class RootStageStrategyAsStageStrategyTest
    extends AbstractStageStrategyTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code RootStageStrategyAsStageStrategyTest} class.
     */
    public RootStageStrategyAsStageStrategyTest()
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
        return new RootStageStrategy();
    }
}
