/*
 * AbstractCardModelListenerTestCase.java
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
 * Created on Dec 25, 2009 at 11:00:07 PM.
 */

package org.gamegineer.table.internal.ui.model;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.ui.model.ICardModelListener} interface.
 */
public abstract class AbstractCardModelListenerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The card model listener under test in the fixture. */
    private ICardModelListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractCardModelListenerTestCase} class.
     */
    protected AbstractCardModelListenerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the card model listener to be tested.
     * 
     * @return The card model listener to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICardModelListener createCardModelListener()
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
        listener_ = createCardModelListener();
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
     * Ensures the {@code cardFocusGained} method throws an exception when
     * passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testCardFocusGained_Event_Null()
    {
        listener_.cardFocusGained( null );
    }

    /**
     * Ensures the {@code cardFocusLost} method throws an exception when passed
     * a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testCardFocusLost_Event_Null()
    {
        listener_.cardFocusLost( null );
    }
}
