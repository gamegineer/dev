/*
 * IStage.java
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
 * Created on Nov 13, 2008 at 8:25:48 PM.
 */

package org.gamegineer.game.core.system;

import java.util.List;

/**
 * A game stage.
 * 
 * <p>
 * Game stages act as the State participant in the State pattern. There is one
 * stage active at any given time within a game. The active stage is responsible
 * for processing all game engine events while it is active. As each stage
 * completes, a new stage becomes active until ultimately the end of the game
 * has been reached.
 * </p>
 * 
 * <p>
 * This interface is not intended to be implemented or extended by clients.
 * </p>
 */
public interface IStage
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the cardinality of this stage.
     * 
     * <p>
     * The cardinality represents the number of times the stage should be
     * activated before proceeding on to the next stage. It may be any
     * non-negative number. A cardinality of zero indicates the stage should
     * continue to be activated until it indicates it is complete.
     * </p>
     * 
     * @return The cardinality of this stage.
     */
    public int getCardinality();

    /**
     * Gets the stage identifier.
     * 
     * @return The stage identifier; never {@code null}.
     */
    /* @NonNull */
    public String getId();

    /**
     * Gets a list view of the stages which are children of this stage.
     * 
     * @return A list view of the stages which are children of this stage; never
     *         {@code null}.
     */
    /* @NonNull */
    public List<IStage> getStages();

    /**
     * Gets the strategy which implements the game logic for this stage.
     * 
     * @return The strategy which implements the game logic for this stage;
     *         never {@code null}.
     */
    /* @NonNull */
    public IStageStrategy getStrategy();
}
