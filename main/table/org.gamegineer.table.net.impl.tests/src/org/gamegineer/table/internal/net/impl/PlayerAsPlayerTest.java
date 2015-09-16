/*
 * PlayerAsPlayerTest.java
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
 * Created on Aug 10, 2011 at 8:04:28 PM.
 */

package org.gamegineer.table.internal.net.impl;

import java.util.EnumSet;
import java.util.Set;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.PlayerRole;
import org.gamegineer.table.net.test.AbstractPlayerTestCase;

/**
 * A fixture for testing the {@link Player} class to ensure it does not violate
 * the contract of the {@link IPlayer} interface.
 */
public final class PlayerAsPlayerTest
    extends AbstractPlayerTestCase
{
    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayerAsPlayerTest} class.
     */
    public PlayerAsPlayerTest()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /*
     * @see org.gamegineer.table.net.test.AbstractPlayerTestCase#createPlayer()
     */
    @Override
    protected IPlayer createPlayer()
    {
        return new Player( "name" ); //$NON-NLS-1$
    }

    /*
     * @see org.gamegineer.table.net.test.AbstractPlayerTestCase#setPlayerRoles(org.gamegineer.table.net.IPlayer, java.util.Set)
     */
    @Override
    protected void setPlayerRoles(
        final IPlayer player,
        final Set<PlayerRole> playerRoles )
    {
        final Player typedPlayer = (Player)player;
        typedPlayer.removeRoles( EnumSet.allOf( PlayerRole.class ) );
        typedPlayer.addRoles( playerRoles );
    }
}
