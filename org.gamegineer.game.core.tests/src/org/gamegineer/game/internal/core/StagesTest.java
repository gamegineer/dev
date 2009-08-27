/*
 * StagesTest.java
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
 * Created on Sep 12, 2008 at 9:34:54 PM.
 */

package org.gamegineer.game.internal.core;

import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.game.internal.core.Stages}
 * class.
 */
public final class StagesTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code StagesTest} class.
     */
    public StagesTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code createUniqueStageWithChildren} method creates a stage
     * with at least one child.
     */
    @Test
    public void testCreateUniqueStageWithChildren_ReturnValue_HasChildren()
    {
        final Stage stage = Stages.createUniqueStageWithChildren();

        assertFalse( stage.getStages().isEmpty() );
    }
}
