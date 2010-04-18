/*
 * AbstractTableModelListenerTestCase.java
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
 * Created on Dec 28, 2009 at 9:12:03 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.ui.model.ITableModelListener} interface.
 */
public abstract class AbstractTableModelListenerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table model listener under test in the fixture. */
    private ITableModelListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractTableModelListenerTestCase} class.
     */
    protected AbstractTableModelListenerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table model listener to be tested.
     * 
     * @return The table model listener to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITableModelListener createTableModelListener()
        throws Exception;

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
        listener_ = createTableModelListener();
        assertNotNull( listener_ );
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
        listener_ = null;
    }

    /**
     * Ensures the {@code cardPileFocusChanged} method throws an exception when
     * passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testCardPileFocusChanged_Event_Null()
    {
        listener_.cardPileFocusChanged( null );
    }

    /**
     * Ensures the {@code tableModelStateChanged} method throws an exception
     * when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testTableModelStateChanged_Event_Null()
    {
        listener_.tableModelStateChanged( null );
    }

    /**
     * Ensures the {@code tableOriginOffsetChanged} method throws an exception
     * when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testTableOriginOffsetChanged_Event_Null()
    {
        listener_.tableOriginOffsetChanged( null );
    }
}
