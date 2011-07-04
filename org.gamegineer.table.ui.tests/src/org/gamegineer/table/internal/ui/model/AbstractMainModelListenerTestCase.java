/*
 * AbstractMainModelListenerTestCase.java
 * Copyright 2008-2011 Gamegineer.org
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
 * Created on Apr 13, 2010 at 10:01:54 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.ui.model.IMainModelListener} interface.
 */
public abstract class AbstractMainModelListenerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The main model listener under test in the fixture. */
    private IMainModelListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractMainModelListenerTestCase} class.
     */
    protected AbstractMainModelListenerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the main model listener to be tested.
     * 
     * @return The main model listener to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IMainModelListener createMainModelListener()
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
        listener_ = createMainModelListener();
        assertNotNull( listener_ );
    }

    /**
     * Ensures the {@code mainModelStateChanged} method throws an exception when
     * passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testMainModelStateChanged_Event_Null()
    {
        listener_.mainModelStateChanged( null );
    }

    /**
     * Ensures the {@code tableClosed} method throws an exception when passed a
     * {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testTableClosed_Event_Null()
    {
        listener_.tableClosed( null );
    }

    /**
     * Ensures the {@code tableOpened} method throws an exception when passed a
     * {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testTableOpened_Event_Null()
    {
        listener_.tableOpened( null );
    }
}
