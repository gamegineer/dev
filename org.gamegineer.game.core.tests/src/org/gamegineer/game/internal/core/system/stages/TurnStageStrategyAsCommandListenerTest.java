/*
 * TurnStageStrategyAsCommandListenerTest.java
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
 * Created on Sep 12, 2008 at 10:06:30 PM.
 */

package org.gamegineer.game.internal.core.system.stages;

import org.gamegineer.engine.core.extensions.commandeventmediator.AbstractCommandListenerTestCase;
import org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.system.stages.TurnStageStrategy}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener}
 * interface.
 */
public final class TurnStageStrategyAsCommandListenerTest
    extends AbstractCommandListenerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code TurnStageStrategyAsCommandListenerTest} class.
     */
    public TurnStageStrategyAsCommandListenerTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.extensions.commandeventmediator.AbstractCommandListenerTestCase#createCommandListener()
     */
    @Override
    protected ICommandListener createCommandListener()
    {
        return new TurnStageStrategy();
    }
}
