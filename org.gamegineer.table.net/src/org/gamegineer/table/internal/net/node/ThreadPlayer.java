/*
 * ThreadPlayer.java
 * Copyright 2008-2013 Gamegineer.org
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
 * Created on Sep 13, 2011 at 7:59:55 PM.
 */

package org.gamegineer.table.internal.net.node;

import net.jcip.annotations.ThreadSafe;

/**
 * A facade for getting and setting the player associated with the current
 * thread.
 */
@ThreadSafe
public final class ThreadPlayer
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The name of the player associated with the current thread. */
    private static final ThreadLocal<String> playerName_ = new ThreadLocal<>();


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code ThreadPlayer} class.
     */
    private ThreadPlayer()
    {
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the name of the player associated with the current thread.
     * 
     * @return The name of the player associated with the current thread or
     *         {@code null} if no player is associated with the current thread.
     */
    /* @Nullable */
    public static String getPlayerName()
    {
        return playerName_.get();
    }

    /**
     * Sets the name of the player associated with the current thread.
     * 
     * @param playerName
     *        The name of the player associated with the current thread or
     *        {@code null} if no player is associated with the current thread.
     */
    public static void setPlayerName(
        /* @Nullable */
        final String playerName )
    {
        playerName_.set( playerName );
    }
}
