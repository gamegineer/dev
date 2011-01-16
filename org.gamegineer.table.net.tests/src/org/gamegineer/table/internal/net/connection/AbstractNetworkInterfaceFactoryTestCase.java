/*
 * AbstractNetworkInterfaceFactoryTestCase.java
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
 * Created on Jan 15, 2011 at 11:52:40 PM.
 */

package org.gamegineer.table.internal.net.connection;

import static org.junit.Assert.assertNotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.connection.INetworkInterfaceFactory}
 * interface.
 */
public abstract class AbstractNetworkInterfaceFactoryTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The network interface factory under test in the fixture. */
    private INetworkInterfaceFactory networkInterfaceFactory_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractNetworkInterfaceFactoryTestCase} class.
     */
    protected AbstractNetworkInterfaceFactoryTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the connection factory to be tested.
     * 
     * @return The connection factory to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract INetworkInterfaceFactory createConnectionFactory()
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
        networkInterfaceFactory_ = createConnectionFactory();
        assertNotNull( networkInterfaceFactory_ );
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
        networkInterfaceFactory_ = null;
    }

    /**
     * Ensures the {@code createNetworkInterface} method throws an exception
     * when passed a {@code null} network table.
     */
    @Test( expected = NullPointerException.class )
    public void testCreateNetworkInterface_NetworkTable_Null()
    {
        networkInterfaceFactory_.createNetworkInterface( null );
    }
}
