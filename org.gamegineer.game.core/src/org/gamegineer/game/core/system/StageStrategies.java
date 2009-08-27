/*
 * StageStrategies.java
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
 * Created on Sep 5, 2008 at 9:53:43 PM.
 */

package org.gamegineer.game.core.system;

import org.gamegineer.game.internal.core.system.stages.RoundStageStrategy;

/**
 * A collection of standard stage strategies that may be used to define a game
 * system.
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
public class StageStrategies
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The default round stage strategy. */
    private static final IStageStrategy DEFAULT_ROUND_STAGE_STRATEGY = new RoundStageStrategy();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StageStrategies} class.
     */
    protected StageStrategies()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates a default stage strategy for a game round.
     * 
     * @return A default stage strategy for a game round; never {@code null}.
     */
    /* @NonNull */
    public static IStageStrategy createDefaultRoundStageStrategy()
    {
        return DEFAULT_ROUND_STAGE_STRATEGY;
    }
}
