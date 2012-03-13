/*
 * SpringUtilsTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Oct 10, 2010 at 9:34:26 PM.
 */

package org.gamegineer.table.internal.ui.util.swing;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.util.swing.SpringUtils} class.
 */
public final class SpringUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code SpringUtilsTest} class.
     */
    public SpringUtilsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code buildCompactGrid} method throws an exception when
     * passed a {@code null} parent component.
     */
    @Test( expected = NullPointerException.class )
    public void testBuildCompactGrid_Parent_Null()
    {
        SpringUtils.buildCompactGrid( null, 1, 1, 1, 1, 1, 1 );
    }

    /**
     * Ensures the {@code buildGrid} method throws an exception when passed a
     * {@code null} parent component.
     */
    @Test( expected = NullPointerException.class )
    public void testBuildGrid_Parent_Null()
    {
        SpringUtils.buildGrid( null, 1, 1, 1, 1, 1, 1 );
    }
}
