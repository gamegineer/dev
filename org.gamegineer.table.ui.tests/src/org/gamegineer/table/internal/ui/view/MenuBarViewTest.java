/*
 * MenuBarViewTest.java
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
 * Created on Oct 8, 2009 at 11:54:43 PM.
 */

package org.gamegineer.table.internal.ui.view;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.table.internal.ui.model.MainModel;
import org.gamegineer.table.ui.TableAdvisor;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.view.MenuBarView} class.
 */
public final class MenuBarViewTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code MenuBarViewTest} class.
     */
    public MenuBarViewTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * model.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Model_Null()
    {
        new MenuBarView( null );
    }

    /**
     * Ensures the {@code getMenuBar} method does not return {@code null}.
     */
    @Test
    public void testGetMenuBar_ReturnValue_NonNull()
    {
        final MenuBarView view = new MenuBarView( new MainModel( new TableAdvisor() ) );

        assertNotNull( view.getMenuBar() );
    }
}
