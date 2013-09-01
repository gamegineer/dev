/*
 * JComponentsTest.java
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
 * Created on Oct 10, 2010 at 9:06:33 PM.
 */

package org.gamegineer.table.internal.ui.util.swing;

import org.junit.Test;

/**
 * A fixture for testing the {@link JComponents} class.
 */
public final class JComponentsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code JComponentsTest} class.
     */
    public JComponentsTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@link JComponents#freezeHeight} method throws an exception
     * when passed a {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testFreezeHeight_Component_Null()
    {
        JComponents.freezeHeight( null );
    }
}
