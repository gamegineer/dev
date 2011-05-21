/*
 * PlayersMessage.java
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
 * Created on May 20, 2011 at 8:56:52 PM.
 */

package org.gamegineer.table.internal.net.common.messages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.Collection;
import java.util.Collections;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.transport.AbstractMessage;

/**
 * A message sent by a server to a client to refresh the collection of players
 * connected to the table network.
 */
@NotThreadSafe
public final class PlayersMessage
    extends AbstractMessage
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** Serializable class version number. */
    private static final long serialVersionUID = 9204915904767282462L;

    /**
     * The collection of players connected to the table network.
     * 
     * @serial The collection of players connected to the table network.
     */
    private Collection<String> players_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayersMessage} class.
     */
    public PlayersMessage()
    {
        players_ = Collections.emptyList();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the collection of players connected to the table network.
     * 
     * @return The collection of players connected to the table network; never
     *         {@code null}. The returned value is a direct reference to the
     *         field and must not be modified.
     */
    /* @NonNull */
    public Collection<String> getPlayers()
    {
        return players_;
    }

    /**
     * Sets the collection of players connected to the table network.
     * 
     * @param players
     *        The collection of players connected to the table network; must not
     *        be {@code null}. No copy is made of this value and it must not be
     *        modified at a later time.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code players} is {@code null}.
     */
    public void setPlayers(
        /* @NonNull */
        final Collection<String> players )
    {
        assertArgumentNotNull( players, "players" ); //$NON-NLS-1$

        players_ = players;
    }
}
