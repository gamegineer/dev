/*
 * AbstractTableNetworkListenerTestCase.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.net.ITableNetworkListener} interface.
 */
public abstract class AbstractTableNetworkListenerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The table network listener under test in the fixture. */
    private ITableNetworkListener listener_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code AbstractTableNetworkListenerTestCase} class.
     */
    protected AbstractTableNetworkListenerTestCase()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the table network listener to be tested.
     * 
     * @return The table network listener to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract ITableNetworkListener createTableNetworkListener()
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
        listener_ = createTableNetworkListener();
        assertNotNull( listener_ );
    }

    /**
     * Ensures the {@link ITableNetworkListener#tableNetworkConnected} method
     * throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testTableNetworkConnected_Event_Null()
    {
        listener_.tableNetworkConnected( null );
    }

    /**
     * Ensures the {@link ITableNetworkListener#tableNetworkDisconnected} method
     * throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testTableNetworkDisconnected_Event_Null()
    {
        listener_.tableNetworkDisconnected( null );
    }

    /**
     * Ensures the {@link ITableNetworkListener#tableNetworkPlayersUpdated}
     * method throws an exception when passed a {@code null} event.
     */
    @Test( expected = NullPointerException.class )
    public void testTableNetworkPlayersUpdated_Event_Null()
    {
        listener_.tableNetworkPlayersUpdated( null );
    }
}
