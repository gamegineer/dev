/*
 * ErrorMessageTest.java
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
 * Created on Apr 28, 2011 at 10:27:57 PM.
 */

package org.gamegineer.table.internal.net.node.common.messages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.common.messages.ErrorMessage}
 * class.
 */
public final class ErrorMessageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The error message under test in the fixture. */
    private ErrorMessage message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ErrorMessageTest} class.
     */
    public ErrorMessageTest()
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
        message_ = new ErrorMessage();
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
     * Ensures the {@code setError} method throws an exception when passed a
     * {@code null} error.
     */
    @Test( expected = NullPointerException.class )
    public void testSetError_Error_Null()
    {
        message_.setError( null );
    }
}
