/*
 * AbstractCommandListenerTestCase.java
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
 * Created on May 9, 2008 at 10:04:53 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandListener}
 * interface.
 */
public abstract class AbstractCommandListenerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command listener under test in the fixture. */
    private ICommandListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractCommandListenerTestCase}
     * class.
     */
    protected AbstractCommandListenerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the command listener to be tested.
     * 
     * @return The command listener to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ICommandListener createCommandListener()
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
        listener_ = createCommandListener();
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
     * Ensures the {@code commandExecuted} method throws an exception when
     * passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testCommandExecuted_Event_Null()
    {
        listener_.commandExecuted( null );
    }

    /**
     * Ensures the {@code commandExecuting} method throws an exception when
     * passed a {@code null} event.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testCommandExecuting_Event_Null()
        throws Exception
    {
        listener_.commandExecuting( null );
    }
}
