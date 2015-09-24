/*
 * PlayerTest.java
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
 * Created on Aug 10, 2011 at 8:10:47 PM.
 */

package org.gamegineer.table.internal.net.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.EnumSet;
import java.util.Optional;
import org.gamegineer.table.net.PlayerRole;
import org.junit.Before;
import org.junit.Test;

/**
 * A fixture for testing the {@link Player} class.
 */
public final class PlayerTest
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The player under test in the fixture. */
    private Optional<Player> player_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayerTest} class.
     */
    public PlayerTest()
    {
        player_ = Optional.empty();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the player under test in the fixture.
     * 
     * @return The player under test in the fixture.
     */
    private Player getPlayer()
    {
        return player_.get();
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
        player_ = Optional.of( new Player( "name" ) ); //$NON-NLS-1$
    }

    /**
     * Ensures the {@link Player#addRoles} method adds player roles.
     */
    @Test
    public void testAddRoles()
    {
        final Player player = getPlayer();
        assertEquals( 0, player.getRoles().size() );

        player.addRoles( EnumSet.of( PlayerRole.LOCAL ) );

        assertEquals( 1, player.getRoles().size() );
        assertTrue( player.getRoles().contains( PlayerRole.LOCAL ) );
    }

    /**
     * Ensures the {@link Player#removeRoles} method removes player roles.
     */
    @Test
    public void testRemoveRoles()
    {
        final Player player = getPlayer();
        player.addRoles( EnumSet.of( PlayerRole.LOCAL ) );
        final int originalRolesSize = player.getRoles().size();
        assertTrue( player.getRoles().contains( PlayerRole.LOCAL ) );

        player.removeRoles( EnumSet.of( PlayerRole.LOCAL ) );

        assertEquals( originalRolesSize - 1, player.getRoles().size() );
        assertFalse( player.getRoles().contains( PlayerRole.LOCAL ) );
    }
}
