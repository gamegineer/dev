/*
 * DeactivateStageCommandAsAbstractStageCommandTest.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Aug 8, 2008 at 11:05:53 PM.
 */

package org.gamegineer.game.internal.core.commands;

import org.gamegineer.game.internal.core.StageVersion;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.commands.DeactivateStageCommand}
 * class to ensure it does not violate the contract of the
 * {@link org.gamegineer.game.internal.core.commands.AbstractStageCommand}
 * class.
 */
public final class DeactivateStageCommandAsAbstractStageCommandTest
    extends AbstractAbstractStageCommandTestCase<DeactivateStageCommand>
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code DeactivateStageCommandAsAbstractStageCommandTest} class.
     */
    public DeactivateStageCommandAsAbstractStageCommandTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.engine.core.AbstractCommandTestCase#createCommand()
     */
    @Override
    protected DeactivateStageCommand createCommand()
    {
        return new DeactivateStageCommand( "id", new StageVersion() ); //$NON-NLS-1$
    }
}
