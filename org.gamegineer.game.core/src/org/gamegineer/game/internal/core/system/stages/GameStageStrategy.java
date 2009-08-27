/*
 * GameStageStrategy.java
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
 * Created on Jul 29, 2008 at 10:55:06 PM.
 */

package org.gamegineer.game.internal.core.system.stages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.game.core.system.AbstractStageStrategy;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.internal.core.GameAttributes;

/**
 * The strategy for the game stage.
 * 
 * <p>
 * The game stage is the root stage in a game. It manages the user-defined
 * stages specified in the game configuration. Once the game stage is complete,
 * the game itself is over.
 * </p>
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class GameStageStrategy
    extends AbstractStageStrategy
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameStageStrategy} class.
     */
    public GameStageStrategy()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.core.system.IStageStrategy#activate(org.gamegineer.game.core.system.IStage, org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    @SuppressWarnings( "boxing" )
    public void activate(
        final IStage stage,
        final IEngineContext context )
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        GameAttributes.GAME_COMPLETE.add( context.getState(), false );
    }

    /*
     * @see org.gamegineer.game.core.system.IStageStrategy#deactivate(org.gamegineer.game.core.system.IStage, org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    public void deactivate(
        final IStage stage,
        final IEngineContext context )
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        GameAttributes.GAME_COMPLETE.remove( context.getState() );
    }

    /*
     * @see org.gamegineer.game.core.system.IStageStrategy#isComplete(org.gamegineer.game.core.system.IStage, org.gamegineer.engine.core.IEngineContext)
     */
    @SuppressWarnings( "boxing" )
    public boolean isComplete(
        final IStage stage,
        final IEngineContext context )
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        return GameAttributes.GAME_COMPLETE.getValue( context.getState() );
    }
}