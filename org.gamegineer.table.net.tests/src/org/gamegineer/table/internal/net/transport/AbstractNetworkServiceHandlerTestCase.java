/*
 * AbstractNetworkServiceHandlerTestCase.java
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
 * Created on Apr 1, 2011 at 9:27:25 PM.
 */

package org.gamegineer.table.internal.net.transport;

import static org.junit.Assert.assertNotNull;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that implement the
 * {@link org.gamegineer.table.internal.net.transport.INetworkServiceHandler}
 * interface.
 */
public abstract class AbstractNetworkServiceHandlerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The network service handler under test in the fixture. */
    private INetworkServiceHandler networkServiceHandler_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractNetworkServiceHandlerTestCase} class.
     */
    protected AbstractNetworkServiceHandlerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the network service handler to be tested.
     * 
     * @return The network service handler to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    /* @NonNull */
    protected abstract INetworkServiceHandler createNetworkServiceHandler()
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
        networkServiceHandler_ = createNetworkServiceHandler();
        assertNotNull( networkServiceHandler_ );
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
        networkServiceHandler_ = null;
    }

    /**
     * Ensures the {@code messageReceived} method throws an exception when
     * passed a {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testMessageReceived_Context_Null()
    {
        networkServiceHandler_.messageReceived( null, new MessageEnvelope( 0, 0, new byte[ 0 ] ) );
    }

    /**
     * Ensures the {@code messageReceived} method throws an exception when
     * passed a {@code null} message envelope.
     */
    @Test( expected = NullPointerException.class )
    public void testMessageReceived_MessageEnvelope_Null()
    {
        networkServiceHandler_.messageReceived( EasyMock.createMock( INetworkServiceContext.class ), null );
    }

    /**
     * Ensures the {@code peerStopped} method throws an exception when passed a
     * {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testPeerStopped_Context_Null()
    {
        networkServiceHandler_.peerStopped( null );
    }

    /**
     * Ensures the {@code started} method throws an exception when passed a
     * {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testStarted_Context_Null()
    {
        networkServiceHandler_.started( null );
    }

    /**
     * Ensures the {@code stopped} method throws an exception when passed a
     * {@code null} context.
     */
    @Test( expected = NullPointerException.class )
    public void testStopped_Context_Null()
    {
        networkServiceHandler_.stopped( null );
    }
}
