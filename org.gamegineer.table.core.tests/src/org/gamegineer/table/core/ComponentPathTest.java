/*
 * ComponentPathTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
 * Created on Jun 10, 2012 at 4:40:21 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.core.ComponentPath}
 * class.
 */
public final class ComponentPathTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentPathTest} class.
     */
    public ComponentPathTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ComponentPath#ComponentPath} constructor throws an
     * exception when passed an illegal index that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Index_Illegal_Negative()
    {
        new ComponentPath( null, -1 );
    }

    /**
     * Ensures the {@link ComponentPath#toList} method returns the correct value
     * when the component hierarchy is one level deep.
     */
    @Test
    public void testToList_OneLevelDeep()
    {
        final ComponentPath componentPath0 = new ComponentPath( null, 0 );
        final List<ComponentPath> expectedValue = Arrays.asList( //
            componentPath0 );

        final List<ComponentPath> actualValue = componentPath0.toList();

        assertEquals( actualValue, expectedValue );
    }

    /**
     * Ensures the {@link ComponentPath#toList} method returns the correct value
     * when the component hierarchy is three levels deep.
     */
    @Test
    public void testToList_ThreeLevelsDeep()
    {
        final ComponentPath componentPath0 = new ComponentPath( null, 0 );
        final ComponentPath componentPath1 = new ComponentPath( componentPath0, 10 );
        final ComponentPath componentPath2 = new ComponentPath( componentPath1, 100 );
        final List<ComponentPath> expectedValue = Arrays.asList( //
            componentPath0, //
            componentPath1, //
            componentPath2 );

        final List<ComponentPath> actualValue = componentPath2.toList();

        assertEquals( actualValue, expectedValue );
    }

    /**
     * Ensures the {@link ComponentPath#toList} method returns the correct value
     * when the component hierarchy is two levels deep.
     */
    @Test
    public void testToList_TwoLevelsDeep()
    {
        final ComponentPath componentPath0 = new ComponentPath( null, 0 );
        final ComponentPath componentPath1 = new ComponentPath( componentPath0, 10 );
        final List<ComponentPath> expectedValue = Arrays.asList( //
            componentPath0, //
            componentPath1 );

        final List<ComponentPath> actualValue = componentPath1.toList();

        assertEquals( actualValue, expectedValue );
    }
}
