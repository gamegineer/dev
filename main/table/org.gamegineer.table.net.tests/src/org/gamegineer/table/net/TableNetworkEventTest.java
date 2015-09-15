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

import static org.gamegineer.common.core.runtime.NullAnalysis.nonNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.easymock.EasyMock;
import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link TableNetworkEvent} class.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
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
        event_ = new TableNetworkEvent( nonNull( EasyMock.createMock( ITableNetwork.class ) ) );
        assertNotNull( event_ );
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
}
