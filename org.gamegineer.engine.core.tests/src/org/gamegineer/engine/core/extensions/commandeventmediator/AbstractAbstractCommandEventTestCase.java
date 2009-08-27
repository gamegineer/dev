/*
 * AbstractAbstractCommandEventTestCase.java
 * Copyright 2008 Gamegineer.org
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
 * Created on Jun 11, 2008 at 11:20:41 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.CommandEvent}
 * class.
 */
public abstract class AbstractAbstractCommandEventTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command event under test in the fixture. */
    private CommandEvent m_event;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractAbstractCommandEventTestCase} class.
     */
    protected AbstractAbstractCommandEventTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the command event to be tested.
     * 
     * @return The command event to be tested; never {@code null}.
     */
    /* @NonNull */
    protected abstract CommandEvent createCommandEvent();

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
        m_event = createCommandEvent();
        assertNotNull( m_event );
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
        m_event = null;
    }

    /**
     * Ensures the {@code getSource} method returns the same object as
     * {@code getEngineContext}.
     */
    @Test
    public void testGetSource_ReturnValue_SameEngineContext()
    {
        assertSame( m_event.getEngineContext(), m_event.getSource() );
    }
}
