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

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link BeginAuthenticationResponseMessage} class.
 */
public final class BeginAuthenticationResponseMessageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The begin authentication response message under test in the fixture. */
    private Optional<BeginAuthenticationResponseMessage> message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the
     * {@code BeginAuthenticationResponseMessageTest} class.
     */
    public BeginAuthenticationResponseMessageTest()
    {
        message_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the begin authentication response message under test in the fixture.
     * 
     * @return The begin authentication response message under test in the
     *         fixture.
     */
    private BeginAuthenticationResponseMessage getMessage()
    {
        return message_.get();
    }

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
        message_ = Optional.of( new BeginAuthenticationResponseMessage() );
    }

    /**
     * Ensures the {@link BeginAuthenticationResponseMessage#setResponse} method
     * throws an exception when passed an illegal response that is empty.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetResponse_Response_Illegal_Empty()
    {
        getMessage().setResponse( new byte[ 0 ] );
    }
}
