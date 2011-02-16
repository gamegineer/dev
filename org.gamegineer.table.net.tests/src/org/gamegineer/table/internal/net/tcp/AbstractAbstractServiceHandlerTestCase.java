/*
 * AbstractAbstractServiceHandlerTestCase.java
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
 * Created on Jan 13, 2011 at 11:26:20 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import static org.junit.Assert.assertNotNull;
import java.nio.channels.SocketChannel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the basic aspects of classes that extend the
 * {@link org.gamegineer.table.internal.net.tcp.AbstractServiceHandler} class.
 */
public abstract class AbstractAbstractServiceHandlerTestCase
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The network interface for use in the fixture. */
    private AbstractNetworkInterface networkInterface_;

    /** The service handler under test in the fixture. */
    private AbstractServiceHandler serviceHandler_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * AbstractAbstractServiceHandlerTestCase} class.
     */
    protected AbstractAbstractServiceHandlerTestCase()
    {
        super();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Creates the service handler to be tested.
     * 
     * @param networkInterface
     *        The network interface for use in the fixture; must not be {@code
     *        null}.
     * 
     * @return The service handler to be tested; never {@code null}.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     * @throws java.lang.NullPointerException
     *         If {@code networkInterface} is {@code null}.
     */
    /* @NonNull */
    protected abstract AbstractServiceHandler createServiceHandler(
        /* @NonNull */
        AbstractNetworkInterface networkInterface )
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
        networkInterface_ = new FakeNetworkInterface();
        networkInterface_.open( TestUtils.createNetworkTableConfiguration() );
        serviceHandler_ = createServiceHandler( networkInterface_ );
        assertNotNull( serviceHandler_ );
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
        serviceHandler_.close();
        serviceHandler_ = null;
        networkInterface_.close();
        networkInterface_ = null;
    }

    /**
     * Ensures the {@code open} method throws an exception if the service
     * handler has been closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testOpen_AfterClose()
        throws Exception
    {
        serviceHandler_.close();

        serviceHandler_.open( new FakeSocketChannel() );
    }

    /**
     * Ensures the {@code open} method throws an exception when attempting to
     * open the service handler more than once.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testOpen_MultipleInvocations()
        throws Exception
    {
        final SocketChannel channel = new FakeSocketChannel();
        serviceHandler_.open( channel );

        serviceHandler_.open( channel );
    }
}
