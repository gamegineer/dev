/*
 * AbstractDispatcherTestCase.java
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
 * Created on Jan 13, 2011 at 10:51:29 PM.
 */

package org.gamegineer.table.internal.net.connection;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.connection.IDispatcher} interface.
 * 
 * @param <H>
 *        The type of the transport handle.
 * @param <E>
 *        The type of the event.
 * @param <T>
 *        The type of the dispatcher.
 */
public abstract class AbstractDispatcherTestCase<H, E, T extends IDispatcher<H, E>>
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dispatcher under test in the fixture. */
    private T dispatcher_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AbstractDispatcherTestCase}
     * class.
     */
    protected AbstractDispatcherTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the dispatcher to be tested.
     * 
     * @return The dispatcher to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract T createDispatcher()
        throws Exception;

    /**
     * Creates a mock event handler for the dispatcher under test.
     * 
     * @return A mock event handler for the dispatcher under test; never {@code
     *         null}.
     */
    /* @NonNull */
    protected final IEventHandler<H, E> createMockEventHandler()
    {
        @SuppressWarnings( "unchecked" )
        final IEventHandler<H, E> eventHandler = EasyMock.createMock( IEventHandler.class );
        return eventHandler;
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
        dispatcher_ = createDispatcher();
        assertNotNull( dispatcher_ );
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
        dispatcher_ = null;
    }

    /**
     * Ensures the {@code registerEventHandler} method throws an exception if
     * the dispatcher has been closed.
     */
    @Test( expected = IllegalStateException.class )
    public void testRegisterEventHandler_AfterClose()
    {
        dispatcher_.close();

        dispatcher_.registerEventHandler( createMockEventHandler() );
    }

    /**
     * Ensures the {@code registerEventHandler} method throws an exception when
     * passed a {@code null} event handler.
     */
    @Test( expected = NullPointerException.class )
    public void testRegisterEventHandler_EventHandler_Null()
    {
        dispatcher_.registerEventHandler( null );
    }

    /**
     * Ensures the {@code unregisterEventHandler} method throws an exception if
     * the dispatcher has been closed.
     */
    @Test( expected = IllegalStateException.class )
    public void testUnegisterEventHandler_AfterClose()
    {
        dispatcher_.close();

        dispatcher_.unregisterEventHandler( createMockEventHandler() );
    }

    /**
     * Ensures the {@code unregisterEventHandler} method throws an exception
     * when passed a {@code null} event handler.
     */
    @Test( expected = NullPointerException.class )
    public void testUnregisterEventHandler_EventHandler_Null()
    {
        dispatcher_.unregisterEventHandler( null );
    }
}
