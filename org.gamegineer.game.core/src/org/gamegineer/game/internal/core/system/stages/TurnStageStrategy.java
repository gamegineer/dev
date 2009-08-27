/*
 * TurnStageStrategy.java
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
 * Created on Sep 12, 2008 at 9:52:44 PM.
 */

package org.gamegineer.game.internal.core.system.stages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.engine.core.IState;
import org.gamegineer.game.core.system.AbstractStageStrategy;
import org.gamegineer.game.core.system.IStage;
import org.gamegineer.game.internal.core.GameAttributes;

/**
 * The default strategy for a game turn stage.
 * 
 * <p>
 * A game turn consists of a single player's moves in order.
 * </p>
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class TurnStageStrategy
    extends AbstractStageStrategy
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TurnStageStrategy} class.
     */
    public TurnStageStrategy()
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

        final IState state = context.getState();

        GameAttributes.TURN_COMPLETE.add( state, false );

        if( GameAttributes.CURRENT_PLAYER_INDEX.isPresent( state ) )
        {
            final int newPlayerIndex = GameAttributes.CURRENT_PLAYER_INDEX.getValue( state ) + 1;
            if( newPlayerIndex < GameAttributes.PLAYER_LIST.getValue( state ).size() )
            {
                GameAttributes.CURRENT_PLAYER_INDEX.setValue( state, newPlayerIndex );
            }
            else
            {
                GameAttributes.ROUND_COMPLETE.setValue( state, true );
            }
        }
        else
        {
            GameAttributes.CURRENT_PLAYER_INDEX.add( state, 0 );
        }
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

        GameAttributes.TURN_COMPLETE.remove( context.getState() );
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

        return GameAttributes.TURN_COMPLETE.getValue( context.getState() );
    }
}
