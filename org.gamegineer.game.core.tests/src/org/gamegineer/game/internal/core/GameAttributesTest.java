/*
 * GameAttributesTest.java
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
 * Created on Jul 25, 2008 at 11:32:21 PM.
 */

package org.gamegineer.game.internal.core;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.game.internal.core.GameAttributes} class.
 */
public final class GameAttributesTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GameAttributesTest} class.
     */
    public GameAttributesTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code stageState} method throws an exception when passed a
     * {@code null} stage identifier.
     */
    @Test( expected = AssertionError.class )
    public void testStageState_StageId_Null()
    {
        GameAttributes.stageState( null );
    }
}