/*
 * HelloRequestMessageTest.java
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
 * Created on Mar 12, 2011 at 9:11:35 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.messages;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link HelloRequestMessage} class.
 */
public final class HelloRequestMessageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The hello request message under test in the fixture. */
    private Optional<HelloRequestMessage> message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloRequestMessageTest} class.
     */
    public HelloRequestMessageTest()
    {
        message_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the hello request message under test in the fixture.
     * 
     * @return The hello request message under test in the fixture.
     */
    private HelloRequestMessage getMessage()
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
        message_ = Optional.of( new HelloRequestMessage() );
    }

    /**
     * Ensures the {@link HelloRequestMessage#setSupportedProtocolVersion}
     * method throws an exception when passed a negative version.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetSupportedProtocolVersion_Version_Illegal_Negative()
    {
        getMessage().setSupportedProtocolVersion( -1 );
    }
}
