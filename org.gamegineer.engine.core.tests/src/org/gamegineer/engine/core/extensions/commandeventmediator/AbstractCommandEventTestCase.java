/*
 * AbstractCommandEventTestCase.java
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
 * Created on Jun 2, 2008 at 10:59:08 PM.
 */

package org.gamegineer.engine.core.extensions.commandeventmediator;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IState.Scope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.commandeventmediator.ICommandEvent}
 * interface.
 * 
 * @param <T>
 *        The type of the command event.
 */
public abstract class AbstractCommandEventTestCase<T extends ICommandEvent>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The command event under test in the fixture. */
    private T event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractCommandEventTestCase}
     * class.
     */
    protected AbstractCommandEventTestCase()
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
    protected abstract T createCommandEvent();

    /**
     * Gets the command event under test in the fixture.
     * 
     * @return The command event under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final T getCommandEvent()
    {
        assertNotNull( event_ );
        return event_;
    }

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
        event_ = createCommandEvent();
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
     * Ensures the {@code getCommand} method does not return {@code null}.
     */
    @Test
    public void testGetCommand_ReturnValue_NonNull()
    {
        assertNotNull( event_.getCommand() );
    }

    /**
     * Ensures the {@code getEngineContext} method does not return {@code null}.
     */
    @Test
    public void testGetEngineContext_ReturnValue_NonNull()
    {
        assertNotNull( event_.getEngineContext() );
    }

    /**
     * Ensures the {@code getEngineContext} method returns a context whose state
     * is immutable.
     */
    @Test( expected = UnsupportedOperationException.class )
    public void testGetEngineContext_State_Immutable()
    {
        event_.getEngineContext().getState().addAttribute( new AttributeName( Scope.APPLICATION, "my_attribute_name" ), "value" ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
