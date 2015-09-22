/*
 * TableNetworkEventTest.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

import static org.junit.Assert.assertSame;
import java.util.Optional;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link TableNetworkEvent} class.
 */
public final class TableNetworkEventTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network event under test in the fixture. */
    private Optional<TableNetworkEvent> event_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code TableNetworkEventTest} class.
     */
    public TableNetworkEventTest()
    {
        event_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the table network event under test in the fixture.
     * 
     * @return The table network event under test in the fixture; never
     *         {@code null}.
     */
    private TableNetworkEvent getEvent()
    {
        return event_.get();
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
        event_ = Optional.of( new TableNetworkEvent( EasyMock.createMock( ITableNetwork.class ) ) );
    }

    /**
     * Ensures the {@link TableNetworkEvent#getSource} method returns the same
     * instance as the {@code getTableNetwork} method.
     */
    @Test
    public void testGetSource_ReturnValue_SameTableNetwork()
    {
        final TableNetworkEvent event = getEvent();

        assertSame( event.getTableNetwork(), event.getSource() );
    }
}
