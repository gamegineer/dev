/*
 * MoveStageStrategy.java
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
 * Created on Jun 21, 2009 at 9:16:06 PM.
 */

package org.gamegineer.tictactoe.internal.core.system.stages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import net.jcip.annotations.Immutable;
import org.gamegineer.engine.core.IEngineContext;
import org.gamegineer.game.core.system.AbstractStageStrategy;
import org.gamegineer.game.core.system.IStage;

/**
 * The strategy for the Tic-Tac-Toe move stage.
 * 
 * <p>
 * This class is immutable.
 * </p>
 */
@Immutable
public final class MoveStageStrategy
    extends AbstractStageStrategy
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MoveStageStrategy} class.
     */
    public MoveStageStrategy()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.core.system.IStageStrategy#isComplete(org.gamegineer.game.core.system.IStage, org.gamegineer.engine.core.IEngineContext)
     */
    public boolean isComplete(
        final IStage stage,
        final IEngineContext context )
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        // TODO: This is a stub for the stage that will accept player moves.
        // It is envisioned the stage hierarchy will be as follows:
        //
        //         game (complete)
        //              round (may not be required)
        //                  turn (to-do)
        //                      move (this class)

        return false;
    }
}
