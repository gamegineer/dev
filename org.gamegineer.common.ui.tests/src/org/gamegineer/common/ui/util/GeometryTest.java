/*
 * GeometryTest.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on Sep 12, 2010 at 10:30:08 PM.
 */

package org.gamegineer.common.ui.util;

import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.common.ui.util.Geometry}
 * class.
 */
public final class GeometryTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GeometryTest} class.
     */
    public GeometryTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link Geometry#calculateCenterPoint} method throws an
     * exception when passed a {@code null} rectangle.
     */
    @Test( expected = NullPointerException.class )
    public void testCalculateCenterPoint_Rectangle_Null()
    {
        Geometry.calculateCenterPoint( null );
    }
}
