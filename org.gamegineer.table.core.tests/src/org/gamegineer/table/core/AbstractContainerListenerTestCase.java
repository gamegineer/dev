/*
 * AbstractContainerListenerTestCase.java
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
 * Created on Mar 29, 2012 at 8:48:26 PM.
 */

package org.gamegineer.table.core;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.core.IContainerListener} interface.
 */
public abstract class AbstractContainerListenerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The container listener under test in the fixture. */
    private IContainerListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractContainerListenerTestCase} class.
     */
    protected AbstractContainerListenerTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the container listener to be tested.
     * 
     * @return The container listener to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract IContainerListener createContainerListener()
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
        listener_ = createContainerListener();
        assertNotNull( listener_ );
    }

    /**
     * Ensures the {@code componentAdded} method throws an exception when passed
     * a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testComponentAdded_Event_Null()
    {
        listener_.componentAdded( null );
    }

    /**
     * Ensures the {@code componentRemoved} method throws an exception when
     * passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testComponentRemoved_Event_Null()
    {
        listener_.componentRemoved( null );
    }

    /**
     * Ensures the {@code containerLayoutChanged} method throws an exception
     * when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testContainerLayoutChanged_Event_Null()
    {
        listener_.containerLayoutChanged( null );
    }
}
