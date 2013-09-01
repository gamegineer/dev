/*
 * AbstractTableModelListenerTestCase.java
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
 * Created on Dec 28, 2009 at 9:12:03 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link ITableModelListener} interface.
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
     * Initializes a new instance of the
     * {@code AbstractTableModelListenerTestCase} class.
     */
    protected AbstractTableModelListenerTestCase()
    {
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
     * Ensures the {@link ITableModelListener#tableChanged} method throws an
     * exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testTableChanged_Event_Null()
    {
        listener_.tableChanged( null );
    }

    /**
     * Ensures the {@link ITableModelListener#tableModelDirtyFlagChanged} method
     * throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testTableModelDirtyFlagChanged_Event_Null()
    {
        listener_.tableModelDirtyFlagChanged( null );
    }

    /**
     * Ensures the {@link ITableModelListener#tableModelFileChanged} method
     * throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testTableModelFileChanged_Event_Null()
    {
        listener_.tableModelFileChanged( null );
    }

    /**
     * Ensures the {@link ITableModelListener#tableModelFocusChanged} method
     * throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testTableModelFocusChanged_Event_Null()
    {
        listener_.tableModelFocusChanged( null );
    }

    /**
     * Ensures the {@link ITableModelListener#tableModelHoverChanged} method
     * throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testTableModelHoverChanged_Event_Null()
    {
        listener_.tableModelHoverChanged( null );
    }

    /**
     * Ensures the {@link ITableModelListener#tableModelOriginOffsetChanged}
     * method throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testTableModelOriginOffsetChanged_Event_Null()
    {
        listener_.tableModelOriginOffsetChanged( null );
    }
}
