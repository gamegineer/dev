/*
 * Model.java
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
 * Created on Sep 17, 2011 at 11:14:30 PM.
 */

package org.gamegineer.table.internal.ui.impl.dialogs.selectremoteplayer;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import net.jcip.annotations.NotThreadSafe;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.annotation.Nullable;
import org.gamegineer.table.internal.ui.impl.model.TableModel;
import org.gamegineer.table.internal.ui.impl.util.Comparators;
import org.gamegineer.table.internal.ui.impl.util.swing.SortedListModel;
import org.gamegineer.table.net.IPlayer;
import org.gamegineer.table.net.ITableNetwork;

/**
 * The select remote player dialog model.
 */
@NotThreadSafe
final class Model
{
    // ======================================================================
    // Fields
    // ======================================================================

    /** The remote player. */
    private @Nullable IPlayer remotePlayer_;

    /** The table model. */
    private final TableModel tableModel_;


    // ======================================================================
    // Constructors
    // ======================================================================

    /**
     * Initializes a new instance of the {@code Model} class.
     * 
     * @param tableModel
     *        The table model; must not be {@code null}.
     */
    Model(
        final TableModel tableModel )
    {
        remotePlayer_ = null;
        tableModel_ = tableModel;
    }


    // ======================================================================
    // Methods
    // ======================================================================

    /**
     * Gets the remote player.
     * 
     * @return The remote player or {@code null} if not specified.
     */
    public @Nullable IPlayer getRemotePlayer()
    {
        return remotePlayer_;
    }

    /**
     * Gets a validator for the remote player field.
     * 
     * @return A validator for the remote player field; never {@code null}.
     */
    @SuppressWarnings( "static-method" )
    IValidator getRemotePlayerValidator()
    {
        return new IValidator()
        {
            @Override
            public IStatus validate(
                final @Nullable Object value )
            {
                final IPlayer remotePlayer = (IPlayer)value;
                if( remotePlayer == null )
                {
                    return ValidationStatus.error( NlsMessages.Model_remotePlayer_notSelected );
                }

                return ValidationStatus.ok();
            }
        };
    }

    /**
     * Gets the collection of remote players.
     * 
     * @return The collection of remote players; never {@code null}.
     */
    ListModel<IPlayer> getRemotePlayers()
    {
        final ITableNetwork tableNetwork = tableModel_.getTableNetwork();
        final IPlayer localPlayer = tableNetwork.getLocalPlayer();
        final DefaultListModel<IPlayer> remotePlayers = new DefaultListModel<>();
        for( final IPlayer player : tableNetwork.getPlayers() )
        {
            if( player != localPlayer )
            {
                remotePlayers.addElement( player );
            }
        }

        return new SortedListModel<>( remotePlayers, Comparators.PLAYER_BY_NAME );
    }

    /**
     * Sets the remote player.
     * 
     * @param remotePlayer
     *        The remote player or {@code null} if not specified.
     */
    public void setRemotePlayer(
        final @Nullable IPlayer remotePlayer )
    {
        remotePlayer_ = remotePlayer;
    }
}
