/*
 * StageAsStageTest.java
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
 * Created on Nov 15, 2008 at 10:02:02 PM.
 */

package org.gamegineer.game.internal.core.system;

import java.util.Collections;
import org.gamegineer.game.core.system.AbstractStageTestCase;
import org.gamegineer.game.core.system.FakeStageStrategy;
import org.gamegineer.game.core.system.IStage;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.system.Stage} class to ensure it
 * does not violate the contract of the
 * {@link org.gamegineer.game.core.system.IStage} interface.
 */
public final class StageAsStageTest
    extends AbstractStageTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StageAsStageTest} class.
     */
    public StageAsStageTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.game.core.system.AbstractStageTestCase#createStage()
     */
    @Override
    protected IStage createStage()
    {
        return Stage.createStage( "id", new FakeStageStrategy(), 0, Collections.<IStage>emptyList() ); //$NON-NLS-1$
    }
}
