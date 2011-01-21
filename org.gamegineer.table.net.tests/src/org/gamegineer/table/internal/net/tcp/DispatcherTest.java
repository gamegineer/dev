/*
 * DispatcherTest.java
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
 * Created on Jan 13, 2011 at 10:58:09 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.tcp.Dispatcher} class.
 */
public final class DispatcherTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The dispatcher under test in the fixture. */
    private Dispatcher dispatcher_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code DispatcherTest} class.
     */
    public DispatcherTest()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

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
        dispatcher_ = new Dispatcher();
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
        dispatcher_.close();
        dispatcher_ = null;
    }

    /**
     * Ensures the {@code open} method throws an exception if the dispatcher has
     * been closed.
     */
    @Test( expected = IllegalStateException.class )
    public void testOpen_AfterClose()
    {
        dispatcher_.close();

        dispatcher_.open();
    }

    /**
     * Ensures the {@code open} method throws an exception when attempting to
     * open the dispatcher more than once.
     */
    @Test( expected = IllegalStateException.class )
    public void testOpen_MultipleInvocations()
    {
        dispatcher_.open();

        dispatcher_.open();
    }

    /**
     * Ensures the {@code registerEventHandler} method throws an exception if
     * the dispatcher has been closed.
     */
    @Test( expected = IllegalStateException.class )
    public void testRegisterEventHandler_AfterClose()
    {
        dispatcher_.close();

        dispatcher_.registerEventHandler( Mocks.createMockEventHandler() );
    }

    /**
     * Ensures the {@code unregisterEventHandler} method throws an exception if
     * the dispatcher has been closed.
     */
    @Test( expected = IllegalStateException.class )
    public void testUnegisterEventHandler_AfterClose()
    {
        dispatcher_.close();

        dispatcher_.unregisterEventHandler( Mocks.createMockEventHandler() );
    }
}
