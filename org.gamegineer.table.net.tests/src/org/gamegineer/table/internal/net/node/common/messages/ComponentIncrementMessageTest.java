/*
 * ComponentIncrementMessageTest.java
 * Copyright 2008-2012 Gamegineer.org
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
 * Created on Jun 30, 2011 at 11:31:15 PM.
 */

package org.gamegineer.table.internal.net.node.common.messages;

import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.common.messages.ComponentIncrementMessage}
 * class.
 */
public final class ComponentIncrementMessageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The component increment message under test in the fixture. */
    private ComponentIncrementMessage message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ComponentIncrementMessageTest}
     * class.
     */
    public ComponentIncrementMessageTest()
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
        message_ = new ComponentIncrementMessage();
    }

    /**
     * Ensures the {@code setIncrement} method throws an exception when passed a
     * {@code null} increment.
     */
    @Test( expected = NullPointerException.class )
    public void testSetIncrement_Increment_Null()
    {
        message_.setIncrement( null );
    }

    /**
     * Ensures the {@code setPath} method throws an exception when passed a
     * {@code null} path.
     */
    @Test( expected = NullPointerException.class )
    public void testSetPath_Path_Null()
    {
        message_.setPath( null );
    }
}
