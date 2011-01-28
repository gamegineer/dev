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

package org.gamegineer.table.internal.net;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableConfigurationBuilder;
import org.gamegineer.table.net.NetworkTableConstants;
import org.gamegineer.table.net.NetworkTableException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.INetworkInterface} interface.
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
     * @return The network interface factory to be tested; never {@code null}.
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
     * Creates a new network table configuration suitable for testing.
     * 
     * @return A new network table configuration suitable for testing; never
     *         {@code null}.
     */
    /* @NonNull */
    private static INetworkTableConfiguration createNetworkTableConfiguration()
    {
        final NetworkTableConfigurationBuilder builder = new NetworkTableConfigurationBuilder();
        builder.setLocalPlayerName( "playerName" ).setHostName( "localhost" ).setPort( NetworkTableConstants.DEFAULT_PORT ); //$NON-NLS-1$ //$NON-NLS-2$
        return builder.toNetworkTableConfiguration();
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

        networkInterface_.open( createNetworkTableConfiguration() );
    }

    /**
     * Ensures the {@code open} method throws an exception when passed a {@code
     * null} configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testOpen_Configuration_Null()
        throws Exception
    {
        networkInterface_.open( null );
    }

    /**
     * Ensures the {@code open} method throws an exception when attempting to
     * open the network interface more than once.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class, timeout = 5000 )
    public void testOpen_MultipleInvocations()
        throws Exception
    {
        final INetworkTableConfiguration configuration = createNetworkTableConfiguration();
        try
        {
            networkInterface_.open( configuration );
        }
        catch( final NetworkTableException e )
        {
            // ignore network errors
        }

        networkInterface_.open( configuration );
    }
}
