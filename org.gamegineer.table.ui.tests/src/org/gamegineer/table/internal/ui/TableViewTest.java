/*
 * TableViewTest.java
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
 * Created on Oct 7, 2009 at 12:00:24 AM.
 */

package org.gamegineer.table.internal.ui;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.internal.ui.TableView}
 * class.
 */
public final class TableViewTest
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableViewTest} class.
     */
    public TableViewTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * document.
     */
    @Test( expected = AssertionError.class )
    public void testConstructor_Document_Null()
    {
        new TableView( null );
    }

    /**
     * Ensures the {@code getDocument} method does not return {@code null}.
     */
    @Test
    public void testGetDocument_ReturnValue_NonNull()
    {
        final TableView view = new TableView( new TableDocument() );

        assertNotNull( view.getDocument() );
    }
}
