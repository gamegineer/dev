/*
 * AbstractEventHandlerTestCase.java
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
 * Created on Jan 8, 2011 at 10:37:08 PM.
 */

package org.gamegineer.table.internal.net.connection;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.connection.IEventHandler} interface.
 * 
 * @param <H>
 *        The type of the transport handle.
 * @param <E>
 *        The type of the event.
 * @param <T>
 *        The type of the event handler.
 */
public abstract class AbstractEventHandlerTestCase<H, E, T extends IEventHandler<H, E>>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The event handler under test in the fixture. */
    private T eventHandler_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractEventHandlerTestCase}
     * class.
     */
    protected AbstractEventHandlerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the event handler to be tested.
     * 
     * @return The event handler to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createEventHandler()
        throws Exception;

    /**
     * Creates a mock event for the event handler under test.
     * 
     * @return A mock event for the event handler under test; never {@code null}
     *         .
     */
    /* @NonNull */
    protected abstract E createMockEvent();

    /**
     * Creates a mock transport handle for the event handler under test.
     * 
     * @return A mock transport handle for the event handler under test; never
     *         {@code null}.
     */
    /* @NonNull */
    protected abstract H createMockTransportHandle();

    /**
     * Gets the event handler under test in the fixture.
     * 
     * @return The event handler under test in the fixture; never {@code null}.
     */
    /* @NonNull */
    protected final T getEventHandler()
    {
        assertNotNull( eventHandler_ );
        return eventHandler_;
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
        eventHandler_ = createEventHandler();
        assertNotNull( eventHandler_ );
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
        eventHandler_ = null;
    }

    /**
     * Ensures the {@code handleEvent} method throws an exception when passed a
     * {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testHandleEvent_Event_Null()
    {
        eventHandler_.handleEvent( null );
    }
}
