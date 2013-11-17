/*
 * PlayersMessage.java
 * Copyright 2008-2013 Gamegineer contributors and others.
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

package org.gamegineer.table.internal.net.impl.node.common.messages;

import static org.gamegineer.common.core.runtime.Assert.assertArgumentNotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.jcip.annotations.NotThreadSafe;
import org.gamegineer.table.internal.net.impl.Player;
import org.gamegineer.table.internal.net.impl.transport.AbstractMessage;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.PlayerRole;

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
     * The collection of names of each player connected to the table network.
     * 
     * @serial The collection of names of each player connected to the table
     *         network.
     */
    private List<String> playerNames_;

    /**
     * The collection of roles of each player connected to the table network.
     * 
     * @serial The collection of roles of each player connected to the table
     *         network.
     */
    private List<Set<PlayerRole>> playerRoles_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code PlayersMessage} class.
     */
    public PlayersMessage()
    {
        playerNames_ = Collections.emptyList();
        playerRoles_ = Collections.emptyList();
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the collection of players connected to the table network.
     * 
     * @param localPlayerName
     *        The name of the local player; must not be {@code null}.
     * 
     * @return The collection of players connected to the table network; never
     *         {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code localPlayerName} is {@code null}.
     */
    /* @NonNull */
    public Collection<IPlayer> getPlayers(
        /* @NonNull */
        final String localPlayerName )
    {
        assertArgumentNotNull( localPlayerName, "localPlayerName" ); //$NON-NLS-1$

        assert playerNames_.size() == playerRoles_.size();
        final Collection<IPlayer> players = new ArrayList<>( playerNames_.size() );
        for( int index = 0, size = playerNames_.size(); index < size; ++index )
        {
            final String playerName = playerNames_.get( index );
            final Set<PlayerRole> playerRoles = playerRoles_.get( index );

            if( playerName.equals( localPlayerName ) )
            {
                playerRoles.add( PlayerRole.LOCAL );
            }

            final Player player = new Player( playerName );
            player.addRoles( playerRoles );
            players.add( player );
        }

        return players;
    }

    /**
     * Sets the collection of players connected to the table network.
     * 
     * @param players
     *        The collection of players connected to the table network; must not
     *        be {@code null}.
     * 
     * @throws java.lang.NullPointerException
     *         If {@code players} is {@code null}.
     */
    public void setPlayers(
        /* @NonNull */
        final Collection<IPlayer> players )
    {
        assertArgumentNotNull( players, "players" ); //$NON-NLS-1$

        playerNames_ = new ArrayList<>( players.size() );
        playerRoles_ = new ArrayList<>( players.size() );
        for( final IPlayer player : players )
        {
            final Set<PlayerRole> playerRoles = player.getRoles();
            playerRoles.remove( PlayerRole.LOCAL );

            playerNames_.add( player.getName() );
            playerRoles_.add( playerRoles );
        }
    }
}
