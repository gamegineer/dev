/*
 * PlayersMessageTest.java
 * Copyright 2008-2012 Gamegineer contributors and others.
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
 * Created on May 20, 2011 at 9:03:28 PM.
 */

package org.gamegineer.table.internal.net.node.common.messages;

import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the
 * {@link org.gamegineer.table.internal.net.node.common.messages.PlayersMessage}
 * class.
 */
public final class PlayersMessageTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The players message under test in the fixture. */
    private PlayersMessage message_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayersMessageTest} class.
     */
    public PlayersMessageTest()
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
        message_ = new PlayersMessage();
    }

    /**
     * Ensures the {@link PlayersMessage#getPlayers} method throws an exception
     * when passed a {@code null} local player name.
     */
    @Test( expected = NullPointerException.class )
    public void testGetPlayers_LocalPlayerName_Null()
    {
        message_.getPlayers( null );
    }

    /**
     * Ensures the {@link PlayersMessage#setPlayers} method throws an exception
     * when passed a {@code null} players collection.
     */
    @Test( expected = NullPointerException.class )
    public void testSetPlayers_Players_Null()
    {
        message_.setPlayers( null );
    }
}
