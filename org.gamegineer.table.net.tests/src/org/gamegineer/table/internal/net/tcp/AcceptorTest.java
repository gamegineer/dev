/*
 * AcceptorTest.java
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
 * Created on Jan 8, 2011 at 9:51:34 PM.
 */

package org.gamegineer.table.internal.net.tcp;

import org.gamegineer.table.net.INetworkTableConfiguration;
import org.gamegineer.table.net.NetworkTableException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.tcp.Acceptor} class.
 */
public final class AcceptorTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The acceptor under test in the fixture. */
    private Acceptor acceptor_;

    /** The dispatcher for use in the fixture. */
    private Dispatcher dispatcher_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code AcceptorTest} class.
     */
    public AcceptorTest()
    {
        super();
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
        dispatcher_ = new Dispatcher();
        dispatcher_.open();
        acceptor_ = new Acceptor( dispatcher_ );
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
        acceptor_.close();
        acceptor_ = null;
        dispatcher_.close();
        dispatcher_ = null;
    }

    /**
     * Ensures the {@code bind} method throws an exception if the acceptor has
     * been closed.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testBind_AfterClose()
        throws Exception
    {
        acceptor_.close();

        acceptor_.bind( TestUtils.createNetworkTableConfiguration() );
    }

    /**
     * Ensures the {@code bind} method throws an exception when attempting to
     * bind the channel more than once.
     * 
     * @throws java.lang.Exception
     *         If an error occurs.
     */
    @Test( expected = IllegalStateException.class )
    public void testBind_MultipleInvocations()
        throws Exception
    {
        final INetworkTableConfiguration configuration = TestUtils.createNetworkTableConfiguration();
        try
        {
            acceptor_.bind( configuration );
        }
        catch( final NetworkTableException e )
        {
            // ignore network errors
        }

        acceptor_.bind( configuration );
    }
}
