/*
 * AbstractTableListenerTestCase.java
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
 * Created on Oct 16, 2009 at 10:11:38 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.ITableListener} interface.
 */
public abstract class AbstractTableListenerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table listener under test in the fixture. */
    private ITableListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractTableListenerTestCase}
     * class.
     */
    protected AbstractTableListenerTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table listener to be tested.
     * 
     * @return The table listener to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITableListener createTableListener()
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
        listener_ = createTableListener();
        assertNotNull( listener_ );
    }

    /**
     * Ensures the {@code cardPileAdded} method throws an exception when passed
     * a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testCardPileAdded_Event_Null()
    {
        listener_.cardPileAdded( null );
    }

    /**
     * Ensures the {@code cardPileRemoved} method throws an exception when
     * passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testCardPileRemoved_Event_Null()
    {
        listener_.cardPileRemoved( null );
    }
}
