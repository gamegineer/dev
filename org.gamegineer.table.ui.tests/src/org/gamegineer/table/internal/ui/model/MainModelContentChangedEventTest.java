/*
 * MainModelContentChangedEventTest.java
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
 * Created on Apr 14, 2010 at 10:49:29 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.gamegineer.table.ui.ITableAdvisor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.ui.model.MainModelContentChangedEvent}
 * class.
 */
public final class MainModelContentChangedEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The main model content changed event under test in the fixture. */
    private MainModelContentChangedEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * MainModelContentChangedEventTest} class.
     */
    public MainModelContentChangedEventTest()
    {
        super();
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
        event_ = new MainModelContentChangedEvent( new MainModel( EasyMock.createMock( ITableAdvisor.class ) ), TableModel.createTableModel() );
    }

    /**
     * Tears down the test fixture.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @After
    public void tearDown()
        throws Exception
    {
        event_ = null;
    }

    /**
     * Ensures the constructor throws an exception when passed a {@code null}
     * table model.
     */
    @Test( expected = NullPointerException.class )
    public void testConstructor_TableModel_Null()
    {
        new MainModelContentChangedEvent( new MainModel( EasyMock.createMock( ITableAdvisor.class ) ), null );
    }

    /**
     * Ensures the {@code getTableModel} method does not return {@code null}.
     */
    @Test
    public void testGetTableModel_ReturnValue_NonNull()
    {
        assertNotNull( event_.getTableModel() );
    }
}
