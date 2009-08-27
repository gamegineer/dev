/*
 * FakeStageStrategy.java
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
 * Created on Nov 22, 2008 at 9:35:16 PM.
 */

package org.gamegineer.game.core.system;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import org.gamegineer.engine.core.IEngineContext;

/**
 * Fake implementation of {@link org.gamegineer.game.core.system.IStageStrategy}.
 * 
 * <p>
 * This class is thread-safe.
 * </p>
 * 
 * <p>
 * This class is intended to be extended by clients.
 * </p>
 */
public class FakeStageStrategy
    extends AbstractStageStrategy
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code FakeStageStrategy} class.
     */
    public FakeStageStrategy()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * The default implementation always indicates the stage is complete.
     * 
     * @see org.gamegineer.game.core.system.IStageStrategy#isComplete(org.gamegineer.game.core.system.IStage,
     *      org.gamegineer.engine.core.IEngineContext)
     */
    public boolean isComplete(
        final IStage stage,
        final IEngineContext context )
    {
        assertArgumentNotNull( stage, "stage" ); //$NON-NLS-1$
        assertArgumentNotNull( context, "context" ); //$NON-NLS-1$

        return true;
    }
}
