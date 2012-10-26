/*
 * ComponentPrototypeUtilsTest.java
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
 * Created on Oct 24, 2012 at 8:36:05 PM.
 */

package org.gamegineer.table.internal.ui.prototype;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.prototype.ComponentPrototypeUtils}
 * class.
 */
public final class ComponentPrototypeUtilsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentPrototypeUtilsTest}
     * class.
     */
    public ComponentPrototypeUtilsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link ComponentPrototypeUtils#getComponentFactory} method
     * throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testGetComponentFactory_Event_Null()
    {
        ComponentPrototypeUtils.getComponentFactory( null );
    }
}
