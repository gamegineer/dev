/*
 * HelloRequestMessageTest.java
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
 * Created on Mar 12, 2011 at 9:11:35 PM.
 */

package org.gamegineer.table.internal.net.messages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.messages.HelloRequestMessage} class.
 */
public final class HelloRequestMessageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The hello request message under test in the fixture. */
    private HelloRequestMessage message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloRequestMessageTest} class.
     */
    public HelloRequestMessageTest()
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
        message_ = new HelloRequestMessage();
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
     * Ensures the {@code setSupportedProtocolVersion} method throws an
     * exception when passed a negative version.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetSupportedProtocolVersion_Version_Illegal_Negative()
    {
        message_.setSupportedProtocolVersion( -1 );
    }
}
