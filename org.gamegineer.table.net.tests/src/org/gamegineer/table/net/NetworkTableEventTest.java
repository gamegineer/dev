/*
 * NetworkTableEventTest.java
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
 * Created on Nov 9, 2010 at 10:18:39 PM.
 */

package org.gamegineer.table.net;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.net.NetworkTableEvent}
 * class.
 */
public final class NetworkTableEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The network table event under test in the fixture. */
    private NetworkTableEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code NetworkTableEventTest} class.
     */
    public NetworkTableEventTest()
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
        event_ = new NetworkTableEvent( EasyMock.createMock( INetworkTable.class ) );
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
     * Ensures the constructor throws an exception when passed a {@code null}
     * network table.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_NetworkTable_Null()
    {
        new NetworkTableEvent( null );
    }

    /**
     * Ensures the {@code getNetworkTable} method does not return {@code null}.
     */
    @Test
    public void testGetNetworkTable_ReturnValue_NonNull()
    {
        assertNotNull( event_.getNetworkTable() );
    }

    /**
     * Ensures the {@code getSource} method returns the same instance as the
     * {@code getNetworkTable} method.
     */
    @Test
    public void testGetSource_ReturnValue_SameNetworkTable()
    {
        assertSame( event_.getNetworkTable(), event_.getSource() );
    }
}
