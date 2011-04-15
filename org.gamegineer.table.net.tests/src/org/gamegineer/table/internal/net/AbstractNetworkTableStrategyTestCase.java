/*
 * AbstractNetworkTableStrategyTestCase.java
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
 * Created on Apr 10, 2011 at 6:14:52 PM.
 */

package org.gamegineer.table.internal.net;

import static org.junit.Assert.assertNotNull;
import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.INetworkTableStrategy} interface.
 */
public abstract class AbstractNetworkTableStrategyTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The network table strategy under test in the fixture. */
    private INetworkTableStrategy networkTableStrategy_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractNetworkTableStrategyTestCase} class.
     */
    protected AbstractNetworkTableStrategyTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the network table strategy to be tested.
     * 
     * @return The network table strategy to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract INetworkTableStrategy createNetworkTableStrategy()
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
        networkTableStrategy_ = createNetworkTableStrategy();
        assertNotNull( networkTableStrategy_ );
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
        networkTableStrategy_ = null;
    }

    /**
     * Ensures the {@code connect} method throws an exception when passed a
     * {@code null} network table configuration.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NullPointerException.class )
    public void testConnect_Configuration_Null()
        throws Exception
    {
        networkTableStrategy_.connect( null );
    }

    /**
     * Ensures the {@code connect} method throws an exception when the network
     * is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = NetworkTableException.class )
    public void testConnect_Connected_ThrowsException()
        throws Exception
    {
        final INetworkTableConfiguration networkTableConfiguration = NetworkTableConfigurations.createDefaultNetworkTableConfiguration();
        networkTableStrategy_.connect( networkTableConfiguration );

        networkTableStrategy_.connect( networkTableConfiguration );
    }

    // TODO: implement the following commented out tests when we replace NetworkTable
    // with INetworkTableStrategyContext

    /**
     * Ensures the {@code connect} method fires a network connected event when
     * the network is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    //@Test
    //public void testConnect_Disconnected_FiresNetworkConnectedEvent()
    //    throws Exception
    //{
    //}

    /**
     * Ensures the {@code disconnect} method fires a network disconnected event
     * when the network is connected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    //@Test
    //public void testDisconnect_Connected_FiresNetworkDisconnectedEvent()
    //    throws Exception
    //{
    //}

    /**
     * Ensures the {@code disconnect} method does not fire a network
     * disconnected event when the network is disconnected.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    //@Test
    //public void testDisconnect_Disconnected_DoesNotFireNetworkConnectionStateChangedEvent()
    //    throws Exception
    //{
    //}
}
