/*
 * ComponentEnableStateTest.java
 * Copyright 2008-2010 Gamegineer.org
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
 * Created on Nov 20, 2010 at 10:11:19 PM.
 */

package org.gamegineer.common.ui.dialog;

import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.common.ui.dialog.ComponentEnableState} class.
 */
public final class ComponentEnableStateTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentEnableStateTest} class.
     */
    public ComponentEnableStateTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code disable} method throws an exception when passed a
     * {@code null} component.
     */
    @Test( expected = NullPointerException.class )
    public void testDisable_Component_Null()
    {
        ComponentEnableState.disable( null );
    }
}
