/*
 * AbstractNetworkInterfaceTestCase.java
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
 * Created on Jan 18, 2011 at 8:50:02 PM.
 */

package org.gamegineer.table.internal.net.transport;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.table.net.NetworkTableConstants;
import org.gamegineer.table.net.NetworkTableException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.transport.INetworkInterface}
 * interface.
 */
public abstract class AbstractNetworkInterfaceTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The network interface under test in the fixture. */
    private INetworkInterface networkInterface_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractNetworkInterfaceTestCase} class.
     */
    protected AbstractNetworkInterfaceTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the network interface to be tested.
     * 
     * @return The network interface to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract INetworkInterface createNetworkInterface()
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
        networkInterface_ = createNetworkInterface();
        assertNotNull( networkInterface_ );
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
        networkInterface_.close();
        networkInterface_ = null;
    }

    /**
     * Ensures the {@code open} method throws an exception if the network
     * interface has been closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testOpen_AfterClose()
        throws Exception
    {
        networkInterface_.close();

        networkInterface_.open( "localhost", NetworkTableConstants.DEFAULT_PORT ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@code open} method throws an exception when passed a {@code
     * null} host name.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testOpen_HostName_Null()
        throws Exception
    {
        networkInterface_.open( null, NetworkTableConstants.DEFAULT_PORT );
    }

    /**
     * Ensures the {@code open} method throws an exception when attempting to
     * open the network interface more than once.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testOpen_MultipleInvocations()
        throws Exception
    {
        try
        {
            networkInterface_.open( "localhost", NetworkTableConstants.DEFAULT_PORT ); //$NON-NLS-1$
        }
        catch( final NetworkTableException e )
        {
            // ignore network errors
        }

        networkInterface_.open( "localhost", NetworkTableConstants.DEFAULT_PORT ); //$NON-NLS-1$
    }
}
