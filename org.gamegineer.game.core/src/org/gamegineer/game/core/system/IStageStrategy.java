/*
 * IStageStrategy.java
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
 * Created on Jul 22, 2008 at 9:48:34 PM.
 */

package org.gamegineer.game.core.system;

import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener;
import org.gamegineer.engine.core.extensions.stateeventmediator.IStateListener;
import org.gamegineer.game.core.GameException;

/**
 * The strategy for a game stage.
 * 
 * <p>
 * A game stage strategy represents the behavioral logic of a specific game
 * stage. A game stage will delegate all stage-related game command requests and
 * game engine events to its associated strategy.
 * </p>
 * 
 * <p>
 * This interface is intended to be implemented but not extended by clients.
 * </p>
 */
public interface IStageStrategy
    extends ICommandListener, IStateListener
{
    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Invoked when the stage receives an activation request.
     * 
     * <p>
     * The strategy is responsible for setting up whatever state is necessary to
     * support the stage while it is active.
     * </p>
     * 
     * @param stage
     *        The stage associated with the strategy; must not be {@code null}.
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code stage} or {@code context} is {@code null}.
     * @throws org.gamegineer.game.core.GameException
     *         If an error occurs while activating the stage.
     */
    public void activate(
        /* @NonNull */
        IStage stage,
        /* @NonNull */
        IEngineContext context )
        throws GameException;

    /**
     * Invoked when the stage receives a deactivation request.
     * 
     * <p>
     * The strategy is responsible for tearing down whatever state it set up to
     * support the stage when it was activated.
     * </p>
     * 
     * @param stage
     *        The stage associated with the strategy; must not be {@code null}.
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code stage} or {@code context} is {@code null}.
     * @throws org.gamegineer.game.core.GameException
     *         If an error occurs while deactivating the stage.
     */
    public void deactivate(
        /* @NonNull */
        IStage stage,
        /* @NonNull */
        IEngineContext context )
        throws GameException;

    /**
     * Indicates the stage is complete and may be deactivated.
     * 
     * @param stage
     *        The stage associated with the strategy; must not be {@code null}.
     * @param context
     *        The engine context; must not be {@code null}.
     * 
     * @return {@code true} if the stage is complete; otherwise {@code false}.
     */
    public boolean isComplete(
        /* @NonNull */
        IStage stage,
        /* @NonNull */
        IEngineContext context );
}
