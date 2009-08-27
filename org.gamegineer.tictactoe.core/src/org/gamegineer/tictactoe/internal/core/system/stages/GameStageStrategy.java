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
 * Created on Jul 18, 2009 at 8:35:11 PM.
 */

package org.gamegineer.tictactoe.internal.core.system.stages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.game.core.system.AbstractStageStrategy;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.tictactoe.core.SquareId;
import org.gamegineer.tictactoe.internal.core.SquareState;
import org.gamegineer.tictactoe.internal.core.TicTacToeGameAttributes;

/**
 * The strategy for the Tic-Tac-Toe game stage.
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
     * @see org.gamegineer.game.core.system.AbstractStageStrategy#activate(org.gamegineer.game.core.system.IStage, org.gamegineer.engine.core.IEngineContext)
     */
    @SuppressWarnings( "boxing" )
    @Override
    public void activate(
        final IStage stage,
        final IEngineContext context )
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        TicTacToeGameAttributes.GAME_COMPLETE.add( context.getState(), false );

        for( final SquareId squareId : SquareId.values() )
        {
            TicTacToeGameAttributes.squareState( squareId ).add( context.getState(), new SquareState( squareId ) );
        }
    }

    /*
     * @see org.gamegineer.game.core.system.AbstractStageStrategy#deactivate(org.gamegineer.game.core.system.IStage, org.gamegineer.engine.core.IEngineContext)
     */
    @Override
    public void deactivate(
        final IStage stage,
        final IEngineContext context )
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        TicTacToeGameAttributes.GAME_COMPLETE.remove( context.getState() );
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

        return TicTacToeGameAttributes.GAME_COMPLETE.getValue( context.getState() );
    }
}
