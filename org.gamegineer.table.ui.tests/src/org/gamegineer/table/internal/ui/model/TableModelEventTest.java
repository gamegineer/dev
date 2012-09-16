/*
 * TableModelEventTest.java
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
 * Created on Dec 28, 2009 at 9:02:07 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.TableModelEvent} class.
 */
public final class TableModelEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table model event under test in the fixture. */
    private TableModelEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableModelEventTest} class.
     */
    public TableModelEventTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Sets up the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Before
    public void setUp()
        throws Exception
    {
        event_ = new TableModelEvent( new TableModel() );
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * source.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_Source_Null()
    {
        new TableModelEvent( null );
    }

    /**
     * Ensures the {@link TableModelEvent#getSource} method returns the same
     * instance as the {@link TableModelEvent#getTableModel} method.
     */
    @Test
    public void testGetSource_ReturnValue_SameTableModel()
    {
        assertSame( event_.getTableModel(), event_.getSource() );
    }

    /**
     * Ensures the {@link TableModelEvent#getTableModel} method does not return
     * {@code null}.
     */
    @Test
    public void testGetTableModel_ReturnValue_NonNull()
    {
        assertNotNull( event_.getTableModel() );
    }
}
