/*
 * BeginAuthenticationResponseMessageTest.java
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
 * Created on Mar 18, 2011 at 9:52:48 PM.
 */

package org.gamegineer.table.internal.net.transport.messages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.transport.messages.BeginAuthenticationResponseMessage}
 * class.
 */
public final class BeginAuthenticationResponseMessageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The begin authentication response message under test in the fixture. */
    private BeginAuthenticationResponseMessage message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * BeginAuthenticationResponseMessageTest} class.
     */
    public BeginAuthenticationResponseMessageTest()
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
        message_ = new BeginAuthenticationResponseMessage();
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
        message_ = null;
    }

    /**
     * Ensures the {@code setPlayerName} method throws an exception when passed
     * a {@code null} player name.
     */
    @Test( expected = NullPointerException.class )
    public void testSetPlayerName_PlayerName_Null()
    {
        message_.setPlayerName( null );
    }

    /**
     * Ensures the {@code setResponse} method throws an exception when passed an
     * illegal response that is empty.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetResponse_Response_Illegal_Empty()
    {
        message_.setResponse( new byte[ 0 ] );
    }

    /**
     * Ensures the {@code setResponse} method throws an exception when passed a
     * {@code null} response.
     */
    @Test( expected = NullPointerException.class )
    public void testSetResponse_Response_Null()
    {
        message_.setResponse( null );
    }
}
