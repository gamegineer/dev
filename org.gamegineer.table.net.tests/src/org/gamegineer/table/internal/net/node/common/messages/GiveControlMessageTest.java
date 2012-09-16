/*
 * GiveControlMessageTest.java
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
 * Created on Aug 30, 2011 at 8:19:20 PM.
 */

package org.gamegineer.table.internal.net.node.common.messages;

import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.common.messages.GiveControlMessage}
 * class.
 */
public final class GiveControlMessageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The give control message under test in the fixture. */
    private GiveControlMessage message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GiveControlMessageTest} class.
     */
    public GiveControlMessageTest()
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
        message_ = new GiveControlMessage();
    }

    /**
     * Ensures the {@link GiveControlMessage#setPlayerName} method throws an
     * exception when passed a {@code null} player name.
     */
    @Test( expected = NullPointerException.class )
    public void testSetPlayerName_PlayerName_Null()
    {
        message_.setPlayerName( null );
    }
}
