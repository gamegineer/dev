/*
 * ActionsTest.java
 * Copyright 2008-2009 Gamegineer.org
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
 * Created on Oct 9, 2009 at 11:21:01 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.Actions} class.
 */
public final class ActionsTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ActionsTest} class.
     */
    public ActionsTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the {@code getExitAction} method does not return {@code null}.
     */
    @Test
    public void testGetExitAction_ReturnValue_NonNull()
    {
        assertNotNull( Actions.getExitAction() );
    }

    /**
     * Ensures the {@code getOpenAboutDialogAction} method does not return
     * {@code null}.
     */
    @Test
    public void testGetOpenAboutDialogAction_ReturnValue_NonNull()
    {
        assertNotNull( Actions.getOpenAboutDialogAction() );
    }
}
