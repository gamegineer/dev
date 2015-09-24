/*
 * Player.java
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
 * Created on Aug 10, 2011 at 7:54:22 PM.
 */

package org.gamegineer.table.internal.net.impl;

import java.util.EnumSet;
import java.util.Set;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.PlayerRole;

/**
 * Implementation of {@link IPlayer}.
 */
@ThreadSafe
public final class Player
    implements IPlayer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The instance lock. */
    private final Object lock_;

    /** The player name. */
    private final String name_;

    /** The collection of player roles. */
    @GuardedBy( "lock_" )
    private final EnumSet<PlayerRole> roles_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Player} class with the specified
     * name and no roles.
     * 
     * @param name
     *        The player name.
     */
    public Player(
        final String name )
    {
        lock_ = new Object();
        name_ = name;
        roles_ = EnumSet.noneOf( PlayerRole.class );
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Adds the specified roles to this player.
     * 
     * @param roles
     *        The collection of roles to add to this player.
     */
    public void addRoles(
        final Set<PlayerRole> roles )
    {
        synchronized( lock_ )
        {
            roles_.addAll( roles );
        }
    }

    /*
     * @see org.gamegineer.table.net.IPlayer#getName()
     */
    @Override
    public String getName()
    {
        return name_;
    }

    /*
     * @see org.gamegineer.table.net.IPlayer#getRoles()
     */
    @Override
    public Set<PlayerRole> getRoles()
    {
        synchronized( lock_ )
        {
            return EnumSet.copyOf( roles_ );
        }
    }

    /*
     * @see org.gamegineer.table.net.IPlayer#hasRole(org.gamegineer.table.net.PlayerRole)
     */
    @Override
    public boolean hasRole(
        final PlayerRole role )
    {
        synchronized( lock_ )
        {
            return roles_.contains( role );
        }
    }

    /**
     * Removes the specified roles from this player.
     * 
     * @param roles
     *        The collection of roles to remove from this player.
     */
    public void removeRoles(
        final Set<PlayerRole> roles )
    {
        synchronized( lock_ )
        {
            roles_.removeAll( roles );
        }
    }
}
