/*
 * AbstractAbstractStateEventTestCase.java
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
 * Created on Jun 12, 2008 at 10:07:37 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.StateEvent}
 * class.
 */
public abstract class AbstractAbstractStateEventTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The state event under test in the fixture. */
    private StateEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractAbstractStateEventTestCase} class.
     */
    protected AbstractAbstractStateEventTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the state event to be tested.
     * 
     * @return The state event to be tested; never {@code null}.
     */
    /* @NonNull */
    protected abstract StateEvent createStateEvent();

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
        event_ = createStateEvent();
        assertNotNull( event_ );
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
     * Ensures the {@code getSource} method returns the same object as {@code
     * getEngineContext}.
     */
    @Test
    public void testGetSource_ReturnValue_SameEngineContext()
    {
        assertSame( event_.getEngineContext(), event_.getSource() );
    }
}
