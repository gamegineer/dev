/*
 * BeginAuthenticationRequestMessageTest.java
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
 * Created on Mar 18, 2011 at 9:38:44 PM.
 */

package org.gamegineer.table.internal.net.messages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.messages.BeginAuthenticationRequestMessage}
 * class.
 */
public final class BeginAuthenticationRequestMessageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The begin authentication request message under test in the fixture. */
    private BeginAuthenticationRequestMessage message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code
     * BeginAuthenticationRequestMessageTest} class.
     */
    public BeginAuthenticationRequestMessageTest()
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
        message_ = new BeginAuthenticationRequestMessage();
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
     * Ensures the {@code setChallenge} method throws an exception when passed
     * an illegal challenge that is empty.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetChallenge_Challenge_Illegal_Empty()
    {
        message_.setChallenge( new byte[ 0 ] );
    }

    /**
     * Ensures the {@code setChallenge} method throws an exception when passed a
     * {@code null} challenge.
     */
    @Test( expected = NullPointerException.class )
    public void testSetChallenge_Challenge_Null()
    {
        message_.setChallenge( null );
    }

    /**
     * Ensures the {@code setIterationCount} method throws an exception when
     * passed an illegal iteration count that is negative.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetIterationCount_IterationCount_Illegal_Negative()
    {
        message_.setIterationCount( -1 );
    }

    /**
     * Ensures the {@code setSalt} method throws an exception when passed an
     * illegal salt that is empty.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetSalt_Salt_Illegal_Empty()
    {
        message_.setSalt( new byte[ 0 ] );
    }

    /**
     * Ensures the {@code setSalt} method throws an exception when passed a
     * {@code null} salt.
     */
    @Test( expected = NullPointerException.class )
    public void testSetSalt_Salt_Null()
    {
        message_.setSalt( null );
    }
}
