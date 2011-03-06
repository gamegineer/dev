/*
 * EchoRequestMessageTest.java
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
 * Created on Mar 5, 2011 at 10:33:55 PM.
 */

package org.gamegineer.table.internal.net.messages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.messages.EchoRequestMessage} class.
 */
public final class EchoRequestMessageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The echo request message under test in the fixture. */
    private EchoRequestMessage message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code EchoRequestMessageTest} class.
     */
    public EchoRequestMessageTest()
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
        message_ = new EchoRequestMessage();
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
     * Ensures the {@code setContent} method throws an exception when passed a
     * {@code null} content.
     */
    @Test( expected = NullPointerException.class )
    public void testSetContent_Content_Null()
    {
        message_.setContent( null );
    }
}
