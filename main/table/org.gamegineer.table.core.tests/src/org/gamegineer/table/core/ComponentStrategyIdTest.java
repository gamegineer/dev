/*
 * ComponentStrategyIdTest.java
 * Copyright 2008-2014 Gamegineer contributors and others.
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
 * Created on Aug 3, 2012 at 9:03:14 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * A fixture for testing the {@link ComponentStrategyId} class.
 */
public final class ComponentStrategyIdTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentStrategyIdTest} class.
     */
    public ComponentStrategyIdTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ComponentStrategyId#toString} method returns the same
     * underlying identifier that was used to construct the component strategy
     * identifier.
     */
    @Test
    public void testToString_ReturnValue_SameId()
    {
        final String expectedId = "id"; //$NON-NLS-1$
        final ComponentStrategyId id = ComponentStrategyId.fromString( expectedId );

        final String actualId = id.toString();

        assertEquals( expectedId, actualId );
    }
}
