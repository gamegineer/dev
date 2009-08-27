/*
 * AbstractStateEventTestCase.java
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
 * Created on Jun 2, 2008 at 8:52:03 PM.
 */

package org.gamegineer.engine.core.extensions.stateeventmediator;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.engine.core.AttributeName;
import org.gamegineer.engine.core.IState.Scope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.engine.core.extensions.stateeventmediator.IStateEvent}
 * interface.
 * 
 * @param <T>
 *        The type of the state event.
 */
public abstract class AbstractStateEventTestCase<T extends IStateEvent>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The state event under test in the fixture. */
    private T m_event;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractStateEventTestCase}
     * class.
     */
    protected AbstractStateEventTestCase()
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
    protected abstract T createStateEvent();

    /**
     * Gets the state event under test in the fixture.
     * 
     * @return The state event under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final T getStateEvent()
    {
        assertNotNull( m_event );
        return m_event;
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
        m_event = createStateEvent();
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
     * Ensures the {@code getEngineContext} method does not return {@code null}.
     */
    @Test
    public void testGetEngineContext_ReturnValue_NonNull()
    {
        assertNotNull( m_event.getEngineContext() );
    }

    /**
     * Ensures the {@code getEngineContext} method returns a context whose state
     * is immutable.
     */
    @Test( expected = UnsupportedOperationException.class )
    public void testGetEngineContext_State_Immutable()
    {
        m_event.getEngineContext().getState().addAttribute( new AttributeName( Scope.APPLICATION, "my_attribute_name" ), "value" ); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
