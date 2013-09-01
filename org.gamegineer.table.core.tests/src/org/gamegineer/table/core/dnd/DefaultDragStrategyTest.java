/*
 * DefaultDragStrategyTest.java
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
 * Created on Mar 8, 2013 at 10:13:04 PM.
 */

package org.gamegineer.table.core.dnd;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.core.dnd.DefaultDragStrategy} class.
 */
public final class DefaultDragStrategyTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DefaultDragStrategyTest} class.
     */
    public DefaultDragStrategyTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link DefaultDragStrategy#DefaultDragStrategy} constructor
     * throws an exception when passed a {@code null} drag component.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_DragComponent_Null()
    {
        new DefaultDragStrategy( null );
    }
}
