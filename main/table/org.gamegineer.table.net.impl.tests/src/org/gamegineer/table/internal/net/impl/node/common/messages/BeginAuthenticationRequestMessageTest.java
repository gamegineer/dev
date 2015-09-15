/*
 * BeginAuthenticationRequestMessageTest.java
 * Copyright 2008-2015 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl.node.common.messages;

import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link BeginAuthenticationRequestMessage} class.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
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
     * Initializes a new instance of the
     * {@code BeginAuthenticationRequestMessageTest} class.
     */
    public BeginAuthenticationRequestMessageTest()
    {
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
     * Ensures the {@link BeginAuthenticationRequestMessage#setChallenge} method
     * throws an exception when passed an illegal challenge that is empty.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetChallenge_Challenge_Illegal_Empty()
    {
        message_.setChallenge( new byte[ 0 ] );
    }

    /**
     * Ensures the {@link BeginAuthenticationRequestMessage#setSalt} method
     * throws an exception when passed an illegal salt that is empty.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetSalt_Salt_Illegal_Empty()
    {
        message_.setSalt( new byte[ 0 ] );
    }
}
