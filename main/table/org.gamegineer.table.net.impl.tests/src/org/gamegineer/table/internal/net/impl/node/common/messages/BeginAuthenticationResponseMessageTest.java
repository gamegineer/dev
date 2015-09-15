/*
 * BeginAuthenticationResponseMessageTest.java
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
 * Created on Mar 18, 2011 at 9:52:48 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.messages;

import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link BeginAuthenticationResponseMessage} class.
 */
@NonNullByDefault( { DefaultLocation.PARAMETER, DefaultLocation.RETURN_TYPE, DefaultLocation.TYPE_BOUND, DefaultLocation.TYPE_ARGUMENT } )
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
     * Initializes a new instance of the
     * {@code BeginAuthenticationResponseMessageTest} class.
     */
    public BeginAuthenticationResponseMessageTest()
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
        message_ = new BeginAuthenticationResponseMessage();
    }

    /**
     * Ensures the {@link BeginAuthenticationResponseMessage#setResponse} method
     * throws an exception when passed an illegal response that is empty.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetResponse_Response_Illegal_Empty()
    {
        message_.setResponse( new byte[ 0 ] );
    }
}
