/*
 * AbstractNetworkTableListenerTestCase.java
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
 * Created on Nov 9, 2010 at 10:13:09 PM.
 */

package org.gamegineer.table.net;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.net.INetworkTableListener} interface.
 */
public abstract class AbstractNetworkTableListenerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The network table listener under test in the fixture. */
    private INetworkTableListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractNetworkTableListenerTestCase} class.
     */
    protected AbstractNetworkTableListenerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the network table listener to be tested.
     * 
     * @return The network table listener to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract INetworkTableListener createNetworkTableListener()
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
        listener_ = createNetworkTableListener();
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
     * Ensures the {@code connectionStateChanged} method throws an exception
     * when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testNetworkConnectionStateChanged_Event_Null()
    {
        listener_.networkConnectionStateChanged( null );
    }
}
