/*
 * TableNetworkEventTest.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link org.gamegineer.table.net.TableNetworkEvent}
 * class.
 */
public final class TableNetworkEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network event under test in the fixture. */
    private TableNetworkEvent event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkEventTest} class.
     */
    public TableNetworkEventTest()
    {
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
        event_ = new TableNetworkEvent( EasyMock.createMock( ITableNetwork.class ) );
        assertNotNull( event_ );
    }

    /**
     * Ensures the {@link TableNetworkEvent#TableNetworkEvent} constructor
     * throws an exception when passed a {@code null} table network.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testConstructor_TableNetwork_Null()
    {
        new TableNetworkEvent( null );
    }

    /**
     * Ensures the {@link TableNetworkEvent#getSource} method returns the same
     * instance as the {@code getTableNetwork} method.
     */
    @Test
    public void testGetSource_ReturnValue_SameTableNetwork()
    {
        assertSame( event_.getTableNetwork(), event_.getSource() );
    }

    /**
     * Ensures the {@link TableNetworkEvent#getTableNetwork} method does not
     * return {@code null}.
     */
    @Test
    public void testGetTableNetwork_ReturnValue_NonNull()
    {
        assertNotNull( event_.getTableNetwork() );
    }
}
