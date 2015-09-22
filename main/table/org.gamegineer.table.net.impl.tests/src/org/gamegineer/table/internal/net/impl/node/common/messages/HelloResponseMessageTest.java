/*
 * HelloResponseMessageTest.java
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
 * Created on Mar 12, 2011 at 9:11:42 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.messages;

import org.eclipse.jdt.annotation.DefaultLocation;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link HelloResponseMessage} class.
 */
@NonNullByDefault( {
    DefaultLocation.PARAMETER, //
    DefaultLocation.RETURN_TYPE, //
    DefaultLocation.TYPE_BOUND, //
    DefaultLocation.TYPE_ARGUMENT
} )
public final class HelloResponseMessageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The hello response message under test in the fixture. */
    private HelloResponseMessage message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code HelloResponseMessageTest} class.
     */
    public HelloResponseMessageTest()
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
        message_ = new HelloResponseMessage();
    }

    /**
     * Ensures the {@link HelloResponseMessage#setChosenProtocolVersion} method
     * throws an exception when passed a negative version.
     */
    @Test( expected = IllegalArgumentException.class )
    public void testSetChosenProtocolVersion_Version_Illegal_Negative()
    {
        message_.setChosenProtocolVersion( -1 );
    }
}
