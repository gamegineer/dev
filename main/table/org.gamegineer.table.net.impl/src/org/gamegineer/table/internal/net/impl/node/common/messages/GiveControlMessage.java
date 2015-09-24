/*
 * GiveControlMessage.java
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
 * Created on Aug 29, 2011 at 8:40:58 PM.
 */

package org.gamegineer.table.internal.net.impl.node.common.messages;

import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.impl.transport.AbstractMessage;

/**
 * A message sent by a node to indicate that the current editor is giving
 * control to another player.
 */
@NotThreadSafe
public final class GiveControlMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 516637895998997340L;

    /**
     * The name of the player to which control is being given.
     * 
     * @serial The name of the player to which control is being given.
     */
    private String playerName_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code GiveControlMessage} class.
     */
    public GiveControlMessage()
    {
        playerName_ = ""; //$NON-NLS-1$
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the name of the player to which control is being given.
     * 
     * @return The name of the player to which control is being given.
     */
    public String getPlayerName()
    {
        return playerName_;
    }

    /**
     * Sets the name of the player to which control is being given.
     * 
     * @param playerName
     *        The name of the player to which control is being given.
     */
    public void setPlayerName(
        final String playerName )
    {
        playerName_ = playerName;
    }
}
